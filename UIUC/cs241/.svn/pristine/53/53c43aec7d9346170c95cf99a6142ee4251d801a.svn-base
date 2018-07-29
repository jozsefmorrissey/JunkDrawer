// Shell
// CS 241 Fall 2015
#define _GNU_SOURCE

#include "log.h"
//#include "vector.h"
//#include <time.h>
#include <stdlib.h>
//#include <stdio.h>
//#include <unistd.h>
//#include <sys/wait.h>
//#include <sys/types.h>
#include <string.h>
//#include <tgmath.h>

Log *Log_create(const char *filename) { 
	Log * yhippie = malloc(sizeof(Log*));
	yhippie->vtr = Vector_create();
	
	size_t lim = 0;
	char *newb = NULL;
	FILE *file = fopen(filename, "r");

	if(file != NULL){
		while(getline(&newb, &lim, file) != EOF){
			newb[strlen(newb) - 1] = 0;
			Vector_append(yhippie->vtr, newb);
			//free(newb);
		}
		free(newb);
		fclose(file);	
	}
	//free(file);

	return yhippie; 
}

int Log_save(Log *log, const char *filename) { 
	FILE * file;
	if(filename == NULL)
		return 1;

	file = fopen(filename, "w");

	if(file == NULL)
		return 1;

	for(size_t i = 0; log->vtr->size > i; i++)
		fprintf(file, "%s\n", Vector_get(log->vtr, i));

	fclose(file);

	return 0; 
}

void Log_destroy(Log *log) {
	Vector_destroy(log->vtr);
	free(log);
}

void Log_add_command(Log *log, const char *command) {
	Vector_append(log->vtr, command);
}

const char *Log_reverse_search(Log *log, const char *prefix) { 
	for(size_t i = log->vtr->size - 1; (int)i > -1; i--){
		char * tester = malloc(strlen(Vector_get(log->vtr, i)) + 1);
		strcpy(tester, Vector_get(log->vtr, i));
		if(tester != NULL && prefix != NULL && strstr(tester, prefix) == tester){
			free(tester);
			return Vector_get(log->vtr, i);
		}
		free(tester);
	}
	printf("No Match\n");
	return NULL; 
}

const char *Log_get_command(Log *log, size_t index) {
return Vector_get(log->vtr, index); 
}

char *Log_get_printable_history(Log *log) {
	char * compilation = "";
	char * oneLINEatAtime = "";
	char * intermediate = "";

	for(size_t i = 0; i < log->vtr->size; i++){
		oneLINEatAtime = malloc(strlen(Vector_get(log->vtr, i)) +1);
		strcpy(oneLINEatAtime, Vector_get(log->vtr, i));
		intermediate = compilation;

		size_t compSize = strlen(oneLINEatAtime) + 1 + strlen(intermediate) + 100;
		compilation = malloc(compSize);
		snprintf(compilation, strlen(oneLINEatAtime) + 1 + strlen(intermediate) + 50, "%s%zu\t%s\n",intermediate, i, oneLINEatAtime);
		if( i > 0)
			free(intermediate);
		free(oneLINEatAtime);
	}

return compilation; 
}













