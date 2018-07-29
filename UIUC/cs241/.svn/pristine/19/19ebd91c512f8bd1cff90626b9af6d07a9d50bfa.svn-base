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
#include <errno.h>

void exicute_error(int itteration, int line, int status);
void test_return_value(FILE * check, int line);
void wait_loc(pid_t * pids, int size, char * member);
void countlines(char * argv[]);
void print(char * id, FILE * file);

#define INPUT_FILE 		(1)
#define OUTPUT_FILE 	(2)
#define MAPPER_EX		(3)
#define REDUCER_EX		(4)
#define MAPPER_COUNT	(5)

#define READ			(0)
#define WRITE			(1)

int main(int argc, char * argv[]) {
	if(argc < 6){///////////////////fix
		printf("When running mr0 please use format:	\n");
		printf("mr2 <input_file> <output_file> <mapper_executable> <reducer_executable> <mapper_count>\n");
		return 1;
	}

    // Create an input pipe for each mapper.
	int number_of_mappers = atoi(argv[MAPPER_COUNT]);

	int splitter_mapper_pipe[number_of_mappers][2];
	int counter;
	for(counter = 0; counter < number_of_mappers; counter++){
		pipe(splitter_mapper_pipe[counter]);
	}

    // Open the input file.
	fclose(stdin);
	FILE * check = freopen(argv[INPUT_FILE], "r", stdin);
	test_return_value(check, __LINE__);

    // Start a splitter process for each mapper.
	pid_t splitter_pids[number_of_mappers];

	for(counter = 0; counter < number_of_mappers; counter++){
		splitter_pids[counter] = fork();

		if(!splitter_pids[counter]){
			int index = 0;
			for(index = 0; index < number_of_mappers; index++){
				if(index != counter)
					close(splitter_mapper_pipe[index][WRITE]);
				close(splitter_mapper_pipe[index][READ]);
			}
			dup2(splitter_mapper_pipe[counter][WRITE], fileno(stdout));
			char string_int[15];
			sprintf(string_int, "%d", counter);
			int status = execl("./splitter", "./splitter", argv[INPUT_FILE], argv[MAPPER_COUNT], string_int, NULL);
			exicute_error(counter, __LINE__, status);
		}
	}
fflush(NULL);

    // Create one input pipe for the reducer.
	int mapper_reducer_pipe[2];
	pipe(mapper_reducer_pipe);

    // Start all the mapper processes.

	pid_t mapper_pids[number_of_mappers];
	for(counter = 0; counter < number_of_mappers; counter++){
		mapper_pids[counter] = fork();
		if(!mapper_pids[counter]){
			dup2(mapper_reducer_pipe[WRITE], fileno(stdout));
			int index = 0;
			for(index = 0; index < number_of_mappers; index++){
				if(index != counter)
					close(splitter_mapper_pipe[index][READ]);
				close(splitter_mapper_pipe[index][WRITE]);
			}
			dup2(splitter_mapper_pipe[counter][READ], fileno(stdin));
			close(mapper_reducer_pipe[READ]);
			int status = execl(argv[MAPPER_EX], argv[MAPPER_EX], NULL);
			exicute_error(counter, __LINE__, status);
		}
	}

	//Close all splitter to mapper pipes.
	for(counter = 0; counter < number_of_mappers; counter++){
		close(splitter_mapper_pipe[counter][READ]);
		close(splitter_mapper_pipe[counter][WRITE]);
	}


    // Start the reducer process.

	pid_t reducer_pid = fork();
	if(!reducer_pid){
		close(mapper_reducer_pipe[WRITE]);
		dup2(mapper_reducer_pipe[READ], fileno(stdin));
		fclose(stdout);
		freopen(argv[OUTPUT_FILE], "w", stdout);
		int status = execl(argv[REDUCER_EX], argv[REDUCER_EX], NULL);
		exicute_error(counter, __LINE__, status);
	}

	close(mapper_reducer_pipe[WRITE]);
	close(mapper_reducer_pipe[READ]);
	// Wait for the splitter to finish.
	wait_loc(&splitter_pids[0], number_of_mappers, "splitter");

	// Wait for the mapper to finish.
	wait_loc(&mapper_pids[0], number_of_mappers, "my_mapper");

    // Wait for the reducer to finish.
	wait_loc(&reducer_pid, 1, "my_reducer");

    // Print nonzero subprocess exit codes.

    // Count the number of lines in the output file.
	countlines(argv);	
	
    return 0;
}

void test_return_value(FILE * check, int line){
	if(check == NULL){
		fprintf(stderr, "Failed to open a file at line %d\n", line);
		exit(2);
	}

}


void exicute_error(int itteration, int line, int status){
	fprintf(stderr, "Exicute failed at line %d on itteration %d with status %d\n", line, itteration, status);
	exit(1);
}


void wait_loc(pid_t * pids, int size, char * member){
	int index;
	for(index = 0; index < size; index++){
		int status = -1;
		waitpid(*(pids + index), &status, WIFEXITED(status));
		if(status != 0)
			fprintf(stderr, "%s %i exited with status %d\n", member, index, status);
	}

}

void countlines(char * argv[]){
	fclose(stdin);
	stdin = fopen(argv[OUTPUT_FILE], "r");
	char * line = NULL;
	size_t size = 0;
	int i = 0;
	while(getline(&line, &size, stdin) != EOF){
		i++;
	}
	fprintf(stderr, "output pairs in %s: %d\n", argv[OUTPUT_FILE], i);
	free(line);
}

void print(char * id, FILE * file){
	char * line = NULL;
	size_t size = 0;
	while(getline(&line, &size, file) != EOF){
		printf("%s", line);
		fprintf(stderr, "%s Reporting:\n%s", id, line);	
	}
}









