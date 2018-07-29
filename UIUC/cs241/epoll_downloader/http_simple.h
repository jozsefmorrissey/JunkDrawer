#ifndef __HTTP_SIMPLE_H__
#define __HTTP_SIMPLE_H__

#define HTTP_OK 200

/* Encapsulate the important parts of a URL */
typedef struct {
  const char *hostname;
  int port;
  const char *pathname;
} URL;


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
URL *URL_parse(const char *url_str_orig);

// Deallocates a URL struct and the strings in it.
void URL_destroy(URL *url);

/**
 * Given a socket that is already open to a web host, send a request
 * for the given file (pathname). hostname is required for the "Host"
 * header line which some servers require.
 */
void HTTP_create_request(const char *pathname, const char *hostname,
                         char **request, size_t *request_length);

typedef struct HTTPHeader {
  const char *key;
  const char *value;
  struct HTTPHeader *next;
} HTTPHeader;

/**
 * Parse a line like "Content-Length: 56673" into
 * "Content-Length" and "56673".
 */
HTTPHeader *HTTPHeader_parse(char *line);


// Encapsulate an HTTP response.
typedef struct {
  const char *protocol;      // "HTTP/1.1"
  int status_code;           // for example: ok: 200, not found: 404
  const char *reason_phrase; // Text description of status_code:
                             // "OK", "Not Found"
  HTTPHeader *headers;       // linked list of key/value pairs
  size_t content_len;        // number of bytes in content
  void *content;             // content (may contain null bytes; don't assume it's a C string)
} WebResult;


/**
 * Given the full content of an HTTP response (headers + content),
 * parse out the first line (with status code), headers, and content
 * into a WebResult object.
 * The 'response' will be modified (like strtok, overwriting delimiters).
 */
WebResult *WebResult_parse(char *response, size_t response_size);


/**
 * Get the value of the header with the given key.
 * The string returned belongs to the WebResult object
 * and should not be freed.
 * Returns NULL if the key is not found.
 */
const char *WebResult_get_header(WebResult *w, const char *key);

/**
 * Returns the value of the Content-Length header.
 * Returns 0 if that header line is not found or if the value is invalid.
 */
size_t WebResult_get_content_length(WebResult *w);


/**
 * Deallocate the memory in use by a WebResult object.
 */
void WebResult_destroy(WebResult *w);

#endif /* __HTTP_SIMPLE_H__ */
