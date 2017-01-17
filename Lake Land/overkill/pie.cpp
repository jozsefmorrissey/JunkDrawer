// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:    Program 04
// Desc:    Program calculates pi to a desired accuracy using a summation.

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define PI 3.14159265358979

int main(void)

{    
   time_t start, end;  //timer used to find efficiency of different commands.
    
   long int counter=-1;   //first time through do while total is not calculated.
   double total=0, sign=4, test=1, term=0, denom=1, error, epsilon, elapsed;

   printf("To what tolerance would you like to calculate Pi?  ");
   scanf("%lf", &epsilon);
   
   time(&start);

do  //sums pi
{  
    
   total=total+term;
   
   term=sign/denom;
   
   denom+=2;
   
   sign*=(-1);
   
   counter++;

test=term;

if (test<0)  //determines absolute value of term without changing terms value.
   test*=(-1);

}
while (test>epsilon);  //tests new value before adding to total.

error=100*(total-PI)/PI; //calculates percent error

if (error<0)    //takes absolute value of error.
   error*=(-1); 
   
   time(&end);

   elapsed=difftime(end, start);

printf("After %li terms, pi is approximately:  %.15f\nFirst o", counter, total);
printf("mitted term = %.15f is less than %.9f\nPercent relativ", term, epsilon);   
printf("e error = %.10f%%\nTime elapsed: %.1f seconds\n\n", error, elapsed);
    
    system("PAUSE");
    
    
    return 0;
}   // end main     







 




  
