// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:    03
// Desc:    This program takes data on temperature, rate of change, and number
//          of iterations in any of the three major scales.  Displays a table 
//          with the temperature of each iteration in Celsius kelvin and 
//          Fahrenheit .

#include <stdio.h>
#include <stdlib.h>

void calcK();
void calcC();
void calcF();

double k,c,f,i,rate;           
int type, row, counter; 


char cout[] = {"Your temperature in Celsius is "};
char kout[] = {"Your temperature in Kelvin is "};
char fout[] = {"Your temperature in Fahrenheit  is "};
char invalid[] = {"Please make a valid selection in the future\n\n"};
char reqResponse[] = {"Do you want your temperature in:"};
char congrats1[] = {"Congratulations you have apparently recorded a temperatu"};
char congrats2[] = {"re lower than the theoretical absolute zero.\n"};
char tooCold[] = {"Your temperature passed the point of absolute zero\n"};
//generic strings of characters used often in the program

int main(void)
{  int unit, loup;
    loup=0;

    
   while(unit!=0) //Allows user to end program.
   {  
    printf("What units is your current temperature:\n1 Kelvin\n2 Celsius\n3 Fahrenheit  \n0 end\n");
    scanf("%i", &unit);
    if(unit==0)
    {  printf("Goodbye\n");
       system("PAUSE");
    }
    else
{    printf("Enter value: ");

       k=0;   //Resets values to prevent premature failures.
       c=0;   
       f=0;
    
       if(unit==1)                                  //If statements check for 
       scanf("%lf", &k);                            //initial values less than 
       if(k<0)                                      //zero prior to moving 
       {  printf("%s%s", congrats1, congrats2);     //forward in the program.
          system("PAUSE");
          break;
       }
          
       if(unit==2)
       scanf("%lf", &c);
       if(c<-273.2)
       {  printf("%s%s", congrats1, congrats2);
          system("PAUSE");
          break;
       }
          
       if(unit==3)
       scanf("%lf", &f);
       if(f<-459.7)
       {  printf("%s%s", congrats1, congrats2);
          system("PAUSE");
          break;
       }
             
    printf("Enter the rate of change: ");
    scanf("%lf", &rate);
    printf("Enter number of intervals: ");
    scanf("%i", &row);
    printf("%20s%22s%22s\n", "Fahrenheit", "Celsius", "Kelvin");
    
    
                       //These if statements serves as a directory to the 
    if(unit==1)        //functions required based on user input.
       calcK();
    
    else if(unit==2)
       calcC();
    
    else if(unit==3)
       calcF();
    
    else 
         printf("%s", invalid);
    
    system("PAUSE");
}   } 
    return 0;
}   // end main     

void calcK()  //Calculations for Kelvin inputs.
{
for(counter = 0; counter < row; counter++) 
   {  
      if(k<0)                   //Tests every time through the for loop for 
    { printf("%s", tooCold);    //values above absolute zero.
      break;      
    }           
      c=k-273.15;
      f=1.8*(c)+32;
      
      printf("%20.2f%22.2f%22.2f\n", f, c, k); 
  
      k=k+rate;
}  } 
    
     
void calcC()  //Calculations for Celsius inputs.
{
for(counter = 0; counter < row; counter++)
   {
      if(c<-273.15)
    { printf("%s", tooCold);
      break;      
    }      
      k=c+273.15;
      f=1.8*(c)+32;
      
      printf("%20.2f%22.2f%22.2f\n", f, c, k);
         
      c=c+rate;
}  } 
    
    
void calcF()  //Calculations for Fahrenheit inputs.
{ 
for(counter = 0; counter < row; counter++)
   {  if(f<-459.7)
    { printf("%s", tooCold);
      break;
    }        
      c=(f-32)/1.8;
      k=c+273.15;
      
      printf("%20.2f%22.2f%22.2f\n", f, c, k);

      f=f+rate;
}  }





  
