#include "cracker1.h"
#include <unistd.h>

typedef struct info{
	pthread_t thread;
	int number;
} info;

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
                   const char *partial_pw, int num);

pthread_mutex_t pf;
pthread_mutex_t bouncer;
pthread_cond_t cv;
pword * head = NULL;
pword * tail = NULL;
double realtime = 0;
double processtime = 0;
int count = 0;
int failed = 0;
int passed = 0;

int start(int thread_count) {
	
	pthread_mutex_init(&bouncer, NULL);
	pthread_cond_init(&cv, NULL);
	pthread_mutex_init(&pf, NULL);

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
	printf("%d passwords recovered, %d failed.\n", passed, failed); 

  return 0;
}

void* work(void * worker){
	info * mid = (info *)worker;

	while(1){
		pthread_mutex_lock(&bouncer);
		while(count == 0){pthread_cond_wait(&cv, &bouncer);}
		if(head == NULL){
			pthread_mutex_unlock(&bouncer);
			break;
		}
		pword * temp = head;
		head = head->next;
		count--;


			
		//printf("I'm worker %d\n", mid->number);


		pthread_mutex_unlock(&bouncer);
		printf("Thread %d: Start %s\n", mid->number, temp->username);

		

		crackPassword(temp->username, temp->hash, temp->password_prefix, mid->number);		

		free(temp);
		//printf("count: %d\t head: %p\n", count, head);
	}
	//print();
	//exit(1);
	//printf("%d leaving\n", mid->number);
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
                   const char *partial_pw,  int num) {

  assert(strlen(username) <= 8);
  assert(strlen(target_hash) == 13);
  assert(strlen(partial_pw) <= 8);

  double start_time = getTime();

  char test_pw[9];
  strcpy(test_pw, partial_pw);

  // unknown_chars will point to the part of the password that is unknown
  char *unknown_chars = test_pw + getPrefixLength(test_pw);
  setStringPosition(unknown_chars, 0);

  int found = 0;
  int hash_count = 0;
  struct crypt_data cdata;
  cdata.initialized = 0;

  do {
    const char *hashed = crypt_r(test_pw, "xx", &cdata);

    // uncomment this if you want to see the hash function doing its thing
    // printf("%s -> %s\n", test_pw, hashed);

    hash_count++;
    found = !strcmp(hashed, target_hash);
  } while (!found && incrementString(unknown_chars));

  double elapsed = getTime() - start_time;

	pthread_mutex_lock(&pf);
  if (found) {
    printf("Thread %d: Password for %s is %s (%d hashes in %.2f seconds)\n", num, username,
           test_pw, hash_count, elapsed);
	passed++;
  } else {
    printf("Thread %d: Password for %s not found (%d hashes in %.2f seconds)\n", num, username,
           hash_count, elapsed);
	failed++;
  }
	pthread_mutex_unlock(&pf);
}














