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

char invalid[] = {"ENTER ALL NEGATIVE VALUES TO END\n\n"};


int order, ct1, ct2, ct3=0, ct4, x=0, fail, card[52], suit[52], temp1[52], temp2[52], start;
int main(void)

{ srand(time(NULL));

    for(ct1=0; ct1<52; ct1++)
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
      }   for(ct4=0; ct4<52; ct4++)
   printf("%i %i\n", card[ct4], suit[ct4]);
   system ("PAUSE");

   printf("shuffle?(Y/N); ");
      start=getchar();
   do
   {  if(start=='y' || start=='Y')
      {for (ct1=0;ct1<52;ct1++)
       {  temp1[ct1]=card[ct1];
          temp2[ct1]=suit[ct1];
          }


      ct2=0;

      for(ct1=0;ct1<26;ct1++)
      {card[ct2++]=temp1[ct1];
       card[ct2++]=temp1[ct1+26];
      }
      card[51]=temp1[51];

      ct2=0;

      for(ct1=0;ct1<26;ct1++)
      {suit[ct2++]=temp2[ct1];
       suit[ct2++]=temp2[ct1+26];
      }}
      fail=0;


   for(ct4=0; ct4<52; ct4++)
   printf("%i %i\n", card[ct4], suit[ct4]);
   system ("PAUSE");
   printf("shuffle?(Y/N); ");
      getchar();
      start=getchar();
   }while(start=='y' || start=='Y');


   system ("PAUSE");
   return 0;
}   // end main


int cardGen(void)
{
    return x++;
}


int suitGen(void)
{
    return rand()%4;
}




