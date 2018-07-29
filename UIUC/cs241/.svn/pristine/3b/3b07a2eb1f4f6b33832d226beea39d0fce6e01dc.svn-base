// Shell
// CS 241 Fall 2015
#define _GNU_SOURCE
#include "log.h"
//#include "vector.h"
//#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
//#include <sys/types.h>
#include <signal.h>
#include <sys/types.h>
#include <string.h>
//#include <tgmath.h>

void userParse(Log * log);
void fileParse(char * parseVictim, Log * log);
int getInt(char *dat);
void parse(char * parseVictim, Log * log);
void exicuteSomeStuffWithAForkImNotRealySure(char * parseVictim);
void changeTheOldDirDir(char * parseVictim);
void yourDoneGoToYourRoom(Log * log);
void indexRetriever(char * parseVictim, Log * log);
void query(char * parseVictim, Log * log);
void findF(int argc, char *argv[], Log * log);
void findH(int argc, char *argv[]);

//
typedef struct {
  	pid_t **pids;
	int size;
	int cap;
} pidArrr;

typedef void (*sighandler_t)(int);


//sem_t s;


void handler(int signal){
	//sem_post(&s);
}

pidArrr * pidArray;

int main(int argc, char *argv[]) { 

	Log * log = Log_create("obeyMyCommands.txt");

	signal(SIGINT, handler);

/*	pidArray = malloc(sizeof(pidArrr));
	pidArray = malloc(sizeof(pid_t*)*100);
	pidArray->size = 0;
	pidArray->cap = 100;
*/
	findH(argc, argv);
	findF(argc, argv, log);
	
		userParse(log);
	return 0; 
}


void findF(int argc, char *argv[], Log * log){
	for(int i = argc-1; i > 0; i--)
		if(!strcmp(argv[i-1], "-f")){
			fileParse(argv[i], log);
			break;
}		}

void findH(int argc, char *argv[]){
	for(int i = argc; i > 0; i--)
		if(!strcmp(argv[i-1], "-h")){
			printf("Shell by jtmorri2\n");
			break;
}		}


void userParse(Log * log){

	while(1){
		char * dir = NULL;
		printf("(pid = %d)%s$ ", getpid(), getcwd(dir, 1024));
		fflush(stdout);
		size_t numberBytes=0;
		char *parseVictim = NULL;			//input string
		getline (&parseVictim, &numberBytes, stdin);
		parseVictim[strlen(parseVictim) - 1] = 0;
		//printf("You typed: %s\tyou dumb fuck\n", parseVictim);
		parse(parseVictim, log);
}	}


void parse(char * parseVictim, Log * log){
		if(!(strstr(parseVictim, "!") == parseVictim))
			Log_add_command(log, parseVictim);


		if(strstr(parseVictim, "cd") == parseVictim)
			changeTheOldDirDir(&parseVictim[3]);

		else if(strstr(parseVictim, "exit") == parseVictim)
			yourDoneGoToYourRoom(log);

		else if(strstr(parseVictim, "!history") == parseVictim)
			printf("%s", Log_get_printable_history(log));

		else if(strstr(parseVictim, "!") == parseVictim && parseVictim[1] < 58 && parseVictim[1]>47)
			indexRetriever(parseVictim, log);
		
		else if(strstr(parseVictim, "!") == parseVictim)
			query(parseVictim, log);
	
		else
			exicuteSomeStuffWithAForkImNotRealySure(parseVictim);
}


void changeTheOldDirDir(char * parseVictim){
	int error = 1;
	error += chdir(parseVictim);
	if(!error)
		printf("%s: No such file or directory\n", parseVictim);
}


void yourDoneGoToYourRoom(Log * log){
	//int wtf;
	//for(int i=0; i < pidArray->size; i++)
		//waitpid(pidArray->pids[i], &wtf, 0);

	//free(pidArray);

	Log_save(log, "obeyMyCommands.txt");
	Log_destroy(log);

	//wait(&wtf);
	exit(1);
}


void indexRetriever(char * parseVictim, Log * log){
	size_t index = getInt(&parseVictim[1]);
	const char * cmd = Log_get_command(log, index);


	if(cmd){
		char * cmdTemp = malloc(strlen(cmd) + 1); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		strcpy(cmdTemp, cmd);
		printf("%s\n", cmd);
		parse(cmdTemp, log);
	}
}


void query(char * parseVictim, Log * log){
	const char *cmd = Log_reverse_search(log, &parseVictim[1]);

	if(cmd){
		char * cmdTemp = malloc(strlen(cmd) + 1); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		strcpy(cmdTemp, cmd);
		printf("%s\n", cmd);
		parse(cmdTemp, log);
	}
}


void fileParse(char * parseVictim, Log * log){
	size_t lim = 0;
	char *newb = NULL;
	FILE *file = fopen(parseVictim, "r");

	if(file != NULL){
		while(getline(&newb, &lim, file)!= EOF){
			if(strlen(newb) > 0){
				newb[strlen(newb) - 1] = 0;
		char * dir = NULL;
		printf("(pid = %d)%s$ %s\n", getpid(), getcwd(dir, 1024), newb);
				parse(newb, log);
			//free(newb); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}	}	}
	yourDoneGoToYourRoom(log);
}


int getInt(char *dat){
	if(dat[0] < 58 && dat[0]>47){ 				//if the 3rd char is not a number move on.
		int index = 0;							//the starting position for the numbers
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

		index = 0;
		for(int i = lim; i > 0; i--){			//adds up the integers with thier appropriate multiplier
			int curInt = (int)dat[index++];
			lineNum += (curInt - 48) * multiplier;
			multiplier /= 10;
		}
												//printf("your number is: %d\n", lineNum);
	//printf("%d\n", lineNum);
		return lineNum;
	}
	else
		return 0;
}


void exicuteSomeStuffWithAForkImNotRealySure(char * parseVictim){
	int waiting = 1;



	char * arguments [strlen(parseVictim)];
	char skittles [strlen(parseVictim)];
	strcpy(skittles, parseVictim);

	arguments[0] = strtok(skittles, " ");
	int i = 1;
	while(arguments[i-1] != NULL){
		arguments[i++] = strtok(NULL, " ");
		//printf("%s", arguments[i-1]);
	}
	if(!strcmp(arguments[i-2], "&")){
		arguments[i-2] = 0;
		//if(pidArray->cap == pidArray->size)
			//pidArray = realloc(pidArray->pids, sizeof(pidArrr*)*2*pidArray->cap);
		//pidArray->pids[pidArray->size++ - 1] = malloc(sizeof(pid_t));
		//pidArray->pids[pidArray->size++ - 1] = getpid();
		waiting = 0;
	}




	pid_t pidO;
	pidO = fork();
	

	if(pidO == 0){

		printf("Command executed by pid=%d\n", getpid());
		execvp(arguments[0], arguments);
		printf("%s: not found\n", parseVictim);
		exit(1);
	}
	if(waiting){
		int garbage = -1;
		waitpid(pidO, &garbage, WIFEXITED(garbage));
}	}

















