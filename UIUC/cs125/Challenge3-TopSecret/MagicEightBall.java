//UIUC CS125 FALL 2014 MP. File: MagicEightBall.java, CS125 Project: Challenge3-TopSecret, Version: 2014-09-22T23:06:56-0500.466274734
 /**
 * TODO: add your netid to the line below.
 * @author jtmorri2
 */
public class MagicEightBall {

	/**
	 * Print "Rick Rolled Four Times in One Day" , "Embarrassing Michigan Road
	 * Trip", "Delete Enter Keys Swapped" or "ACE CS125 Exam" according to the
	 * following rules:
	 * 
	 * Unhappy students with less than 10 hours of study always results in
	 * "Embarrassing Michigan Road Trip". Social happy students with more than 20
	 * hours of CS125 study always results in "Ace CS125 Exam". Social, unhappy
	 * students with more than 15 hours of study always results in "Rick Rolled
	 * Four Times in One Day". All other students have their "Delete Enter Keys
	 * Swapped" by the ACM office.
	 * 
	 * @param args
	 */
	
	//Unhappy && study hours<10 				"Embarrassing Michigan Road Trip"
	//Happy && study hours>20					"Ace CS125 Exam"
	//Unhappy && study hours>15					"Rick Rolled Four Times in One Day""
	//else										"Delete Enter Keys Swapped"
	
	public static void main(String[] args) {
		TextIO.putln("Hours spent working on CS125?");
		int hours = TextIO.getlnInt();
		TextIO.putln("Happy?");
		boolean happy = TextIO.getlnBoolean();
		TextIO.putln("Know mentor's innermost Java secrets?");
		boolean social = TextIO.getlnBoolean();

		// Use FOUR output statements - one for each output possibility.
		// An example (incorrect) if-else structure is shown below.
		// However, you should use the above rules to construct your own
		// solution.
		if (!happy && hours>15 && social) {
			TextIO.put("Rick Rolled Four Times in One Day");
		} else {
			if (happy && social && hours>20) {
				TextIO.put("Ace CS125 Exam");
			} else if (!happy && hours<10) {
				TextIO.put("Embarrassing Michigan Road Trip");
			} else {
				TextIO.put("Delete Enter Keys Swapped");
			}
		}

	}

}
