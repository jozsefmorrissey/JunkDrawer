/*
  Fetch one web document using nonblocking I/O calls and select().

  This demonstrates the use of select(), poll(), and epoll().

  Change the #define for waitForIO to switch between the three versions.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <assert.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <poll.h>
#include <time.h>
#ifndef __CYGWIN__
#include <sys/epoll.h>
#endif

#include "http_simple.h"
#include "common.h"


#define TIMEOUT_SEC 5
#define TIMEOUT_USEC 0

double getTime();
FILE *openOrDie(const char *filename, const char *mode);

/**
 * If is_read, wait for input data on fd.
 * If not is_read, wait for write to be ready on fd.
 * Returns 0 on timeout, nonzero on success.
 */
int waitForIOSelect(int fd, int is_read);  // use select()
int waitForIOPoll(int fd, int is_read);    // use poll()
int waitForIOEpoll(int fd, int is_read);   // use epoll()

// set this to any of the three functions above
#define waitForIO waitForIOSelect


int main(int argc, char **argv) {
  const char *url_str, *output_filename = "stdout",
    *header_filename = NULL, *request_filename = NULL;
  URL *url;
  struct sockaddr *address;
  socklen_t address_len;
  int socket_fd;
  FILE *outf = stdout, *header_outf = NULL, *request_outf = NULL;
  char *request;
  size_t request_length, bytes_sent;
  ssize_t byte_count;
  ByteBuffer response;
  double start_time;

  // check args
  if (argc < 2 || argc > 5) {
    fprintf(stderr,
            "\n"
            "  %s <url> [<output_file> [headers [request]]]\n"
            "  Reqeust a document from a web server.\n"
            "    output_file - the file to which the conent will be written\n"
            "      if omitted, the content will be sent to stdout.\n"
            "    headers - if present, the HTTP response headers will be\n"
            "      written to this file\n"
            "    request - if present, this HTTP request will be written\n"
            "      to this file.\n"
            "\n", argv[0]);
    return 1;
  }

  url_str = argv[1];

  // open the output file
  output_filename = argv[2];
  if (argc > 2) {
    outf = openOrDie(output_filename, "w");
    if (argc > 3) {
      header_filename = argv[3];
      header_outf = openOrDie(header_filename, "w");
      if (argc > 4) {
        request_filename = argv[4];
        request_outf = openOrDie(request_filename, "w");
      }          
    }
  }

  // parse the URL
  url = URL_parse(url_str);
  if (!url) return 1;

  // resolve the hostname into an IP address
  if (getWebAddress(url->hostname, url->port, &address, &address_len))
    return 1;

  // create a nonblocking socket
  socket_fd = socket(AF_INET, SOCK_STREAM | SOCK_NONBLOCK, 0);
  if (socket_fd == -1) {
    perror("Failed to create socket");
    return 1;
  }

  // initialize the connection to the remote host
  if (connect(socket_fd, address, address_len)) {
    int err = errno;
    if (err == EINPROGRESS) {

      start_time = getTime();
      if (!waitForIO(socket_fd, 0)) {
        fprintf(stderr, "Timeout waiting to connect.\n");
        exit(1);
      }
      fprintf(stderr, "Connected to %s:%d after %.2f ms\n", 
	      url->hostname, url->port, (getTime() - start_time) * 1000);

    } else {
      fprintf(stderr, "Connecting to remote host: %m");
      return 1;
    }
  }

  // build the request message
  HTTP_create_request(url->pathname, url->hostname,
                      &request, &request_length);

  if (request_outf) {
    byte_count = fwrite(request, 1, request_length, request_outf);
    fprintf(stderr, "%zd bytes written to %s\n", byte_count, request_filename);
    fclose(request_outf);
  }
                        
  
  // send the request
  bytes_sent = 0;
  while (1) {
    byte_count = write(socket_fd, request + bytes_sent,
                       request_length - bytes_sent);
    if (byte_count == -1) {
      perror("Error sending request");
      return 1;
    } else {
      bytes_sent += byte_count;
      fprintf(stderr, "Request: sent %zd of %zu bytes\n", bytes_sent,
	      request_length);

      // stop sending chunks if the whole request has been sent
      if (bytes_sent == request_length) break;

      start_time = getTime();
      if (!waitForIO(socket_fd, 0)) {
        fprintf(stderr, "Timeout waiting to write.\n");
	return 1;
      }
      fprintf(stderr, "Write after %.2f ms\n", 
	      (getTime() - start_time) * 1000);

    }
  }                       

  // read the response
  ByteBuffer_init(&response);
  while (1) {
    char chunk[4096];

    start_time = getTime();
    if (!waitForIO(socket_fd, 1)) {
      fprintf(stderr, "Timeout waiting to read.\n");
      exit(1);
    }
    double ms = (getTime() - start_time) * 1000;
    
    byte_count = read(socket_fd, chunk, sizeof(chunk));
    if (byte_count < 0) {
      perror("Error in read()");
      return 1;
    } else if (byte_count == 0) {
      break;
    } else {
      
      ByteBuffer_append(&response, chunk, byte_count);
      fprintf(stderr, "Read %zd bytes after %.2f ms, total of %zu\n",
              byte_count, ms, response.size);
    }
  }

  // split the headers from the content
  WebResult *w = WebResult_parse(response.data, response.size);
  if (!w) return 1;

  // write the headers to their own file
  if (header_outf) {
    size_t header_len = response.size - w->content_len;
    fwrite(response.data, 1, header_len, header_outf);
    fclose(header_outf);
    fprintf(stderr, "%zu bytes written to %s\n", header_len, header_filename);
  }

  byte_count = fwrite(w->content, 1, w->content_len, outf);
  if ((size_t)byte_count != w->content_len) {
    fprintf(stderr, "Error writing web document to a file: "
            "wrote %zd of %zu bytes\n", byte_count, w->content_len);
    return 1;
  }
  fclose(outf);

  if (outf != stdout) {
    fprintf(stderr, "%zu bytes written to %s\n", w->content_len,
            output_filename);
  }

  ByteBuffer_destroy(&response);
  URL_destroy(url);
  WebResult_destroy(w);
  free(request);
  free(address);

  return 0;
}


/* Returns a "wall clock" timer value in seconds.
   This timer keeps ticking even when the thread or process is idle. */
double getTime() {
  struct timespec t;
  clock_gettime(CLOCK_MONOTONIC, &t);
  return t.tv_sec + 1e-9 * t.tv_nsec;
}


FILE* openOrDie(const char *filename, const char *mode) {
  FILE *f = fopen(filename, mode);
  if (!f) {
    fprintf(stderr, "Failed to open \"%s\"\n", filename);
    exit(1);
  }
  return f;
}


/**
 * If is_read, wait for input data on fd.
 * If not is_read, wait for write to be ready on fd.
 * Returns 0 on timeout, nonzero on success.
 */

// use select()
int waitForIOSelect(int fd, int is_read) {
  fd_set fd_set, *read_set = NULL, *write_set = NULL;
  struct timeval timeout;
  int num_ready;

  // fd_set represents a set of file descriptors in which we're interested.
  // Initialize them all to 0, then set the one that we want.
  FD_ZERO(&fd_set);
  FD_SET(fd, &fd_set);

  timeout.tv_sec = TIMEOUT_SEC;
  timeout.tv_usec = TIMEOUT_USEC;

  /* select() takes three sets of file descriptors:
      - files that are being read
      - files that are being written
      - files that are being monitored for exceptions

     Based on is_read, we're either reading from or writing to fd.
  */

  if (is_read) {
    read_set = &fd_set;
  } else {
    write_set = &fd_set;
  }

  // wait for something to happen to the file descriptor
  num_ready = select(fd+1, read_set, write_set, NULL, &timeout);
  if (num_ready == -1) {
    perror("select() failed");
    exit(1);
  }

  return num_ready;
}


// use poll()
int waitForIOPoll(int fd, int is_read) {

  // Create a pollfd object that describes which file descriptor we're
  // interested in and whether we're reading from or writing to it.
  struct pollfd pfd;
  pfd.fd = fd;
  pfd.events = is_read ? POLLIN : POLLOUT;

  int millis = (int)(TIMEOUT_SEC * 1000 + TIMEOUT_USEC / 1000.0);

  // wait for something to happen
  int result = poll(&pfd, 1, millis);

  if (result == -1) {
    perror("Waiting for IO");
    exit(1);
  }
  return result;
}

#ifndef __CYGWIN__

// use epoll()
int waitForIOEpoll(int fd, int is_read) {
  // epoll-create, epoll_ctl, epoll-wait

  struct epoll_event event;

  // create a new file descriptor managed by the epoll functions
  int epoll_fd = epoll_create(1);
  if (epoll_fd < 0) {
    perror("epoll_create failed");
    exit(1);
  }

  event.events = is_read ? EPOLLIN : EPOLLOUT;
  event.data.ptr = 0;
  
  /* event.data is a union that holds whatever data the user wants.
     When epoll_wait selects that event, event.data will be unchanged.
     For example, to use this to store a pointer:
       event.data.ptr = my_pointer;
     To store a 32-bit index:
       event.data.u32 = my_index;
  */

  // add this event to the epoll file descriptor
  if (epoll_ctl(epoll_fd, EPOLL_CTL_ADD, fd, &event)) {
    perror("epoll_ctl() failed");
    exit(1);
  }

  int millis = (int)(TIMEOUT_SEC * 1000 + TIMEOUT_USEC / 1000.0);

  // wait for something to happen
  int num_ready = epoll_wait(epoll_fd, &event, 1, millis);
  close(epoll_fd);

  if (num_ready < 0) {
    perror("epoll_wait() failed");
    exit(1);
  } else {
    // printf("num_ready %d, %d %lx\n", num_ready, (int)event.events, event.data.u64);
    return num_ready;
  }
}

#endif // __CYGWIN__

    
