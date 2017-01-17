// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:
// Desc:

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

void tradeIn(int p);
void cardDisplay(int v, int s);
void deal(void);
void displayOrder(void);
void createDeck (void);
void shuffle (void);
void bridge (void);
int cardGen(void);
int suitGen(void);
void displayHand(int p);


int order, ct1, ct2, ct3, ct4, ct5, ct6,ct7, players;
int card[52], suit[52], display[52], temp1[52], temp2[52], temp3[52];

int main(void)
{
    int numShuf;
    
        

    for(ct1=0; ct1<52; ct1++)
        createDeck();

    printf("A randomized deck has been created:\n");
    displayOrder();

    ct7=1;
    for(ct1=0; ct1<52; ct1++)
    {   printf("%i %i\t", card[ct1], suit[ct1]);
        cardDisplay(card[ct1], suit[ct1]);
    }

    printf("\n\nHow many times would you like to shuffle: ");
    scanf("%i", &numShuf);

    for(ct5=0; ct5<numShuf; ct5++)
    {   shuffle();
        bridge();
        displayOrder();
    }

    printf("\n\nThe final order is:\n");
    displayOrder();

    deal();

    system ("PAUSE");
    return 0;
}   // end main


int cardGen(void)
{   return rand()%13;
}


int suitGen(void)
{   return rand()%4;
}


void createDeck (void)
{
    int fail;

        do
            {  fail=0;
               card[ct3]=cardGen();
               suit[ct3]=suitGen();
               for(ct4=0; ct4<ct1; ct4++)
               {  if(card[ct3]==card[ct4] && suit[ct3]==suit[ct4])
                     fail++;
               }
            }while(fail>0);
            ct3++;
}


void shuffle (void)
{   for (ct1=0;ct1<52;ct1++)
    {   temp1[ct1]=card[ct1];
        temp2[ct1]=suit[ct1];
        temp3[ct1]=display[ct1];
    }

    ct2=51;
    ct3=0;


    for(ct1=0;ct1<13;ct1++)
    {  card[ct3++]=temp1[ct2-3];
        card[ct3++]=temp1[ct2-2];
        card[ct3++]=temp1[ct2-1];
        card[ct3++]=temp1[ct2];
        ct2-=4;
    }
    ct2=51;
    ct3=0;

    for(ct1=0;ct1<13;ct1++)
    {   suit[ct3++]=temp2[ct2-3];
        suit[ct3++]=temp2[ct2-2];
        suit[ct3++]=temp2[ct2-1];
        suit[ct3++]=temp2[ct2];
        ct2-=4;
    }
    ct2=51;
    ct3=0;


    for(ct1=0;ct1<13;ct1++)
    {  display[ct3++]=temp3[ct2-3];
        display[ct3++]=temp3[ct2-2];
        display[ct3++]=temp3[ct2-1];
        display[ct3++]=temp3[ct2];
        ct2-=4;
    }
}


void bridge (void)
{   for (ct1=0;ct1<52;ct1++)
    {   temp1[ct1]=card[ct1];
        temp2[ct1]=suit[ct1];
        temp3[ct1]=display[ct1];
    }


    ct2=0;

    for(ct1=0;ct1<26;ct1++)
    {   card[ct2++]=temp1[ct1];
        card[ct2++]=temp1[ct1+26];
    }

    ct2=0;

    for(ct1=0;ct1<26;ct1++)
    {   suit[ct2++]=temp2[ct1];
        suit[ct2++]=temp2[ct1+26];
    }

    ct2=0;

    for(ct1=0;ct1<26;ct1++)
    {   display[ct2++]=temp3[ct1];
        display[ct2++]=temp3[ct1+26];
    }
 }

 void displayOrder(void)
 {  ct2=0;
    for(ct1=0; ct1<2; ct1++)
    {   for(ct4=0; ct4<5; ct4++)
        {    for(ct3=0; ct3<4; ct3++)
             printf("%2i ", display[ct2++]);
             printf(", ");
        }
        printf("\n");
    }

    for(ct4=0; ct4<3; ct4++)
    {   for(ct3=0; ct3<4; ct3++)
        printf("%2i ", display[ct2++]);
        printf(", ");
    }

    printf("\n\n");
 }

void deal(void)
{   int players;
    ct1=0;

    printf("How many players(Max 5)");
    scanf("%i", &players);

    ct7=0;
    for(ct1=0; ct1<52; ct1++)
    {   printf("%i %i\t", card[ct1], suit[ct1]);
        cardDisplay(card[ct1], suit[ct1]);
    }

    ct5=26;
    ct4=0;
    ct6=0;
    for(ct1=0; ct1<players; ct1++)
    {

        printf("Player %i:\n", ct1+1);
        system ("PAUSE");



        displayHand(players);



        ct7=1;
        tradeIn(players);
    }

        ct6=0;
        ct4=0;

    for(ct1=0; ct1<players; ct1++)
    {   printf("Player %i\n", ct1+1);
        displayHand(players);
    }



}

void displayHand(int p)
{   ct7=1;

    for(ct3=0; ct3<5; ct3++)
        {
            cardDisplay(card[ct4], suit[ct4]);
            ct4++;
        }
        printf("\n");
}

void cardDisplay(int v, int s)
{   const char *suit[4] = {"Clubs", "Diamonds", "Hearts", "Spades"};
    const char *value[13] ={"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};


    printf("%i. %s of %s\n", ct7++, value[v], suit[s]);
}
void tradeIn(int p)
{
    int trade, disgaurd[4], v;


    printf("How many cards would you like to trade in? ");
    scanf("%i", &trade);

    for(ct2=0; ct2<trade; ct2++)
    {   printf("Enter the corrisponding number to one card you wish to disgaurd: ");
        scanf("%i", &disgaurd[ct2]);

        v=5*(ct1)+disgaurd[ct2]-1;
        printf("%i %i %i, initial %i, final %i\n", ct1, ct2, ct5, v, ct5);

        card[v]=card[ct5];
        suit[v]=suit[ct5++];
    }


}
