// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:    PROG07
// Desc:    This program creates a randomized deck of cards. Lets
//          the user choose between playing poker or displaying
//          the deck five cards at a time. The poker function
//          allows the user to shuffle the deck. Deals 5 cards to
//          upto 5 players allowing for one turn in of up to 4
//          cards.

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void assigned(void);
void poker(void);
void tradeIn(int p);
void deal(void);

void createDeck (void);
int cardGen(void);
int suitGen(void);
void shuffle (void);
void bridge (void);

void displayOrder(void);
void displayHand(void);
void cardDisplay(int v, int s);


//Variables ct1-5 are counters. This program requires over 20 counters up to 5
//at one time.
int order, ct1, ct2, ct3, ct4, ct5, players;
int value[52], suit[52], display[52], temp1[52], temp2[52], temp3[52];

int main(void)
{   srand(time(NULL));
    char prog;
    int seed, end=0;

        //Prompts the user to seed the program.
        printf("Enter seed value, either:\n\t>= 0 to randomize rand( ) with u");
        printf("ser-input seed value\n\t<  0 to randomize rand( ) with system");
        printf(" time\n");

        scanf("%i", &seed);

        if(seed>=0)
            srand(seed);

        if(seed<0)
            srand(time(NULL));

        do
        {   ct1=ct2=ct3=ct4=ct5=0;

            printf("Do you want to use the assigned function, play poker or end program(A/P/E)?");
            prog=getchar();
            prog=getchar();

            printf("\n");

            if (prog=='A' || prog=='a')
            {   for(ct1=0; ct1<52; ct1++)
                    createDeck();

                assigned();
            }

            else if(prog=='p' || prog=='P')
            {   poker();
            }
            else if(prog=='e' || prog=='E')
            {
                for(ct2=0; ct2<100000; ct2++)  //Smilies!
                    printf("%2c", 1);
                end++;
            }
            //Prints after invalid inputs.
            else
                printf("Would you like to try that again.....\n\n");

        }while(end==0);
        return 0;
}   // end main


//Displays 5 cards at a time working through the deck.
void assigned(void)
{   char cont;
    int terminate;
    terminate=ct4=0;





    do
    {   displayHand();

        do
        {   //Skips option to continue when the deck is cashed out.
            if(ct4!=50)
                {   printf("continue? ");
                    cont=getchar();
                    cont=getchar();
                }
            else
                cont='N';

            //Terminates the outer while loop.
            if(cont=='N' || cont=='n')
                terminate++;

            //Introducing couter 5 allows the loop to keep running until a valid input is recieved.
            if(cont=='Y' || cont=='y' || cont=='N' || cont=='n')
                ct5++;

            else
            {   printf("You wanna try that again.....\n\n");
                ct5=0;
            }
        }while(ct5==0);

    }while(terminate==0);
    printf("\n");
}


void poker(void)
{   char again;
    int numShuf;

    do
    {   ct1=ct2=ct3=ct4=ct5=0;

        for(ct1=0; ct1<52; ct1++)
            createDeck();

        printf("A randomized deck has been created:\n");
        displayOrder();

        printf("\n\nHow many times would you like to shuffle: ");
        scanf("%i", &numShuf);

        //Shuffles bridges and displays the order for the number of times specified.
        for(ct5=0; ct5<numShuf; ct5++)
        {   if(ct5==numShuf-1)  //Prints above the final order to declare the final card order.
            printf("\n\nThe final order is:\n");
            shuffle();
            bridge();
            displayOrder();
        }

        deal();

        //This program cannot determine a winner.
        printf("You figure out who won\n\nDo you want to play again (Y/N)? ");
        getchar();  //This getchar receives the new line character left by
        again=getchar();           //previous scanf.

    }while(again=='y');

    printf("\n");
}


void tradeIn(int p)  //Allows each player to trade in cards.
{   int trade, disgaurd[4], initial;

    do  //Continues to ask until player acknowledges 4 is the maximum value
    {   printf("How many cards would you like to trade in (MAX 4)? ");
        scanf("%i", &trade);
    }while(trade>4);

    printf("Which card would you like to discard: ");
    for(ct2=0; ct2<trade; ct2++)
    {   do
        {
            scanf("%i", &disgaurd[ct2]);
            if(disgaurd[ct2]<6 && disgaurd[ct2]>0)
        {//Initial is the initial card location ct1=player-1 since array starts at
         //zero, one must be subtracted form the input values by the player.
            initial=5*(ct1)+disgaurd[ct2]-1;

         //ct5 equals 25 initially and is incremented each time it is called.
            value[initial]=value[ct5];
            suit[initial]=suit[ct5++];
        }
        }while(disgaurd[ct2]>5 || disgaurd[ct2]<1);
    }

//Exceeds number of lines displayed preventing players from seeing one another’s
//cards.
for(ct3=0; ct3<293; ct3++)
    printf("\n");
}


//Deals the randomized deck of cards to up to 5 players.
void deal(void)
{   int players;
    ct1=0;

    do  //continues to ask until they enter a number from 1 to 5.
    {   printf("How many players(Max 5): ");
        scanf("%i", &players);
    }while(players>5);

    ct5=25;  //Tells tradeIn where to start getting new cards from.
    ct4=0;
    //Deals to the specified number of players.
    for(ct1=0; ct1<players; ct1++)
    {
        for(ct2=0; ct2<293; ct2++)
            printf("\n");

        printf("Player %i:\n", ct1+1); //Prints player number before displaying
        system ("PAUSE");              //cards.



        displayHand();


        tradeIn(players);
    }

    ct4=0;  //Counts array location from 0 to 5*players.
    //Displays everyone’s hand after all trades have been made.
    for(ct1=0; ct1<players; ct1++)
    {   printf("Player %i\n", ct1+1);
        displayHand();
}   }


//Creates a random deck of cards.
void createDeck (void)
{   int fail;

    do
    {   fail=0;
        value[ct3]=cardGen();     //The display is used to illustrate the
        suit[ct3]=suitGen();      //shuffle function with values 1-52.
        display[ct3]=ct3+1;
        //this tests each new card against the previously created cards.
        for(ct4=0; ct4<ct1; ct4++)
        {   if(value[ct3]==value[ct4] && suit[ct3]==suit[ct4])
            fail++; //Fail is incremented if the new card equals any of the
        }                                                 //previous cards.
    }while(fail>0);

    ct3++;
}


int cardGen(void)
{   return rand()%13;
}


int suitGen(void)
{   return rand()%4;
}

//Rebuilds the deck four cards at a time.
void shuffle (void)
{   int x;
    //Randomly desides which suffle group to use.
    x=rand()%2;
    //Creates temporary copies of arrays.
    for (ct1=0;ct1<52;ct1++)
    {   temp1[ct1]=value[ct1];
        temp2[ct1]=suit[ct1];
        temp3[ct1]=display[ct1];
    }
    if(x==0)
    {   ct2=51;
        ct3=0;
        for(ct1=0;ct1<13;ct1++)         //Each of these take four variables assigned
        {  value[ct3++]=temp1[ct2-3];   //to the bottom and places them on top.
            value[ct3++]=temp1[ct2-2];
            value[ct3++]=temp1[ct2-1];
            value[ct3++]=temp1[ct2];
            ct2-=4;
        }
        ct2=51;
        ct3=0;                          //Could have placed them all in a single for
        for(ct1=0;ct1<13;ct1++)         //loop. it seems easier to read and understand this way.
        {   suit[ct3++]=temp2[ct2-3];
            suit[ct3++]=temp2[ct2-2];
            suit[ct3++]=temp2[ct2-1];
            suit[ct3++]=temp2[ct2];
            ct2-=4;
        }
        ct2=51;
        ct3=0;
        for(ct1=0;ct1<13;ct1++)
        {   display[ct3++]=temp3[ct2-3];
            display[ct3++]=temp3[ct2-2];
            display[ct3++]=temp3[ct2-1];
            display[ct3++]=temp3[ct2];
            ct2-=4;
    }   }
    if(x==1)
    {   ct2=51;
        ct3=0;
        for(ct1=0; ct1<4; ct1++)
        {   value[ct3++]=temp1[ct2-12];
            value[ct3++]=temp1[ct2-11];
            value[ct3++]=temp1[ct2-10];
            value[ct3++]=temp1[ct2-9];
            value[ct3++]=temp1[ct2-8];
            value[ct3++]=temp1[ct2-7];
            value[ct3++]=temp1[ct2-6];
            value[ct3++]=temp1[ct2-5];
            value[ct3++]=temp1[ct2-4];
            value[ct3++]=temp1[ct2-3];
            value[ct3++]=temp1[ct2-2];
            value[ct3++]=temp1[ct2-1];
            value[ct3++]=temp1[ct2];
            ct2-=13;
        }

        ct2=51;
        ct3=0;
        for(ct1=0; ct1<4; ct1++)
        {   suit[ct3++]=temp2[ct2-12];
            suit[ct3++]=temp2[ct2-11];
            suit[ct3++]=temp2[ct2-10];
            suit[ct3++]=temp2[ct2-9];
            suit[ct3++]=temp2[ct2-8];
            suit[ct3++]=temp2[ct2-7];
            suit[ct3++]=temp2[ct2-6];
            suit[ct3++]=temp2[ct2-5];
            suit[ct3++]=temp2[ct2-4];
            suit[ct3++]=temp2[ct2-3];
            suit[ct3++]=temp2[ct2-2];
            suit[ct3++]=temp2[ct2-1];
            suit[ct3++]=temp2[ct2];
            ct2-=13;
        }

        ct2=51;
        ct3=0;
        for(ct1=0; ct1<4; ct1++)
        {   display[ct3++]=temp3[ct2-12];
            display[ct3++]=temp3[ct2-11];
            display[ct3++]=temp3[ct2-10];
            display[ct3++]=temp3[ct2-9];
            display[ct3++]=temp3[ct2-8];
            display[ct3++]=temp3[ct2-7];
            display[ct3++]=temp3[ct2-6];
            display[ct3++]=temp3[ct2-5];
            display[ct3++]=temp3[ct2-4];
            display[ct3++]=temp3[ct2-3];
            display[ct3++]=temp3[ct2-2];
            display[ct3++]=temp3[ct2-1];
            display[ct3++]=temp3[ct2];
            ct2-=13;
}   }   }


//Splits the deck in half and alternates them i.e. (0,26,1,27,...,25,51).
void bridge (void)
{   for (ct1=0;ct1<52;ct1++)
    {   temp1[ct1]=value[ct1];
        temp2[ct1]=suit[ct1];
        temp3[ct1]=display[ct1];
    }
    ct2=0;
    for(ct1=0;ct1<26;ct1++)
    {   value[ct2++]=temp1[ct1];
        value[ct2++]=temp1[ct1+26];
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


//Displays order of cards.
 void displayOrder(void)
 {  ct2=0;
    //Displays two lines of five groups of 4.
    for(ct1=0; ct1<2; ct1++)
    {   for(ct4=0; ct4<5; ct4++)
        {    for(ct3=0; ct3<4; ct3++)
             printf("%2i ", display[ct2++]);
             printf(", ");
        }
        printf("\n");
    }
    //Displays the remaining 12 cards in groups of 4.
    for(ct4=0; ct4<3; ct4++)
    {   for(ct3=0; ct3<4; ct3++)
        printf("%2i ", display[ct2++]);
        printf(", ");
    }

    printf("\n\n");
 }


//Displays cards in increments of 5.
void displayHand(void)
{   ct3=1;

    for(ct2=0; ct2<5; ct2++)
        {
            cardDisplay(value[ct4], suit[ct4]);
            ct4++;
        }
    printf("\n");
}


// Displays card name based on values contained in the numerical array.
void cardDisplay(int v, int s)
{   char suitChar[4][9] = {"Clubs", "Diamonds", "Hearts", "Spades"};
    char valueChar[13][6] ={"Two", "Three", "Four", "Five", "Six", "Seven",
    "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};

    printf("%i. %s of %s\n", ct3++, valueChar[v], suitChar[s]);
}

