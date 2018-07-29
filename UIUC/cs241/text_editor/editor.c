#define _GNU_SOURCE

#include "editor.h"

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <tgmath.h>


//Main is simply a junction that directs input to the proper function.
int main(int argc, char *argv[]) {

	char *filename = get_filename(argc, argv);
	struct node *begining = malloc(sizeof(data));
	//begining->current = malloc(sizeof(char*));
	//begining->nextline = malloc(sizeof(data));
	begining->current = NULL;
	begining->nextline = NULL;

	init(begining, filename);				//parses a file if it already exists
	int running = 1;						//flag used to terminate the program

	while(running==1){

		size_t numberBytes=0;
		char *parseVictim = NULL;			//input string
		size_t numChars = getline (&parseVictim, &numberBytes, stdin);
//printf("%d", __LINE__);
		int strstart = 0;
		int line = getint(parseVictim, &strstart);		//determins line number entered if any, and the begining of the string input

		switch(*parseVictim){
			case 'w':						//overwrite

				overwrite(begining, line, &parseVictim[strstart]);
				break;

			case 'q':						//quit
				running = 0;
				break;

			case 'p':						//print
				display(begining, line);
				break;

			case 'a':						//concatinate
				append(begining, line, &parseVictim[strstart]);
				break;

			case 's':						//save
				save(begining, filename);
				break;

			case '/':						//find
				find(begining, &parseVictim[strstart]);
				break;

			default:						//havent decided on a defualt case
				printf("You entered an invalid command, try again\n");
		}
		free(parseVictim);
	}
	destroy(begining);
 	return 1;
}

//returns filename
char *get_filename(int argc, char *argv[]) {
	int val = argc;//to prevent unused variable warnings
	val = 1;
  return argv[val];
}

//parces data assigning each string to a node in a linear sequence.
void init(data *data_ptr, char *filename) {
	FILE *filedata = NULL;
	size_t lim = 0;
	char *newb = NULL;		//string to contain the info from the file, line by line

	filedata = fopen (filename, "r");

	int count = 0;

	if(filedata != NULL){
		while(getline(&newb, &lim, filedata)!= EOF){
			//printf("size of newb: %d\t line number is: %d\n", (int)strlen(newb), count++);
			if(data_ptr == NULL)
				data_ptr = malloc(sizeof(data));
			if(strlen(newb) > 0){				// if there is a string assign it to data_ptr->current
				//printf("i am mallocing!!!!!!!!!!!!!!!!!!\t");
				data_ptr->current = malloc(strlen(newb)+1);
				strcpy(data_ptr->current, newb);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				//printf("%s\n", data_ptr->current);
			}
				data_ptr->nextline = malloc(sizeof(data));
				data_ptr = data_ptr->nextline;	//move to the next node for the next line 
				
				//printf("%p\n", data_ptr);
		}
		free(newb);
	}
	free(filedata);
	//fclose(filedata);
}

//determins the line int entered for spacific functions w, p, a
int getint(char *dat, int * strstart){
	if(dat[2] < 58 && dat[2]>47){ 				//if the 3rd char is not a number move on.
		int index = 2;							//the starting position for the numbers
		int lineNum = 0;						//return value
		double multiplier = 1;					//multiplier for the ones, tens, hundreds,... place
		int keepGoing = 1;						//flag to determine when there are no more numbers.
		int lim = 0;							//the number of digits in the number

		while(keepGoing == 1){					//simply counts the number of digits

			int curInt = (int)dat[index++];
			if(curInt < 58 && curInt>47){
				lim++;
			}
			else
				keepGoing = 0;
		}
		for(int i = lim; i > 1; i--)			//10^lim 
			multiplier *= 10;

		index = 2;
		for(int i = lim; i > 0; i--){			//adds up the integers with thier appropriate multiplier
			int curInt = (int)dat[index++];
			lineNum += (curInt - 48) * multiplier;
			multiplier /= 10;
		}
		*strstart = lim + 3;
												//printf("your number is: %d\n", lineNum);
		return lineNum;
	}
	else{
		*strstart = 1;
		return 0;
	}
}

//prints the line specified by the user
void display(data *data_ptr, int line) {
	int curline = 1;
	while(data_ptr != NULL && data_ptr->nextline!=NULL && curline < line){	//searches for line
		data_ptr = data_ptr->nextline;
		curline++;
	}
	if(data_ptr != NULL && data_ptr->current != NULL)						//prints line if it exists or error message
		printf("%d\t%s", line, data_ptr->current);
	else
		printf("There is no line number: %d\n", line);
}


//over writes a line specified by the user
void overwrite(data *data_ptr, int line, char *string) {
	data_ptr = locateline(data_ptr, line);
	data * neighbor = data_ptr->nextline;
	bool terminate = false;
//printf("%d", __LINE__);
	if(data_ptr->current != NULL)
		free(data_ptr->current);

	while(!terminate){
		char * explorerOftheSEVENseas = string;
		explorerOftheSEVENseas = strstr(string, "\\n");
		char * lollyLOLLYgagger = string;
		int lollyCount = 0;
	//printf("entereddddddddddddddddddddddddddddddddddddd");

		if(explorerOftheSEVENseas != NULL){
			while(lollyLOLLYgagger != explorerOftheSEVENseas)
				lollyCount++;

			for(int i = 0; i<lollyCount; i++)
				data_ptr->current[i] = explorerOftheSEVENseas[i];

			data_ptr->nextline = malloc(sizeof(data));
			data_ptr = data_ptr->nextline;

			string = &explorerOftheSEVENseas[lollyCount + 1];
		}
		else
			terminate = true;
	}
	
	
	data_ptr->current = malloc(sizeof(string));
	data_ptr->current = string;						//once line is located current is assigned the input string
	
	data * nextline = neighbor;
	
}


data * locateline(data * start, int line){
	int currentline = 1;
	while(line > currentline){						//climb through untill you find the line specified
		if(start == NULL)
			start = malloc(sizeof(data));
		
		if(start->current == NULL){				//if there is no string in current alocate space and make it a return char
			start->current = malloc(sizeof(char));
			start->current = "\n";
		}
		if(start->nextline == NULL){
			start->nextline = malloc(sizeof(data));
			start->nextline->nextline == NULL;
			start->nextline->current == NULL;
		}

		start=start->nextline;				//move to the next node that represents next line
		//start->nextline = malloc(sizeof(data));
 		//start->current = malloc(sizeof(char*));
		//start->nextline = NULL;
 		//start->current = NULL;
											//printf("currentline: %d, goal: %d", currentline, line);
		currentline++;
	}
	//start->nextline = NULL;
 	//start->current = NULL;
	return start;
}

//concatinates to the end of a specified line
void append(data *data_ptr, int line, char *string) {
	int curline = 1;

	data_ptr = locateline(data_ptr, line);

	unsigned curlen = strlen(data_ptr->current);
	unsigned stringlen = strlen(string);

	char * helper = malloc(stringlen + curlen + 2);

	strcpy(helper, data_ptr->current);

	for(unsigned i = 0; i < stringlen; i++){		//adds string on the end of current on char at a time
		helper[curlen++ - 1] = string[i];
		//printf("%c\n", data_ptr->current[curlen - 2]);
	}
	free(data_ptr->current);
	data_ptr->current = helper;
}

//finds the search term and places brackets around it
void find(data *data_ptr, const char *search_term) {

	char * found ;									//pointer to the begining of the term when found
	unsigned searchlen = strlen(search_term);
	int lineNum = 0;

	char helper[searchlen];
	for(int i = 0; i < searchlen-1; i++)
		helper[i] = search_term[i];
	helper[searchlen-1] = 0;


	while(data_ptr != NULL && data_ptr->current != NULL){						//continues to run as long as there are more nodes(lines) to evaluate

		char printstr[strlen(data_ptr->current)*3];						//string that will be displayed if the term is found
		printstr[0] = 0;

		int countdata = 0;							//our location in the data string
		int countprint = 0;							//our location in the print string
		int theresmore = 1;							//flag intended to inform that we are at the end of the string (kind of redundant i know)
		char *worker = data_ptr->current;

		int count = 0;

		while(theresmore == 1){	
			//printf("search term: %s\n", helper);
	
			char *found = strstr(worker, helper);

			if(found != NULL){						//if found copythe string up to the end of the term

			//moves along the current string
				while(worker != found){				//copy up to the word
					//printf("worker: %s\n", found);
					printstr[countprint++] = (*worker);
					worker++;
				}

				printstr[countprint++]='[';			//place a bracket in the print string

				for(int i = 0; i < searchlen-1; i++){	//copy up to the end of the term
					printstr[countprint++] = *worker++;
				}
				printstr[countprint++] = ']';		//place a closing bracket in the print string
			}
			else
				theresmore = 0;		 	
		}

		if(printstr[0] != 0){						//copy till the end of the data string

			while(worker[0] != 0){
				printstr[countprint++] = *worker++;
			}
			printf("%d\t%s", lineNum, printstr);					//print
		}
		data_ptr = data_ptr->nextline;				//move on to the next node(line)
		lineNum++;
	}																															//printf("%d\n", __LINE__);
}

//saves the node data structure into a text file
void save(data *data_ptr, char *filename) {
	FILE *dataReal;
	dataReal = fopen (filename, "w");

	while(data_ptr != NULL){						//moves through the nodes attaching the string information in current int lines of the data file

		if(data_ptr->current != NULL){				//printf("%s", data_ptr->current);
			fprintf(dataReal, "%s", data_ptr->current);
		}
		data_ptr = data_ptr->nextline;
	}
	fclose(dataReal);
}
//destructor that moves through the data structure freeing pointers
void destroy(data *data_ptr) {
	data * helper;

	while(data_ptr != NULL){
		helper = data_ptr->nextline;
		if(data_ptr->current != NULL)
			free(data_ptr->current);	

		free(data_ptr);
		data_ptr = helper;
	}
}
