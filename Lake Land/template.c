// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:
// Desc:

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int order, ct1, ct2, ct3, ct4, ct5, ct6,ct7, players, card[52];
void createDeck (void);

int main(void)
{   srand(time(NULL));

    int numShuf;

    for(ct1=0; ct1<52; ct1++)
        createDeck();

    for(ct4=0; ct4<51; ct4++)
   printf("%i \n", card[ct4]);

    system ("PAUSE");
    return 0;
}   // end main


int cardGen(void)
{   return rand()%52;
}



void createDeck (void)
{
    int fail;

    do
            {  fail=0;
               card[ct3]=cardGen();

               for(ct4=0; ct4<ct1; ct4++)
               {  if(card[ct3]==card[ct4])
                     fail++;
               }
            }while(fail>0);
            ct3++;
}


