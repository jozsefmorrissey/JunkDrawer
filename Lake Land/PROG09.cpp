// Name:	Jozsef Morrissey
// Date:	September 9, 2013
// Prog:    PROG09
// Desc:    This program prompts user for n of an nx(n+1) matrix. Calculates
//          determinate of each matrix created following the Cramers rule
//          process. Calculates x1-xn for the matrix.

#include <stdio.h>
#include <stdlib.h>

void input (void);
void theProcess(void);
void output (void);

void keyLocationsANDnumbers(void);
void assignValues (void);
void minorCreator(void);
void minorValues(void);
void twoBYtwoValues(void);

void termGrouping(void);
void calculateDeterminate(void);


float determinate[10];
int counter;
long int matrixGroup;
int matrixStartLocation[10000], row[10][10], column[10][10], columns[10][10];
int group[10000][10000], matrixEndLocation[100000], matrixQuantity[10000];
int climbLocation[100], matrix[10][300000][10][10], term[10][9000000], n;


int main(void)
{   int terminate=0;

    do
    {   counter=0;
        printf("Enter the number of equations (0 to end): ");
        scanf("%i", &n);

        if(n>0)
        {   input();
            output();
            system("PAUSE");
        }
    }while(n!=0);

    printf("Goodbye\n");

    system("PAUSE");

    return 0;
}   // end main


//This controls the process of finding the determinate of a matrix.
void theProcess(void)
{   keyLocationsANDnumbers();
    assignValues ();
    minorCreator();
    minorValues();
    twoBYtwoValues();
    termGrouping();
    calculateDeterminate();
}


//Calculates nessisary values for counter starting and stoping points.
void keyLocationsANDnumbers(void)
{   long int matrixMultiplier, counter1=1, counter2, counter3;

    //Determins the ending location of each group of minors.
    matrixEndLocation[n]=0;
    matrixEndLocation[n-1]=n;
    matrixMultiplier=matrixEndLocation[0]=n;
    counter2=n-2;

    for(counter3=n; counter3>2; counter3--)
    {   matrixMultiplier*=(n-counter1++);
        matrixEndLocation[0]+=matrixMultiplier;
        matrixEndLocation[counter2--]=matrixEndLocation[0];
    }
    //Determines the starting location of each group of minors.
    for(counter3=2; counter3<n; counter3++)
        matrixStartLocation[counter3]=matrixEndLocation[counter3+1]+1;

    //Determins values to increment variables during the multiplication process.
    matrixMultiplier=1;
    for(counter3=2; counter3<=n; counter3++)
    {   climbLocation[counter3]=matrixMultiplier;
        matrixMultiplier*=(counter3);
    }
    //Determimes the number of matricies in each tier.
    for(counter3=n+1; counter3>=2; counter3--)
    matrixQuantity[counter3-1]=matrixEndLocation[counter3-1]-matrixEndLocation[counter3];

    if (n==2)
    {  matrixQuantity[2]=1;
       matrixStartLocation[2]=0;
    }
    
}


//Recieves input form user. creates n+1 matricies and sends them through the
//process.
void input (void)
{   int x, y, z, w;

    printf("\nThis program uses Cramers Rule to solve a linear system.\nEnter");
    printf(" each of the %i linear equations as four integers seperated by", n);
    printf(" a space.\nFor example, x - 2y +3z = 4 should be entered as 1 -2 ");
    printf("3 4\n\n");

    //Isolates input into columns.
    for(x=1; x<=n; x++)
    {   printf("Enter equation %i: ", x);
        for(y=1; y<n+2; y++)
            scanf("%i", &columns[x][y]);
    }
    printf("\n");

    //Copies the matrix into each memory location.
    w=1;
    for(x=0; x<=n; x++)
    {   for(z=1; z<=n+1; z++)
            for(y=1; y<=n+1; y++)
            {   matrix[x][0][y][z]=columns[y][w];
                if(y%(n+1)==0)
                    w++;
                if(w%(n+2)==0)
                    w=1;
    }       }
    //Replaces each column of a matrix with the answer column.
    for(x=1; x<=n; x++)
            for(z=1; z<=n; z++)
                matrix[x][0][z][x]=matrix[x][0][z][n+1];

    for(matrixGroup=0; matrixGroup<=n; matrixGroup++)
        theProcess();
}


//Displays the answer vector.
void output (void)
{   int x, y;

    if(determinate[0]!=0)
    {   x=1;
        printf("System Has unique solution (%.1f", determinate[x++]/determinate[0]);
        for(y=2; y<=n; y++)
        printf(", %.1f", determinate[x++]/determinate[0]);
        printf(")\n\n");
    }
    else
        printf("System does not have a unique solution because determinate is 0.\n\n");
}


//Assigns the i and j values for each position of the matrix.
void assignValues (void)
{   int rowCount, columnCount, sizE;

    sizE=n;
    for (rowCount=1; rowCount<=sizE; rowCount++)
    {   for(columnCount=1; columnCount<=sizE; columnCount++)
        {   row[rowCount][columnCount]=rowCount;
            column[rowCount][columnCount]=columnCount;
}   }   }


//Creates all the minors from n-1 to the 2 matrix.
void minorCreator(void)
{   int originalMatrix=0, x, testedColumn, testedRow, rowTest, columnTest;
    int positionNnew, matrixSize;
    long int  counter, nextSet, nextSETlocation, matrixCount;

    matrixCount=counter=rowTest=1;
    nextSETlocation=nextSet=matrixSize=n;
    for(x=0; x<=matrixQuantity[2]*2; x++)
    {   for(columnTest=1;columnTest<=matrixSize;columnTest++)
        {   for(testedRow=1;testedRow<=matrixSize;testedRow++)
            {   positionNnew=1;
                for(testedColumn=1;testedColumn<=matrixSize;testedColumn++)
    //tests rows and columns for equality and does record them into minor.
                {   if (row[rowTest][columnTest]==row[testedRow][testedColumn] || column[rowTest][columnTest]==column[testedRow][testedColumn]);

                    else
                        matrix[matrixGroup][matrixCount][testedRow-1][positionNnew++]=matrix[matrixGroup][originalMatrix][testedRow][testedColumn];
            }   }
            if (matrixCount==nextSet)
            {   nextSETlocation*=(n-counter++);
                nextSet+=nextSETlocation;
                matrixSize--;
            }
            matrixCount++;
        }
        originalMatrix++;
}   }


//Isolates the values for minors and assigns them to individual groups depending
//on their tier.
void minorValues(void)
{   long int counter1, counter2, counter3, counter4, counter5, counter6;
    int sign, limit, initialize;

    for(counter1=3; counter1<=n; counter1++)
    {   counter4=matrixStartLocation[counter1];
        counter3=counter5=1;
        counter6=0;

        if (counter1!=n)
        {   initialize=matrixStartLocation[counter1];
            limit=counter1*matrixQuantity[counter1];
        }
        else
        {   initialize=1;
            limit=n;
        }
        for(counter2=1; counter2<=limit; counter2++)
        {   counter6++;

            if(counter5%2==0)
            {   sign=-1;
            }
            else
                sign=1;

            group[counter1][counter3++]=sign*matrix[matrixGroup][counter4][1][counter5++];
            if (counter6%counter1==0)
            {   counter5=1;
                counter4++;
}   }   }   }


//Calculates the determinate for the two by two matricies.
void twoBYtwoValues(void)
{   long int counter1, counter2=1, counter3, counter4=1;

    counter4=matrixStartLocation[2];
    for(counter1=1; counter1<=matrixQuantity[2]; counter1++)
    {   group[2][counter2++]=matrix[matrixGroup][counter4][1][1]*matrix[matrixGroup][counter4][2][2];
        group[2][counter2++]=-matrix[matrixGroup][counter4][1][2]*matrix[matrixGroup][counter4++][2][1];
    }
    counter3=0;

    if (n==2)
       determinate[counter++]=group[2][1]+group[2][2];
}


//Multiplys the minor terms together.
void termGrouping(void)
{   long int counter1, counter3, counter4, counter5, x;
    long int counterArray1[20000];

    for(x=0; x<=20; x++)
        counterArray1[x]=1;

    counter4=1;
    for(counter5=1; counter5<=matrixQuantity[2]*2; counter5++)
    {   if(counter4==1)
            for(x=3; x<=n; x++)
                counterArray1[x]-=1;

        for(x=3; x<=n; x++)
        {   if((counter4-1)%(climbLocation[x])==0)
                counterArray1[x]++;
        }

        for(counter1=2; counter1<=n; counter1++)
        {   for(x=2; x<=n; x++)
            {   if(counter1==x)
                {   counter3=counterArray1[x];
            }   }
            term[counter1][counter5]=group[counter1][counter3];
        }
    counter4++;
    counterArray1[2]++;
}   }


//Adds the multiplied minor terms together.
void calculateDeterminate(void)
{   long int x, y;

    if(n!=2)
    {    for(x=1; x<=matrixQuantity[2]*2; x++)
        term[0][x]=1;

        for(x=1; x<=matrixQuantity[2]*2; x++)
           for(y=2; y<=n;y++)
               term[0][x]*=term[y][x];

    determinate[matrixGroup]=0;
    for(x=1; x<=matrixQuantity[2]*2; x++)
        determinate[matrixGroup]+=term[0][x];
}  }
