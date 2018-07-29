#include <stdio.h>
#include "common.h"
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>

int main(int argc, char * argv[]) {
    // open all the files for output
	FILE * out[argc - 1];
	int i = 0;
	for(; i<argc - 1; i++){
		out[i] = fopen(argv[i + 1], "a");
		if(out[i] == NULL)
			fprintf(stderr, "Failed to open %s in shuffler", argv[i]);
	}
	FILE * test = fopen("test.txt", "a");
    // read lines from stdin, hash them, send them to the correct output
	char * line = NULL;
	size_t size = 0;
	int bytes;
	while((bytes = getline(&line, &size, stdin)) != EOF){
		char temp[bytes + 1];
		strcpy(temp, line);
		strtok(temp, ":");
		
		int has = hashKey(temp);
		int mod = has % (argc - 1);
		int err = fprintf(stderr, "hash: %d\t mod: %d\t LINE: '%s'\n", has, mod, line);
		fprintf(out[mod], "%s", line);
		fprintf(test, "%s", line);
		

		if(err == -1){
			fprintf(stderr, "Faild to write in shuffler at line %d\n", __LINE__);

		}
	}
fflush(NULL);

    // each line is expected to be in "<key>: <value>" form

    return 0;
}
