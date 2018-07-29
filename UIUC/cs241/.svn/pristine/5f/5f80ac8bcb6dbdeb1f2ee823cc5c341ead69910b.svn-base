// Shell
// CS 241 Fall 2015
#include "log.h"
// Create and test your log here
int main() { 
	Log * log = Log_create("CreateSomthing");
	for(size_t i = 0; i < 200; i ++){
		char * line = malloc(19);
		snprintf(line, 19, "This is line:\t%d", (int)i);
		Log_add_command(log, line);
		free(line);
	}
	//printf("%s",Log_get_command(log, 0));
	Log_save(log, "one");
	Log_save(log, "one");
	//Log_save(log, "two");
	Log_save(log, "two");

	//printf("%s", Log_reverse_search(log, "This line is by far"));
	Log_reverse_search(log, "This line is by far");

	char * hereWEgo = Log_get_printable_history(log);
	printf("%s", hereWEgo);
	free(hereWEgo);


	//printf("%s",Log_reverse_search(log, "that"));
	Log_reverse_search(log, "that");

	Log_destroy(log);

return 0; 
}
