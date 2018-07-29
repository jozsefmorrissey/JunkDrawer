//UIUC CS125 FALL 2014 MP. File: ExamplePrint.java, CS125 Project: Challenge1-DebugMe, Version: 2014-09-08T11:14:22-0500.167275778
public class ExamplePrint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.print("I will ");
		System.out.print("print on the same");
		System.out.print("line");
		System.out.println(); // prints a newline.
		System.out.println("\n"); // prints two newlines
		System.out
				.print("Three newlines were printed - hence there are two blank lines");
		System.out.print(". This is a quote \"   character inside a quote!");

		int integerDivision = 47232 / 1000;
		System.out
				.println("Here's a simple trick that will be useful for zip codes..."
						+ integerDivision);

		int score = 50;
		// will zero out score if it's >40 and value of integerDivision is positive.
		if ( score>40 && integerDivision > 0)
			score = 0;
		
		System.out.print("Your score :" + score);
		
		//Factorial hint : Read the comments in Factorial.java!
		
	}

}
