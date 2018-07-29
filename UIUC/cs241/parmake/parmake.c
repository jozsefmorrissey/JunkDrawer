// Parmake
// CS 241 Fall 2015
// University of Illinois at Urbana-Champaign
#define _GNU_SOURCE
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>
#include "queue.h"
//#include "queue.c"
#include "parser.h"
#include <pthread.h>
#include <semaphore.h>
#include <sys/wait.h>

#define SIGNAL_FILE_DEPENDENCE (57);

typedef struct{

	queue_t * dependencies;
	queue_t * commands;
	int run;
	char target[];
}targetS;

typedef struct{
	pthread_t thread;
	int id;
}worker;

void parseTargets(char * target);
void parseDependency(char * target, char * dependency);
void parseCommand(char * target, char * command);
void * findTar(char * target);
unsigned int findTarIndex(char * target);
void sendMSG(void * t, int end);
void readPrep();
void readConc();
void writePrep();
void * deligate(void * p);
void writeConc();
void freeTar();
void * noDepTar();
void exicute(targetS * tar);
void terminateProcess();
void * readMSG();
void * createThreads();
void joinThreads(void * workers);
void printTarget(targetS * worker);
void removeDependencies();
void depRemove(targetS * t);
void fileDep(targetS * t);

void printStruct();

pthread_cond_t cvTask;
pthread_mutex_t task;

pthread_cond_t cvWrite;
pthread_mutex_t w;

pthread_mutex_t r;
pthread_cond_t cvRemove;

sem_t sem_read;

int writing = 0;
long wT;

queue_t targetList;
queue_t removeList;
/**
 * Entry point to parmake.
 */
int main(int argc, char **argv) { 
	int getVal;
	int tarIndex = 0;
	char * filename = NULL;
	char * workerThreads = NULL;
	wT = 1;


	//parse command line arguments
	int i;
	for(i = 0; i<argc ; i++){
		getVal = getopt(argc, argv, "f:j:");
		if(getVal == -1)
			break;
		tarIndex += 2;
		switch (getVal){
			case 'f':
				filename = malloc(strlen(optarg) + 1);
				strcpy(filename, optarg);
				//filename = optarg;
				break;
			case 'j':
				workerThreads = malloc(sizeof(optarg));
				strcpy(workerThreads, optarg);
				//printf("optarg: %s\t workT: %s\n", optarg, workerThreads);
				break;
			case '?':
				break;
			default:
				break; 
		}


		if(filename == NULL){
			if(access("makefile", R_OK))
				filename = "makefile";
			else if(access("Makefile", R_OK))
				filename = "Makefile";
			else
				return 1;
		}
		char * hiccups;
		if(workerThreads != NULL)
			wT = strtol(workerThreads, &hiccups, 10);
	}
	//if(argc == i){
		//filename = NULL;
	//	printf("yey!");	
	//}

	sem_init(&sem_read, 0, wT);

	pthread_mutex_init(&task, NULL);
	pthread_cond_init(&cvTask, NULL);

	pthread_mutex_init(&w, NULL);
	pthread_cond_init(&cvWrite, NULL);

	pthread_mutex_init(&r, NULL);
	pthread_cond_init(&cvRemove, NULL);

	void (*pt)(char*) = parseTargets;
	void (*pd)(char*, char*) = parseDependency;
	void (*pc)(char*, char*) = parseCommand;

	queue_init(&targetList);
	queue_init(&removeList);

	parser_parse_makefile(filename, NULL, pt, pd, pc);
	//parser_parse_makefile(filename, &argv[tarIndex], pt, pd, pc);


	//printf("for file %s there are %li workers\n", filename, wT); 

	void * workers = createThreads(wT);

	targetS * init = malloc(sizeof(targetS));

	init->run = SIGNAL_FILE_DEPENDENCE;
	//strcpy(init->target, NULL);
	fileDep(init);


	removeDependencies();

	joinThreads(workers);
	//printf("Threads Successfully Joined!\n");

return 0; 
}


void removeDependencies(){
	while(1){//ffffffffffffffffffffffffiiiiiiiiiiiiiiiiiiiiiiiixxxxxxxxxxxxxxx
		targetS * t = readMSG();
		if(!t)
			break;
		depRemove(t);
	}
}

void depRemove(targetS * t){
	writePrep();
	char * done = t->target;
	int i, j;
	for(i = 0; i < targetList.size; i++){
		targetS * search = queue_at(&targetList, i);
		for(j = 0; j < search->dependencies->size; j++){
			if(!strcmp(queue_at(search->dependencies, j), done)){
				queue_remove_at(search->dependencies, j);
			}
		}
	}
	writeConc();
	pthread_cond_broadcast(&cvTask);		
	freeTar(t);
	//printStruct();
}


void fileDep(targetS * t){
	writePrep();
	int i, j;
	for(i = 0; i < targetList.size; i++){
		targetS * search = queue_at(&targetList, i);
		int acTar = access(t->target, F_OK);
		if(acTar == 0){
			for(j = 0; j < search->dependencies->size; j++){
				char * dep = queue_at(search->dependencies, j);
				int acDep = access(dep, F_OK);
				if(acDep == -1)
					break;
				else if(j == search->dependencies->size){
					printf("check that date!\n");
				}					
			}
		}
	}
	writeConc();
	pthread_cond_broadcast(&cvTask);
	pthread_cond_broadcast(&cvWrite);		
	//freeTar(t);
	//printStruct();
}


void * createThreads(){
	void * workers = malloc(sizeof(worker)*wT);
	worker * traverse = workers;
	int i;

	for(i = 0; i < wT; i++){
		traverse->id = i;
		int error = pthread_create(&traverse->thread, NULL, deligate, traverse);
		if(error)
			printf("pthread failed to create at line %d with error value %d\n", __LINE__, error);
		//else
			//printf("created thread %d\t @ location %p\n", traverse->id, traverse);
		traverse += 1;
	}
	return workers;
}

void joinThreads(void * workers){
	worker * traverse = workers;
	int i;
	for(i = 0; i < wT; i++){
		int error = pthread_join(traverse->thread, NULL);
		if(error)
			printf("pthread failed to join at line %d with error value %d\n", __LINE__, error);
		//else
			//printf("joined thread %d\t @ location %p\n", traverse->id, traverse);
		traverse += 1;				
	}
	free(workers);
}


void * deligate(void * p){

	targetS * tar;
	readPrep();
	int end = 0;
	int i = targetList.size;
	//printf("size: %d\n", i);
	readConc();

	while(1){
		tar = noDepTar();

		if(i == 0)
			break;
		else if(!tar){			
			pthread_mutex_lock(&task);
			pthread_cond_wait(&cvTask, &task);
			pthread_mutex_unlock(&task);
		}
		else{
		exicute(tar);
		if(i == 1){
			end = i;
		}
		sendMSG(tar, end);
		}
		readPrep();
		i = targetList.size;
		readConc();	
	}



	pthread_cond_signal(&cvTask);

	return NULL;
}

void freeTar(void * t){
	//t = (targetS *)t;
	/*int i;
	while(!!commands->size){
		char * cmd = queue_dequeue(t->commands);
		free(cmd);
	}*/
	queue_destroy(((targetS *)t)->commands);
	queue_destroy(((targetS *)t)->dependencies);
	free(t);
}


void exicute(targetS * tar){
	//tar = ((targetS*)tar);
	//printf("line: %d", __LINE__);
	//printf("Here is the one you want");
	//printTarget(tar);
	while(tar->commands->size > 0){
		char * cmd = queue_dequeue(tar->commands);
		//printf("line: %d", __LINE__);
		//pid_t child = fork();
		//printf("child: %d", (int)child);
	
		//printf("entered the system with cmd: %s\n", cmd);
		system(cmd);
		//printf("exited:\n");
		//terminateProcess();
		free(cmd);
	}
}


void terminateProcess(){

}


void * noDepTar(){
	int i;				
	writePrep();
	for(i=0; i < targetList.size; i++){
		targetS * curTar = queue_at(&targetList, i);
		//printf("size: %u\n", curTar->dependencies->size);
		if(!curTar->dependencies->size){
			int cur;
			sem_getvalue(&sem_read, &cur);
			//printf("\n\nSEM SIZE: %d\n", cur);
			void * ret = queue_remove_at(&targetList, i);
			writeConc();
			return ret;
		}
	}		
	writeConc();
	return NULL;
}


void readPrep(){
	pthread_mutex_lock(&w);
	while(writing == 1){
		pthread_cond_wait(&cvWrite, &w);
	}
	sem_wait(&sem_read);
	pthread_mutex_unlock(&w);
	

}


void readConc(){
	sem_post(&sem_read);
	int cur = 0;
	sem_getvalue(&sem_read, &cur);
	//printf("sem val: %d\n", cur);
	if(cur == wT)
		pthread_cond_signal(&cvWrite);
}


void writePrep(){
	pthread_mutex_lock(&w);
	writing = 1;
	int cur = 0;
	sem_getvalue(&sem_read, &cur);
	while(cur < wT){
		pthread_cond_wait(&cvWrite, &w);
		sem_getvalue(&sem_read, &cur);
	}

}


void writeConc(){
	writing = 0;
	pthread_mutex_unlock(&w);
	pthread_cond_broadcast(&cvWrite);
}


void sendMSG(void * t, int end){
	pthread_mutex_lock(&r);
	queue_enqueue(&removeList, t);
	if(end)
		queue_enqueue(&removeList, NULL);
	pthread_cond_signal(&cvRemove);
	pthread_mutex_unlock(&r);
	//printf("HERE                    HERE\n");
	//printTarget(t);
}


void * readMSG(){
	pthread_mutex_lock(&r);
	while(removeList.size == 0){
		pthread_cond_wait(&cvRemove, &r);
	}
	void * retVal = queue_dequeue(&removeList);
	pthread_mutex_unlock(&r);
	return retVal;
}


void parseTargets(char * target){
	
	targetS * cur = malloc(strlen(target) + 1 + sizeof(queue_t *)*2);
	cur->dependencies = malloc(sizeof(queue_t));
	cur->commands = malloc(sizeof(queue_t));
	strcpy((cur->target), target);
	cur->run = 0;

	queue_enqueue(&targetList, cur);
	queue_init((cur->dependencies));
	queue_init((cur->commands));
}

void parseDependency(char * target, char * dependency){
	//printf("for target: '%s' we have dependency: '%s'\n", target, dependency);
	targetS * cur = findTar(target);
	//printf("cur: %s\n", cur->target);
	char * dep = malloc(strlen(dependency) + 1);
	strcpy(dep, dependency);
	queue_enqueue(cur->dependencies, dep);


	/*char cwd[1024 + strlen(target) + 1];
	getcwd(cwd, sizeof(cwd));
	strcat(cwd, target);
	int ac = access(cwd, F_OK);
	printf("target: %s\t access: %d\t rule: %s\n", target, ac, cwd);
	*/
}

void parseCommand(char * target, char * command){
	targetS * cur = findTar(target);
	//printf("cur: %s\n", cur->target);
	char * cmd = malloc(strlen(command) + 1);
	strcpy(cmd, command);
	queue_enqueue(cur->commands, cmd);
	
}

void * findTar(char * target){

	unsigned int i = 0;
	for(i = 0; i < targetList.size; i++){
		targetS * cur = queue_at(&targetList, i);
		if(!strcmp(cur->target, target))
			return cur;
	}
	return NULL;
}

unsigned int findTarIndex(char * target){

	unsigned int i = 0;
	for(i = 0; i < targetList.size; i++){
		targetS * cur = queue_at(&targetList, i);
		if(!strcmp(cur->target, target))
			return i;
	}
	return -1;
}


void printStruct(){
	unsigned int i;
	for(i = 0; i < targetList.size; i ++){
		targetS * worker = queue_at(&targetList, i);
		printTarget(worker);
	}
}

void printTarget(targetS * worker){
	if(!worker){
		printf("NULL\n");
		return;
	}
	unsigned int j, k;
	printf("TARGET: %s\n", worker->target);
	printf("Dependence: ");
	for(j = 0; j < worker->dependencies->size; j++){
		char * dep = queue_at(worker->dependencies, j);
		printf("%s, ", dep);
	}
	printf("\nCommands: \n");
	for(k = 0; k < worker->commands->size; k++){
		char * cmd = queue_at(worker->commands, k);
		printf("%s\n", cmd);
	}
	printf("\n");
}













