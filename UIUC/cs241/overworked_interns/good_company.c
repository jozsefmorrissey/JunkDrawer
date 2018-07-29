// Overworked Interns
// CS 241 Fall 2015

#define _GNU_SOURCE
#include "company.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

// Your good solution needs to go here
void *work_interns(void *p) {
  CompanyData *company = (CompanyData *)p;
  pthread_mutex_t *intern_left, *intern_right;
  while (running) {
    intern_left = company->intern_left;
    intern_right = company->intern_right;
    //printf("Company %d needs their interns!\n", company->number);
    pthread_mutex_lock(intern_left);

    if(pthread_mutex_trylock(intern_right) == 0){    
		printf("Company %d is working\n", company->number);
		usleep(1 + rand() % 3);
		company->hire_count++;
		pthread_mutex_unlock(intern_right);
		pthread_mutex_unlock(intern_left);
		printf("Company %d has finished working with us\n", company->number);
		//usleep(1200000);		
	}
	else{
		usleep(1);
		pthread_mutex_unlock(intern_left);
		pthread_mutex_t * temp = company->intern_left;
		company->intern_left = company->intern_right;
    	company->intern_right = temp;
	}
  }
  return NULL;
}
