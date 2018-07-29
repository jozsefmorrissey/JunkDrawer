// Malloc
// CS 241 Fall 2015
// University of Illinois at Urbana-Champaign
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <time.h>
#include <time.h>

#define FALSE (0)
#define TRUE (1)


typedef struct memoryStruct {
	struct memoryStruct * nextFree;
	struct memorySturct * prevFree;
	unsigned int size;
	char Free;
	char blockSize;



	char varification;	
	char moreVarification;	

} memoryMap;

#define STRUCTSIZE sizeof(memoryMap)

memoryMap * head = NULL;
memoryMap * tail = NULL;
memoryMap * freehead = NULL;
memoryMap * freetail = NULL;
size_t totalSize = 0;

void initiate();
void initStructs(memoryMap * ptr, size_t size);
void initVariables(memoryMap * ptr, size_t size, char blocksize);

void * getnext(memoryMap * ptr);
void * nextSize(void * ptr, int size);
void * getPrev(memoryMap * ptr);
void * prevSize(void * ptr, int size);
void * searchSize(size_t size);
void * expandMemory(size_t size);

void * combineForward(memoryMap * ptr);
void checkTail(memoryMap * ptr);


char getFree(memoryMap * ptr);

size_t getSize(memoryMap * ptr);
memoryMap * getback(memoryMap * ptr);

void setFree(memoryMap * ptr, char free);
void setSize(memoryMap * ptr, size_t size);


void printSturcture();

//!!!!!!!!!!!!!!!!!!!!!!!!!!!functions to implement!!!!!!!!!!!!!!!!!!!!!!!!!!!!
void divideMemory(size_t sizeDesired, memoryMap * ptr); 

void addLink(memoryMap * ptr);
void rmLink(memoryMap * ptr);
void setNextFree(memoryMap * current, memoryMap * next);
memoryMap * getPrevFree(memoryMap * ptr);
memoryMap * getNextFree(memoryMap * ptr);
void printFrees();


#define allocSize (100000)

int bad = 0;
int good = 0;
int total = 0;

/*int main(){
	printf("%zu\n%zu\n", sizeof(size_t), sizeof(int));
	initiate();
	memoryMap * one = malloc(44);
	memoryMap * two = malloc(600);
	memoryMap * three = malloc(18);

	free(one);
	free(two);	
	free(three);	
	
	int i;
	void * boo [400];
	for(i = 0; i < 64; i++){
		boo[i] = malloc(4);
		//printf("boo size: %d\n", getSize(getnext(head)));
	}
	for(i = 0; i < 64; i++){	
		free(boo[i]);
	}








	for(i = 0; i < 400; i++){
		boo[i] = malloc(4);
		//printf("boo size: %d\n", getSize(getnext(head)));
	}
	printSturcture();



	for(i = 0; i < 400; i++){	
		free(boo[i]);
	}	
	
	memoryMap * four = malloc(18);
	memoryMap * five = malloc(144);
	memoryMap * six = malloc(400);
	
	free(four);
	free(five);
	free(six);

	printSturcture();

	return 0;
}*/









void printSturcture(){
	printf("\n\n--------------------------------------STRUCTURE----------------------------------\n\n");

	memoryMap * ex = head;
	printf("head: %zu\tmiddle: %zu\tmiddle: %zu\ttail: %zu\n", (size_t)head, (size_t)getnext(head), (size_t)getPrev(tail), (size_t)tail);
	printf("head: %p \tsize: %zu \tfree: %i \tboolean: %i\n", ex, getSize(ex), getFree(ex), 0);
	ex = getnext(ex);
	while(ex < tail){
		printf("node: %p \tsize: %zu \tfree: %i \tboolean: %i\n", ex, getSize(ex), getFree(ex), 0);
		ex = getnext(ex);
	}
	printf("tail: %p \tsize: %zu \tfree: %i \tboolean: %i\n", tail, getSize(tail), getFree(tail), 0);
	printf("TOTAL SIZE: %zu\n", totalSize);

	printf("\n\n--------------------------------------STRUCTURE----------------------------------\n\n");
}
/**
 * Allocate space for array in memory
 *
 * Allocates a block of memory for an array of num elements, each of them size
 * bytes long, and initializes all its bits to zero. The effective result is
 * the allocation of an zero-initialized memory block of (num * size) bytes.
 *
 * @param num
 *    Number of elements to be allocated.
 * @param size
 *    Size of elements.
 *
 * @return
 *    A pointer to the memory block allocated by the function.
 *
 *    The type of this pointer is always void*, which can be cast to the
 *    desired type of data pointer in order to be dereferenceable.
 *
 *    If the function failed to allocate the requested block of memory, a
 *    NULL pointer is returned.
 *
 * @see http://www.cplusplus.com/reference/clibrary/cstdlib/calloc/
 */
void *calloc(size_t num, size_t size) { 
	size_t total = size*num;
	char * ptr = malloc(total);
	size_t i;	

	for(i = 0; i < total; i++){
		ptr[i] = 0;
	}
	

return ptr; 
}

/**
 * Allocate memory block
 *
 * Allocates a block of size bytes of memory, returning a pointer to the
 * beginning of the block.  The content of the newly allocated block of
 * memory is not initialized, remaining with indeterminate values.
 *
 * @param size
 *    Size of the memory block, in bytes.
 *
 * @return
 *    On success, a pointer to the memory block allocated by the function.
 *
 *    The type of this pointer is always void*, which can be cast to the
 *    desired type of data pointer in order to be dereferenceable.
 *
 *    If the function failed to allocate the requested block of memory,
 *    a null pointer is returned.
 *
 * @see http://www.cplusplus.com/reference/clibrary/cstdlib/malloc/
 */
void *malloc(size_t size) { 
	if(size == 0)
		return NULL;

	if(head == NULL)
		initiate();

		void * ret = searchSize(size);
		if(ret && getSize(ret) < size)
			return NULL;

		if(ret)
			return ret;

	return NULL;
	
}

/**
 * Deallocate space in memory
 *
 * A block of memory previously allocated using a call to malloc(),
 * calloc() or realloc() is deallocated, making it available again for
 * further allocations.
 *
 * Notice that this function leaves the value of ptr unchanged, hence
 * it still points to the same (now invalid) location, and not to the
 * null pointer.
 *
 * @param ptr
 *    Pointer to a memory block previously allocated with malloc(),
 *    calloc() or realloc() to be deallocated.  If a null pointer is
 *    passed as argument, no action occurs.
 */
void free(void *ptr) {
  // "If a null pointer is passed as argument, no action occurs."
  if (!ptr)
    return;

	/////printf("\t\t\t\t\tBEFORE\nFreeing: %zu\n", (size_t)ptr);
	//printSturcture();

{

			//printf("Fucked up size: %d\n", getSize((memoryMap*)ptr - 1));
			memoryMap * prev = getPrev(ptr);
			//printSturcture();
			//printf("ptr: %p\t prev: %p \t size: %d\n", (ptr), prev, ((memoryMap*) ptr-2)->size); 
			//printf("ptr: %zu\t prev: %zu\n", (size_t)(ptr), (size_t)sbrk(0)); 
			//printf("ptr size: %zu\t prev size: %zu\n", (size_t)getSize(ptr - 1), (size_t)getSize(prev)); 

			ptr = combineForward(ptr);
			if((size_t)ptr != (size_t)head && getFree(prev))
				ptr = combineForward(prev);


			//printf("\t\t\t\t\tAFTER\n");
			//printSturcture();
		
	}
  return;
}

/**
 * Reallocate memory block
 *
 * The size of the memory block pointed to by the ptr parameter is changed
 * to the size bytes, expanding or reducing the amount of memory available
 * in the block.
 *
 * The function may move the memory block to a new location, in which case
 * the new location is returned. The content of the memory block is preserved
 * up to the lesser of the new and old sizes, even if the block is moved. If
 * the new size is larger, the value of the newly allocated portion is
 * indeterminate.
 *
 * In case that ptr is NULL, the function behaves exactly as malloc, assigning
 * a new block of size bytes and returning a pointer to the beginning of it.
 *
 * In case that the size is 0, the memory previously allocated in ptr is
 * deallocated as if a call to free was made, and a NULL pointer is returned.
 *
 * @param ptr
 *    Pointer to a memory block previously allocated with malloc(), calloc()
 *    or realloc() to be reallocated.
 *
 *    If this is NULL, a new block is allocated and a pointer to it is
 *    returned by the function.
 *
 * @param size
 *    New size for the memory block, in bytes.
 *
 *    If it is 0 and ptr points to an existing block of memory, the memory
 *    block pointed by ptr is deallocated and a NULL pointer is returned.
 *
 * @return
 *    A pointer to the reallocated memory block, which may be either the
 *    same as the ptr argument or a new location.
 *
 *    The type of this pointer is void*, which can be cast to the desired
 *    type of data pointer in order to be dereferenceable.
 *
 *    If the function failed to allocate the requested block of memory,
 *    a NULL pointer is returned, and the memory block pointed to by
 *    argument ptr is left unchanged.
 *
 * @see http://www.cplusplus.com/reference/clibrary/cstdlib/realloc/
 */
void *realloc(void *ptr, size_t size) {
  // "In case that ptr is NULL, the function behaves exactly as malloc()"
	if(size%8)
		size += 8 - size%8;
  if (!ptr)
    return malloc(size);

  // "In case that the size is 0, the memory previously allocated in ptr
  //  is deallocated as if a call to free() was made, and a NULL pointer
  //  is returned."
  if (!size) {
    free(ptr);
    return NULL;
  }
		
	int ogSize = getSize(ptr);

	combineForward(ptr);

	if((size_t)getSize(ptr) < size){
		memoryMap * newPtr = malloc(size);
		if(getSize(newPtr) < size)
			return NULL;
		memcpy(newPtr, ptr, ogSize);
		free(ptr);
		return newPtr;
	}
	
	if((size_t)getSize(ptr) >= size){
		divideMemory(size, ptr);
		return ptr;
	}
  return NULL;
}



//helpers!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1

void initiate(){
	time_t t;
	srand((unsigned) time(&t));

	size_t size = 704 + 2*STRUCTSIZE;
	void * givin = sbrk(size);
	head = givin  + STRUCTSIZE;
	tail = (void*)head;
	totalSize = size;

		initStructs(tail, 704);
	setFree(tail, TRUE);
}




void initStructs(memoryMap * ptr, size_t size){
	initVariables(ptr, size, 0);
}


void initVariables(memoryMap * ptr, size_t size, char blocksize){
	setSize(ptr, size);

	setFree(ptr, FALSE);
 	(ptr - 1)->nextFree = NULL;
	(ptr - 1)->prevFree = NULL;
	checkTail(ptr);
}


/*void varifyValues(memoryMap * ptr){
	memoryMap * back = getback(ptr);
	if(getSize(ptr) != getSize(back))
		return FALSE;
	if(getNextFree(ptr) != getNextFree(back))
		return FALSE;
	if(getPrevFree(ptr) != getPrevFree(back))
		return FALSE;
	if(getFree(ptr) != getFree(back))
		return FALSE;
	if((ptr - 1)->Varification != back->Varification)
		return FALSE;
	if((ptr - 1)->varification != back->varification)
		return FALSE;
}*/


/*void varifyValues(memoryMap * ptr){
	memoryMap * back = getback(ptr);
	if(getSize(ptr) != getSize(back))
		return FALSE;
	if(getNextFree(ptr) != getNextFree(back))
		return FALSE;
	if(getPrevFree(ptr) != getPrevFree(back))
		return FALSE;
	if(getFree(ptr) != getFree(back))
		return FALSE;
	if((ptr - 1)->Varification != back->Varification)
		return FALSE;
	if((ptr - 1)->varification != back->varification)
		return FALSE;
}*/


void checkTail(memoryMap * ptr){
	if(getPrev(ptr) == tail)
		tail = ptr;
}


char getFree(memoryMap * ptr){return (ptr - 1)->Free;}
size_t getSize(memoryMap * ptr){return (size_t)(ptr - 1)->size;}


void setSize(memoryMap * ptr, size_t size){
	(ptr - 1)->size = (unsigned int)size;
	getback(ptr)->size = (unsigned int)size;
}





void * searchSize(size_t size){
	if(size%8)
		size += 8 - size%8;
	
	memoryMap * explorer = freehead;
	memoryMap * best = NULL;
	int diff = -1;
	while(explorer && explorer < tail){
		int curDiff = -1;
		if(getFree(explorer)){
			curDiff = getSize(explorer) - size; 
			if(curDiff >= 0){
				if(curDiff == 0){
					initStructs(explorer, size);
					return explorer;
				}
				if(curDiff < diff || (diff == -1 && curDiff>-1)){
					best = explorer;
					diff = curDiff;
				}
			}
		}
		explorer = getNextFree(explorer);
	}
	int taildiff = -1;
	if(getFree(tail))
		taildiff = getSize(tail) - size;
	if(taildiff > -1 && diff == -1){
		void * imTiredOfAllTheseBugs = tail;
		divideMemory(size, tail);
		
		return imTiredOfAllTheseBugs;
	}
	else if(diff>-1){
		divideMemory(size, best);
		//initStructs(best, size);
		return best;
	}
	else{
		void * newb = expandMemory(size);
		initStructs(newb, size);
		return newb;
	}
}

memoryMap * getback(memoryMap * ptr){
		return (void*)ptr + getSize(ptr);
}


void * getnext(memoryMap * ptr){
	if(ptr == tail)
		return NULL;
	return (void*)ptr + getSize(ptr) + STRUCTSIZE*2;
}


void * getPrev(memoryMap * ptr){
	if(ptr == head){
		return NULL;
	}
	memoryMap * ptrPrev = ptr - 1;
	return ((void*)ptr - getSize(ptrPrev) - STRUCTSIZE*2);
}



void * 	expandMemory(size_t size){
	void* check;

	if((size_t)totalSize < size){
		if(size < 2*STRUCTSIZE)
			size += 2*STRUCTSIZE;
		check = sbrk(size);
	}
	else
		check = sbrk(totalSize);

	//printf("Valid?: %li\n", (long int)check);
	if((long int)check == -1)
		check = sbrk(size);
	if((long int)check == -1)
		return NULL;

	void * temp = tail;
	tail = NULL;

	tail = getnext(temp);
	void * locationFool = sbrk(0);

	size_t tailSize = (size_t)locationFool - (size_t)tail;
	//printf("tailSize: %zu\t location: %zu\t tail: %zu\t check: %zu\n", tailSize, (size_t)locationFool, (size_t)tail, (size_t)check);
	initStructs(tail, tailSize - STRUCTSIZE);	

	setFree(tail, TRUE);

	if(getFree(temp)){
		tail = combineForward(temp);	
	}
	
	totalSize+= tailSize;
	void * ptr = tail;
	if(getSize(tail) < size){
		return expandMemory(size);
	}
	//if(getSize(ptr) < size)
		divideMemory(size, tail);
	return ptr;
}


void * combineForward(memoryMap * ptr){
	if(ptr == tail)
		return ptr;

	memoryMap * next = getnext(ptr);
	size_t nextSize = 0;
	size_t currentSize = getSize(ptr);

	if(next)
		nextSize = getSize(next);



	if(getFree(next)){
		rmLink(next);
		size_t elemSize = STRUCTSIZE*2;
		initStructs(ptr,  nextSize + currentSize + (size_t)elemSize);
		if(next == tail)
			tail = ptr;
	}
	setFree(ptr, TRUE);	
	return ptr;
}



void divideMemory(size_t sizeDesired, memoryMap * ptr){
	int size = getSize(ptr);
	//sizeDesired += 16;
	if(sizeDesired + (size_t)STRUCTSIZE*2 + 16 <= (size_t)size ){
		memoryMap * back = ((void*)ptr) + STRUCTSIZE*2 + sizeDesired;
		size_t backSize = size - sizeDesired - STRUCTSIZE*2;
		initStructs(ptr, sizeDesired);
		initStructs(back, backSize);
		if(ptr == tail)
			tail = back;
		setFree(back, TRUE);
	}
	else{
		initStructs(ptr, size);
	}
}



void setFree(memoryMap * ptr, char free){
	//printFrees();
	(ptr - 1)->Free = free;
	getback(ptr)->Free = free;
	if(free)
		addLink(ptr);
	else
		rmLink(ptr);
	//printFrees();
}



int checkFree(memoryMap * ptr){
	memoryMap * ex = freehead;
	while(ex){
		if(ex == ptr)
			return FALSE;
		ex = getNextFree(ex);
	}
	return TRUE;
}

void printFrees(){
	if(!freehead)
		return;
	memoryMap * ex = freehead;
	printf("%p", ex);
	ex = getNextFree(ex);
	while(ex){
		printf(" => %p", ex);
		ex = getNextFree(ex);
	}
	printf("\n");
}


void printFreesP(){
	if(!freehead)
		return;
	memoryMap * ex = freetail;
	printf("%p", ex);
	ex = getPrevFree(ex);
	while(ex){
		printf(" => %p", ex);
		ex = getPrevFree(ex);
	}
	printf("\n");
}


void addLink(memoryMap * ptr){
	if(freehead == NULL || checkFree(ptr)){
		if(freehead == NULL){
			freehead = ptr;
			freetail = ptr;
			(ptr - 1)->nextFree = NULL;
			(ptr - 1)->prevFree = NULL;
		}	
		else{
			setNextFree(freetail, ptr);
			(ptr - 1)->nextFree = NULL;
			freetail = ptr;
		}
	}
}


void rmLink(memoryMap * ptr){
	if(!freehead)
		return;
	if(!ptr)
		return;

	if(!(freehead == NULL) && !checkFree(ptr)){
		if(ptr == freehead && ptr == freetail){
			freehead = NULL;
			freetail = NULL;
		}
		else if(ptr == freehead){
			freehead = getNextFree(ptr);
			(getNextFree(ptr) - 1)->prevFree = NULL;
		}
		else if(ptr == freetail){
			freetail = getPrevFree(ptr);
	 		(getPrevFree(ptr) - 1)->nextFree = NULL;
		}
		else{
			setNextFree(getPrevFree(ptr), getNextFree(ptr));
		}
		(ptr - 1)->nextFree = NULL;
		(ptr - 1)->prevFree = NULL;
	}
}


void setNextFree(memoryMap * current, memoryMap * next){
	if(current){
		(current - 1)->nextFree = (struct memoryStruct*)next;
	}
	if(next)
		(next - 1)->prevFree = (struct memorySturct *)current;
			
}

memoryMap * getPrevFree(memoryMap * ptr){
	if((size_t)ptr > (size_t)tail + getSize(tail) || (size_t)ptr < (size_t)head)
		return NULL;
	return (memoryMap*)(ptr - 1)->prevFree;
}

memoryMap * getNextFree(memoryMap * ptr){
	if((size_t)ptr > (size_t)tail + getSize(tail) || (size_t)ptr < (size_t)head)
		return NULL;
	return (memoryMap*)(ptr - 1)->nextFree;
}






