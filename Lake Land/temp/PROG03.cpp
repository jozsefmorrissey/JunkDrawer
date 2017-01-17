// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:    PROG03
// Desc:    This program is designed to receive a temperature in Kelvin, Celsius
//          or Fahrenheit and convert it into a unit that the user specifies. 

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

void calcK();
void calcC();
void calcF();
    
int unit, counter;


char cout[] = {"Your temperature in Celsius is "};
char kout[] = {"Your temperature in Kelvin is "};
char fout[] = {"Your temperature in Fahrenheit  is "};
char invalid[] = {"Please make a valid selection in the future\n\n"};
char reqResponse[] = {"Do you want your temperature in:"};
char congrats1[] = {"Congratulations you have apparently recorded a temperatu"};
char congrats2[] = {"re lower than the theoretical absolute zero.\n"};
//generic strings of characters used often in the program

int main(void)
{  counter=0;
    
   while(counter++<5)
   {  
    printf("What units is your current temperature:\n1 Kelvin\n2 Celsius\n3 Fahrenheit  \n0 end\n");
    
    scanf("%i", &unit);
                       //Main serves as a directory to the functions required 
    if(unit==1)        //based on user input.
       calcK();
    
    else if(unit==2)
       calcC();
    
    else if(unit==3)
       calcF();
    
    else if(unit==0)
    {  printf("Goodbye\n");
       counter=5;
    }
    else 
         printf("%s", invalid);
    
    system("PAUSE");
   } 
    return 0;
}   // end main 

    

void calcK()
    {float k,c,f;                 //Calculations and display for Kelvin input
     int type;       
            
      printf("Enter value: ");
      scanf("%f", &k);
      c=k-273.15;
      f=1.8*(c)+32;
      
    { if(k>0)
      {  printf("%s\n1 Celsius\n2 Fahrenheit  \n3 both\n", reqResponse);
         scanf("%i", &type);
         
            if (type==1)
               printf("%s %f\n\n", cout, c);
               
               else if(type==2)
                  printf("%s %f\n\n", fout, f);
               
                  else if(type==3)
                     printf("%s %f \n%s %f\n\n", cout, c, fout, f);
            
                     else
                        printf("%s\n", invalid);
      }
    
      else
         printf("%s%s", congrats1, congrats2);
    }}
     
     
     
    void calcC()
    {float k,c,f;                  //Calculations and display for Celsius input
     int type;       
            
      printf("Enter value: ");
      scanf("%f", &c);
      k=c+273.15;
      f=1.8*(c)+32;
      
      if(k>0)
       { printf("%s\n1 Kelvin\n2 Fahrenheit  \n3 both\n", reqResponse);
         scanf("%i", &type);
         
            if (type==1)
               printf("%s %f\n\n", kout, k);
               
               else if(type==2)
                  printf("%s %f\n\n", fout, f);
               
                  else if(type==3)
                     printf("%s %f \n%s %f\n\n", kout, k, fout, f);
            
                     else
                        printf("%s\n", invalid);
      }
      else
         printf("%s%s", congrats1, congrats2);
    }
    
    
    void calcF()
    {float k,c,f;               //Calculations and display for Fahrenheit  input
     int type;       
            
      printf("Enter value: ");
      scanf("%f", &f);
      c=(f-32)/1.8;
      k=c+273.15;
      
      if(k>0)
      {  printf("%s\n1 Kelvin\n2 Celsius\n3 both\n", reqResponse);
         scanf("%i", &type);
         
            if (type==1)
               printf("%s %f\n\n", kout, k);
               
               else if(type==2)
                  printf("%s %f\n\n", cout, c);
               
                  else if(type==3)
                     printf("%s %f \n%s %f\n\n", kout, k, cout, c);
            
                     else
                        printf("%s\n", invalid);
       }
      else
         printf("%s%s", congrats1, congrats2);
    }
