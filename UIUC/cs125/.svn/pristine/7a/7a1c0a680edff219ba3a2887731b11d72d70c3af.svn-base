//UIUC CS125 FALL 2014 MP. File: SubsetSolverTest.java, CS125 Project: RevengeOfTheCA, Version: 2014-11-29T21:37:15-0600.117232747
//import junit.framework.TestCase;

/** A tester for the SubsetSolver class
 * 
 * @author nphadke2
 *
 */

public class SubsetSolverTest {
	
	public static int testSmallCases() {
		SubsetSolver s1 = new SubsetSolver(1);
		if(1 != s1.solve()) {
			System.out.println("Fail on 1");
			return 0;
		}
		
		SubsetSolver s2 = new SubsetSolver(2);
		if(2 != s2.solve()) {
			System.out.println("Fail on 2");
			return 0;
		}
		
		return 1;
	}
	
	public static int testLargeCases() {
		SubsetSolver s3 = new SubsetSolver(15);
		if(16384 != s3.solve()) {
			System.out.println("Fail on 15");
			return 0;
		}
		
		SubsetSolver s4 = new SubsetSolver(20);
		if(524288 != s4.solve()) {
			System.out.println("Fail on 20");
			return 0;
		}
		
		return 1;
	}
	
	public static void main(String[] args) {
		if(testSmallCases() == 1 && testLargeCases() == 1)
			System.out.println("No failures.");
		else
			System.out.println("Failures found.");
		
		// TODO add your own test cases
	}
	
	
}