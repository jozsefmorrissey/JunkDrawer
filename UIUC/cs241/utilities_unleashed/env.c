#define _XOPEN_SOURCE 700
#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <string.h>

extern char **environ;

int main(int argc, char *argv[])
{
//execvp(argv[2], &argv[1]);

//printf("%d\n", argc);
	if(argc < 2){
		int i = 0;
		while(environ[i])
			printf("%s\n", environ[i++]);

	}
	else{
	
		char virginSacrafice[strlen(argv[1]) + 1];
		strcpy(virginSacrafice, argv[1]);
		int numStrings = 1;	

		char * garbage = strtok(virginSacrafice, ",");
		while(garbage != NULL){
			garbage = strtok(NULL, ",");
			numStrings++;
	//printf("%s\n", garbage);
		}

		char * strings[numStrings +1];

		strings[0] = strtok(argv[1], ",");

		int count = 0;
		while(strings[count] != NULL)
			strings[++count] =  strtok(NULL, ",");

			for(int i = 0; i < count; i++){
				//printf("i = %d\tstrings: %s\n", count, strings[i]);
				putenv(strings[i]);
			}
	
			int sizeargs=0;
			for(int i = 3; i < argc; i++)
				sizeargs += (int)strlen(argv[i]) + 1;

			char ** args = malloc(sizeargs);
			for(int i = 1; i < argc-1; i++)
				args[i-1] = argv[i];

			execvp(argv[argc-1], args);

	
			int stat;
			wait(&stat);
	}
  return 0;
}
