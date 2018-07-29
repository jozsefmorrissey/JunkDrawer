#include "cracker2.h"
#include <unistd.h>
#include <stdbool.h>

typedef struct info{
	pthread_t thread;
	int number;
} info;

typedef struct barrier_t
{
    pthread_mutex_t mtx;
    pthread_cond_t cv;
    // Total number of threads
    unsigned n_threads;
    // Increasing or decreasing count
    // Hint: Why is there both a barrier_waitdown & a barrier_waitup barrier.c?
    unsigned count;
    // Indicates when all threads have arrived
    bool done;
} barrier_t;

typedef struct pword{
	char username[9];
	char hash[14];
	char password_prefix[9];
	struct pword * next;
}pword;

void* work(void * worker);
void parseInput();
void print();
void crackPassword(const char *username, const char *target_hash,
                   const char *partial_pw, int num, size_t start, size_t end);

int barrier_destroy(barrier_t *barrier);
int barrier_init(barrier_t *barrier, unsigned num_threads);
int barrier_wait(barrier_t *barrier);

void getSubrange(int unknown_letter_count, int thread_count, int thread_id,
                 long *start_index, long *count);

pthread_mutex_t pf;
pthread_mutex_t tsan;
pthread_mutex_t bouncer;
pthread_mutex_t notfoundM;
pthread_cond_t cv;
pword * head = NULL;
pword * tail = NULL;
double realtime = 0;
double processtime = 0;
int count = 0;
int failed = 0;
int passed = 0;
barrier_t frontbar;
barrier_t backbar;
int passFound = 0;
int notfound = 0;
int threadCount;
int wthreads = 0;
double startt = 0;
double startcpu = 0;
double finalTime = 0;
size_t totIT=0;
int headEliminatecounterVersion8 = 0;
pword * temp;

int start(int thread_count) {
	
	pthread_mutex_init(&bouncer, NULL);
	pthread_cond_init(&cv, NULL);
	pthread_mutex_init(&pf, NULL);
	pthread_mutex_init(&tsan, NULL);
	pthread_mutex_init(&notfoundM, NULL);

	barrier_init(&frontbar, thread_count);
	barrier_init(&backbar, thread_count);

	threadCount = thread_count;

	if(thread_count < 1)
		thread_count = 4;
	
	info workers[thread_count];

	int i;
	for(i=0; i<thread_count; i++){
		info * worker = &workers[i];
		worker->number = i + 1;
		pthread_create(&worker->thread, NULL, work, worker);
	}

	parseInput();

	int joined;
	for(i=0; i<thread_count; i++){
		//printf("here\n");
		joined = pthread_join(workers[i].thread, NULL);
		//printf("joined: %d\n", joined);
	}
			if(startt){
				double elapsed = -startt + getTime();
				double total = -startcpu + getCPUTime();
				printf("Total CPU time: %.2f seconds.\n", total);
				printf("CPU usage: %.2fx\n\n", total/elapsed);
			}

	printf("%d passwords recovered, %d failed.\n", passed, failed); 
	
	barrier_destroy(&frontbar);
	barrier_destroy(&backbar);

  return 0;
}

void* work(void * worker){
	info * mid = (info *)worker;
	while(1){
		pthread_mutex_lock(&bouncer);	
		//printf("here first\n");
		while(count == 0){pthread_cond_wait(&cv, &bouncer);}

		if(head == NULL){
			//printf("i leaft\n");
			pthread_mutex_unlock(&bouncer);
			break;
		}
		
		if(++headEliminatecounterVersion8 == threadCount){
			free(temp);
			temp = head;
			head = head->next;
			count--;
			headEliminatecounterVersion8 = 0;
		}

		pthread_mutex_unlock(&bouncer);
		barrier_wait(&frontbar);
		pthread_mutex_lock(&bouncer);	

		if(wthreads++ == 0){
			if(startt){
				double elapsed = getCPUTime() - startcpu;
				printf("Total CPU time: %.2f seconds.\n", elapsed);
				printf("CPU usage: %.2fx\n\n", elapsed/finalTime);
			}
			
			printf("start %s\n", temp->username);
			startt = getTime();
			startcpu = getCPUTime();
			
		}


		size_t numChars = strlen(temp->password_prefix) - getPrefixLength(temp->password_prefix);
		size_t need = 1;

		size_t i;
		for(i=0; i<numChars; i++)
			need *= 26;


		long start = 0;
		long end = 10000000000000;
		int ublaBoobla = getPrefixLength(temp->password_prefix);
		getSubrange(strlen(temp->password_prefix) - ublaBoobla, threadCount, mid->number,
                 &start, &end);

		char blah[9];
		strcpy(blah, temp->password_prefix);
		//printf("blah: %s\t strlen: %d\n", blah, strlen(temp->password_prefix));
		setStringPosition(&blah[ublaBoobla], start);
		pthread_mutex_unlock(&bouncer);
		printf("Thread %d: Start %s at %zu (%s)\n", mid->number, temp->username, (size_t)start, blah);

		barrier_wait(&frontbar);

		crackPassword(temp->username, temp->hash, temp->password_prefix, mid->number, start, end);

		barrier_wait(&frontbar);
		pthread_mutex_lock(&notfoundM);
		notfound = 0;		
		pthread_mutex_unlock(&notfoundM);

	}

	

	pthread_cond_broadcast(&cv);
	return NULL;
}




void parseInput(){
	FILE *inf = stdin;
	
	size_t buf_len = 0;
  	char *line = NULL;
	//double start_time = getTime();

	if(getline(&line, &buf_len, inf) != -1){
		
			pword * first = malloc(sizeof(pword));
			sscanf(line, "%8s %13s %8s", first->username, first->hash, first->password_prefix);
			head = first;
			tail = first;
			tail->next = NULL;
		
		while(getline(&line, &buf_len, inf) != EOF){
			pword * curr = malloc(sizeof(pword));
			sscanf(line, "%8s %13s %8s", curr->username, curr->hash, curr->password_prefix);
			pthread_mutex_lock(&bouncer);
			tail->next = curr;
			tail = tail->next;
			tail->next = NULL;
			count++;
			pthread_cond_signal(&cv);
			pthread_mutex_unlock(&bouncer);	

		}
		free(line);
		count+=2;
		pthread_cond_broadcast(&cv);

	}
}

void print(){
	pword * ex = head;
	while(ex){
		printf("username: %s\t hash: %s\t prefix: %s\n", ex->username, ex->hash, ex->password_prefix);
		ex = ex->next;
	}
	printf("\n\n");
}



void crackPassword(const char *username, const char *target_hash,
                   const char *partial_pw,  int num, size_t start, size_t end) {

  assert(strlen(username) <= 8);
  assert(strlen(target_hash) == 13);
  assert(strlen(partial_pw) <= 8);

  double start_time = getTime();

  char test_pw[9];
  strcpy(test_pw, partial_pw);

  // unknown_chars will point to the part of the password that is unknown
  char *unknown_chars = test_pw + getPrefixLength(test_pw);
  setStringPosition(unknown_chars, start);

  int found = 0;
  int hash_count = 0;
  struct crypt_data cdata;
  cdata.initialized = 0;

	size_t it = 0;

		barrier_wait(&frontbar);
	int x = 0;
  do {
    const char *hashed = crypt_r(test_pw, "xx", &cdata);
	
    // uncomment this if you want to see the hash function doing its thing
    // printf("%s -> %s\n", test_pw, hashed);
	//if(x++ % 25 == 0){
		pthread_mutex_lock(&tsan);
		if(passFound || it == end){
			pthread_mutex_unlock(&tsan);
			break;
		}
		pthread_mutex_unlock(&tsan);
		x = 0;
	//}
	//printf("passFound: %d\n", passFound);

    hash_count++;
	it++;
    found = !strcmp(hashed, target_hash);
  } while (!found && incrementString(unknown_chars));

  double elapsed = getTime() - start_time;

	pthread_mutex_lock(&pf);

	if(found){
		pthread_mutex_lock(&tsan);
		passFound = threadCount;
		pthread_mutex_unlock(&tsan);
	}
	else{
		pthread_mutex_lock(&notfoundM);
		notfound++;		
		pthread_mutex_unlock(&notfoundM);
	}

	pthread_mutex_unlock(&pf);
	barrier_wait(&frontbar);
	pthread_mutex_lock(&pf);
	totIT += it;

	pthread_mutex_lock(&notfoundM);
	if(notfound == threadCount)
		printf("Thread %d: Stop after %zu iterations (end)\n", num, it);
	else if(found)	
		printf("Thread %d: Stop after %zu iterations (found)\n", num, it);
	else
		printf("Thread %d: Stop after %zu iterations (cancelled)\n", num, it);
	pthread_mutex_unlock(&notfoundM);

	pthread_mutex_unlock(&pf);
	barrier_wait(&frontbar);
	pthread_mutex_lock(&pf);
	wthreads = 0;

	pthread_mutex_lock(&notfoundM);
  if (found) {
    printf("Password for %s is %s (%zu hashes in %.2f seconds)\n", username,
           test_pw, totIT, finalTime = -startt + getTime());
	totIT = 0;
		passed++;
  } else if(notfound-- == threadCount){
    printf("Password for %s not found (%zu hashes in %.2f seconds)\n", username,
           totIT, finalTime = -startt + getTime());
	totIT = 0;
	failed++;
		passFound = threadCount;
  }
	pthread_mutex_unlock(&notfoundM);


	pthread_mutex_lock(&tsan);
	passFound--;
	pthread_mutex_unlock(&tsan);

	pthread_mutex_unlock(&pf);	
}


int barrier_init(barrier_t *barrier, unsigned num_threads)
{
    int error = 0;
	//barrier = malloc(sizeof(barrier));
	pthread_mutex_init(&barrier->mtx, NULL);
	pthread_cond_init(&barrier->cv, NULL);
	barrier->n_threads = num_threads;
	barrier->count = 0;
	barrier->done = 0;
    return error;
}

/**
 * Destroy your barrier.
 */
int barrier_destroy(barrier_t *barrier)
{
    int error = 0;
	pthread_mutex_destroy(&barrier->mtx);
	pthread_cond_destroy(&barrier->cv);
    return error;
}

/**
 * Helper function for first step of barrier_wait. As the name it implies, it
 * probably does something in an upwards direction...
 */
int barrier_waitup(barrier_t *barrier)
{	
	pthread_mutex_lock(&barrier->mtx);

	barrier->count++;

	if(barrier->count == barrier->n_threads){
		barrier->done = true;
		pthread_cond_broadcast(&barrier->cv);
	}

	while(barrier->done == false){pthread_cond_wait(&barrier->cv, &barrier->mtx);}

	pthread_mutex_unlock(&barrier->mtx);

    return 0;
}

/**
 * Helper function for second step of barrier_wait. As the name it implies, it
 * probably does something in a downwards direction...
 */
int barrier_waitdown(barrier_t *barrier)
{
	pthread_mutex_lock(&barrier->mtx);

	barrier->count--;

	if(barrier->count == 0){
		barrier->done = false;
		pthread_cond_broadcast(&barrier->cv);
	}
	while(barrier->done){pthread_cond_wait(&barrier->cv, &barrier->mtx);}

	pthread_mutex_unlock(&barrier->mtx);
    return 0;
}

/**
 * With the intended solution we have in mind, you do not have to change this
 * function. However, as stated before, you may implement this function
 * differently if you wish.
 */
int barrier_wait(barrier_t *barrier)
{
    barrier_waitup(barrier);
    barrier_waitdown(barrier);
    return 0;
}

void getSubrange(int unknown_letter_count, int thread_count, int thread_id,
                 long *start_index, long *count) {
  int i;
  long max = 1, end_index;
  for (i=0; i < unknown_letter_count; i++) max *= 26;
  *start_index = max * (thread_id - 1) / thread_count;
  end_index = max * thread_id / thread_count;
  if (end_index > max)
    end_index = max;
  *count = end_index - *start_index;
}









