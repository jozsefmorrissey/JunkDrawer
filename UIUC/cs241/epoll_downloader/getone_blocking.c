/*
  Fetch one web document using blocking I/O calls.
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
#include "http_simple.h"
#include "common.h"


void getWebDocument(const char *url_str, FILE *outf);


int main(int argc, char **argv) {
  const char *url_str, *output_filename;
  URL *url;
  struct sockaddr *address;
  socklen_t address_len;
  int socket_fd;
  FILE *outf;
  char *request;
  size_t request_length, bytes_sent;
  ssize_t byte_count;
  ByteBuffer response;

  if (argc < 2 || argc > 3) {
    fprintf(stderr, "\n  %s <url> [<output_file>]\n\n", argv[0]);
    return 1;
  }

  url_str = argv[1];
  output_filename = argv[2];
  if (output_filename) {
    outf = fopen(output_filename, "w");
    if (!outf) {
      fprintf(stderr, "Cannot open %s for writing.\n", output_filename);
      exit(1);
    }
  } else {
    outf = stdout;
    output_filename = "stdout";
  }

  // parse the URL
  url = URL_parse(url_str);
  if (!url) return 1;

  // resolve the hostname into an IP address
  if (getWebAddress(url->hostname, url->port, &address, &address_len))
    return 1;

  // create the socket
  socket_fd = socket(AF_INET, SOCK_STREAM, 0);
  if (socket_fd == -1) {
    perror("Failed to create socket");
    return 1;
  }

  // connect to the remote host
  if (connect(socket_fd, address, address_len)) {
    perror("Connecting to remote host");
    return 1;
  }

  // build the request message
  HTTP_create_request(url->pathname, url->hostname,
                      &request, &request_length);

  // send the request
  bytes_sent = 0;
  while (bytes_sent < request_length) {
    byte_count = write(socket_fd, request + bytes_sent,
		       request_length - bytes_sent);
    if (byte_count == -1) {
      perror("Error sending request");
      return 1;
    } else {
      fprintf(stderr, "Request: sent %zd of %zu bytes\n", bytes_sent,
	      request_length);
      bytes_sent += byte_count;
    }
  }

  // read the response
  ByteBuffer_init(&response);
  while (1) {
    char chunk[4096];
    byte_count = read(socket_fd, chunk, sizeof(chunk));
    if (byte_count < 0) {
      perror("Error reading response");
      return 1;
    } else if (byte_count == 0) {
      break;
    } else {
      ByteBuffer_append(&response, chunk, byte_count);
      fprintf(stderr, "Read %zd bytes, total of %zu\n",
              byte_count, response.size);
    }
  }

  // split the headers from the content
  WebResult *w = WebResult_parse(response.data, response.size);
  if (!w) return 1;

  byte_count = fwrite(w->content, 1, w->content_len, outf);
  if ((size_t)byte_count != w->content_len) {
    fprintf(stderr, "Error writing web document to a file; "
            "wrote %zd of %zu bytes\n", byte_count, w->content_len);
    return 1;
  }
  fclose(outf);

  fprintf(stderr, "%zu bytes written to %s\n", w->content_len,
          output_filename);

  ByteBuffer_destroy(&response);
  URL_destroy(url);
  WebResult_destroy(w);
  free(request);
  free(address);

  return 0;
}
