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
void forkfor(int max, pid_t * children);
void makeFifos(int max_mapper, void * args);
void remove_fifos(char ** fifs, int max);
void check_dup(int err, int line);
void check_fopen(FILE * error, int line);

#define INPUT_FILE 		(1)
#define OUTPUT_FILE 	(2)
#define MAPPER_EX		(3)
#define REDUCER_EX		(4)
#define MAPPER_COUNT	(5)
#define REDUCER_COUNT	(6)

#define READ			(0)
#define WRITE			(1)

int main(int argc, char * argv[]) {
	if(argc == -1 && argv[0] != NULL){///////////////////fix
		printf("When running mr0 please use format:	\n");
		printf("mr2 <input_file> <output_file> <mapper_executable> <reducer_executable> <mapper_count> <reducer_count>\n");
		return 1;
	}
	int number_of_mappers = atoi(argv[MAPPER_COUNT]);
	int number_of_reducers = atoi(argv[REDUCER_COUNT]);
	int err;
	FILE * error;
	void ** args = malloc(10*(number_of_reducers + 2) + number_of_reducers*sizeof(char*)+1);
	*(args + number_of_reducers*sizeof(char*)) = NULL;
	/*makeFifos(number_of_reducers, args);
	int k = 0;
	for(; k< number_of_reducers + 3; k++)
	printf("%p\n", (char*)*(args + k));
	return 1;
	*/
    // Create an input pipe for each mapper.

	

	int splitter_mapper_pipe[number_of_mappers][2];
	int counter;
	for(counter = 0; counter < number_of_mappers; counter++){
		pipe(splitter_mapper_pipe[counter]);
	}


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
			err = dup2(splitter_mapper_pipe[counter][WRITE], fileno(stdout));
			check_dup(err, __LINE__);
			char string_int[15];
			sprintf(string_int, "%d", counter);
			int status = execl("./splitter", "./splitter", argv[INPUT_FILE], argv[MAPPER_COUNT], string_int, NULL);
			perror("Splitter error: ");
			exicute_error(counter, __LINE__, status);
		}
	}
fflush(NULL);


    // Create one input pipe for the shuffler.
	int mapper_shuffler_pipe[2];
	pipe(mapper_shuffler_pipe);

    // Start the mappers.

	pid_t mapper_pids[number_of_mappers];
	for(counter = 0; counter < number_of_mappers; counter++){
		mapper_pids[counter] = fork();
		if(!mapper_pids[counter]){
			err = dup2(mapper_shuffler_pipe[WRITE], fileno(stdout));
			check_dup(err, __LINE__);
			int index = 0;
			for(index = 0; index < number_of_mappers; index++){
				if(index != counter)
					close(splitter_mapper_pipe[index][READ]);
				close(splitter_mapper_pipe[index][WRITE]);
			}
			err = dup2(splitter_mapper_pipe[counter][READ], fileno(stdin));
			check_dup(err, __LINE__);
			close(mapper_shuffler_pipe[READ]);
			int status = execl(argv[MAPPER_EX], argv[MAPPER_EX], NULL);
			exicute_error(counter, __LINE__, status);
		}
	}

	//Close all splitter to mapper pipes.
	for(counter = 0; counter < number_of_mappers; counter++){
		close(splitter_mapper_pipe[counter][READ]);
		close(splitter_mapper_pipe[counter][WRITE]);
	}

    // Create a fifo file for each reducer.
	makeFifos(number_of_reducers, args);

    // Start the shuffler.
	pid_t shuffler_pid = fork();
	if(!shuffler_pid){
		close(mapper_shuffler_pipe[WRITE]);
		err = dup2(mapper_shuffler_pipe[READ], fileno(stdin));
		check_dup(err, __LINE__);
		fclose(stdout);
		//freopen(argv[OUTPUT_FILE], "w", stdout);
		int status = execv("shuffler", (char *const*)args);
		exicute_error(counter, __LINE__, status);
	}
		close(mapper_shuffler_pipe[WRITE]);
		close(mapper_shuffler_pipe[READ]);

    // Open the output file.
	fclose(stdout);
	error = freopen(argv[OUTPUT_FILE], "w", stdout);
	check_fopen(error, __LINE__);
    // Start the reducers.
	pid_t reducer_pids[number_of_reducers];

	for(counter = 0; counter < number_of_reducers; counter++){
		reducer_pids[counter] = fork();

		if(!reducer_pids[counter]){

			//fclose(stdin);
			error = fopen(args[counter + 1], "r");
			check_fopen(error, __LINE__);
			//fprintf(stderr, "file number: %d\n", fileno(fi));
			if(fileno(error) == -1)
				perror("Reducer attempted to open");
			
			err = dup2(fileno(error), fileno(stdin));
			check_dup(err, __LINE__);
			


	char ex[20] = "./";
	strcat(ex, argv[REDUCER_EX]);

			int status = execv(ex, (char* const*)args);
			exicute_error(counter, __LINE__, status);
		}
	}

    // Wait for all the reducers to finish.
	// Wait for the splitter to finish.
	wait_loc(&splitter_pids[0], number_of_mappers, "splitter");

	// Wait for the mapper to finish.
	wait_loc(&mapper_pids[0], number_of_mappers, "my_mapper");

	//wait for the suffler to finish
	//wait_loc(&shuffler_pid, 1, "my_shuffler");
	//perror("shuffler: ");
    // Wait for the reducer to finish.
	wait_loc(&reducer_pids[0], number_of_reducers, "my_reducer");

    // Remove the fifo files.
	remove_fifos((char**)args, number_of_reducers);

    // Print nonzero subprocess exit codes.


    // Count the number of lines in the output file.
	countlines(argv);

    return 0;
}


void test_return_value(FILE * check, int line){
	if(check == NULL){
		fprintf(stderr, "Failed to open a file at line %d\n", line);
		perror("ERROR: ");
		exit(2);
	}

}


void check_dup(int err, int line){
	if(err == -1)
		fprintf(stderr, "Dup failed at line %d\n", line);
}


void check_fopen(FILE * error, int line){
	if(error == NULL)
		fprintf(stderr, "fopen failed at line %d\n", line);
	
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
	fprintf(stderr, "%s has finished\n", member);

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

void forkfor(int max, pid_t * children){
	int i = 0;
	for(; i < max; i++){
		*(children + i) = fork();
	}
}


void makeFifos(int max_mapper, void * args){
	char Split[10] = "Split";
	int i;
	char ** list = args;
	char * explorer = (args + (max_mapper+2)*sizeof(char*) + 1);
	sprintf(explorer, "splitter");
	*list = explorer;
	explorer+= strlen(explorer) + 1;
	
	
	for(i = 1; i < max_mapper + 1; i++){
		char ichar[3];
		sprintf(ichar, "%d",i - 1);
		strcat(Split, ichar);
		sprintf(explorer, "%s", Split);
		remove(Split);
		mkfifo(Split, S_IRWXU | ENXIO);
		strcpy(Split, "Split");

		*(list + i) = explorer;
		explorer += strlen(explorer) + 1;
	}
}


void remove_fifos(char ** fifs, int max){
	int i= 0;
	for(; i < max; i++){
		//FILE * fi = fopen(fifs[i + 1], "w");
		//int err = remove(fi);
		int err = unlink(fifs[i + 1]);

		if(err){
			fprintf(stderr, "removing: '%s'\t %d\n", fifs[i+1], err);
			perror("idk:");
		}
	}
}













