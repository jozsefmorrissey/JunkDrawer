//UIUC CS125 FALL 2014 MP. File: OddSum.java, CS125 Project: Challenge3-TopSecret, Version: 2014-09-22T23:06:56-0500.466274734
/**
 * Prints sum of odd numbers in a specific format.
 * TODO: add your netid to the line below
 * @author jtmorri2
 */
public class OddSum { 
/**
Example output if user enters 10:
Max?
1 + 3 + 5 + 7 + 9 = 25
25 = 9 + 7 + 5 + 3 + 1

Example output if user enters 11:
Max?
1 + 3 + 5 + 7 + 9 + 11 = 36
36 = 11 + 9 + 7 + 5 + 3 + 1

 */
 public static void main(String[] args) { 
	 int total=0, maxTest=0, i=0;
	 int max[]= new int[1000];
	 System.out.println("Max?");
	 max[0]=TextIO.getlnInt();
	 maxTest=max[0];
	 while (maxTest>0){
		 if(maxTest%2 == 0)
			 maxTest--;
		 max[i]=maxTest--;
		 i++;
	}
	 for(i=0 ; i*2<max[0] ; i++)
		 total=total+max[i];
	 
	 for(i=max[0]/2 ; i>=0 ; i--){
		 if(i==0)
			 System.out.print(max[i] + " ");
		 else
			 System.out.print(max[i] + " + ");
	 }
	 System.out.print("= " + total + "\n" + total + " = ");
	 
	 for(i=0 ; i*2<max[0] ; i++)
		 if(i*2==max[0] || i*2==max[0]-1)
			 System.out.print(max[i] + "");
		 else
			 System.out.print(max[i] + " + ");
	 
  } // end of main method
} // end of class 
