// Shell
// CS 241 Fall 2015
#include "vector.h"
//#include <time.h>
#include <stdlib.h>
#include <stdio.h>
//#include <unistd.h>
//#include <sys/wait.h>
//#include <sys/types.h>
#include <string.h>

Vector *Vector_create() {
	Vector *hereYouGo_haveANiceDay = malloc(sizeof(Vector));

	hereYouGo_haveANiceDay->array = malloc(100*sizeof(char*));
	hereYouGo_haveANiceDay->size = 0;
	hereYouGo_haveANiceDay->capacity = 100;

return hereYouGo_haveANiceDay; 
}

void Vector_destroy(Vector *vtr) {
	for(int i = 0; i < (int)vtr->size; i++)
		if(vtr->array[i] != NULL)
			free(vtr->array[i]);
	free(vtr->array);
	free(vtr);
}

int Vector_size(Vector *vtr) {
 	return (int)vtr->size; 
}

void Vector_resize(Vector *vtr, size_t new_size) {
	if(vtr->size == new_size)
		return;
	if(vtr->size > new_size){
		for(int i = new_size; i < (int)vtr->size; i++){
			if(vtr->array[i] != NULL){
				free(vtr->array[i]);
				vtr->array[i] = NULL;
			}
		}
		if(new_size < vtr->size/10)
			decreaseCapacity(vtr);
	}
	else{
		if(new_size > vtr->capacity)
			increaseCapacity(vtr, new_size);
		for(int i = vtr->size; i < (int)new_size; i++){
			//vtr->array[i] = malloc(sizeof(char*));
			vtr->array[i] = NULL;
		}
	}
	vtr->size = new_size;
}

void Vector_set(Vector *vtr, size_t index, const char *str) {
	if((int)index > -1 && index < vtr->size){
		if(vtr->array[index] != NULL)		
			free(vtr->array[index]);
		vtr->array[index] = NULL;
		if(str != NULL){
			vtr->array[index] = malloc((int)strlen(str) + 1);
			strcpy(vtr->array[index], str);
		}
	}
	else
		printf("INVALID INDEX ENTERED.\n");
}

const char *Vector_get(Vector *vtr, size_t index) {	
	if((int)index > -1 && (int)vtr->size > (int)index)
		return vtr->array[index];
	else{
		printf("Invalid Index\n");
		return NULL; 
	}
}

void Vector_append(Vector *vtr, const char *str) {
	if(++vtr->size == vtr->capacity)
		increaseCapacity(vtr, vtr->size);
	if(str != NULL){
		vtr->array[vtr->size - 1] = malloc(strlen(str) + 1);
		strcpy(vtr->array[vtr->size -1], str);
	}
}

void increaseCapacity(Vector *vtr, int reqSize){
	size_t oldC = vtr->capacity;
	while((int)vtr->capacity < reqSize + 1)
		vtr->capacity *= 3;
	char ** ptr = NULL;
	//printf("Cap: %zu\treqSize: %d\n", vtr->capacity, reqSize);
	for(int i = 0; i <100; i++){
	 	ptr = realloc(vtr->array, vtr->capacity*sizeof(char*));
		if(ptr != NULL)
			break;
		//printf("Round: %d\tCap: %zu\n", i, vtr->capacity);
	}
	if(ptr == NULL){
		printf("FAILED TO ALLOCATE MEMORY\n");
		exit(1);
	}
	else{
		//printf("%d\t", Vector_size(vtr));
		vtr->array = ptr;
		for(int i = oldC; i < (int)vtr->capacity; i++)
			vtr->array[i] = NULL;
	}

}

void decreaseCapacity(Vector *vtr){
	//size_t oldC = vtr->capacity;
	vtr->capacity /= 2;
	char ** ptr = NULL;
	for(int i = 0; i <100; i++){
	 	ptr = realloc(vtr->array, vtr->capacity*sizeof(char*));
		if(ptr != NULL)
			break;
		//printf("Round: %d\t", i);
	}
	if(ptr == NULL){
		printf("FAILED TO ALLOCATE MEMORY\n");
		exit(1);
	}
	else{
		vtr->array = ptr;
	}
}












