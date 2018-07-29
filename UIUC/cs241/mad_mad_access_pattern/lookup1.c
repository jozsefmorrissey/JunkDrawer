#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "tree.h"

void errorMes(int e, int line);
uint32_t getU32(FILE * f, int offset);
float price(FILE * f);
char* word(FILE * f);
void getChild(FILE * f, int child);
void find(char * search_word, FILE * f);

void printNotFound(char * word);
void print(FILE * f);

#define LEFT_CHILD 0
#define RIGHT_CHILD 4
#define COUNT 8
#define PRICE 12
#define WORD 16

int main(int argc, char * argv[]){

	if(argc < 3)
		errorMes(1, __LINE__);
	FILE * file = fopen(argv[1], "r");

	if(!file)
		errorMes(2, __LINE__);
	char fileType[5];
	fileType[4] = 0;

	fread(&fileType, 1, 4, file);


	if(strcmp(fileType, "BTRE"))
		errorMes(3, __LINE__);

/*
	uint32_t lc = getU32(file, LEFT_CHILD);
	uint32_t rc = getU32(file, RIGHT_CHILD);
	uint32_t count = getU32(file, COUNT);
	float p = price(file);
	char * w = word(file);

	printf("lc: %u\t rc: %u\t count: %u\t price: %.2f\t word: %s\n", lc, rc, count, p, w);



	getChild(file, LEFT_CHILD);

	lc = getU32(file, LEFT_CHILD);
	rc = getU32(file, RIGHT_CHILD);
	count = getU32(file, COUNT);
	p = price(file);
	w = word(file);

	printf("lc: %u\t rc: %u\t count: %u\t price: %.2f\t word: %s\n", lc, rc, count, p, w);

	getChild(file, RIGHT_CHILD);

	lc = getU32(file, LEFT_CHILD);
	rc = getU32(file, RIGHT_CHILD);
	count = getU32(file, COUNT);
	p = price(file);
	w = word(file);

	printf("lc: %u\t rc: %u\t count: %u\t price: %.2f\t word: %s\n", lc, rc, count, p, w);
*/
	int i;
	for(i = 2; i < argc; i++){
		find(argv[i], file);
		fseek(file, 4, SEEK_SET);
	}

	//free(w);

	return 0;
}
void find(char * search_word, FILE * f){
	int x = strcmp(search_word, word(f));
	uint32_t offset = -1;
	if(x == 0){
		print(f);
		return;
	}	
	else if(x > 0){
		offset = RIGHT_CHILD;
	}
	else{
		offset = LEFT_CHILD;
	}
	if(!getU32(f, offset)){
		printNotFound(search_word);
	}
	else{
		getChild(f, offset);
		find(search_word, f);
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

void print(FILE * f){
	printf("%s: %u at $%.2f\n", word(f), getU32(f, COUNT), price(f));	
}

void printNotFound(char * word){
	printf("%s not found\n", word);
}


uint32_t getU32(FILE * f, int offset){
	int err = fseek(f, offset, SEEK_CUR);
	if(err)
		errorMes(4, __LINE__);
	uint32_t retVal;
	err = fread(&retVal, sizeof(uint32_t), 1, f);
	if(!err)
		errorMes(5, __LINE__);
	fseek(f, -offset - sizeof(uint32_t), SEEK_CUR);
	return retVal;
}

float price(FILE * f){
	int err = fseek(f, PRICE, SEEK_CUR);
	if(err)
		errorMes(4, __LINE__);
	float price;
	err = fread(&price, sizeof(float), 1, f);
	if(!err)
		errorMes(5, __LINE__);
	fseek(f, -PRICE - sizeof(float), SEEK_CUR);
	return price;
}

char* word(FILE * f){
	int err = fseek(f, WORD, SEEK_CUR);
	if(err)
		errorMes(4, __LINE__);
	char * word = malloc(8);
	size_t size = 0;
	err = getdelim(&word, &size, (char)0, f);
	//printf("getdelim: %ld\n", ftell(f));
	if(!err)
		errorMes(6, __LINE__);
	fseek(f, -WORD - err, SEEK_CUR);
	//printf("prince: %d\t size: %d\t getdelim2: %ld\n",PRICE, err, ftell(f));
	return word;
}

void getChild(FILE * f, int child){
	//size_t start = (size_t)f;
	//printf("child: %d\t cur pos: %ld\n", child, ftell(f));
	uint32_t offset = getU32(f, child);
	//if(offset == 0)
		
	fseek(f, (long)offset, SEEK_SET);
	
}


















