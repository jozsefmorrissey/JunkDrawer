#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <assert.h>
#include "http_simple.h"
#include "common.h"


/**
 * Parses the given string as a URL.
 * If successful, returns a URL object. All fields will always be set.
 * If missing, protocol will be "http".
 * If missing, port will be 80.
 * If missing, pathname will be "/".
 *
 * http_parser_parse_url() from libhttp_parser would be useful, but it
 * is too strict. For example, it fails on "www.google.com", since the
 * "http://" is missing.
 *
 * Returns NULL and prints error message to stderr if the URL string
 * cannot be parsed.
 */
URL *URL_parse(const char *url_str_orig) {
  const char *err_msg = NULL;
  int chars_eaten;

  URL *url = (URL*) malloc(sizeof(URL));
  url->hostname = url->pathname = NULL;
  url->port = 80;

  const char *url_str = url_str_orig;

  // check that the protocol (everything before "://") is either omitted
  // or is http
  const char *protocol_end = strstr(url_str, "://");
  if (protocol_end) {
    if (strncmp(url_str, "http://", 7)) {
      err_msg = "unrecognized protocol (only http supported)";
      goto fail;
    } else {
      url_str = protocol_end + 3;
    }
  }

  // extract the hostname
  const char *end_hostname = strpbrk(url_str, ":/");
  // if no colon and no slash, then there is no port number or file path
  if (!end_hostname) {
    url->hostname = strdup(url_str);
    url_str += strlen(url_str);
  } else {
    url->hostname = substring(url_str, end_hostname - url_str);
    url_str = end_hostname;
  }
  
  // now we're at the char after the hostname

  // check if there's a port number
  if (*url_str == ':') {
    // check for at least one successful conversion (the %d), because
    // %n may or may not count
    if (1 > sscanf(url_str, ":%d%n", &url->port, &chars_eaten)) {
      err_msg = "invalid port number";
      goto fail;
    }
    url_str += chars_eaten;
  }

  // now we should be at the filename

  // if it's missing just use "/"
  if (url_str[0] == 0) {
    url->pathname = strdup("/");
  } else {
    if (url_str[0] != '/') {
      err_msg = "no path name found";
      goto fail;
    } else {
      url->pathname = strdup(url_str);
    }
  }

  /*
  printf("%s\n  %s\n  %d\n  %s\n",
	 url_str_orig, url->hostname, url->port, url->pathname);
  */

  return url;

 fail:
  fprintf(stderr, "URL_parse error %s: %s\n", err_msg, url_str_orig);
  URL_destroy(url);
  return NULL;
}


// Deallocates a URL struct and the strings in it.
void URL_destroy(URL *url) {
  if (url) {
    free((void*)url->hostname);
    free((void*)url->pathname);
    free(url);
  }
}




/**
 * Parse a line like "Content-Length: 56673" into
 * "Content-Length" and "56673".
 */
HTTPHeader *HTTPHeader_parse(char *line) {
  HTTPHeader *h = (HTTPHeader*) malloc(sizeof(HTTPHeader));
  h->next = NULL;
  
  char *colon = strchr(line, ':');

  // if there is no colon, take the whole line as a key and set value
  // to a zero-length string
  if (!colon) {
    h->key = strdup(line);
    h->value = strdup("");
  } else {
    h->key = substring(line, colon-line);
    char *value = colon + 1;

    // skip a single space after the colon, if present
    if (*value == ' ') value++;
    h->value = strdup(value);
  }

  // printf("%s: %s\n", h->key, h->value);

  return h;
}


/**
 * Given a socket that is already open to a web host, send a request
 * for the given file (pathname). hostname is required for the "Host"
 * header line which some servers require.
 */
void HTTP_create_request(const char *pathname, const char *hostname,
                         char **request, size_t *request_length) {

  asprintf(request, "GET %s HTTP/1.0\r\n"
           "Host: %s\r\n"
           "Connection: close\r\n"  // vs keep-alive
           "User-Agent: UIUC-cs241-project\r\n"
           "\r\n",
           pathname, hostname);
  
  *request_length = strlen(*request);
}


/**
 * Given the full content of an HTTP response (headers + content),
 * parse out the first line (with status code), headers, and content
 * into a WebResult object.
 * The 'response' will be modified (like strtok, overwriting delimiters).
 */
WebResult *WebResult_parse(char *response, size_t response_size) {
  const char *read_pos = response;
  const char *response_end = response + response_size;
  WebResult *w = (WebResult*) malloc(sizeof(WebResult));
  w->protocol = w->reason_phrase = w->content = NULL;
  w->headers = NULL;
  HTTPHeader *headers_tail = NULL;

  // get the first line, in the form "HTTP/1.1 404 Not Found"
  const char *line, *line_end;
  char *first_line;
  nextLine(&read_pos, response_end, &line, &line_end);
  first_line = substring(line, line_end - line);

  // This would be much easier:
  // sscanf(first_line, "%ms %d %m[^\n]", ...
  // but I'd like this to work on cygwin, and cygwin's "newlib" C library
  // still doesn't support the 'm' option in scsanf.
  
  int end_proto_offset = -1, start_phrase_offset = -1;
  // check for at least one successful conversion (the %d), because
  // %n may or may not count
  if (sscanf(first_line, "%*s%n %d %n",
             &end_proto_offset, &w->status_code, &start_phrase_offset) < 1
      || end_proto_offset == -1
      || start_phrase_offset == -1 ) {
    fprintf(stderr, "Unrecognized server response: %s\n", first_line);
    WebResult_destroy(w);
    return NULL;
  }

  w->protocol = substring(first_line, end_proto_offset);
  w->reason_phrase = strdup(first_line + start_phrase_offset);

  free(first_line);

  // Parse the header lines
  while (1) {
    nextLine(&read_pos, response_end, &line, &line_end);

    size_t len = line_end - line;
    // last header line
    if (len == 0) break;

    char *line_copy = substring(line, len);

    HTTPHeader *header = HTTPHeader_parse(line_copy);
    if (headers_tail) {
      headers_tail->next = header;
      headers_tail = header;
    } else {
      w->headers = headers_tail = header;
    }
    free(line_copy);
  }

  w->content_len = response + response_size - read_pos;
  w->content = (char*) malloc(w->content_len);
  assert(w->content);
  memcpy(w->content, read_pos, w->content_len);

  size_t expected_content_len = WebResult_get_content_length(w);
  if (expected_content_len && w->content_len != expected_content_len) {
    fprintf(stderr, "Warning: expected %zu bytes, got %zu\n",
            expected_content_len, w->content_len);
  }

  return w;
}


/**
 * Get the value of the header with the given key.
 * The string returned belongs to the WebResult object
 * and should not be freed.
 * Returns NULL if the key is not found.
 */
const char *WebResult_get_header(WebResult *w, const char *key) {
  HTTPHeader *h = w->headers;
  while (h) {
    if (!strcmp(h->key, key)) return h->value;
    h = h->next;
  }
  return NULL;
}


/**
 * Returns the value of the Content-Length header.
 * Returns 0 if that header line is not found or if the value is invalid.
 */
size_t WebResult_get_content_length(WebResult *w) {
  const char *length_str = WebResult_get_header(w, "Content-Length");
  if (length_str) {
    size_t len = 0;
    if (1 != sscanf(length_str, "%zu", &len)) {
      fprintf(stderr, "Invalid Content-Length: %s\n", length_str);
    }
    return len;
  } else {
    return 0;
  }
}


void WebResult_destroy(WebResult *w) {
  if (!w) return;
  HTTPHeader *h = w->headers;
  while (h) {
    HTTPHeader *next = h->next;
    free((char*)h->key);
    free((char*)h->value);
    free(h);
    h = next;
  }
  // free((char*)w->first_line);
  free((char*)w->protocol);
  free((char*)w->reason_phrase);
  free((char*)w->content);
  free(w);
}
