#
# CS 241
#

CC = gcc
INC = -I.
FLAGS = -Wall -Wextra -Wno-unused -Wno-unused-parameter -g

all: simulator

simulator: simulator.o libscheduler/libscheduler.o libpriqueue/libpriqueue.o
	$(CC) $^ -o $@

libscheduler/libscheduler.o: libscheduler/libscheduler.c libscheduler/libscheduler.h
	$(CC) -c $(FLAGS) $(INC) $< -o $@

libpriqueue/libpriqueue.o: libpriqueue/libpriqueue.c libpriqueue/libpriqueue.h
	$(CC) -c $(FLAGS) $(INC) $< -o $@

simulator.o: simulator.c libscheduler/libscheduler.h
	$(CC) -c $(FLAGS) $(INC) $< -o $@




.PHONY : clean
clean:
	rm -rf simulator *.o libscheduler/*.o libpriqueue/*.o
