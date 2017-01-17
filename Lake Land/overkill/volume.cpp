// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:    
// Desc:    

#include <stdio.h>
#include <stdlib.h>


double volumeCalc(double h, double w, double l);
double organize (double x, double y, double z);
double isolate (double x, double y, double z);
int main(void)
{  
   double hieght, width, end, length, volume;
   
   while(end!=1)
   {
       printf("Enter the hieght then width then length\n(Enter 3 negative val");
       printf("ues to end): ");
       scanf("%lf %lf %lf", &hieght, &width, &length);
       
          if(hieght<0 && width<0 && length<0)
          {  end=1;
             organize(hieght, width, length);
          }
          
          else if(hieght>0 && width>0 && length>0)  
          {  volume=volumeCalc(hieght, width, length);
             printf("The volume of your quadraladeral is: %.2f\n\n", volume);
          }   
             
          else
          {  printf("Please enter positive values in the future");
             organize(hieght, width, length);
          }   
             
             system("PAUSE");  
   }      

    
    
    return 0;
}   // end main  


double organize(double x, double y, double z)
{   double temp;
    
    if(x<y)
    {  temp=x;
       x=y;
       y=temp;
    }
    
    if(x<z)
    {  temp=x;
       x=z;
       z=temp;
    }
    
    if(z>y)
    {  temp=z;
       z=y;
       y=temp;
    }
isolate(x, y, z);
}    

    
double isolate (double x, double y, double z)  
    {   if(z<=0)
  
       { if(y<=0)
            
          { if(x<=0)
             { printf("Error: All inputs must be positive.\n");
               printf("%f %f %f are all invalid inputs\n\n", x, y, z);
             }//displays all three variables if all three are negative or zero
             
            else
            {  printf("Error: All inputs must be positive.\n");
               printf("%f %f are both invalid inputs\n\n", y, z); 
          } }  //displayes two variables if two are negative or zero
             
         else   
          { printf("Error: All inputs must be positive.\n");
            printf("%f is an invalid input\n\n", z);
    }  }  

return 0;
}


double volumeCalc(double h, double w, double l)
{       return (h*w*l);
}


  
