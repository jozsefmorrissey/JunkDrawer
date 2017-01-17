// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:    
// Desc:    

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#define e 2.71828183

double downPayment();
double savemorecalc(double d, double adj);
double savings(double down);
double savingsAdj(double down, double adj);
double continuously();

double p, r, d, final;
int m;


int main(void)
{  double rate, duration, principle, payment, tenDown, fifteenDown, twentyFiveDown;
   char interval;
   
   printf("Enter the principle amount: ");
   scanf("%lf", &p);
    
   printf("Enter interest rate: ");
   scanf("%lf", &r);
   r*=.01;
   
   printf("How often is the interest compounded yearly monthly weekly daylie or continuous: ");
   getchar();
   interval=getchar();
   
   if(interval=='D' || interval=='d')
      m=365;
   else if(interval=='W' || interval=='w')
      m=55;
   else if(interval=='M' || interval=='m')
      m=12;
   else if(interval=='Y' || interval=='y')
      m=1;
   else
      printf("invalid");

   printf("Enter the number of months the loan is for:");
   scanf("%lf", &d);
   d/=12;

   if(interval=='C' || interval=='c')
      final=continuously();
   else
      final=p*(pow(1+(r/m), (m*d)));   
   
   payment=final/(d*12);

   printf("Your loan totals out to be %.2f\nprinciple %.2f\nInterest %.2f\nMonthly payment %.2f\n", final, p, final-p, payment);
   system("PAUSE");

   downPayment();


   system("PAUSE");

    return 0;
}   // end main    


double downPayment()
{ double tenDown, fifteenDown, twentyFiveDown, down, savemore10, savemore15, adj1=.75, adj2=.85, adj3=.9;
  double savemore25, savemore25s, saving, saving10, saving15, saving25, saving25s, percentDown;
  char downYN;
 
  printf("\nAre you curently contributing a down payment?(Y/N) ");
  getchar();
  downYN=getchar();
  
  if (downYN=='Y' || 'y'==downYN)
  {  printf("How much is your down payment value: ");
     scanf("%lf", &down);
     percentDown=down/(down+p);
     saving=savings(down)-(final);
     printf("\nYou are already saving %.2f on interest.\n\n", saving);
  }   
  if (downYN=='N' || 'n'==downYN)
     percentDown=0;
     
  if (percentDown>0.25)
  {  saving=savemorecalc(down, 1);
     printf("That is a suffecient down payment\n");
  }   
  if (percentDown<0.25)
  {  savemore25=savemorecalc(down, adj1);
     saving25=savings(down)-savingsAdj(down, adj1);
     printf("If you saved 25%%: %.2f\nYou would save %.2f in interest\n\n", savemore25, saving25);
  }  
  if (percentDown<0.15)
  {  savemore15=savemorecalc(down, adj2); 
     saving15=savings(down)-savingsAdj(down, adj2);
     printf("If you saved 15%%: %.2f\nYou would save %.2f in interest\n\n", savemore15, saving15);  
  }
  if (percentDown<=0.1) 
  {  savemore10=savemorecalc(down, adj3);
     saving10=savings(down)-savingsAdj(down, adj3);
     printf("If you saved 10%%: %.2f\nYou would save %.2f in interest\n", savemore10, saving10); 
  }
  return 0;
}

double savemorecalc(double d, double adj)
{  return (p+d)*(1-adj);
}
double savings(double down)
{  return (p+down)*(pow(1+(r/m), (m*d)));
} 
double savingsAdj(double down, double adj)
{  return ((p+down)*adj)*(pow(1+(r/m), (m*d)));
}
double continuously()
{  return p*pow(e,(r*d));
}
