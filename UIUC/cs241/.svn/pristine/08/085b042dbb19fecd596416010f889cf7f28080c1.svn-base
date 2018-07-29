/** @file part2-main.c */

/*
 * Machine Problem #0
 * CS 241 Fall 2015
 */

#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#include "part2-functions.h"

/**
 * (Edit this function to print out the twelve "Illinois" lines in part2-functions.c in order.)
 */
int main()
{
	int * value = malloc(sizeof(int*)*10);
	first_step(81);
	*value = 132;
	second_step(value);

	int **erray ;
	*value = 8942;
	erray = &value;
	double_step(erray);
	
	*(int*)((void*)value+5) = 15;
	strange_step(value);

	char * str = "hey";
	empty_step(str);

	char * six = "fucu";
	two_step(six, six);

	char * f = "a";
	char * s = f + 2;
	char * t = s + 2;
	three_step(f,s,t);

	char * ff = "aa";
	char * ss = "cii";
	char * tt = "daqq";
	step_step_step(ff, ss, tt);

	int val = 'a';
	it_may_be_odd(f, val);
	//printf("%p\n", fun);
	//char * albert = malloc(11);
	char albert[10] = " ,CS241, ";
	tok_step(albert);


	
	tricky_step(fun);



	char o[4] ={1,2};
	//char o[1] = 
	the_end(o, o);

	free(value);
	//free(o);

    return 0;
}


data_t* other2(data_t* thas, data_t* thit){
	data_t * retval = malloc(sizeof(data_t));

	//printf("%f\n", thit->value.floatval);
	if(thas->value.intval < 101)
		retval->value.intval = thas->value.intval + thit->value.intval;



	//printf("%s\n", retval->value.stringval);
	else{	
		char * newb = malloc(100);
		char newbie [100];
		//char * newbs = malloc(100);
		sprintf(newbie, "%.2f", thit->value.floatval);
		//printf("this is my retval: %s\n", thas->value.stringval);
		//printf("this is my newb: %s\n", newb);
		//printf("this is my newbie: %s\n", newbie);
		//retval->value.stringval = malloc(100);
		//retval->value.stringval = newb;



		int max = 0;
		while(thas->value.stringval[max]!=0){
		//printf("this is my newb: %s\n", newb);
			newb[max] = thas->value.stringval[max];
			max++;
		}
		int count = 0;
		while(newbie[count] != 0){

			newb[max+count] = newbie[count];
			count++;
		}
		newb[count+max] = 0;
		//printf("this is my newb: %s\n", newb);
		//retval->value.stringval = NULL;
		retval->value.stringval = newb;
		
		//free(newb);
		//printf("this is my retval: %s\n", retval->value.stringval);
	}

	return retval;
} 
data_t* other(data_t* thas, data_t* thit){
	data_t * retval = malloc(sizeof(data_t));
	retval->type = thas->type;
	retval->type = thit->type;
	retval->value.floatval = (float)thas->value.intval - thit->value.floatval;
	retval->type = ERROR;
	return retval;
} 
stuff fun(char * pm){
	//stuff ot = otherfunc;
	//*ot = otherfunc(data_t, data_t);
	if(pm[0] == '-')
		return other;
	else
		return other2;
} 
