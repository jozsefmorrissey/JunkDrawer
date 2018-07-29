#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <unistd.h>




int main(int argc, char **argv)
{	
	#define ADDRESS argv[1]
	#define PORT argv[2] 
	if(argc != 3){
		printf("When running this program please use format:\nclient [address] [port]\n");
		exit(1);
	}


    /*QUESTION 1*/
	int s;

    /*QUESTION 2*/
	int sock_fd = socket(AF_INET, SOCK_STREAM, 0);

    /*QUESTION 3*/
	struct addrinfo hints, *result;

    /*QUESTION 4*/
	memset(&hints, 0, sizeof(struct addrinfo));


    /*QUESTION 5*/
	hints.ai_family = AF_INET;
	hints.ai_socktype = SOCK_STREAM;


    /*QUESTION 6*/
	s = getaddrinfo(ADDRESS, PORT, &hints, &result);
    
	if(s){
		fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(s));
		exit(1);
	}

    /*QUESTION 7*/
	s = connect(sock_fd, result->ai_addr, result->ai_addrlen);
	if(s){
		perror("connect()");
		exit(1);
	}
    // Now that a connection has ben established, we can start sending data to the server.
	char * buffer;// = "I can talk to my self digitally now.... no more analog crazy for me\r\r\n";
	size_t size = 0;
	while(1){
	
		getline(&buffer, &size, stdin);
		if(strstr(buffer, "!") == buffer){
			break;
		}
		else{

			printf("sending bytes\n", buffer);
			
fflush(NULL);
			write(sock_fd, buffer, strlen(buffer));

			//char resp[1000];
			//int len = read(sock_fd, resp, 999);
			//resp[len] = 0;
			//printf("Response: %s\n", resp);
		}

	}
	free(buffer);
	free(result);
	printf("Closing Client\n");
    return 0;
}
