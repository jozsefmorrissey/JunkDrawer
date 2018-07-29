#define _GNU_SOURCE
#include "cipher.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <unistd.h>


#define NUMBER_OF_PROCESSES (40)

//sem_t children;

int main(int argc, char *argv[]) {
	if(argc == 1){
		printf("When running this program please use format:\nbrutus <input_file_path> <output_file_path>\n");
		exit(1);
	}

	pid_t children[NUMBER_OF_PROCESSES];
	int pip[NUMBER_OF_PROCESSES][2];	

	FILE * source = fopen(argv[1], "r");
	//FILE * dest = fopen(argv[2], "w");

/*	int i;
	for(i = 0; i < NUMBER_OF_PROCESSES; i++){

	}
*/	
	size_t len = 0;
	char * line;
	int i = 0;
	//printf("input file: %s\noutput file: %s\n", argv[1], argv[2]);
	while(getline(&line, &len, source) != EOF){
		//printf("%s", line);
		pipe(pip[i]);
		children[i] = fork();
		if(children[i] == 0){
			close(pip[i][0]);

			//printf("This is your child Reporting:\n");
			char * answer = malloc(len);
			answer = get_most_likely_print_out(line);
			write(pip[i][1], answer, 4096);
			free(answer);
			free(line);
			fclose(source);
			close(pip[i][1]);
			exit(1);
		}
		else{
			close(pip[i][1]);
			//printf("%d\n", i);
		}
		i++;
	}
	FILE * out = fopen(argv[2], "w");
	fclose(out);
	fclose(source);
	free(line);

	out = fopen(argv[2], "a");
	//printf("here\n");
	int max = i;
	for(i = 0; i<max ; i++){
		//printf("reading from pipe %d\n", i);
		char * buff = malloc(4096);
		int bytes;
		bytes = read(pip[i][0], buff, 4096);
		

		fprintf(out, "%s", buff);
		//fseek(out, bytes, SEEK_SET);
		//printf("%s", buff);
		close(pip[i][0]);
	}
	fclose(out);
    return 0;
}
	
























