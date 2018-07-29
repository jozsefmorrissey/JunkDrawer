#include <stdio.h>
#include <time.h>
#include <errno.h>
#include <ncurses.h>
#include <assert.h>
#include "download.h"


// number of seconds to wait after each action
extern float TIMEOUT_SEC;

// flag that says whether we're using curses full-screen display or not
extern int TUI;

// Call this before each call to select() to slow down the program.
// Set delay_ms to the nubmer of milliseconds you want it to sleep.
extern void delay();
extern int delay_ms;

// Returns zero if the user has terminated the run, otherwise nonzero.
int checkForQuit();

// useful macro
#define MAX(a,b) ((a)>(b)?(a):(b))


/**
 * This is the main loop.
 * 
 * It wait for each of the downloads to finish, using select() to
 * handle all of them simultaneously.
 *
 * downloads - an array of pointers to Download objects
 * download_count - the size of the downloads[]
 *
 * Each download object has a state. See DownloadState in download.h.
 * They will all be started by the time this is call, but they may have failed,
 * so they will be either in the DL_CONNECTING or DL_DONE state.
 *
 * 1. DL_CONNECTING.  connect() has been called, but the connection has
 *   has not been established yet. You'll know it's established when the
 *   socket is ready for writing.
 *
 *   When the connection is established, call Download_process_data()
 *   to start sending the request.
 *
 * 2. DL_REQUESTING.  The connection is established and we're sending
 *   the request to the server. If the request is long, it may take
 *   a few calls to write() to send it all. You'll know you can send the
 *   next chunk when the socket is ready for writing.
 *
 *   When the socket is ready to send another chunk, call
 *   Download_process_data() to send the next chunk of the request.
 *
 * 3. DL_DOWNLOADING.  The request has been sent and we're waiting for the
 *   response. You'll know a chunk of data has arrived when the socket
 *   is ready for reading.
 *
 *   When a chunk of data has arrived, call Download_process_data() to
 *   read it into our buffer.
 *
 * 4. DL_DONE.  Either an error was encountered or all the data has been
 *   received. No more work is necessary for this socket.
 *
 * Yes, Download_process_data() is the only function you need to call
 * when data is ready. Take a look at its implementation if download.c
 * if you like, but you shouldn't have to change it.
 *
 * Timeout - if everything works well, the data will arrive in a
 *   timely manner, and all downloads will progress to the DL_DONE
 *   state.  However, we're dealing with the Internet, and it often
 *   does not work well. If TIMEOUT_SEC seconds elapse without any
 *   activitiy on any download that is not DL_DONE, call
 *   Download_fail() on all the downloads that are not done.
 *
 *   This is already implemented (see the "timeout - mark downloads
 *   failed" comment), but you'll need to figure out how to time out
 *   after TIMEOUT_SEC seconds with epoll().
 */
void controlLoop(int download_count, Download *downloads[]) {
  fd_set read_fds, write_fds;
  struct timeval timeout;

  // Set this to the number of milliseconds to wait in calls to delay().
  // You may wish to set this to slow down the program during debugging.
  delay_ms = 0;

  while (checkForQuit()) {
    
    timeout.tv_sec = (int)TIMEOUT_SEC;
    timeout.tv_usec = (int)((TIMEOUT_SEC - timeout.tv_sec) * 1000000);

    FD_ZERO(&read_fds);
    FD_ZERO(&write_fds);

    int pending = 0, max_fd = 0;
    for (int i=0; i < download_count; i++) {
      Download *d = downloads[i];
      assert(d->state != DL_NOT_STARTED);
      
      if (d->state != DL_DONE) {
        pending++;
        max_fd = MAX(max_fd, d->socket_fd);
      }
      
      switch (d->state) {

        // when waiting for connect() or write(), add this socket to the
        // set of write fd's
      case DL_CONNECTING:
      case DL_REQUESTING:
        FD_SET(d->socket_fd, &write_fds);
        break;

        // when waiting for read(), add this socket to the set of read fd's
      case DL_DOWNLOADING:
        FD_SET(d->socket_fd, &read_fds);
        break;

      case DL_NOT_STARTED:
      case DL_DONE:
        // do nothing
        d->state = d->state;
      }

    }

    // no more work to do
    if (pending == 0) break;
    
    // wait for something to happen 
    delay();
    int num_ready = select(max_fd+1, &read_fds, &write_fds, NULL, &timeout);
    if (num_ready == -1) {
      // if select() was interrupted, ignore it
      if (errno == EINTR) continue;
      fprintf(stderr, "select() failed with code %d: %m", errno);
      return;
    } else if (num_ready == 0) {

      // timeout - mark downloads failed
      for (int i=0; i < download_count; i++) {
        Download *d = downloads[i];
        if (d->state == DL_CONNECTING) {
          Download_fail(d, "timeout connecting");
        } else if (d->state == DL_REQUESTING) {
          Download_fail(d, "timeout sending request");
        } else if (d->state == DL_DOWNLOADING) {
          Download_fail(d, "timeout receiving data");
        }
	Download_print_update(d);
      }
      if (TUI) {
        refresh();
      } else {
        fprintf(stderr, "Timeout with %d incomplete downloads.\n", pending);
      }
      break;
    }

    int num_handled = 0;
    for (int i=0; num_handled < num_ready && i < download_count; i++) {
      Download *d = downloads[i];

      switch (d->state) {

        // if this socket is ready for writing, then either the connection
        // has completed, or the previous write completed. Either way,
        // the next thing to do is to write another chunk of the request.
      case DL_CONNECTING:
      case DL_REQUESTING:
        if (FD_ISSET(d->socket_fd, &write_fds)) {
          num_handled++;
          Download_process_data(d);
	  Download_print_update(d);
        }
        break;

        // if this socket is ready for reading, then another chunk
        // of data has arrived
      case DL_DOWNLOADING:
        if (FD_ISSET(d->socket_fd, &read_fds)) {
          num_handled++;
          Download_process_data(d);
          Download_print_update(d);
        }
        break;

      case DL_NOT_STARTED:
      case DL_DONE:
        // do nothing
        break;
      }
    }
    
    if (TUI) refresh();
  }

  // all done

}

