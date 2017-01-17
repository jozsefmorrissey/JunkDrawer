// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:    Trig Calculator
// Desc:    This program takes any two known values of a right triangle as
//          inputs and displays the size of all 3 sides and the degree of the 
//          non 90 degree angles. It also rejects 0 and - values for inputs. 

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#define PI 3.14159
float sub1();
int main(void)
{   
   int  t, a; 
       float a1, a2, h, l1, l2, ad;
       while (t>0)
       {  printf("Enter the number that matches your known information...\n1"); 
          printf(" Hypotenuse and leg\n2 Both legs\n3 Hypot and angle\n4 Leg"); 
          printf(" and adjecent angle\n5 leg and opposite angle\n");
          //displays a list the user identifies the line that corrisponds 
          //whith thier information
          
          scanf("%i", &t);
       
             if(t==1)                //depending on which selection the user
             goto opt1;              //chooses these if statements guide the
                 if(t==2)            //the program to the correct calculations
                 goto opt2;          //for the spacific input.
                    if(t==3)
                    goto opt3;
                       if(t==4)
                       goto opt4;
                          if(t==5)
                          goto opt5;
             
             opt1:{printf("Enter hypotenuse  then leg: ");   
                   scanf("%f %f", &h, &l1);    //The opt locations are the mini
                   if(h>0 && l1>0)             //programs desiged for the input 
                   {  l2=sqrt(h*h-l1*l1);      //given in the corresponding
                      goto result;              //numerical selection from the
                   }     else                  //list.
                         goto fail;} 
                            
             opt2:{printf("Enter legs: ");
                   scanf("%f %f", &l1, &l2);
                   if(l1>0 && l2>0)
                   {  h=sqrt(l1*l1+l2*l2);
                      goto result;
                   }     else
                         goto fail;}
                      
             opt3:{printf("Is your angle in... \n1 Degrees \n2 Radians\n");
                   scanf("%i", &a);
                   if(a==1)
                   {  printf("Enter hypotenuse then angle in degrees: ");
                      scanf("%f %f", &h, &ad);
                       { if (0<ad && ad<90)
                         {a1=(ad*PI)/180;
                          goto calc3;}
                          
                          else
                          printf("Come on man, give me a realistic angle\n");
                          goto end;
                       }}                      
             {     if(a==2)
                      printf("Enter hypotenuse then angle in radians: ");
                      scanf("%f %f", &h, &a1);
                         { if (a1>0 && a1<1.570796327)
                         
                   calc3:{ if(h>0 && a1>0)
                      {    l1=h*sin(a1);
                           l2=h*cos(a1);
                           goto result;
                      }      else
                             goto fail;}
                            
                          else
                          printf("Come on man, give me a realistic angle\n");
                          goto end;
                       } 
                
             opt4: printf("Is your angle in... \n1 Degrees \n2 Radians\n");
                   scanf("%i", &a);
             {     if(a==1)
                   {  printf("Enter leg then adjacent angle in degrees: ");
                      scanf("%f %f", &l1, &ad);
                      {  if (0<ad && ad<90)
                       { a1=(ad*PI)/180;
                         goto calc4;
                       }   
                      else
                          printf("Come on man, give me a realistic angle\n");
                          goto end;
             }        }        
                   if(a==2) 
                      printf("Enter leg then adjecent angle in radians: ");
                      scanf("%f %f", &l1, &a1);
                      { if (a1>0 && a1<1.570796327)
                   
                           calc4:{ if(l1>0 && a1>0)
                       {           l2=l1*tan(a1);
                                   h=l1/cos(a1);
                                   goto result;
                       }              else
                                      goto fail;
                                 }
                             else
                          printf("Come on man, give me a realistic angle\n");
                          goto end;
                       }}    
                   
opt5: printf("Is your angle in... \n1 Degrees \n2 Radians\n");
      scanf("%i", &a);
      
    { if(a==1)
        { printf("Enter leg then opposite angle in degrees: ");
          scanf("%f %f", &l1, &ad);
        }
         { if (0<ad && ad<90)
            { a1=(ad*PI)/180;
              goto calc5;
            }
          else
             printf("Come on man, give me a realistic angle\n");
             goto end;
         }            
      if(a==2) 
        { printf("Enter leg then opposite angle in radians: ");
          scanf("%f %f", &l1, &a1);
        }            
          { if (a1>0 && a1<1.570796327)
            calc5:{ if(l1>0 && a1>0)
                     { l2=l1/tan(a1);
                       h=l1/sin(a1);
                       goto result;
                     }                  
                     else
                       goto fail;           
                  }                 
            else
               printf("Come on man, give me a realistic angle\n");
               goto end;
          }          
      else
          printf("Please make valid selections in the future");
    }    
             result: printf("Hypotenuse : %f\n Leg length %f has the ", h, l1); 
             printf("adjacent angle %f rads or %f degrees\n Leg length %f has ", atan(l2/l1), (atan(l2/l1)*180)/PI, l2);
             printf("the adjacent angle %f rads or %f degrees\n\n", atan(l1/l2), (atan(l1/l2)*180)/PI);
             goto end; //Displays final values for variables independent of the 
                       //route taken in the program.
                       
             fail: printf("Please enter positive nonzero values. I am "); 
                   printf("taking care of the magnitude, \ndirection is on you.");
                   printf("\n\n");
                   //Displayed whenever a 0 or negitive is entered.
             
             end:; //Used to skip fail statement.
              
             system("PAUSE");
                   
       }     
    system("PAUSE");
    
    return 0;
}   // end main            
