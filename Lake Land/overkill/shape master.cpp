// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:    PROG05 
// Desc:    This program takes inputs for a multitude of shapes and desplays
//          area and perimeter for two demetional shapes. Surface area and 
//          volume for three dementional shapes.

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#define PI 3.14159

void shapeMaster();          //Selection functions.       
void twoD ();
void threeD ();

void boring ();              //Assigned program main.

void rectangle ();           //Input functions for various shapes.
void trapizoid ();
void circle ();
void ellipse ();
void triangle ();
void cube ();
void rectangularPrism ();
void cylinder ();
void pyramid ();
void cone ();
void sphere ();
void ellipsoid ();

//Area functions
double rectangleArea (double l, double w);
double trapizoidArea (double t, double b, double h);
double circleArea (double r);
double ellipseArea (double x, double y);
double triangleArea (double b, double h);

//Volume functions
double cubeVolume (double s);
double rectangularPrismVolume (double l, double w, double h);
double cylinderVolume (double h, double r);
double pyramidVolume (double l, double d, double h);
double coneVolume (double r, double h);
double sphereVolume (double r);
double ellipsoidVolume (double x, double y, double z);

//Perimeter functions
double rectanglePerimeter (double l, double w);
double trapizoidPerimeter (double t, double b, double h);
double circlePerimeter (double r);
double ellipsePerimeter (double x, double y);
double trianglePerimeter (double b, double h);

//Surface area functions
double cubeSurfaceArea (double s);         
double rectangularPrismSurfaceArea (double l, double w, double h);
double cylinderSurfaceArea (double h, double r);
double pyramidSurfaceArea (double l, double d, double h);
double coneSurfaceArea (double r, double h);
double sphereSurfaceArea (double r);
double ellipsoidSurfaceArea (double x, double y, double z);

//Variable test functions
double inputTest2 (double x, double y);
double inputTest3 (double x, double y, double z);

char invalid[] = {"Please make a valid selection in the future\n\n"};
double area, volume, surfaceArea, perimeter;
int fail=0;

//Allowes user to choose between assigned and shape master.
int main(void)
{
   char rabbitHole;
    
      printf("Would you like to use the assigned program or Shape Master ");
      printf("3000 (A/S): ");
      scanf("%c", &rabbitHole);
    
      if (rabbitHole=='A' || rabbitHole=='a')
         boring();
       
      else if (rabbitHole=='S' || rabbitHole=='s')
         shapeMaster();
            
      else
          printf("%s", invalid);
    
    return 0;
}   // end main  


//Assigned program inputs.
void boring ()
{
   double length, width, hieght, terminate=0;
  
      do 
      {
         printf("Enter length, width, and hieght(all negatives to end): ");
         scanf("%lf %lf %lf", &length, &width, &hieght);
      
            if(length<=0 && width<=0 && hieght<=0)
               terminate=1;
            
            else
            {  inputTest3 (length, width, hieght);
                
                volume=length*width*hieght;

               if(volume>0 && fail==0)
                  printf("The volume of your shape is %.2f\n\n", volume);   
            }
   system("PAUSE");            
      }
      while(terminate!=1);
}     
    

//Allowes user to choose 2 or 3 demetional objects and prints the final results 
//of their selection.     
void shapeMaster()
{
   int demintions, terminate=0;

      while (terminate!=1)
      {  
         area=surfaceArea=perimeter=volume=0;   
             
         printf("Is your shape 2 or 3 demintional(0 to end): ");
         scanf("%i", &demintions); 
      
            if(demintions==2) 
               twoD();
         
               else if (demintions==3)
                  threeD();
               
                  else if (demintions==0)
                     terminate=1;   
         
                     else
                        printf("%s", invalid);
               
        if(area>0 && fail==0)
           printf("The area of your shape is %.2f\n", area); 
           
        else if(volume>0 && fail==0)
           printf("The volume of your shape is %.2f\n", volume);
           
        if(surfaceArea>0 && fail==0)
           printf("The surface area of your shape is %.2f\n\n", surfaceArea); 
           
        else if(perimeter>0 && fail==0)
           printf("The perimeter of your shape is %.2f\n\n", perimeter); 
           
   system("PAUSE");              
}     }

//Allowes user to choose several different 2 demetional objects.
void twoD()
{   
   int type;
   
      printf("Is your shape a:\n1  Square/Rectangle/Parrallelagram\n2  Trapiz");
      printf("iod\n3  Circle\n4  Ellipse\n5  Triangle\n");
      scanf("%i", &type);
      
         switch(type)
         {  case 1:
               rectangle ();
               break;

            case 2:
               trapizoid ();  
               break;
                 
            case 3:
               circle ();
               break;
                 
            case 4:
               ellipse ();
               break;
                 
            case 5:
               triangle ();
               break;
               
            default:
               printf("%s", invalid);    
}        }


//Allowes user to choose several different 3 demetional objects.
void threeD()
{   
   int type;
   
      printf("Is your shape a:\n1  Cube\n2  Rectangular prism\n3  Cylinder");
      printf("\n4  Pyramid\n5  Cone\n6  Sphere\n7  Ellipsiod\n");
      scanf("%i", &type);
      
         switch(type)
         {  case 1:
               cube ();
               break;
                 
            case 2:
               rectangularPrism ();  
               break;
                 
            case 3:
               cylinder ();
               break;
                 
            case 4:
               pyramid ();  
               break;
                 
            case 5:
               cone ();  
               break;
                 
            case 6:
               sphere ();
               break;
                 
            case 7:
               ellipsoid ();     
               break;
               
            default:
               printf("%s", invalid);     
}        }


void rectangle ()
{
   double base, hieght;
      
      printf("Enter hieght and width: ");
      scanf("%lf %lf", &base, &hieght); 
      
         inputTest2 (base, hieght);
         
         area=rectangleArea(base, hieght); 
         perimeter=rectanglePerimeter(base, hieght);      
}      


void trapizoid ()
{
   double top, bottom, hieght;
   
      printf("Enter the lenght of the top and bottom, then the hieght: ");
      scanf("%lf %lf %lf", &top, &bottom, &hieght);
      printf("The perimeter describes a trapizoid semetric about the y axis");
      
         inputTest3 (top, bottom, hieght);
      
         area=trapizoidArea (top, bottom, hieght);
         perimeter=trapizoidPerimeter (top, bottom, hieght);
}

      
void circle ()
{
   double radius;
   
      printf("Enter radius: ");
      scanf("%lf", &radius);
      
         if(radius<=0)
            printf("Please enter positive inputs in the future");
      
         area=circleArea (radius);
         perimeter=circlePerimeter (radius);
}


void ellipse ()
{
   double radius1, radius2;
   
      printf("Enter the two radii: ");
      scanf("%lf %lf", &radius1, &radius2);
      
         inputTest2 (radius1, radius2);
      
         area=ellipseArea (radius1, radius2);
         perimeter=ellipsePerimeter (radius1, radius2);   
}


void triangle ()
{
   double base, hieght;
   
      printf("Enter base and hieght: ");
      scanf("%lf %lf", &base, &hieght);
      
         inputTest2 (base, hieght);
      
         area=triangleArea (base, hieght);
}


void cube ()
{
   double side;
   
      printf("Enter the lenght of one of a side: ");
      scanf("%lf", &side);
      
      if(side<0)
         printf("Please enter positive inputs in the future");
      
         volume=cubeVolume (side);
         surfaceArea=cubeSurfaceArea (side);
}


void rectangularPrism ()
{
   double length, depth, hieght;
   
      printf("Enter length, width, and hieght: ");
      scanf("%lf %lf %lf", &length, &depth, &hieght);
      
         inputTest3 (length, depth, hieght);
      
         volume=rectangularPrismVolume (length, depth, hieght);
         surfaceArea=rectangularPrismSurfaceArea (length, depth, hieght);
}


void cylinder ()
{
   double radius, hieght;
   
      printf("Enter hieght then radius: ");
      scanf("%lf %lf", &hieght, &radius);
      
         inputTest2 (radius, hieght);
      
         volume=cylinderVolume (hieght, radius);
         surfaceArea=cylinderSurfaceArea (hieght, radius);
}


void pyramid ()
{
   double side, length, hieght;
   
      printf("Enter number of sides then thier base lenght then hieght: ");
      scanf("%lf %lf %lf", &side, &length, &hieght);
      
         inputTest3 (side, length, hieght);
      
         volume=pyramidVolume (side, length, hieght);
         surfaceArea=pyramidSurfaceArea (side, length, hieght);
}

void cone ()
{
   double radius, hieght;
   
      printf("Enter radius and hieght: ");
      scanf("%lf %lf", &radius, &hieght);
      
         inputTest2 (radius, hieght);
      
         volume=coneVolume (hieght, radius);
         surfaceArea=coneSurfaceArea (radius, hieght);
}


void sphere ()
{
   double radius;
   
      printf("Enter radius: ");
      scanf("%lf", &radius);
      
         if (radius<0)
            printf("Please enter positive inputs in the future");
      
            volume=sphereVolume (radius);
            surfaceArea=sphereSurfaceArea (radius);
}


void ellipsoid ()
{
   double radius1, radius2, radius3;
   
      printf("Enter the three radii: ");
      scanf("%lf %lf %lf", &radius1, &radius2, &radius3);
      printf("Surface area is an approximation\n");
      
         inputTest3 (radius1, radius2, radius3);
         
         
         volume=ellipsoidVolume (radius1, radius2, radius3);
         surfaceArea=ellipsoidSurfaceArea (radius1, radius2, radius3);
}

//Tests and isolates two variables for negative numbers.
double inputTest2 (double x, double y)
{ 
   double temp;
      if(x<y)
      {  temp=x;
         x=y;
         y=temp;
      }
      
      if(y<0)
      {  fail=1;
       
         if(x<0)
         {  printf("Error: All inputs must be positive.\n");
            printf("%f %f are both invalid inputs\n\n", y, x); 
         }
         else
         {  printf("Error: All inputs must be positive.\n");
            printf("%f is an invalid input\n\n", y);   
      }  }
}

//Tests and isolates three variables for negative numbers.
double inputTest3 (double x, double y, double z)
{  
   double temp;
    
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
     
      if(z<=0)
     
      { if(y<=0)
        {  fail=1;
            
           if(x<=0)
           {  printf("Error: All inputs must be positive.\n");
              printf("%f %f %f are all invalid inputs\n\n", x, y, z);
           }//displays all three variables if all three are negative or zero
             
           else
           {  printf("Error: All inputs must be positive.\n");
              printf("%f %f are both invalid inputs\n\n", y, z); 
        }  }  //displayes two variables if two are negative or zero
              
        else   
        {  printf("Error: All inputs must be positive.\n");
           printf("%f is an invalid input\n\n", z);
     }  } 
}  

double rectangleArea (double l, double w)
{ return l*w;
}

double trapizoidArea (double b, double t, double h)
{      double temp;

       if(t>b)
       {  temp=t;
          t=b;
          b=temp;
       }
       
       return .5*(t+b)*h;
}

double circleArea (double r)
{  return PI*r*r;
}

double ellipseArea (double x, double y)
{  return PI*x*y;
}

double triangleArea (double b, double h)
{  return .5*b*h;       
}

double cubeVolume (double s)
{  return s*s*s;
}

double rectangularPrismVolume (double l, double w, double h)
{  return l*w*h;
}

double cylinderVolume (double h, double r)
{  return PI*h*r*r;
}

double pyramidVolume (double s, double l, double h)
{  return (h*s*l*l*(1/tan(PI/s))/12);
}

double coneVolume (double h, double r)
{  return (PI*h*r*r)/3;
}

double sphereVolume (double r)
{  return (4*PI*r*r*r)/3;
}

double ellipsoidVolume (double x, double y, double z)
{  return (4*PI*x*y*z)/3;
}

double rectanglePerimeter (double l, double w)
{  return 2*(l+w);
}

double trapizoidPerimeter (double t, double b, double h)
{      double temp;
       
       if(t>b)
       {  temp=t;
          t=b;
          b=temp;
       }
       return 2*sqrt(h*h+((b-t)/2)*((b-t/2)))+t+b;
}

double circlePerimeter (double r)
{  return 2*PI*r;
}

double ellipsePerimeter (double x, double y)
{  return 2*PI*sqrt(.5*(x*x+y*y));
}

double trianglePerimeter (double b, double h)
{  return 0;
}

double cubeSurfaceArea (double s)
{  return 6*s*s;
}

double rectangularPrismSurfaceArea (double l, double w, double h)
{  return 2*(l*w+w*h+h*l);
}

double cylinderSurfaceArea (double h, double r)
{  return PI*r*r+2*h*PI*r;
}

double pyramidSurfaceArea (double s, double l, double h)
{  return .25*s*l*(sqrt(4*h*h+l*l*(1/tan(PI/s))*(1/tan(PI/s)))+l*(1/tan(PI/s)));
}

double coneSurfaceArea (double r, double h)
{  return PI*r*sqrt(r*r+h*h);
}

double sphereSurfaceArea (double r)
{  return 4*PI*r*r;
}

double ellipsoidSurfaceArea (double x, double y, double z)
{  double p;
   p=log(3)/log(2);
   return 2*PI*pow((pow(x*x,p)+pow(y*y,p)+pow(z*z,p)),1/p);
}





  
