#include "python2.7/Python.h"

int main(int argc, char * argv[])
{
	char * filename = malloc(50);
	strcpy(filename, "/usr/bin/python PYcode/draw_dock.py");
   // char * command = "/usr/bin/python ";
    //command += filename;

	int i = 1;
	for(; i< argc; i++){
		strcat(filename, " ");
		strcat(filename, argv[i]);
	}

	FILE* in = popen(filename, "r");
   	 
    if(in == NULL)
    	printf("Failed\n");
    pclose(in);
    return 0;
 }

