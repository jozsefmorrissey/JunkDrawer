#include <stdlib.h>
#include <stdio.h>
#include "mini_valgrind.h"
int main()
{
    char *ptr1 = (char *)malloc(10);
    char *ptr2 = (char *)malloc(10);
    char *ptr3 = (char *)malloc(10);
    char *ptr4 = (char *)malloc(10);

    free(ptr1);
    ptr1 = NULL;
    free(ptr2);
    ptr2 = NULL;
    free(ptr3);
    ptr3 = NULL;
    free(ptr4);
    ptr4 = NULL;


    ptr1 = (char *)malloc(10);
    ptr2 = (char *)malloc(10);
    ptr3 = (char *)malloc(10);
    ptr4 = (char *)malloc(10);

    free(ptr2);
    ptr2 = NULL;
    free(ptr1);
    ptr1 = NULL;
    free(ptr4);
    ptr4 = NULL;
    free(ptr3);
    ptr3 = NULL;

	printf("%d\n", -1*(int)sizeof(char)*32 + 8);


    // Do NOT modify this line
    atexit(print_report);
    return 0;
}
