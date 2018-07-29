#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <unistd.h>
#include <signal.h>
#include <pthread.h>


typedef void (*sighandler_t)(int);


struct thread_info{
	int client_fd;
	int not_free;
};

#define MESG_SIZE (256)
void* processClient(void  * arfv);
struct thread_info * find_free_block(struct thread_info * info);


void * data[2];


void handler(int signal){
	if(signal == SIGINT){
		//printf("ignored\n");
		free(data[0]);
		free(data[1]);
		exit(0);
	}
	printf("Somthing");
}

int main(int argc, char** argv)
{
	if(argc != 2){
		printf("When running this program please use format:\nserver [port]\n");
		exit(1);
	}

    #define PORT argv[1]
	signal(SIGINT, handler);

    /*QUESTION 1*/
	int s;

    /*QUESTION 2*/
	int sock_fd = socket(AF_INET, SOCK_STREAM, 0);

    /*QUESTION 3*/
	struct addrinfo hints, *result;   
    

    /*QUESTION 8*/
	int optval = 1;
	setsockopt(sock_fd, SOL_SOCKET, SO_REUSEPORT, &optval, sizeof(optval));

    /*QUESTION 4*/
	memset(&hints, 0, sizeof(struct addrinfo));


    /*QUESTION 5*/
	hints.ai_family = AF_INET;
	hints.ai_socktype = SOCK_STREAM;
	hints.ai_flags = AI_PASSIVE;


    /*QUESTION 6*/
	s = getaddrinfo(NULL, PORT, &hints, &result);
   	if(s){
		fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(s));
		exit(1);
	}
	data[1] = result;

    /*QUESTION 9*/
	s = bind(sock_fd, result->ai_addr, result->ai_addrlen);
	if(s){
		perror("bind()");
		exit(1);
	}
    
    /*QUESTION 10*/
	s = listen(sock_fd, 10);
	if(s){
		perror("listen()");
		exit(1);
	}

    // Can now start accepting and processing client connections
    /*QUESTION 11*/
	struct sockaddr_in *result_addr = (struct sockaddr_in *) result->ai_addr;
	printf("Listening on file descriptor %d, port %d\n", sock_fd, ntohs(result_addr->sin_port));

	printf("Waiting for connection....\n");
	struct thread_info * infoBlock = calloc(sizeof(struct thread_info), 500);
	data[0] = infoBlock;
	
	while(1){

		//struct thread_info * info = malloc(sizeof(struct thread_info));
		struct thread_info * info = find_free_block(infoBlock);	
		info->client_fd = accept(sock_fd, NULL, NULL);
		printf("Connection made: client_fd=%d\n", info->client_fd);
	
		pthread_t usless;
		sigset_t set;
		sigemptyset(&set);
		sigaddset(&set, SIGINT);
		pthread_sigmask(SIG_BLOCK, &set, NULL);
		pthread_create(&usless, NULL, processClient, info);
		//processClient(info);
		//sigdelset(&set, SIGINT);
		sigprocmask(SIG_UNBLOCK, &set, NULL);
	}
	free(infoBlock);

    return 0;
}

void* processClient(void * argv) {
    //int client_fd = (intptr_t) arg;
	struct thread_info * info = (struct thread_info *)argv;
    pthread_detach(pthread_self()); // no join() required

    // Continue reading input from the client and print to stdout
	while(1){
		char buffer[1000];
		int len = read(info->client_fd, buffer, sizeof(buffer) - 1);
		if(len == 0){
			printf("Closing connection with client_fd=%d\n", info->client_fd); 
			//free(info);
			info->not_free = 0;
			break;
		}
		buffer[len] = 0;

		//printf("Read %d chars\n", len);
		//printf("===\n");
		printf("%s", buffer);
	}
    
    return NULL;
}

struct thread_info * find_free_block(struct thread_info * info){
	int i = 0;
	for(; i<500; i++){
		if(!info->not_free){
			info->not_free = 1;
			return info;
		}
		else
			info += sizeof(struct thread_info);
	}

	return NULL;
}




