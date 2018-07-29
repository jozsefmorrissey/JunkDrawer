#ifndef __COMMON_H__
#define __COMMON_H__

#include <sys/types.h>
#include <sys/socket.h>

typedef struct {
  char *data;
  size_t capacity, size;
} ByteBuffer;

void ByteBuffer_init(ByteBuffer *buf);
void ByteBuffer_append(ByteBuffer *buf, void *chunk, size_t chunk_len);
void ByteBuffer_destroy(ByteBuffer *buf);


/**
 * Given a host name and port number, this looks up the host name and
 * returns an address that can be used with connect().
 *
 * address_family - (output parameter) will be set to either AF_INET
 *   (for IPv4) or AF_INET6 (for IPv6)
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
                  struct sockaddr **address, socklen_t *address_len);
  

/**
 * Returns a copy of 'length' bytes of src, allocated with malloc;
 * The string is null-terminated.
 * If length is negative, copies the whole string.
 */
char *substring(const char *src, ssize_t length);


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
             const char **found, const char **found_end);


#endif /* __COMMON_H__ */
