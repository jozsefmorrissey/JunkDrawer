/** @file libscheduler.h
 */

#ifndef LIBSCHEDULER_H_
#define LIBSCHEDULER_H_

/**
  Constants which represent the different scheduling algorithms.
*/
typedef enum {FCFS, SJF, PSJF, PRI, PPRI, RR} scheme_t;

void scheduler_start_up(int, scheme_t);
int scheduler_new_job(int, int, int, int);
int scheduler_job_finished(int, int, int);
int scheduler_quantum_expired(int, int);
float scheduler_average_turnaround_time();
float scheduler_average_waiting_time();
float scheduler_average_response_time();
void  scheduler_clean_up();

void  scheduler_show_queue();

void treadWork();

#endif /* LIBSCHEDULER_H_ */
