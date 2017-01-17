// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:    01
// Desc:    This program is designed to recieve an integer input for the radius of a circle and calculate the diameter, circumfrence, and area.
            

#include <stdio.h>
#include <stdlib.h>
#define PI 3.14159

int main(void)
{   
    int radius;
    
    printf("Enter the radius of the circle  ");
   	scanf("%i", &radius);
    
    printf("\nThe diameter is %i\n", 2*radius);
    printf("The circumfrence is %f\n", PI*2*radius);
    printf("The area is %f\n", PI*radius*radius);
    
    system("PAUSE");
    
    return 0;
}   // end main            
