/** @file libscheduler.c
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "libscheduler.h"
#include "../libpriqueue/libpriqueue.h"


/**
  Stores information making up a job to be scheduled including any statistics.

  You may need to define some global variables or a struct to store your job queue elements. 
*/
typedef struct _job_t
{
	int job_number;
	int time;
	int run_time;
	int priority;
	int exec_time;
} job_t;

typedef struct _core_t{
	int id;
	job_t * job;
	int isRunning;
} core_t;

// TODO This is a good place for some global variables? Like number of jobs,
// number of cores, total waiting time, total response time, etc.


//priqueue_t running_jobs;
priqueue_t waiting_jobs;
core_t * cores;

int jobs_running = 0;
int	jobs_waiting = 0;
int number_cores;
float total_runtime = 0;
float total_waittime = 0;
float total_responsetime = 0;
int number_jobs = 0;
float total_turnaroundtime = 0;
int preempt = 0;

int counter = 0;

int(*comparer)(const void *, const void *);


void findcmp(scheme_t sch);
int sjfcmp(const void * a, const void * b);
int pricmp(const void * a, const void * b);
int nocmp(const void * a, const void * b);
int corecmp(const void * a, const void * b);
void clean_up_queue(priqueue_t * q);
int run(job_t * job, int time);
int get_free_core();
int priority_check(job_t * job);
core_t * get_lowest_priority();
int timcmp(const void * a, const void * b);

/**
  Initalizes the scheduler.
 
  Assumptions:
    - You may assume this will be the first scheduler function called.
    - You may assume this function will be called once once.
    - You may assume that cores is a positive, non-zero number.
    - You may assume that scheme is a valid scheduling scheme.

  @param cores the number of cores that is available by the scheduler. These
    cores will be known as core(id=0), core(id=1), ..., core(id=cores-1).
  @param scheme  the scheduling scheme that should be used. This value will be
    one of the six enum values of scheme_t
*/
void scheduler_start_up(int core_num, scheme_t sch)
{
	//job_list = malloc(sizeof(priqueue_t));
	findcmp(sch);
	printf("%p == %p", comparer, pricmp);
	//priqueue_init(&running_jobs, comparer);
	priqueue_init(&waiting_jobs, comparer);
	
	number_cores = core_num;
	cores = malloc(sizeof(core_t)*core_num);

	int i;
	for(i=0; i<core_num; i++){
		core_t * current_core = cores + i;
		current_core->id = i;
		current_core->isRunning = 0;
		current_core->job = NULL; 
	}
}


/**
  Called when a new job arrives.
 
  If multiple cores are idle, the job should be assigned to the core with the
  lowest id.
  If the job arriving should be scheduled to run during the next
  time cycle, return the zero-based index of the core the job should be
  scheduled on. If another job is already running on the core specified,
  this will preempt the currently running job.
  Assumptions:
    - You may assume that every job wil have a unique arrival time.

  @param job_number a globally unique identification number of the job arriving.
  @param time the current time of the simulator.
  @param running_time the total number of time units this job will run before it
    will be finished.
  @param priority the priority of the job. (The lower the value, the higher the
    priority.)
  @return index of core job should be scheduled on
  @return -1 if no scheduling changes should be made. 
 
 */
int scheduler_new_job(int job_number, int time, int running_time, int priority)
{
	job_t * job = malloc(sizeof(job_t));	
	job->job_number = job_number;
	job->time = time;
	job->run_time = running_time;
	job->priority = priority;
	job->exec_time = -1;

	number_jobs++;
	total_runtime += running_time;

	int core_id = get_free_core();
	printf("NEW JOB ADDED!\n");
	if(core_id != -1){
		(cores + core_id)->job = job;
		(cores + core_id)->isRunning = 1;
	printf("\nCORE NUMBER: %d\nCORE RUNNING: %d\n\n", core_id, (cores + core_id)->isRunning);
		run(job, time);
		return core_id;
	}
	else if(preempt == 14){
		printf("ppppppppppprrrrrrrrrrrrrreeeeeeeeeeeeeeeemmmpt\n");
		return priority_check(job);
	}
	printf("9999999999 POSTED JOB *8888888888888888888888\n");
	priqueue_offer(&waiting_jobs, job);
	return -1;
}

int priority_check(job_t * job){

	core_t * lowest = get_lowest_priority();
	printf("lowest: %d\t @core: %d\n", lowest->job->job_number, lowest->id);
	if(comparer(lowest->job, job) > 0){
		priqueue_offer(&waiting_jobs, job);
		if(lowest->job->time == job->time)
			lowest->job->exec_time == -1;
		scheduler_quantum_expired(lowest->id, job->time);
		return lowest->id;
	}
	priqueue_offer(&waiting_jobs, job);

	return -1;
}

int get_free_core(){
	int i;
	for(i = 0; i< number_cores; i++){
		printf("CORE NUMBER: %d \t RUNNING: %d\n", i, (cores+i)->isRunning);
		if(!((cores + i)->isRunning))
			return i;
	}
	return -1;
}

core_t * get_lowest_priority(){

	core_t * lowest = cores;
	int i;
	for(i = 0; i < number_cores; i++){
		core_t * current = (cores + i);
		if(current ->isRunning){
			printf("newb\n");
			lowest = cores + i;	
			i++;
			break;
		}
	}
	printf("i: %d\n", i);
	for(; i<number_cores; i++){
		core_t * current = (cores + i);
		if(current->isRunning){
			printf("cmp loc: %p\n", comparer);
			if(comparer(current->job, lowest->job) > 0 ){
				printf("changed ");
				lowest = current;
			}
		}
	}
	return lowest;
}

/**
  Called when a job has completed execution.
 
  The core_id, job_number and time parameters are provided for convenience. You
  may be able to calculate the values with your own data structure.
  If any job should be scheduled to run on the core free'd up by the
  finished job, return the job_number of the job that should be scheduled to
  run on core core_id.
 
  @param core_id the zero-based index of the core where the job was located.
  @param job_number a globally unique identification number of the job.
  @param time the current time of the simulator.
  @return job_number of the job that should be scheduled to run on core core_id
  @return -1 if core should remain idle.
 */
int scheduler_job_finished(int core_id, int job_number, int time)
{
	core_t * core = (cores + core_id);
	job_t * job = core->job;
	total_turnaroundtime += time - job->time;


	free(job);
	
	job = priqueue_poll(&waiting_jobs);
	printf("JOB PULL: %p\n", job);
	if(job != NULL){
		core->job = job;
		core->isRunning = 1;
	
		return run(job, time);
	}
	else
		core->isRunning = 0;

	return -1;
}


/**
  When the scheme is set to RR, called when the quantum timer has expired
  on a core.
 
  If any job should be scheduled to run on the core free'd up by
  the quantum expiration, return the job_number of the job that should be
  scheduled to run on core core_id.

  @param core_id the zero-based index of the core where the quantum has expired.
  @param time the current time of the simulator. 
  @return job_number of the job that should be scheduled on core cord_id
  @return -1 if core should remain idle
 */
int scheduler_quantum_expired(int core_id, int time)
{	
	job_t * job1 = ((cores + core_id)->job);

	job_t * job2 = priqueue_poll(&waiting_jobs);

	if(job2 == NULL){
		//priqueue_offer(&waiting_jobs, job2);
		return job1->job_number;
	}
	else {
	int run_time = time - job1->exec_time;
	//total_runtime += run_time;
	job1->run_time -= run_time;	
	priqueue_offer(&waiting_jobs, job1);

	printf("\nHELLO\nTHERE\n\n");

	//job = priqueue_poll(&waiting_jobs);	

	cores[core_id].job = job2;
	run(job2, time);
	return job2->job_number;
	}
//	else{
//		priqueue_offer(&waiting_jobs, job2);
//		return job1->job_number;
//	}
//_free_core() != -1)
//		return job->job_number;

	return -1;
}


int run(job_t * job, int time){
	printf("exec_time: %d\n", job->exec_time);
	if(job->exec_time == -1){
	printf("counter: %d\n", ++counter);
		total_responsetime += time - job->time;
	}

	job->exec_time = time;
	return job->job_number;
}

/**
  Returns the average waiting time of all jobs scheduled by your scheduler.

  Assumptions:
    - This function will only be called after all scheduling is complete (all
      jobs that have arrived will have finished and no new jobs will arrive).
  @return the average waiting time of all jobs scheduled.
 */
float scheduler_average_waiting_time()
{
	return (total_turnaroundtime - total_runtime)/number_jobs;
}


/**
  Returns the average turnaround time of all jobs scheduled by your scheduler.

  Assumptions:
    - This function will only be called after all scheduling is complete (all
      jobs that have arrived will have finished and no new jobs will arrive).
  @return the average turnaround time of all jobs scheduled.
 */
float scheduler_average_turnaround_time()
{
	//printf("total turn around time: %d\nnum jobs: %d\n", total_turnaroundtime, number_jobs);
	return total_turnaroundtime/number_jobs;
}


/**
  Returns the average response time of all jobs scheduled by your scheduler.

  Assumptions:
    - This function will only be called after all scheduling is complete (all
      jobs that have arrived will have finished and no new jobs will arrive).
  @return the average response time of all jobs scheduled.
 */
float scheduler_average_response_time()
{
	return total_responsetime/number_jobs;
}


/**
  Free any memory associated with your scheduler.
 
  Assumptions:
    - This function will be the last function called in your library.
*/
void scheduler_clean_up(){
	//clean_up_queue(&running_jobs);
	clean_up_queue(&waiting_jobs);
	free(cores);
}

void clean_up_queue(priqueue_t * q){
	void * item = priqueue_poll(q);
	if(item != NULL){
		free(item);
		clean_up_queue(q);
	}
}

/**
  This function may print out any debugging information you choose. This
  function will be called by the simulator after every call the simulator
  makes to your scheduler.
  In our provided output, we have implemented this function to list the jobs in
  the order they are to be scheduled. Furthermore, we have also listed the
  current state of the job (either running on a given core or idle). For
  example, if we have a non-preemptive algorithm and job(id=4) has began
  running, job(id=2) arrives with a higher priority, and job(id=1) arrives with
  a lower priority, the output in our sample output will be:

    2(-1) 4(0) 1(-1)  
  
  This function is not required and will not be graded. You may leave it blank
  if you do not find it useful.
 */
void scheduler_show_queue()
{
	// This function is left entirely to you! Totally optional.
	
}


void findcmp(scheme_t sch){
	if(sch == SJF || sch == PSJF)
		comparer = sjfcmp;
	else if(sch == PRI || sch == PPRI)
		comparer = pricmp;
	else
		comparer = nocmp;

	if(sch == PSJF || sch == PPRI)
		preempt = 14;
}


int sjfcmp(const void * a, const void * b){
	int a_time = ((job_t *)a)->run_time;
	int b_time = ((job_t *)b)->run_time;

	if(a_time < b_time)
		return -1;
	if(a_time > b_time)
		return 1;

	return timcmp(a,b);
}


int pricmp(const void * a, const void * b){
	int a_priority = ((job_t*)a)->priority;
	int b_priority = ((job_t*)b)->priority;
	//return a_priority - b_priority;
	if(a_priority < b_priority)
		return -1;
	if(b_priority < a_priority)
		return 1;
	return timcmp(a,b);
}

int timcmp(const void * a, const void * b){
	int a_time = ((job_t *)a)->time;
	int b_time = ((job_t *)b)->time;
	if(a_time < b_time)
		return -1;
	if(b_time < a_time)
		return 1;
	return 0;
}

int nocmp(const void * a, const void * b){
	return 0;
}

int corecmp(const void * a, const void * b){
	int a_id = ((core_t*)a)->id;
	int b_id = ((core_t*)a)->id;

	if(a_id < b_id)
		return -1;
	if(a_id > b_id)
		return 1;
	return 0;
}






