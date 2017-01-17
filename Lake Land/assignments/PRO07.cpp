// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:    PROG07
// Desc:    This program is designed to recieve two times in hours, minutes and 
//          seconds and return the time elapsed in seconds.

#include <stdio.h>
#include <stdlib.h>

int numsecs(int h, int m, int s);

int main(void)
{  int hourI, minuteI, secondI, secondInitial, hourF, minuteF, secondF;
   int secondFinal, secondTotal, testI, testF;
   
   printf("Enter starting time (hour, minute, second):  ");
   scanf("%i %i %i", &hourI, &minuteI, &secondI);
   
   if(hourI>=0 && minuteI>=0 && secondI>=0)  //Testing for positive values at 
      testI=1;                               //at each input shortens and 
                                             //clarifies if statments.
   secondInitial=numsecs(hourI, minuteI, secondI);
   
   printf("Enter   ending time (hour, minute, second):  ");
   scanf("%i %i %i", &hourF, &minuteF, &secondF);
   
   if(hourF>=0 && minuteF>=0 && secondF>=0)
      testF=1;

   secondFinal=numsecs(hourF, minuteF, secondF);
   if(testI==1 && testF==1)
   {  if(secondFinal>secondInitial)
      secondTotal=secondFinal-secondInitial;
   
      else if(secondFinal<secondInitial)  //Adds 12 hours to correct for a 
      secondTotal=12*60*60+secondFinal-secondInitial;  //larger initial time.
   
      printf("From %02i:%02i:%02i to %02i:", hourI, minuteI, secondI, hourF);
      printf("%02i:%02i there are %i seconds",  minuteF, secondF, secondTotal);
      printf("\n");
   }
   else  //Prints when negative time values are entered.
   {  printf("I see now why you need this program to find the number of secon");
      printf("ds between two \ntimes. You should check your inputs and try ag");
      printf("ain.\n");
   }
system("PAUSE");

    return 0;
}   // end main   

int numsecs(int h, int m, int s)  //Returns number of seconds.
{  return (h*60+m)*60+s; 
}




  
