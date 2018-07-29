#include "basketcases.h"

/**
 * You should allocate memory for the `basket_report_t` struct that you return, and set its members as described in the MP specification.
 *
 * @param   baskets     An array of `basket_t` structs
 * @param   length      The length of the array `baskets`
 * @return              A `basket_report_t` struct with fields set as described in the MP specification
 */
basket_report_t * handle_baskets (basket_t baskets[], size_t length) {
    // TODO: Implement this function.
	
	basket_report_t * report = malloc(sizeof(basket_report_t));

	if(length > 0){
	
		char * holder = malloc(10);
		strcpy(holder, baskets[length - 1].label);

		for(int i = length-1; i > 0; i--)
			strcpy(baskets[i].label, baskets[i-1].label);

		strcpy(baskets[0].label, holder);
		free(holder);

		if(length %2 == 1)
			report->target_basket = &baskets[length/2];
		else
			report->target_basket = NULL;

		report->triples_summed = 0;
		for(int i = 0; i < (int)length - 2; i++){
				report->triples_summed = report->triples_summed + (baskets[i].offset + baskets[i+1].offset + baskets[i+2].offset);

		}
		if(report->triples_summed == 0)
				report->bad_batch = &baskets[0];	
	}
	else{
		report->triples_summed = 0;	
		report->bad_batch = NULL;
	}
    return report;
}
