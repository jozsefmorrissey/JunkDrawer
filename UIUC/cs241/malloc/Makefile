# Malloc
# CS 241 Fall 2015

CC = gcc
INC = -I.
FLAGS += -O3 -Wextra -Wall -Werror -Wno-unused-parameter
TESTERS = $(patsubst %.c, %, $(wildcard testers/*))

all: alloc.so contest-alloc.so mreplace mcontest tester-agents

alloc.so: alloc.c
	$(CC) $^ $(FLAGS) -o $@ -shared -fPIC

contest-alloc.so: contest-alloc.c
	$(CC) $^ $(FLAGS) -o $@ -shared -fPIC -ldl

mreplace: mreplace.c
	$(CC) $^ $(FLAGS) -o $@

mcontest: mcontest.c contest.h
	$(CC) $^ $(FLAGS) -o $@ -ldl -lpthread

tester-agents: part1 part2

part1: part1/tester-1 part1/tester-2 part1/tester-3 part1/tester-4

part2: $(subst testers, part2, $(TESTERS))

part1/%: testers/%.c
	@mkdir -p part1/
	$(CC) $^ $(FLAGS) -o $@

part2/%: testers/%.c
	@mkdir -p part2/
	$(CC) $^ $(FLAGS) -DPART2 -o $@

.PHONY : clean
clean:
	-rm -rf *.o *.so mreplace mcontest part1 part2
