#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <unistd.h>
#include <signal.h>
#include <fcntl.h>

#include <pthread.h>

#include "queue.h"

const char* TYPE1 = "heart_beat";
const char* TYPE2 = "blood_sugar";
const char* TYPE3 = "body_temp";

// The wearable server socket, which all wearables connect to.
int wearable_server_fd;

// A lock for your queue.
pthread_mutex_t queue_lock_;
// A queue for all received data.
queue_t receieved_data_;

#define THREAD_COUNT (500)

typedef struct SampleData {

	char type_[50];
	int data_;

} SampleData;


struct thread_info{
	int client_fd;
	pthread_t thread;
	int max_time;
	int not_free;
};

queue_t list;
pthread_mutex_t list_lock;
int num_threads = 0;
//int request_in = 0;
long request_time = -1;
pthread_cond_t req;
pthread_cond_t dat;
struct thread_info * infoBlock;

int check_max(long val);
void join_wearables(struct thread_info * info);
struct thread_info * find_free_block(struct thread_info * info);
void signal_received(int sig);
int bdt(void * arg);
int htb(void * arg);
int bls(void * arg);


int compare (const void * a, const void * b) {
  return ( *(int*)a - *(int*)b );
}

void handler(int signal){
	if(signal == SIGINT){
		//printf("ignored\n");
		signal_received(signal);
		exit(0);
	}
	printf("Somthing");
}

/**

    Used to write out the statistics of a given results set (of timestamp_entry's).
    To generate the result set see queue_gather(). fd is the file descriptor to
    which the information is sent out. The type is the type of data that is written out
    (TYPE1, TYPE2, TYPE3). results is the array of timestamp_entrys, and size is 
    the size of that array. NOTE: that you should call method for every type 
    (TYPE1, TYPE2, TYPE3), and then write out the infomration "\r\n" to signify that
    you have finished sending out the results.
*/
void write_results(int fd, const char* type, timestamp_entry* results, int size) {
    long avg = 0;
    int i;

    char buffer[1024];
    int temp_array[size];
    sprintf(buffer, "Results for %s:\n", type);
    sprintf(buffer + strlen(buffer), "Size:%i\n", size);
    for (i = 0;i < size;i ++) {
        temp_array[i] = ((SampleData*)(results[i].data_))->data_;
        avg += ((SampleData*)(results[i].data_))->data_;
    }

    qsort(temp_array, size, sizeof(int), compare);

    if (size != 0) {
    	sprintf(buffer + strlen(buffer), "Median:%i\n", (size % 2 == 0) ?
            (temp_array[size / 2] + temp_array[size / 2 - 1]) / 2 : temp_array[size / 2]);
	} else { 
		sprintf(buffer + strlen(buffer), "Median:0\n");
	}

    sprintf(buffer + strlen(buffer), "Average:%li\n\n", (size == 0 ? 0 : avg / size));
    write(fd, buffer, strlen(buffer));
}

/**
    Given an input line in the form <timestamp>:<value>:<type>, this method 
    parses the infomration from the string, into the given timestamp, and
    mallocs space for SampleData, and stores the type and value within
*/
void extract_key(char* line, long* timestamp, SampleData** ret) {
	*ret = malloc(sizeof(SampleData));
	sscanf(line, "%zu:%i:%[^:]%:\\.*", timestamp, &((*ret)->data_), (*ret)->type_);
}

void* wearable_processor_thread(void* args) {
	struct thread_info * info = args;
	int socketfd = info->client_fd;

	//Use a buffer of length 64!
	//TODO read data from the socket until -1 is returned by read

	num_threads++;
	size_t size = 64;
	char buffer[size];

	while(read(socketfd, buffer, size) > 0){

		SampleData * sam;
		long timestamp;
		extract_key(buffer, &timestamp, &sam);


		pthread_mutex_lock(&list_lock);	
		while(request_time == 1){
			pthread_cond_wait(&req, &list_lock);
		}
		queue_insert(&list, timestamp, sam);
		fprintf(stderr, "Logged\n");
		if(timestamp > info->max_time)
			info->max_time = timestamp;

		pthread_mutex_unlock(&list_lock);
		if(request_time == 0){
			request_time = 1;
			pthread_cond_signal(&dat);
		}

	}


	info->not_free = 2;
	//num_threads--;
	close(socketfd);
	return NULL;
}

void* user_request_thread(void* args) {
	int socketfd = *((int*)args);
	//int i, ret;

	//TODO read data from the socket until -1 is returned by read
	//Requests will be in the form
	//<timestamp1>:<timestamp2>, then write out statistics for data between
	//those timestamp ranges
		fprintf(stderr, "%i\n", __LINE__);
	size_t count = 100;
	char buff[count];
	int rdcheck = read(socketfd, buff, count);
	if(rdcheck == 0)
		fprintf(stderr, "No requests read\n");
	while(rdcheck > 0){			/////write data parse buff
		buff[rdcheck + 1] = 0; 
		char * parse;
		char temp[count];
		strcpy(temp, buff);
		parse = strtok(temp, ":");

		char * point = strstr(buff, ":");
	
		request_time = 0;
		int end = atol(point + 1);
		pthread_mutex_lock(&list_lock);
		fprintf(stderr, "in\n");
		while(check_max(end)){
			request_time = 0;
			pthread_cond_broadcast(&dat);
			pthread_cond_wait(&dat, &list_lock);
		}
		int start = atol(parse);

//socketfd = fileno(stderr);

		int blood_size;
		timestamp_entry * blood = queue_gather(&list, start, end, &bls, &blood_size);
		write_results(socketfd, "blood_sugar", blood, blood_size);

		int body_size;
		timestamp_entry * body = queue_gather(&list, start, end, &bdt, &body_size);
		write_results(socketfd, "body_temp", body, body_size);

		int heart_size;
		timestamp_entry * heart = queue_gather(&list, start, end, &htb, &heart_size);
		write_results(socketfd, "heart_beat", heart, heart_size);

		free(blood);
		free(body);
		free(heart);


		char buff[15] = "\r\n";
		int check = write(socketfd, buff, strlen(buff));
		if(check == -1)
			fprintf(stderr, "Write failed: termination byte not delivered\n");
		fprintf(stderr, "%i\n", __LINE__);
		fflush(NULL);
		request_time = -1;
		pthread_mutex_unlock(&list_lock);
		pthread_cond_broadcast(&req);

		rdcheck = read(socketfd, buff, count);
	}

	close(socketfd);
	return NULL;
}


int bls(void * arg){
	SampleData * sam = arg;
	if(!strcmp(sam->type_, "blood_sugar"))
		return 1;
	return 0;
}

int htb(void * arg){
	SampleData * sam = arg;
	if(!strcmp(sam->type_, "heart_beat"))
		return 1;
	return 0;
}

int bdt(void * arg){
	SampleData * sam = arg;
	if(!strcmp(sam->type_, "body_temp"))
		return 1;
	return 0;
}


//IMPLEMENT!
//given a string with the port value, set up a 
//serversocket file descriptor and return it
int open_server_socket(const char* port) {
	//TODO



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
	s = getaddrinfo(NULL, port, &hints, &result);
   	if(s){
		fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(s));
		exit(1);
	}

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

	free(result);
	return sock_fd;
}

void signal_received(int sig) {
	//TODO close server socket, free anything you don't free in main
		//free stuff
	join_wearables(infoBlock);
	queue_destroy(&list, 1);
	free(infoBlock);
	pthread_mutex_destroy(&list_lock);
	pthread_cond_destroy(&req);
	pthread_cond_destroy(&dat);
	close(wearable_server_fd);
	exit(sig);
}

int main(int argc, const char* argv[]) {
	if (argc != 3) {
		printf("Invalid input size\n");
		exit(EXIT_FAILURE);
	}

	queue_init(&list);
	pthread_mutex_init(&list_lock, NULL);

	//TODO setup sig handler for SIGINT
	signal(SIGINT, handler);
	pthread_cond_init(&req, NULL);
	pthread_cond_init(&dat, NULL);
	infoBlock = calloc(THREAD_COUNT, sizeof(struct thread_info));
	
	int request_server_fd = open_server_socket(argv[2]);
	wearable_server_fd = open_server_socket(argv[1]);

	pthread_t request_thread;
	int request_socket = accept(request_server_fd, NULL, NULL);
	if(request_socket == -1)
		fprintf(stderr, "Accept failed at line %i\n", __LINE__);


	struct thread_info * info = find_free_block(infoBlock);	
	int err = pthread_create(&request_thread, NULL, user_request_thread, &request_socket);
	if(err != 0)
		fprintf(stderr, "Pthread failed to create at line %i\n", __LINE__);	

	info->thread = request_thread;	
	info->not_free = 2;
	close(request_server_fd);

	//queue_init(&receieved_data_);
	pthread_mutex_init(&queue_lock_, NULL);

	//TODO accept continous requests

	while(1){

		//struct thread_info * info = malloc(sizeof(struct thread_info));
		info = find_free_block(infoBlock);	
		info->client_fd = accept(wearable_server_fd, NULL, NULL);
		if(info->client_fd == -1)
			fprintf(stderr, "Accept failed at line %i\n", __LINE__);
		

		//printf("Connection made: client_fd=%d\n", info->client_fd);
	
		sigset_t set;
		sigemptyset(&set);
		sigaddset(&set, SIGINT);
		pthread_sigmask(SIG_BLOCK, &set, NULL);
		err = pthread_create(&info->thread, NULL, wearable_processor_thread, info);
		if(err != 0)
			fprintf(stderr, "Pthread failed to create at line %i\n", __LINE__);		
		info->not_free = 1;
		sigprocmask(SIG_UNBLOCK, &set, NULL);
		close(info->client_fd);

		fprintf(stderr, "%i\n", __LINE__);
	}
	

	//TODO join all threads we spawned from the wearables
	signal_received(0);

	//queue_destroy(&receieved_data_, 1);

	return 0;
}



struct thread_info * find_free_block(struct thread_info * info){
	int i = 0;
	for(; i<THREAD_COUNT; i++){
		if(info->not_free == 0){
			//info->not_free = 1;
			return info;
		}
		else
			info++;
	}

	return NULL;
}


void join_wearables(struct thread_info * info){
	int i = 0;
	for(; i<THREAD_COUNT; i++){
		if(info->not_free != 0){
			printf("joined\n");
			fflush(NULL);
			pthread_join(info->thread, NULL);
			close(info->client_fd);
		}
		info++;
	}
}


int check_max(long val){
	struct thread_info * worker = infoBlock;
	int checked = 0;
	int count = 0;
	while(checked < num_threads && count < THREAD_COUNT){
		if(worker->not_free != 0){
			count++;
			if(worker->max_time < val && worker->not_free == 1){
				return 1;
			}
		}	
	}
	return 0;
}















