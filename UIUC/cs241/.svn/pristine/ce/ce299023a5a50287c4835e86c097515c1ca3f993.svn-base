// Shell
// CS 241 Fall 2015
#include "vector.h"
#include <time.h>
#include <stdlib.h>
#include <stdio.h>
//#include <unistd.h>
//#include <sys/wait.h>
//#include <sys/types.h>
#include <string.h>
// Create and test your vector here

int byOne(int inc);
int changeSize(int inc);
int halfIt(Vector * vtr);
int edgeCases();
int return0(Vector * vtrkey, Vector * vtr);

int main() { 	

	time_t t;
	srand((unsigned) time(&t));
	
	int ret = 1;
	ret = ret && byOne(1);

	ret = ret && changeSize(2);
	ret = ret && changeSize(5);
	ret = ret && changeSize(3);

	ret = ret && edgeCases();

	if(ret)
		printf("SUCCESS\n");
	else
		printf("FAILURE\n");

return 0; }


int byOne(int inc){
	Vector * addmadness = Vector_create();
	int predictedSize = 1;
	for(int i = 0; i < 4000; i+=inc){
		Vector_append(addmadness, NULL);
		if(predictedSize != (int)addmadness->size || addmadness->array[i] != NULL){
			Vector_destroy(addmadness);
			return 0;
		}
		predictedSize++;
	}
	halfIt(addmadness);
	Vector_destroy(addmadness);
	return 1;
}

int changeSize(int inc){
	Vector * vtr = Vector_create();
	Vector_resize(vtr, inc);
	//printf("%d\n", vtr->size);
	size_t predictedSize = vtr->size*inc;
	for(int i = 0; i < 10; i++){
		Vector_resize(vtr, vtr->size*inc);
	//printf("\nhere it is: %d\t%d\n", vtr->size, predictedSize);
		if(predictedSize != vtr->size){
			Vector_destroy(vtr);
			return 0;
		}
		predictedSize = vtr->size*inc;
	}
	halfIt(vtr);
	Vector_destroy(vtr);
	return 1;
}

int halfIt(Vector * vtr){
	while(vtr->size > 0){
		size_t oldS = vtr->size;
		Vector_resize(vtr, vtr->size/2);
		if(oldS/2 != vtr->size){
			//printf("size: %d\tpsize: %d\n", oldS/2, vtr->size);
			Vector_destroy(vtr);
			return 0;
		}	
		
	}
	return 1;
}

int edgeCases(){

	Vector * vtr = Vector_create();
	Vector * vtrkey = Vector_create();

	Vector_append(vtr, "one");

	if(strcmp(vtr->array[0], "one"))
		return return0(vtrkey, vtr);

	Vector_resize(vtr, 1000);
	Vector_set(vtr, 999, "i WONder If thIs will Break?");
	if(strcmp(Vector_get(vtr, 999), "i WONder If thIs will Break?"))
		return return0(vtrkey, vtr);

	Vector_resize(vtr, 0);
	if(vtr->array[0] != NULL || vtr->array[999] != NULL)
		return return0(vtrkey, vtr);

	Vector_resize(vtr, 10000);
	Vector_resize(vtrkey, 10000);

	if(Vector_get(vtr, -1) != NULL || Vector_get(vtr, 1001) != NULL)
		return return0(vtrkey, vtr);

	Vector_set(vtr, 0, "This will be the first of five randomized sentences that i will create.");
	Vector_set(vtr, 1, "I wonder if all this testing is worth the effort?");
	Vector_set(vtr, 2, "I doubt it seeing as my program hasnt failed any of my tests yet.");
	Vector_set(vtr, 3, "What ever i dont want to do any thing else right now.");
	Vector_set(vtr, 4, "This way i atleast feal productive.");

	Vector_set(vtrkey, 0, "This will be the first of five randomized sentences that i will create.");
	Vector_set(vtrkey, 1, "I wonder if all this testing is worth the effort?");
	Vector_set(vtrkey, 2, "I doubt it seeing as my program hasnt failed any of my tests yet.");
	Vector_set(vtrkey, 3, "What ever i dont want to do any thing else right now.");
	Vector_set(vtrkey, 4, "This way i atleast feal productive.");

	for(size_t i = 5; i < vtr->size; i++){
		int index = rand()%5;

		Vector_set(vtr, i, vtr->array[index]);
		Vector_set(vtrkey, i, vtr->array[index]);

	}

	for(int j = 0; j < 5; j++){
		Vector_resize(vtr, vtr->size/2);
		for(size_t i = 0; i < vtr->capacity; i++){

			if(i > vtr->size - 1){
				if(vtr->array[i] != NULL)
					return return0(vtrkey, vtr);
			}
			else{
				if(strcmp(vtr->array[i], vtrkey->array[i]))
					return return0(vtrkey, vtr);
			}
		}
	}
	Vector_destroy(vtr);
	Vector_destroy(vtrkey);

	return 1;
}

int return0(Vector * vtrkey, Vector * vtr){
	Vector_destroy(vtr);
	Vector_destroy(vtrkey);
	return 0;
}
	
	














