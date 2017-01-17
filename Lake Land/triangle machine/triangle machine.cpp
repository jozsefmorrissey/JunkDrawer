// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:    
// Desc:    

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

float sub1 (float x, float y);
float sub2 (float x, float y);
float sub3 (float x, float y);
float sub4 (float x, float y);
float sub5 (float x, float y);
float sub6 (float x, float y);
float sub7 (float x, float y);
float sub8 (float x, float y);
float sub9 (float x, float y);
float sub10 (float x, float y);
int main(void)
{   
    int  t;
    float a1, a2, h, l1, l2;
    while h>0
    {  printf("Enter the number that matches your known information...\n1. Hypotenus and leg\n2. 2 legs\n3. Hypot and angle\n4. Leg and adjecent angle\n5. leg and opposite angle\n");
       scanf("%i", &t);
       
      {   if(t==1)
       {  printf("Enter hypotenus then leg: ");
          scanf("%f %f", &h, &l1);
          l2=sub1(h,l1);
       
       }     else if (t==2)
         {   printf("Enter legs: ");
             scanf("%f %f", &l1, &l2);
             h=sub2(l1, l2);
          
         }      else if (t==3)
             {  printf("Enter Hypot then angle in radians: ");
                scanf("%f %f", &h, &a1);
                l1=sub3(h, a1);
                l2=sub4(h, a1);
             
             }     else if (t==4)
                {  printf("Enter leg then adjecent angle in radians: ");
                   scanf("%f %f", &l1, &a1);
                   l2=sub5(l1, a1);
                   h=sub6(l1, a1);
                
                }     else if (t==5)
                   {  printf("Enter leg then opposite angle in radians: ");
                      scanf("%f %f", &l1, &a1);
                      l2=sub7(l1, a1);
                      h=sub8(l1, a1);
                   }  
      }
                       
    printf("hypot: %f\n legs: %f %f\n angles: %f %f\n", h, l1, l2, atan(l1/l2), atan(l2/l1));

    system("PAUSE");
    }
    return 0;
}   // end main        

float sub1 (float x, float y){
return sqrt(x*x-y*y);} 

float sub2 (float x, float y){
return sqrt(x*x+y*y);} 

float sub3 (float x, float y){
return x*sin(y);} 

float sub4 (float x, float y){
return x*cos(y);}

float sub5 (float x, float y){
return x*tan(y);} 

float sub6 (float x, float y){
return x/cos(y);}

float sub7 (float x, float y){
return x/tan(y);} 

float sub8 (float x, float y){
return x/sin(y);}
   
