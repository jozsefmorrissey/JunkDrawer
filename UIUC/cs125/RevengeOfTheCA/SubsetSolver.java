//UIUC CS125 FALL 2014 MP. File: SubsetSolver.java, CS125 Project: RevengeOfTheCA, Version: 2014-11-29T17:29:42-0600.994349668
//UIUC Fall 2014 CS125 Midterm 3 Bonus

/**
 * This is the only file you need to edit. Complete the solve() method
 * as per the specifications provided below.
 * 
 * @author YOUR_NET_ID
 */
public class SubsetSolver {
	// TODO add your own private instance variables here
	// HINT: look at the files that were given to you!
	private int size;
	private int[] components;
	private int number = 0;
	
	public SubsetSolver(int n) {
		// TODO initialize your private instance variables here!
		// Feel free to use loops here
		size = n;
		components = new int[n+1];
		for(int i = 0; i<n +1; i++)
			components[i] = i;
		
	}
	
	
	// Returns the number of subsets from the set {1,2,3,...,n} whose
	// elements sum to an even number.
	// No loops! You must use recursion! No constant-time solutions either.
	// HINT: maybe you want to write a wrapper method?
	public int solve() {
		// TODO write your method here!
		
		solveLevel(size, 0);
		System.out.println(number/2 +1);
		return number/2 +1;
	}
	public void solveLevel(int level, int min){
		int test = 0;
		test = level + min;
			
		if(level>0){
			solveLevel(level-1, min+level);
			solveLevel(level-1, min);
		}
		if(test !=0 && test%2==0)
			number++;
		
		
		
			
		
			
		
	}
}
