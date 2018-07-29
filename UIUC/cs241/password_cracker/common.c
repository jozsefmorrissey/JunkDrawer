#define _GNU_SOURCE
#include <time.h>
#include <assert.h>
#include <string.h>

#include "common.h"

/* Returns a "wall clock" timer value in seconds.
   This timer keeps ticking even when the thread or process is idle. */
double getTime() {
  struct timespec t;
  clock_gettime(CLOCK_MONOTONIC, &t);
  return t.tv_sec + 1e-9 * t.tv_nsec;
}

/* Returns a process-wide CPU time value in seconds.
   This will tick faster than getTime() if mutiple threads are busy. */
double getCPUTime() {
  struct timespec t;
  clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &t);
  return t.tv_sec + 1e-9 * t.tv_nsec;
}

/* Return a thread-specific CPU time value in seconds.
   This timer only ticks when the current thread is not idle. */
double getThreadCPUTime() {
  struct timespec t;
  clock_gettime(CLOCK_THREAD_CPUTIME_ID, &t);
  return t.tv_sec + 1e-9 * t.tv_nsec;
}

/* Given a string like "abc.....", return the number of letters in it. */
int getPrefixLength(const char *str) {
  char *first_dot = strchr(str, '.');
  if (first_dot)
    return first_dot - str;
  else
    return strlen(str);
}

/* Set 'result' to the string that you'd get after replacing every
   character with 'a' and calling incrementString() on it 'n' times. */
void setStringPosition(char *result, long n) {
  char *p = result + strlen(result) - 1;

  while (p >= result) {
    *p-- = 'a' + (n % 26);
    n /= 26;
  }
}

/* Increment the letters in 'str'.
   Returns 1 if the increment is successful.
   Returns 0 if all the letters in the string are 'z'. */
int incrementString(char *str) {
  assert(str);

  char *p = str + strlen(str) - 1;

  // find the last character after the prefix that is not a z
  while (p >= str && *p == 'z')
    p--;

  // if we found one, increment it
  if (p >= str) {

    // increment this character and move to the next one
    (*p++)++;

    // and set all the remaining characters to 'a'
    while (*p)
      *p++ = 'a';

    return 1;

  } else {

    // reached the end
    return 0;
  }
}
