// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:    PROG02   
// Desc:    This program is designed to receive 3 integers as an input and 
//          display whether one of those integers are negative or 0 and whether 
//          they could describe the sides of a triangle.

#include <stdio.h>
#include <stdlib.h>
#define PI 3.14159

int main(void)
{   
    int  int1, int2, int3, temp, more;
    
    begining:
    
    printf("Input 3 positive integers:  ");
    scanf("%i %i %i", &int1, &int2, &int3);
    
    if(int1<int2)
    {          temp=int2;    //these three if statements swap the variables to
               int2=int1;    //make int1-3 highest to lowest to guarantee the
               int1=temp;    //the location of the hypotenuse and make the 
    }                        //it easier to isolate the negative and zero values  
    if (int1<int3)
    {           temp=int3;
                int3=int1;
                int1=temp;
    }           
    if (int2<int3)
    {           temp=int3;
                int3=int2;
                int2=temp;
    }
  {   if(int3<=0)
  
       { if(int2<=0)
            
          { if(int1<=0)
             { printf("Error: All inputs must be positive.\n");
               printf("%i %i %i are all invalid inputs\n\n", int1, int2, int3);
             }//displays all three variables if all three are negative or zero
             
            else
            {  printf("Error: All inputs must be positive.\n");
               printf("%i %i are both invalid inputs\n\n", int2, int3); 
          } }  //displayes two variables if two are negative or zero
             
         else   
          { printf("Error: All inputs must be positive.\n");
            printf("%i is an invalid input\n\n", int3);
       }  }  //displayes one variables if one are negative or zero
       
      else if (int1*int1==int2*int2+int3*int3)
             {  printf("%i %i and %i could be sides of ", int1, int2, int3);
                printf("a right triangle\n");
             } //tests the inputs against the pathagorean therom      
      else
       { printf("%i %i and %i could not be sides of a ", int1, int2, int3);
         printf("right triangle\n\n");
  }    }
    system("PAUSE");
    
    return 0;
}   // end main            
