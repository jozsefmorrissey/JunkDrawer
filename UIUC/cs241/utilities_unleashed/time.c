#define _XOPEN_SOURCE 700
#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>




int main(int argc, char *argv[])
{

	struct timespec waffles;
	struct timespec syrup;
	//clockid_t clkTime;

	pid_t pid;

	clock_gettime(CLOCK_REALTIME, &waffles);
	pid = fork();
	if(pid == 0){

	execlp(argv[1], argv[1], argv[2], NULL);

	//long counter = 1000000;

	//while(counter > 0)
		//counter--;


	}	
	int stat;
	wait(&stat);
	clock_gettime(CLOCK_REALTIME, &syrup);

	long ourput = 1000000000*(syrup.tv_sec - waffles.tv_sec) + (syrup.tv_nsec - waffles.tv_nsec);
		
	printf("%ld\n", ourput);


  return 0;
}

