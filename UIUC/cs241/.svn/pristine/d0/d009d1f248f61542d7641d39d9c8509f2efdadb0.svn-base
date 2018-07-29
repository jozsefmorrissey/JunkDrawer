#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "queue.h"





/**
 *  Given a queue_element, place it on the queue.  Can be called by multiple threads.
 *  Blocks if the number of items on the queue is equal to the queue's max size
 */
void queue_push(queue_t* queue, void* data) {
	if(queue == NULL)
		return;


	pthread_mutex_lock(&queue->m);
	while(queue->count == queue->maxSize){pthread_cond_wait(&queue->cv, &queue->m);}

	//printf("Count: %d \tMax: %d\n", queue->count, queue->maxSize);

	queue_node_t * simplicity = malloc(sizeof(queue_node_t));
	simplicity->data = data;
	simplicity->next = NULL;


	if(queue->head == NULL){
		queue->head = simplicity;
		queue->tail = simplicity;

	//printf("head\n");
	
		queue->count++;
		pthread_cond_signal(&queue->cv);
		pthread_mutex_unlock(&queue->m);

		return;
	}
	//printf("Head Value: %d \tHead Loc: %d\n", *((int*)queue->head->data), (int)queue->head->data);

	queue_node_t * temp = queue->tail;
	queue->tail = simplicity;
	temp->next = simplicity;

	queue->count++;
	pthread_cond_signal(&queue->cv);
	pthread_mutex_unlock(&queue->m);
}

/**
 *  Retrieve the queue_element at the front of the queue.  Can be called by multiple threads.
 *  Blocks if there are no tasks on the queue.
 */
void* queue_pull(queue_t* queue) {
	if(queue == NULL)
		return;

	pthread_mutex_lock(&queue->m);
	while(queue->count == 0){pthread_cond_wait(&queue->cv, &queue->m);}	

	//printf("Output\tCount: %d \tMax: %d\n", queue->count, queue->maxSize);

	queue_node_t * temp = queue->head;
	int * retval = temp->data;
	queue->head = queue->head->next;
	
	queue->count--;
	pthread_cond_signal(&queue->cv);
	pthread_mutex_unlock(&queue->m);

	//printf("temp Value: %d \ttemp Loc: %d\n", *((int*)temp->data), (int)temp->data);
	free(temp);

    return retval;
}

/**
 *  Initializes the queue
 */
void queue_init(queue_t* queue, int maxSize){

	pthread_mutex_init(&queue->m, NULL);

	queue->count = 0;
	queue->maxSize = maxSize;

	pthread_mutex_init(&queue->m, NULL);
	pthread_cond_init(&queue->cv, NULL);

	queue->head = NULL;
}

/**
 *  Destorys the queue, freeing any remaining nodes in it.
 */
void queue_destroy(queue_t* queue){
	if(queue == NULL)
		return;

	queue_node_t * hitman = NULL;
	while(queue->head){
		hitman = queue->head;
		queue->head = queue->head->next;
		free(hitman);
	}
}



