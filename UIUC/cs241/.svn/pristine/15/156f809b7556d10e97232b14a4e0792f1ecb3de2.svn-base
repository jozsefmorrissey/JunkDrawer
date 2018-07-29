#include <stdlib.h>
#include <assert.h>
#include <string.h>
#include <stdio.h>
#include <sys/socket.h>
#include <netdb.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <ncurses.h>
#include <errno.h>
#include "download.h"
#include "common.h"

int download_tui = 0;

static void Download_print_tui_update(Download *d);


// Allocate and initialize a Download object.
Download *Download_create(const char *url_str, const char *output_filename,
                          int index) {
  URL *url = URL_parse(url_str);
  if (!url) return NULL;

  FILE *outf = fopen(output_filename, "w");
  if (!outf) {
    fprintf(stderr, "Failed to open %s\n", output_filename);
    return NULL;
  }

  Download *d = (Download*) calloc(1, sizeof(Download));
  assert(d);

  d->index = index;
  d->url_str = strdup(url_str);
  d->url = url;

  d->output_filename = strdup(output_filename);
  d->output_file = outf;
  
  d->state = DL_NOT_STARTED;
  d->socket_fd = -1;

  HTTP_create_request(url->pathname, url->hostname,
                      &d->request, &d->request_len);

  ByteBuffer_init(&d->header_buf);

  d->content_length_state = CL_HEADERS_INCOMPLETE;
  
  return d;
}


/**
 * Do the DNS lookup, and call connect() in nonblocking mode.
 * Returns 0 on success.
 * On error, sets d->error_msg and returns nonzero.
 */
int Download_start(Download *d) {

  // look up the host IP
  struct addrinfo hints, *address;
  memset(&hints, 0, sizeof(struct addrinfo));
  hints.ai_family = AF_INET;
  hints.ai_socktype = SOCK_STREAM;
  char port_str[30];
  sprintf(port_str, "%d", d->url->port);

  Download_print_update(d);
  if (download_tui) refresh();

  int result = getaddrinfo(d->url->hostname, port_str, &hints, &address);
  if (result) {
    asprintf(&d->error_msg, "%s:%d %s\n",
             d->url->hostname, d->url->port, gai_strerror(result));
    d->state = DL_DONE;
    return -1;
  }

  // save the IP address as a string
  struct sockaddr_in *sin = (struct sockaddr_in*) address->ai_addr;
  d->ip_address = strdup(inet_ntoa(sin->sin_addr));

  // make the socket nonblocking
  d->socket_fd = socket(AF_INET, SOCK_STREAM | SOCK_NONBLOCK, 0);
  if (d->socket_fd == -1) {
    asprintf(&d->error_msg, "Failed to create socket: %m");
    d->state = DL_DONE;
    return -1;
  }

  // start the connection
  connect(d->socket_fd, address->ai_addr, address->ai_addrlen);

  freeaddrinfo(address);

  d->state = DL_CONNECTING;

  return 0;
}


// Call this when data is ready to be written or read.
void Download_process_data(Download *d) {
  ssize_t byte_count;
  
  switch (d->state) {
    // if we were waiting to connect then we're now ready to write
    // if we were waiting to write then we're now ready to write
  case DL_CONNECTING:
    d->state = DL_REQUESTING;
  case DL_REQUESTING:

    // write some of the request
    assert(d->request);
    assert(d->request_len > 0);
    assert(d->request_bytes_sent < d->request_len);
    
    byte_count = write(d->socket_fd,
                       d->request + d->request_bytes_sent,
                       d->request_len - d->request_bytes_sent);
    
    if (byte_count == -1) {
      if (errno == EINTR) {
	// ignore interrupts
	break;
      } else if (errno == EAGAIN || errno == EWOULDBLOCK) {
	asprintf(&d->error_msg, "error: write() not ready\n");
      } else {
	asprintf(&d->error_msg, "Send request error: %m");
      }
      d->state = DL_DONE;
    } else {
      
      d->request_bytes_sent += byte_count;
      
      // if we're done writing, start reading
      if (d->request_bytes_sent == d->request_len) {
        d->state = DL_DOWNLOADING;
      }
      
    }
    break;
    
    // data is ready to be read
  case DL_DOWNLOADING:
    {
      char readbuf[4*1024];
      byte_count = read(d->socket_fd, readbuf, sizeof(readbuf));
      
      if (byte_count == -1) {
	if (errno == EINTR) {
	  // ignore interrupts
	  break;
	} else if (errno == EAGAIN || errno == EWOULDBLOCK) {
	  asprintf(&d->error_msg, "error: read() not ready\n");
	} else {
	  asprintf(&d->error_msg, "Read error: %m\n");
	}
        d->state = DL_DONE;
      } else if (byte_count > 0) {
	int headers_were_complete = Download_are_headers_complete(d);

        // More data has been received.
        // Add it to the header buffer or the data file or both.
        Download_add_bytes(d, readbuf, byte_count);

	// if the headers were just completed, get the HTTP status code
	if (!headers_were_complete && Download_are_headers_complete(d)) {
	  d->http_status_code = Download_get_HTTP_code
	    (d, &d->http_reason_phrase);
	  if (d->http_status_code > 0 &&
	      d->http_status_code != HTTP_OK) {
	    d->error_msg = strdup(d->http_reason_phrase);
	    d->state = DL_DONE;
	  }
	}
	  
      } else {
        // EOF
        d->content_length = d->content_bytes_received;
        d->content_length_state = CL_FOUND;
        d->state = DL_DONE;
        fclose(d->output_file);
        d->output_file = NULL;

        // Download_write_output(d);
        
      }
    }
    break;

    // Download_process_data should not be called on an object in any of these states
  case DL_DONE:
    Download_fail(d, "Download_process_data invalid when DL_DONE");
    break;

  case DL_NOT_STARTED:
    Download_fail(d, "Download_process_data invalid when DL_NOT_STARTED");
    break;
  }
}        


// New data received - add it to the header buffer or the output file
void Download_add_bytes(Download *d, char *data, size_t data_size) {
  size_t write_size;

  // Until the headers are complete, add incoming data to the header buffer
  if (!Download_are_headers_complete(d)) {

    ByteBuffer_append(&d->header_buf, data, data_size);

    // if this just completed the headers, move the leftovers to the
    // data file
    if (Download_are_headers_complete(d)) {
      size_t leftover_size = d->header_buf.size - d->headers_length;
      write_size = fwrite(d->header_buf.data + d->headers_length,
                          1, leftover_size, d->output_file);
      if (write_size != leftover_size) {
        Download_fail(d, "Failed to write output file");
      }
      d->header_buf.size = d->headers_length;
      d->content_bytes_received = leftover_size;
    }
  }

  // headers are complete; send the data directly to the output file
  else {
    write_size = fwrite(data, 1, data_size, d->output_file);
    if (write_size != data_size) {
      Download_fail(d, "Failed to write output file");
    }
    d->content_bytes_received += data_size;
  }
}


// Returns nonzero if all the headers have been received
int Download_are_headers_complete(Download *d) {
  return Download_get_headers_length(d) != 0;
}


// Returns the number of bytes used by the headers.
// If the headers are incomplete, returns 0.
size_t Download_get_headers_length(Download *d) {
  // return cached value
  if (d->headers_length) return d->headers_length;

  const char *start = d->header_buf.data;
  const char *pos = start;
  const char *end = d->header_buf.data + d->header_buf.size;
  const char *line_start, *line_end;

  // A blank line marks the end of the headers, so if a blank line is found,
  // the headers are complete.
  while (nextLine(&pos, end, &line_start, &line_end)) {
    if (line_end == line_start) {
      // empty line found; end of headers
      d->headers_length = pos - start;
      return d->headers_length;
    }
  }
  return 0;
}


// If the headers are complete, returns the HTTP status code
// and sets *reason_phrase to the text description of that code.
// If the headers are incomplete, behavior is undefined.
int Download_get_HTTP_code(Download *d, char **reason_phrase) {
  const char *pos = d->header_buf.data;
  const char *line_start, *line_end;

  *reason_phrase = NULL;

  // find the first line of the headers
  if (!nextLine(&pos, d->header_buf.data + d->header_buf.size,
                &line_start, &line_end))
    return 0;

  char *line = substring(line_start, line_end - line_start);

  int status_code, phrase_offset;
  if (sscanf(line, "%*s %d %n", &status_code, &phrase_offset) < 1)
    return 0;

  *reason_phrase = strdup(line + phrase_offset);
  free(line);

  return status_code;
}


// Returns the value of the Content-Length header, if found.
// If Content-Length is missing, invalid, or not yet received, returns 0.
size_t Download_get_expected_length(Download *d) {
  const char *pos, *start, *end;
  const char *line_start, *line_end;

  // return cached value
  if (d->content_length_state == CL_FOUND) {
    return d->content_length;
  } else if (d->content_length_state == CL_NOT_FOUND) {
    return 0;
  }
  
  // A blank line marks the end of the headers, so if a blank line is found,
  // the headers are complete.
  start = pos = d->header_buf.data;
  end = d->header_buf.data + d->header_buf.size;
  while (nextLine(&pos, end, &line_start, &line_end)) {

    // end of headers; Content-Length isn't found and never will be
    if (line_end == line_start) {
      d->headers_length = pos - start;
      d->content_length_state = CL_NOT_FOUND;
      return 0;
    }
    
    if (!strncmp(line_start, "Content-Length:", 15)) {
      char *len_str = substring(line_start+15, line_end-line_start-15);
      size_t len;
      int nconv = sscanf(len_str, " %zu", &len);
      free(len_str);
      if (1 == nconv) {
        d->content_length_state = CL_FOUND;
        d->content_length = len;
      } else {
        d->content_length_state = CL_NOT_FOUND;
        d->content_length = 0;
      }
      return d->content_length;
    }
  }

  return 0;
}


/**
 * Returns the fraction of the content that has been downloaded, as
 * a float between 0 and 100.
 * If the percentage is unknown, returns 0.
 */
float Download_get_percent_done(Download *d) {
  size_t content_length = Download_get_expected_length(d);
  if (d->content_bytes_received == 0 || content_length == 0) return 0;

  return 100.0f * d->content_bytes_received / content_length;
}


// On fatal error, call this with the error message and it will
// set d->error_msg and change the status to DL_DONE.
void Download_fail(Download *d, const char *error_msg) {
  free(d->error_msg);
  d->error_msg = strdup(error_msg);
  d->state = DL_DONE;
}


void Download_print_update(Download *d) {
  if (download_tui) {
    Download_print_tui_update(d);
    return;
  }

  size_t size = Download_get_expected_length(d);
  size_t recvd = d->content_bytes_received;

  printf("%-25.25s ", d->output_filename);

  if (d->error_msg) {
    printf("ERROR: %s\n", d->error_msg);
  }

  else if (d->state == DL_NOT_STARTED) {
    if (d->ip_address) {
      printf("connecting to %s\n", d->ip_address);
    } else {
      printf("looking up IP address for %s\n", d->url->hostname);
    }
  }

  else if (d->state == DL_CONNECTING) {
    printf("connecting to %s\n", d->ip_address);
  }

  else if (d->state == DL_REQUESTING) {
    printf("send request %zu of %zu bytes\n", d->request_bytes_sent,
	   d->request_len);
  }

  else if (size > 0) {
    float pct_done = Download_get_percent_done(d);
    printf("%6.2f%%: %9zu of %9zu bytes\n", pct_done, recvd, size);
  } else {
    if (recvd == 0) {
      printf("send request %zu of %zu bytes\n", d->request_bytes_sent,
	     d->request_len);
    } else {
      printf("%9zu bytes\n", recvd);
    }
  }
}


void Download_print_tui_update(Download *d) {
  size_t size = Download_get_expected_length(d);
  size_t recvd = d->content_bytes_received;
  int width = getmaxx(stdscr);

  mvprintw(d->index, 0, "%-25.25s ", d->output_filename);

  if (d->error_msg) {
    printw("ERROR: %s\n", d->error_msg);
    mvchgat(d->index, 0, -1, A_NORMAL, 1, NULL);
  }

  else if (d->state == DL_NOT_STARTED) {
    if (d->ip_address) {
      printw("connecting to %s\n", d->ip_address);
    } else {
      printw("looking up IP address for %s\n", d->url->hostname);
    }
  }
  
  else if (d->state == DL_CONNECTING) {
    printw("connecting to %s\n", d->ip_address);
  }

  else if (d->state == DL_REQUESTING) {
    printw("send request %zu of %zu bytes\n", d->request_bytes_sent,
	   d->request_len);
  }

  else if (size > 0) {
    float pct_done = Download_get_percent_done(d);
    printw("%6.2f%%: %9zu of %9zu bytes\n", pct_done, recvd, size);

    // highlight the beginning of the row to do a progress-bar effect
    int chars_lit = pct_done / 100 * width + 0.5f;
    mvchgat(d->index, 0, chars_lit, A_REVERSE, 2, NULL);
    
  } else {
    if (recvd == 0) {
      printw("send request %zu of %zu bytes\n", d->request_bytes_sent,
	     d->request_len);
    } else {
      printw("         %9zu bytes\n", recvd);
    }
  }
}


// Deallocate Download object and its contents.
void Download_destroy(Download *d) {
  URL_destroy(d->url);
  free(d->url_str);
  free(d->request);
  free(d->output_filename);
  if (d->output_file)
    fclose(d->output_file);
  free(d->ip_address);
  ByteBuffer_destroy(&d->header_buf);
  free(d->error_msg);
  free(d->http_reason_phrase);
  free(d);
}
