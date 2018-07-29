//UIUC CS125 FALL 2014 MP. File: CallAStaticMethod.java, CS125 Project: Challenge5-DataStructures, Version: 2014-10-12T12:12:55-0500.784759526
/**
 * Prints out only lines that contain an email address Each printed line is
 * justified to right by prepending the text with '.' characters The minimum
 * width of the line including padding is 40 characters. See the test case for
 * example input and expected output.
 * 
 * @author jtmorri2
 */

class CallAStaticMethod {

	public static void main(String[] args) {

		while (!TextIO.eof()) {
			String line = TextIO.getln();
			String temp = "";
			// Use ExampleClassMethods
			// 'isEmailAddress' and 'createPadding' to complete this method
			if(line.indexOf('@')>0){
				
				for(int i=0; line.length()<40; i++){
					temp="";
					temp+="."+line;
					line=temp;
				}	
				TextIO.putln(line);
			}
		}

	}
}
