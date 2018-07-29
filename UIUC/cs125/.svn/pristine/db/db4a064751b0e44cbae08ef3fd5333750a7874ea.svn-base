//UIUC CS125 FALL 2014 MP. File: MovieSurvey.java, CS125 Project: Challenge2-Hollywood, Version: 2014-09-14T08:00:09-0500.141575101
/**
 * A program to run a simple survey and report the results. See MovieSurvey.txt
 * for more information. TODO: add your netid to the line below
 * 
 * @author jtmorri2
 */
public class MovieSurvey {
	public static void main(String[] arg) {
		// TODO: Write your program here
		// Hints :
		// Formatted output
		// http://math.hws.edu/javanotes/c2/s4.html#basics.4.4
		int cinima, dvd, comp, total;
		double percentCinima, percentNcinima;
		
		System.out.println("Welcome. We're interested in how people are watching movies this year.\nThanks for your time. - The WRITERS GUILD OF AMERICA.");
		System.out.println("Please tell us about how you've watched movies in the last month.\nHow many movies have you seen at the cinema?");
		cinima=TextIO.getlnInt();
		System.out.println("How many movies have you seen using a DVD or VHS player?");
		dvd=TextIO.getlnInt();
		System.out.println("How many movies have you seen using a Computer?");
		comp=TextIO.getlnInt();
		
		total = cinima + dvd + comp;
		
		
		percentCinima = ((double)cinima / (double)total);
		percentNcinima = 1 - percentCinima;
		System.out.println("Summary: " + cinima + " Cinema movies, " + dvd + " DVD/VHS movies, " + comp + " Computer movies");
		System.out.println("Total: " + total + " movies");
		System.out.printf("Fraction of movies seen at a cinema: %.2f%%\n", percentCinima*100.0);
		System.out.printf("Fraction of movies seen outside of a cinema: %.2f%%",  percentNcinima*100.0);
		
		// Don't just copy paste the expected output
		// For grading purposes your code may be tested
		// with other input values.
	}
}
