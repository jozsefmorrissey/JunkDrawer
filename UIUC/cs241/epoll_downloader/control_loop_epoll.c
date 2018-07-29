#include <stdio.h>
#include <time.h>
#include <errno.h>
#include <ncurses.h>
#include <assert.h>
#include <unistd.h>
#include <sys/epoll.h>
#include "download.h"


// number of seconds to wait after each action
extern float TIMEOUT_SEC;

// flag that says whether we're using curses full-screen display or not
extern int TUI;

// Call this before each call to epoll_wait() to slow down the program.
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
 * It wait for each of the downloads to finish, using epoll to
 * handle all of them simultaneously.  See control_loop_select.c
 * for an implementation using select().
 *
 * downloads - an array of pointers to Download objects
 * download_count - the size of the downloads[]
 *
 * Each download object has a state. See DownloadState in download.h.
 * They will all be started by the time this is called, but they may have failed,
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
 *   Feel free to borrow code from control_loop_select.c (see
 *   the "timeout - mark downloads failed" comment), but you'll need
 *   to figure out how to time out after TIMEOUT_SEC seconds with
 *   epoll_wait().
 */
void controlLoop(int download_count, Download *downloads[]) {

  // Set this to the number of milliseconds to wait in each call to delay().
  // You may wish to set this to slow down the program during debugging.
  delay_ms = 0;

  // add epoll initialization code here  
  int timeout = 2;

  int epfd = epoll_create(1);
  struct epoll_event events[1];

  for(int i = 0; i < download_count; i++){
	    struct epoll_event event;
		event.data.ptr = downloads[i];
		assert(event.data.ptr != DL_NOT_STARTED);
		
		if(downloads[i]->state != DL_DONE){
			event.events = EPOLLIN | EPOLLOUT;
			epoll_ctl(epfd, EPOLL_CTL_ADD, downloads[i]->socket_fd, &event);
		}
  }	
		

  while (checkForQuit()) {
	if(download_count == 0)
		break;
    delay();

    // add other epoll stuff here
    //int finished = 0;//, max_fd = 0;
	int ready = epoll_wait(epfd, &events[0], 1, timeout);
	if(ready == -1){
		if(errno == EINTR) continue;
		fprintf(stderr, "epoll() failed with code %d: %m", errno);
		return;
	}	
	else if(ready == 0){
		for(int i = 0; i < download_count; i++){
			Download *d = downloads[i];

			switch (d->state){
				case DL_CONNECTING:
          			Download_fail(d, "timeout connecting");
					break;

				case DL_REQUESTING:
		        	Download_fail(d, "timeout sending request");
					break;

				case DL_DOWNLOADING:
         			Download_fail(d, "timeout receiving data");
					break;
				case DL_NOT_STARTED:
					break;
				case DL_DONE:
					break;

			}
			Download_print_update(d);
		}
     if (TUI) {
        refresh();
      } else {
        fprintf(stderr, "Timeout with %d incomplete downloads.\n", download_count);
      }
      break;
	  }
	  else{
 
			Download * e = (Download*) (events[0].data.ptr);

			int num_handled = 0;
			for (int i=0; i < ready; i++) {
			  Download *d = e + i;

			  switch (d->state) {

				// if this socket is ready for writing, then either the connection
				// has completed, or the previous write completed. Either way,
				// the next thing to do is to write another chunk of the request.
			  case DL_CONNECTING:
			  case DL_REQUESTING:
				if(events[i].events & EPOLLOUT){

				  num_handled++;
				  Download_process_data(d);
				  Download_print_update(d);

				}
				break;

				// if this socket is ready for reading, then another chunk
				// of data has arrived
			  case DL_DOWNLOADING:
				if(events[i].events & EPOLLIN){
				  num_handled++;
				  Download_process_data(d);
				  Download_print_update(d);

				}
				break;

			  case DL_NOT_STARTED:
			  case DL_DONE:
				// do nothing
					download_count--;
					epoll_ctl(epfd, EPOLL_CTL_DEL, d->socket_fd, NULL);
					break;
			  }
			}    
			if (TUI) refresh();
	  
    }

  }

    
    if (TUI) refresh();

  // add epoll shutdown code here
	close(epfd);
}























