CFLAGS=-std=c99 -g -Wall -Wextra -Werror -Wno-unused-parameter -D_GNU_SOURCE
CC=clang
EXECS=getone_blocking getone mget_select mget
LIBS=-lncurses -lrt

all: $(EXECS)

getone_blocking: getone_blocking.o http_simple.o common.o
	$(CC) $(CFLAGS) $^ $(LIBS) -o $@

getone: getone.o http_simple.o common.o
	$(CC) $(CFLAGS) $^ $(LIBS) -o $@

mget: mget.o http_simple.o common.o download.o control_loop_epoll.o
	$(CC) $(CFLAGS) $^ $(LIBS) -o $@

mget_select: mget.o http_simple.o common.o download.o control_loop_select.o
	$(CC) $(CFLAGS) $^ $(LIBS) -o $@

common.o: common.c common.h
download.o: download.c download.h
http_simple.o: http_simple.c http_simple.h

.PHONY: clean
clean:
	rm -f $(EXECS) *.o *.exe *~ *.jpg *.html *.iso dracula.txt
	svn up grade_report.html &> /dev/null


