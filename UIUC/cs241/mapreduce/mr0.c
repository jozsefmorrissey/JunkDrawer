#include <stdio.h>
#include <stdlib.h>
#include <alloca.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <assert.h>
#include "common.h"

void print(char * id);


int main(int argc, char * argv[]) {
	
/*////////////////////////////////////////////////////////////////////////////////////////
	//FILE * file = fopen(argv[1], "w");
	argc++;
	//close(1);
	freopen(argv[2], "a", stdout);
	printf("did this appear inside the designated file\n\n\ny yes it did.");
	freopen(argv[1], "r", stdin);


	char * line = NULL;
	size_t size = 0;
	while(getline(&line, &size, stdin) != EOF){
		fprintf(stderr, "%s", line);
		printf("%s", line);
	}

	fprintf(stderr, "wtf mate\n");
	free(line);
	fflush(NULL);
	close(1);
	close(2);
	return 0;
//*/////////////////////////////////////////////////////////////////////////////////////////////


	if(argc < 5){///////////////////fix
		printf("When running mr0 please use format:\n");
		printf("mr2 <input_file> <output_file> <mapper_executable> <reducer_executable>\n");
		return 1;
	}
	FILE * varify;
    // Open the input file.
	fclose(stdin);
	varify = freopen(argv[1], "r", stdin);
	if(!varify){
		printf("Failed to open input file\n");
		return 3;
	}
	descriptors_add(fileno(varify));
    // Create a pipe to connect the mapper to the reducer.
	
	int pip[2];
	pipe(pip);
	#define READ pip[0]
	#define WRITE pip[1]
    
	// Start the mapper.
	varify = fdopen(WRITE, "w");
	if(!varify){
		printf("Failed to pipe in\n");
		return 4;
	}

	pid_t parent[2];
	parent[0] = fork();

	descriptors_add(fileno(varify));

	if(parent[0] == -1){
		perror("Failed to fork 1\n");
		return 2;
	}

	if(!parent[0]){
		descriptors_add(WRITE);
		close(READ);

		FILE * clear = fopen(argv[2], "w");
		fclose(clear);

		dup2(WRITE, fileno(stdout));
		execl(argv[3], argv[3], NULL);

		fprintf(stderr, "failed to execute mapper\n");
		exit(3);
	} 

	int status = -1;
	waitpid(parent[0], &status, WIFEXITED(status));
	if(status != 0)
		fprintf(stderr, "my_mapper exited with status %d\n", status);

	dup2(READ, fileno(stdin));
	parent[1] = fork();


	if(parent[1] == -1){
		perror("Failed to fork 2\n");
		return 2;
	}
	if(!parent[1]){
		descriptors_add(READ);
		close(WRITE);
		fclose(stdout);
		stdout = fopen(argv[2], "w");

		execl(argv[4], argv[4], NULL);
		print("Output fork");

		perror("failed to execute reducer\n");
		exit(3);
	}


	close(READ);
	close(WRITE);


	status = -1;
	waitpid(parent[1], &status, WIFEXITED(status));
	if(status != 0)
		fprintf(stderr, "my_reducer exited with status %d\n", status);
	descriptors_closeall();
    // Wait for the reducer to finish.

    // Print nonzero subprocess exit codes.

	// Count the number of lines in the output file.
	fclose(stdin);
	stdin = fopen(argv[2], "r");
	char * line = NULL;
	size_t size = 0;
	int i = 0;
	while(getline(&line, &size, stdin) != EOF){
		i++;
	}
	fprintf(stderr, "output pairs in %s: %d\n", argv[2], i);
	free(line);
	fclose(stdin);

    return 0;
}

void print(char * id){
	char * line = NULL;
	size_t size = 0;
	while(getline(&line, &size, stdin) != EOF){
		printf("%s", line);
		fprintf(stderr, "%s Reporting:\n%s", id, line);	
	}
}

















