#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "tree.h"
#include <sys/mman.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>




void errorMes(int e, int line);
uint32_t * getU32(void * f, int offset);
float * price(void * f);
char * word(void * f);
void * getChild(void * f, int child);
void find(char * search_word, void * f);

void printNotFound(char * word);
void print(void * f);

#define LEFT_CHILD 0
#define RIGHT_CHILD 4
#define COUNT 8
#define PRICE 12
#define WORD 16

void * og;

int main(int argc, char * argv[]){

	int fd;
	struct stat mystat;
	void *pmap;
	
	fd = open(argv[1], O_RDONLY);
	if(fd == -1){
		perror("open");
		exit(1);
	}

	if(fstat(fd, &mystat) < 0){
		perror("fstat");
		close(fd);
		exit(1);
	}

	pmap = mmap(0, mystat.st_size, PROT_READ, MAP_SHARED, fd, 0);
	
	if(pmap == MAP_FAILED){
		perror("mmap");
		close(fd);
		exit(1);
	}
	
	pmap += 4;

	int i;
	og = pmap;
	//printf("%p\n", pmap);
	for(i=2;i<argc;i++){
		find(argv[i], pmap);
		//printf("%p\n", pmap);
		pmap = og;
	}
	return 0;
}


void find(char * search_word, void * p){
	int x = strcmp(search_word, word(p));
	uint32_t offset = -1;
	//printf("p: %p\n", p);
	if(x == 0){
		print(p);
		return;
	}	
	else if(x > 0){
		offset = *(int*)(p + RIGHT_CHILD);
	}
	else{
		offset = *(int*)(p + LEFT_CHILD);
	}
	if(!offset){
		printNotFound(search_word);
	}
	else{

		p = getChild(p, offset);
		//printf("p: %p\t offset: %u ", p, offset);
		find(search_word, p);
	}

}


void errorMes(int e, int line){
	if(e == 1)
		printf("When running this program please use the format:\nLookup1 <data_file> <keyword> [<keyword> ...]\n");
	else if(e == 2)
		printf("File not found\n");
	else if(e == 3)
		printf("Data file is not a binary search tree\n");
	else{
		if(e == 4)
			printf("fseek failed to find array data location\n");
		if(e == 5)
			printf("fread failed to read struct data\n");	
		if(e == 6)
			printf("getline failed to read the word\n");
		printf("Error at line %d", line);
	}
	exit(e);
}

void print(void * f){
	printf("%s: %u at $%.2f\n", word(f), *getU32(f, COUNT), *price(f));	
}

void printNotFound(char * word){
	printf("%s not found\n", word);
}


uint32_t * getU32(void * f, int offset){
	return f + offset;
}

float * price(void * f){
	return f + PRICE;
}

char * word(void * f){
	return f + WORD;
}

void * getChild(void * f, int child){
	return og + child - 4;
	
}

