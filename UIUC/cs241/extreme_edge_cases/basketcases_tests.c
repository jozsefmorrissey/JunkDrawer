#include <string.h>
#include "basketcases_tests.h"
#include "basketcases.h"
#include <time.h>

/**
 * Test various implementations of `handle_baskets`. You are passed the function pointer `func`; you should compare the output of `func`
 * to your implementation of `handle_baskets` for various inputs...
 *
 * @param   func    An implementation of `handle_baskets` that may or may not be correct.
 */
int test_basketcases(basket_report_t *(*func)(basket_t[], size_t)) {

	if(!noBaskets(func))
		return 0;

	if(!oneBasket(func))
		return 0;

	if(!sixtySevenBaskets(func))
		return 0;

	if(!fiveBaskets(func))
		return 0;

    return 1;
}


int noBaskets(basket_report_t *(*func)(basket_t[], size_t)){
	basket_report_t *report = func(NULL, 0);
	if(report->bad_batch != NULL){
		free(report);
		return 0;
	}
	free(report);
	return 1;
}




int oneBasket(basket_report_t *(*func)(basket_t[], size_t)){
    char *original_string = "abcde";
	srand(time(NULL));
    basket_t *baskets = (basket_t*) calloc(1, sizeof(basket_t));
    baskets->label = malloc((strlen(original_string) + 1) * sizeof(char));
    strcpy(baskets->label, original_string);

    basket_report_t *report = func(baskets, 1);

    // TODO return 1 if `handle_baskets` worked correctly
	if(report->triples_summed != 0 ){//|| !strcmp(report->bad_batch->label, "abcde"))
			destroy(1,baskets, report);
		return 0;
	}

	if(report->bad_batch != &baskets[0]){
			destroy(1,baskets, report);
		return 0;
	}
			destroy(1,baskets, report);
	return 1;
}




int fiveBaskets(basket_report_t *(*func)(basket_t[], size_t)){
	char * l[5] = {"RAYNE", "RAYON", "RAZED", "RAZEE", "RAZER"};

	char * lr[5] = {"RAZER", "RAYNE", "RAYON", "RAZED", "RAZEE"};

	basket_t * busk = (basket_t*) calloc(5, sizeof(basket_t));
	
	for(int i = 0; i < 5; i++){
	    busk[i].label = malloc(10);
		strcpy(busk[i].label, l[i]);
														
		
		busk[i].offset = 1;
	}
	basket_report_t *report = func(busk, (size_t)5);
	for(int i = 0; i < 5; i++){
		if(strcmp(busk[i].label, lr[i])){
			destroy(5, busk, report);
			return 0;
		}
	}
	if(report->triples_summed != 9 ){//|| !strcmp(report->bad_batch->label, "abcde"))
		destroy(5, busk, report);
		return 0;
	}
	destroy(5, busk, report);
	return 1;
}

void destroy(int lim, basket_t * bastard, basket_report_t *report){
	for(int i = 0; i < lim; i++)
		free(bastard[i].label);
	free(report);
	free(bastard);
}




int sixtySevenBaskets(basket_report_t *(*func)(basket_t[], size_t)){
	char * list[66] = {"SERAL", "SERED", "SERER", "SERES", "SERFS", "SERGE", "SERIC", "SERIF", "SERIN", "SERKS", "SERON", "SEROW", "SERRA", "SERRE", "SERRS", "SERRY", "SERUM", "SERVE", "SERVO", "SESEY", "SESSA", "SETAE", "RAYLE", "RAYNE", "RAYON", "RAZED", "RAZEE", "RAZER", "RAZES", "RAZOO", "RAZOR", "REACH", "REACT", "READD", "READS", "READY", "REAKS", "REALM", "REALO", "REALS", "REAME", "REAMS", "REAMY", "REANS", "NEARS", "NEATH", "NEATS", "NEBEK", "NEBEL", "NECKS", "NEDDY", "NEEDS", "NEEDY", "NEELD", "NEELE", "NEEMB", "NEEMS", "NEEPS", "NEESE", "NEEZE", "NEGRO", "NEGUS", "NEIFS", "NEIGH", "NEIST", "NEIVE"};

		char * listRight[66] = {"NEIVE", "SERAL", "SERED", "SERER", "SERES", "SERFS", "SERGE", "SERIC", "SERIF", "SERIN", "SERKS", "SERON", "SEROW", "SERRA", "SERRE", "SERRS", "SERRY", "SERUM", "SERVE", "SERVO", "SESEY", "SESSA", "SETAE", "RAYLE", "RAYNE", "RAYON", "RAZED", "RAZEE", "RAZER", "RAZES", "RAZOO", "RAZOR", "REACH", "REACT", "READD", "READS", "READY", "REAKS", "REALM", "REALO", "REALS", "REAME", "REAMS", "REAMY", "REANS", "NEARS", "NEATH", "NEATS", "NEBEK", "NEBEL", "NECKS", "NEDDY", "NEEDS", "NEEDY", "NEELD", "NEELE", "NEEMB", "NEEMS", "NEEPS", "NEESE", "NEEZE", "NEGRO", "NEGUS", "NEIFS", "NEIGH", "NEIST"};

	basket_t * bask = (basket_t*) calloc(66, sizeof(basket_t));
	
	for(int i = 0; i < 66; i++){
	    bask[i].label = malloc(10);
		strcpy(bask[i].label, list[i]);
														
		int r = rand();
		bask[i].offset = r%1500;
	}

	basket_report_t *report = func(bask, (size_t)66);
	for(int i = 0; i < 66; i++){
		if(strcmp(bask[i].label, listRight[i])){
			destroy(66, bask, report);
			return 0;
		}
	}
	if(report->triples_summed != tsum(bask, 66)){//|| !strcmp(report->bad_batch->label, "abcde"))
		destroy(66, bask, report);
		return 0;
}
	destroy(66, bask, report);
	return 1;
}




int tsum(basket_t baskets[], size_t length){
	int cabage = 0;
	for(int i = 0; i < (int)length - 2; i++)
		cabage = cabage + (baskets[i].offset + baskets[i+1].offset + baskets[i+2].offset);

	return cabage;
}







