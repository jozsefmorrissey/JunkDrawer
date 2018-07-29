/*
  Multi-way web get.
  This tool downloads multiple web documents simultaneously
  using nonblocking socket I/O.

  To install ncurses.h:
    sudo yum install ncurses-devel
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <sys/time.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <ncurses.h>
#include <errno.h>
#include <unistd.h>
#include "download.h"


// If you want to slow down the program, set this to the number of milliseconds
// to wait before each call to select() or epoll_wait().
int delay_ms = 0;


float TIMEOUT_SEC = 3.0;

// Text user interface - a full-screen interface implemented with ncurses.
// See http://invisible-island.net/ncurses/NCURSES-Programming-HOWTO.html
int TUI = 1;

// set in checkForQuit if the user hits 'q'
static int do_quit = 0;

// An array of objects tracking all the downloads in progress
static Download **downloads = NULL;
static int download_count = 0;

// Defined in control_loop.c
// This is the function you should modify.
void controlLoop(int download_count, Download *downloads[]);


void printHelp();
int lineCount(FILE*);
int initDownloads(const char *todo_filename,
		  int *download_count, Download ***downloads);
int checkForQuit();
void tuiStart();
void tuiRedraw();
void tuiEnd();

enum ProgramState {PS_RUNNING, PS_PAUSED, PS_DONE} programState = PS_RUNNING;

  
int main(int argc, char **argv) {
  if (argc < 2 || argc > 3) printHelp();
  int argno = 1;
  if (!strcmp(argv[argno], "-debug")) {
    TUI = 0;
    argno++;
  }
  if (argno != argc-1) printHelp();
  const char *todo_filename = argv[argno];

  // if stdout is not a terminal, disable fullscreen output
  if (!isatty(STDOUT_FILENO)) TUI = 0;
  download_tui = TUI;

  if (initDownloads(todo_filename, &download_count, &downloads))
    return 1;
  
  if (TUI) tuiStart();

  // Start all the downloads.
  // This does the DNS lookup, creates the socket (in nonblocking
  // mode), and calls connect(). The connection has not completed
  // yet--since the socket is nonblocking, the connection is not
  // complete until the file descriptor is ready for writing.
  for (int i=0; i < download_count; i++)
    Download_start(downloads[i]);

  tuiRedraw();

  // Wait for the downloads to finish.
  controlLoop(download_count, downloads);

  if (TUI) tuiEnd();

  for (int i=0; i < download_count; i++)
    Download_destroy(downloads[i]);
  free(downloads);
  
  return 0;
}


  
void printHelp() {
  fprintf(stderr, "\n  mget [-debug] <todo_file>\n"
          "  todo_file: name of a file containing one line per web document that is\n"
          "  to be downloaded. Each line contains the URL of the document and the name\n"
          "  of a file on the local machine to which will be saved. A single space will\n"
          "  separate the two.\n\n"
          "  For example:\n"
          "    www.google.com google_homepage.html\n"
          "    http://linux.die.net/man/4/epoll epoll.html\n"
          "\n"
          "  -debug : disable full-screen interface and just send all events to stdout\n"
          "\n");
  exit(1);
}


// Set up the ncurses screen
void tuiStart() {
  initscr();
  start_color();
  init_pair(1, COLOR_RED, COLOR_BLACK);
  init_pair(2, COLOR_GREEN, COLOR_BLACK);
  clear();
  noecho();
  cbreak();

  tuiRedraw();
}


void tuiRedraw() {
  clear();
  for (int i=0; i < download_count; i++)
    Download_print_update(downloads[i]);

  int width = getmaxx(stdscr);
  int height = getmaxy(stdscr);
  const char *msg;
  switch (programState) {
  case PS_RUNNING: msg = "<-press q to quit, SPC to pause->\n"; break;
  case PS_PAUSED: msg = "PAUSED - press any key to continue\n"; break;
  case PS_DONE: msg = "<-press any key to exit->\n"; break;
  }
  int xpos = width/2 - strlen(msg)/2;
  // clear the line first
  mvprintw(height - 1, 0, "\n");
  mvprintw(height - 1, xpos, msg);
  refresh();
}

// Stop the ncurses screen
void tuiEnd() {

  // if the user didn't quit by hitting 'q', wait for them to press the any key
  if (!do_quit) {
    programState = PS_DONE;
    tuiRedraw();

    // flush keyboard input buffer
    nodelay(stdscr, true);
    while (ERR != getch()) {}

    // wait for one more keystroke
    nodelay(stdscr, false);
    while (1) {
      int c = getch();
      if (c == KEY_RESIZE)
	tuiRedraw();
      else
	break;
    }
  }
  
  echo();
  endwin();
}
  

// count the number of lines in a file, then seek back to the beginning
int lineCount(FILE *inf) {
  char *line = NULL;
  size_t line_len = 0;
  int count = 0;

  while (-1 != getline(&line, &line_len, inf))
    count++;

  free(line);
  fseek(inf, 0, SEEK_SET);
  return count;
}


/**
 * Initialize the array of Download objects, one for each line in the
 * input file.  If there are any invalid lines in the input file, an
 * error message is printed to stderr and this returns nonzero.  On
 * success, return zero.
 */
int initDownloads(const char *todo_filename,
		  int *download_count, Download ***downloads) {

  FILE *todo_file = fopen(todo_filename, "r");
  if (!todo_file) {
    fprintf(stderr, "Failed to open %s\n", todo_filename);
    return 1;
  }

  *download_count = lineCount(todo_file);
  *downloads = (Download**) malloc(sizeof(Download*) * *download_count);
  
  char *line = NULL;
  size_t line_len = 0;
  int line_no = 0;
  while (-1 != getline(&line, &line_len, todo_file)) {

    char *url = strtok(line, " ");
    char *output_file = strtok(NULL, "\r\n");

    if (!url || !output_file || !url[0] || !output_file[0]) {
      fprintf(stderr, "%s:%d Invalid input line\n", todo_filename, line_no+1);
      return 1;
    }

    (*downloads)[line_no] = Download_create(url, output_file, line_no);
    if (!(*downloads)[line_no])
      return 1;
    
    line_no++;
  }

  free(line);
  fclose(todo_file);
  
  return 0;
}


// Returns zero if the user has terminated the run, otherwise nonzero.
int checkForQuit() {
  if (!TUI) return 1;

  // set getch() to nonblocking mode
  nodelay(stdscr, true);
  int c = getch();
  // if no key has been pressed, do nothing
  if (c == ERR) return 1;

  // if the user hit q, then signal a quit
  if (c == 'q' || c == 'Q') {
    do_quit = 1;
    return 0;
  }

  // on window resize, redraw all
  if (c == KEY_RESIZE) {
    tuiRedraw();
    return 1;
  }

  // if the user hit the spacebar, pause
  if (c == ' ') {
    programState = PS_PAUSED;
    tuiRedraw();

    // set getch() to blocking mode and wait for another keystroke
    nodelay(stdscr, false);
    while (1) {
      c = getch();
      if (c == KEY_RESIZE)
	tuiRedraw();
      else
	break;
    }
    
    programState = PS_RUNNING;
    tuiRedraw();
  }

  // ignore any other key
  return 1;
}


void delay() {
  if (delay_ms > 0) {
    struct timespec t;
    t.tv_sec = delay_ms / 1000;
    t.tv_nsec = (delay_ms % 1000) * 1000000;
    nanosleep(&t, NULL);
  }
}
