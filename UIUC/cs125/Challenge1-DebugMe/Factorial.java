//UIUC CS125 FALL 2014 MP. File: Factorial.java, CS125 Project: Challenge1-DebugMe, Version: 2014-09-08T11:14:22-0500.167275778

/**
 * A program to calculate a factorial. The given code may contain errors. Fix the
 * given code and add additional code to calculate a factorial and pass the unit
 * tests. Hint sometimes an 'int' just 'aint big enough.
 * 
 * @see Factorial-ReadMe.txt for details on how to complete this program.
 * @author jtmorri2
 */
public class Factorial {
	public static void main(String[] args) {
		long max = 0, min=0;
		int initial=0;
		boolean test=false;
		
		do
		{	System.out.println("Enter a number between 1 and 20 inclusive.");
			initial=TextIO.getlnInt();
	
			if(initial>0 && initial<20)
			{	max=initial;
				min=initial-1;
				
				while (min > 1) {
					max=max*min--;
				}
				TextIO.putln(initial + "! = " + max);
				test=true;
			}	
			else
				test=false;
	}while (test == false);	
		}
}
