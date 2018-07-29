CC = gcc
FLAGS = -W -std=gnu99
LINKOPTS = -pthread

all: client server

client: client.c
	$(CC) $(FLAGS) $(LINKOPTS) client.c -o client

server: server.c
	$(CC) $(FLAGS) $(LINKOPTS) server.c -o server

clean:
	rm client server