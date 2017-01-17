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
void cardDisplay(int v, int s);

char invalid[] = {"ENTER ALL NEGATIVE VALUES TO END\n\n"};
const char *suit[4] = {"Clubs", "Diamonds", "Hearts", "Spades"};
const char *value[13] ={"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};

int main(void)
{  int players, p, i, n, x, y, z, r, t, fail, ct1, ct2, ct3, ct4;
   srand(time(NULL));
   ct1=ct2=ct3=ct4=t=x=y=0;

   printf("How many players (MAX 5): ");
   scanf("%i", &players);

   z=players*5;

   int card[z];
   int suit[z];

   for(ct1=0; ct1<players; ct1++)
{    for(ct2=0; ct2<5; ct2++)
    { do
      {  fail=0;
         card[ct3]=cardGen();
         suit[ct3]=suitGen();
         for(ct4=-1; ct4<z; ct4++)
         {  if(card[ct3]==card[ct4] && suit[ct3]==suit[ct4])
               fail++;
      }  }
      while(fail>1);

      ct3++;
    }
}
   ct4=0;

   for(ct1=0; ct1<players; ct1++)
   {  for(ct2=0; ct2<5; ct2++)
      {  for(ct3=0; ct3<5; ct3++)
         {  cardDisplay(card[ct4], suit[ct4]);
            ct4++;

         }
         system ("PAUSE");
         for(ct1=0; ct1<293; ct1++)
         printf("\n");

      }
   }
   return 0;
}   // end main


int cardGen(void)
{  return rand()%13;
}


int suitGen(void)
{  return rand()%4;
}


void cardDisplay(int v, int s)
{
   printf("%s of %s\n", value[v], suit[s]);
}







