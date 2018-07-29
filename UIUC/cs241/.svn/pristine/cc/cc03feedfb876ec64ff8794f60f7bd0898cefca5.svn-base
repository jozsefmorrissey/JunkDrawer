#
# Text Editor Lab
# CS 241 Fall 2015
#

CC = gcc
INC = -I.
FLAGS = -Wall -Wextra -Werror -Wno-unused -Wno-sign-compare -g -std=c99

all: editor

editor: editor.o
	$(CC) $^ -o $@

editor.o: editor.c editor.h
	$(CC) -c $(FLAGS) $(INC) $< -o $@

.PHONY : clean
clean:
	-rm -r -f *.o editor
