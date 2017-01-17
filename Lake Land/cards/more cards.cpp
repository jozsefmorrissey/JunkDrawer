// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:
// Desc:

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

int cardGen(void);
int suitGen(void);

int order, ct1, ct2, ct3=0, ct4, x=0, fail, card[52], suit[52];




int main(void)
{  char start;
   srand(time(NULL));
   order=rand();
   do
   {  printf("shuffle?(Y/N); ");
      start=getchar();
      if(start=='y' || start=='Y')
      for(ct1=-1; ct1<52; ct1++)
      {  do
            {  fail=0;
               card[ct1]=cardGen();
               suit[ct1]=suitGen();
               for(ct4=0; ct4<ct1; ct4++)
               {  if(card[ct3]==card[ct4] && suit[ct3]==suit[ct4])
                     fail++;
               }
            }while(fail>0);
            ct3++;
      }
   for(ct4=-1; ct4<51; ct4++)
   printf("%i %i\n", card[ct4], suit[ct4]);
   system ("PAUSE");
   }while(start=='y' || start=='Y');


   system ("PAUSE");
   return 0;
}   // end main


int cardGen(void)
{
    return rand()%13;
}


int suitGen(void)
{
    return rand()%4;
}






