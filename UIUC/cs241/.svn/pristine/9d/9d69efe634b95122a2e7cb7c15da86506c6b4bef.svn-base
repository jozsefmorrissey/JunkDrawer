#ifndef __DOWNLOAD_H__
#define __DOWNLOAD_H__

#include <sys/types.h>
#include "http_simple.h"
#include "common.h"

// Track the progress of a download
enum DownloadState {
  DL_NOT_STARTED,
  DL_CONNECTING,    // connect initiated, wait for write activity
  DL_REQUESTING,    // write of request initiated, wait for write activity
  DL_DOWNLOADING,   // read of result initiated, wait for read activity
  DL_DONE
};

// Track the progress in finding the "Content-Length" header, which is 
// how we know how large the download should be. We need this to compute
// what percentage of the download has been completed.
enum ContentLengthState {
  CL_HEADERS_INCOMPLETE,  // Not all the headers have been received yet,
                          // and we won't look for Content-Length until
                          // they are.

  CL_NOT_FOUND,           // Headers are complete, but Content-Length was
                          // missing, so the length is unknown.

  CL_FOUND                // Content-Length was found
};


typedef struct {
  int index;
  URL *url;
  char *url_str;
  char *output_filename;
  FILE *output_file;
  char *ip_address;
  enum DownloadState state;

  int socket_fd;

  char *request;
  size_t request_len, request_bytes_sent;

  ByteBuffer header_buf;

  size_t headers_length;
  enum ContentLengthState content_length_state;
  size_t content_length;   // expected content length; this is only valid
                           // if content_length_state == CL_FOUND
  size_t content_bytes_received;

  int http_status_code;
  char *http_reason_phrase;

  char *error_msg;         // set this if there was an error
} Download;

extern int download_tui;

// Allocate and initialize a Download object.
// Returns NULL if url_str cannot be parsed.
Download *Download_create(const char *url_str, const char *output_filename,
                          int index);

// Do the DNS lookup, and call connect() in nonblocking mode.
int Download_start(Download *d);

// Call this when data is ready to be written or read.
void Download_process_data(Download *d);

// New data received - add it to the header buffer or the output file
void Download_add_bytes(Download *d, char *data, size_t data_size);

// Returns nonzero if all the headers have been received
// If this returns true, d->headers_length is set.
int Download_are_headers_complete(Download *d);

// Returns the number of bytes used by the headers.
// If the headers are incomplete, returns 0.
size_t Download_get_headers_length(Download *d);

// If the headers are complete, returns the HTTP status code
// and sets *reason_phrase to the text description of that code.
// If the headers are incomplete, behavior is undefined.
int Download_get_HTTP_code(Download *d, char **reason_phrase);

// Returns the value of the Content-Length header, if found.
// If Content-Length is missing or not yet received, returns 0.
size_t Download_get_expected_length(Download *d);

/**
 * Returns the fraction of the content that has been downloaded, as
 * a float between 0 and 100.
 * If the percentage is unknown, returns 0.
 */
float Download_get_percent_done(Download *d);

// Write the content to the output file.
// On success, return the number of bytes in the content.
// On error, set d->error_msg and return -1.
ssize_t Download_write_output(Download *d);

// On fatal error, call this with the error message and it will
// set d->error_msg and change the status to DL_DONE.
void Download_fail(Download *d, const char *error_msg);

void Download_print_update(Download *d);

// Deallocate Download object and its contents.
void Download_destroy(Download *d);

#endif /* __DOWNLOAD_H__ */
