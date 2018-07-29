#ifndef __BASKETCASES_H
#define __BASKETCASES_H
#define _XOPEN_SOURCE 700

//#include <time.h>
#include <stdlib.h>
#include <stdio.h>
//#include <unistd.h>
//#include <sys/wait.h>
//#include <sys/types.h>
#include <string.h>


typedef struct {
    char *label;
    int offset;
} basket_t;

typedef struct {
    int triples_summed;
    basket_t *target_basket;
    basket_t *bad_batch;
} basket_report_t;

basket_report_t *handle_baskets (basket_t baskets[], size_t length);

#endif
