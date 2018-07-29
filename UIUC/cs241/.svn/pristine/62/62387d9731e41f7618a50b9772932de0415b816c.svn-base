#include <stdlib.h>
#include <string.h>
#include <netdb.h>
#include <stdio.h>
#include <assert.h>
#include <arpa/inet.h>
#include "common.h"


void ByteBuffer_init(ByteBuffer *buf) {
  buf->capacity = 1024;
  buf->data = malloc(buf->capacity);
  assert(buf->data);
  buf->size = 0;
}


void ByteBuffer_append(ByteBuffer *buf, void *chunk, size_t chunk_len) {
  size_t required_size = buf->size + chunk_len;

  // grow buffer if needed
  if (required_size > buf->capacity) {
    if (buf->capacity * 2 >= required_size) {
      buf->capacity *= 2;
    } else {
      buf->capacity = required_size;
    }

    buf->data = realloc(buf->data, buf->capacity);
    assert(buf->data);
  }

  memcpy(buf->data + buf->size, chunk, chunk_len);
  buf->size += chunk_len;
}


void ByteBuffer_destroy(ByteBuffer *buf) {
  free(buf->data);
  buf->data = 0;
  buf->size = buf->capacity = 0;
}


/**
 * Given a host name and port number, this looks up the host name and
 * returns an address that can be used with connect().
 *
 * address - (output parameter) will be set to a malloc'd struct
 *   sockaddr that encodes the IP address and port number. Caller will
 *   need to deallocate this memory with free().
 * 
 * address_len - (output parameter) will be set to the number of bytes in
 *               address (this is needed by connect()).
 *
 * Returns 0 on success. On error, prints a message to stderr and
 * returns 1.
 */
int getWebAddress(const char *hostname, int port,
                  struct sockaddr **address, socklen_t *address_len) {

  struct addrinfo hints, *infoptr;
  memset(&hints, 0, sizeof(struct addrinfo));
  hints.ai_family = AF_INET;
  hints.ai_socktype = SOCK_STREAM;
  char port_str[30];
  sprintf(port_str, "%d", port);

  int result = getaddrinfo(hostname, port_str, &hints, &infoptr);
  if (result) {
    fprintf(stderr, "getaddrinfo failed on %s:%d: %s\n",
            hostname, port, gai_strerror(result));
    return 1;
  }

  // save a copy of the first address
  *address_len = infoptr->ai_addrlen;
  *address = malloc(*address_len);
  assert(*address);
  memcpy(*address, infoptr->ai_addr, *address_len);

  // print all addresses returned
  /*
  struct addrinfo *addr = infoptr;
  while (addr) {
    struct sockaddr *saddr = addr->ai_addr;
    struct sockaddr_in *saddr_in = (struct sockaddr_in*) saddr;
    char addr_str[50];
    inet_ntop(addr->ai_family, &saddr_in->sin_addr, addr_str,
              sizeof(addr_str)-1);
    fprintf(stderr, "  %s\n", addr_str);

    // this works
    // printf("address %s\n", inet_ntoa(saddr_in->sin_addr));
    
    addr = addr->ai_next;
  }
  */

  freeaddrinfo(infoptr);

  return 0;
}


/**
 * Returns a copy of 'length' bytes of src, allocated with malloc;
 * The string is null-terminated.
 * If length is negative, copies the whole string.
 */
char *substring(const char *src, ssize_t length) {
  if (length < 0) return strdup(src);
  char *result = malloc(length + 1);
  result[0] = 0;
  strncat(result, src, length);
  return result;
}


/**
   Starting at *str, scan forward to the next newline.  'end' points
   to the character after the end of the string; do not read beyond it.
   
   If a newline is found:
   - Set *found to the beginning of the line (the value of *start
     when the function was called).
   - If the newline is preceded by a carriage return, set
     *found_end to point to the carriage return.
   - Otherwise, set *found_end to point to the newline.
   - Set *start to point to the character after the newline.
   - Return 1.
  
   If a newline is not found, return 0.
  
   This is designed to easily scan through lines in a loop:

     while (nextLine(&pos, end, &found, &found_end)) {
       s = substring(found, found_end - found);
       ...
     }
   
 */
int nextLine(const char **start, const char *end,
             const char **found, const char **found_end) {

  const char *start_orig = *start;
  const char *nl;

  for (nl = *start; nl < end; nl++) {
    if (*nl == '\n') {
      *start = nl + 1;

      // if there is a least one character in the line, check if the character
      // before the newline is a carriage return.  If it is, back up by one
      // so the carriage is omitted from the line.
      if (nl > start_orig && nl[-1] == '\r') nl--;

      *found = start_orig;
      *found_end = nl;
      return 1;
    }
  }

  return 0;
}


