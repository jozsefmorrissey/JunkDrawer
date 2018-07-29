#ifndef __BASKETCASES_TESTS_H
#define __BASKETCASES_TESTS_H

#include <stdlib.h>
#include <stdio.h>
#include "basketcases.h"

int test_basketcases(basket_report_t *(*func)(basket_t[], size_t));

int noBaskets(basket_report_t *(*func)(basket_t[], size_t));
int oneBasket(basket_report_t *(*func)(basket_t[], size_t));
int fiveBaskets(basket_report_t *(*func)(basket_t[], size_t));
int sixtySevenBaskets(basket_report_t *(*func)(basket_t[], size_t));

int tsum(basket_t baskets[], size_t length);

void destroy(int lim, basket_t * bastard, basket_report_t *report);

#endif
