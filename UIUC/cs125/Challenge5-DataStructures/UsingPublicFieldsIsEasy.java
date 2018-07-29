//UIUC CS125 FALL 2014 MP. File: UsingPublicFieldsIsEasy.java, CS125 Project: Challenge5-DataStructures, Version: 2014-10-12T12:12:55-0500.784759526

/**
 * Complete the class method 'analyze' that takes a SimplePublicPair object as an argument
 * and returns a new SimplePublicTriple object.
 * The SimplePublicTriple needs to set up as follows:
 * x = the minimum value of 'a' and 'b'
 * y = the maximum value of 'a' and 'b'
 * description:a*b=M 
 *   where a,b, and M are replaced with the numerical values of a, b and the multiplication of a and b.
 * Your code will create a SimplePublicTriple, initializes the three fields and return a reference to the SimplePublicTriple object.
 *
 *@author jtmorri2
 */
public class UsingPublicFieldsIsEasy {
	private static String description;
		private int x;
		private int y;
		
	public static SimplePublicTriple analyze(SimplePublicPair in) {
		int product;
		product = in.a*in.b;
		SimplePublicTriple thing= new SimplePublicTriple();
		if(in.a>in.b){
			thing.x=in.b;
			thing.y=in.a;
			thing.description=("" + thing.y +"*" + thing.x + "=" + product);
		}
		else{
			thing.x=in.a;
			thing.y=in.b;
			thing.description=("" + thing.x +"*" + thing.y + "=" + product);
		}
		
		
		
		return thing;
	}
}
