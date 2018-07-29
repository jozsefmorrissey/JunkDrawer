//UIUC CS125 FALL 2014 MP. File: CipherBreaker.java, CS125 Project: Challenge3-TopSecret, Version: 2014-09-22T23:06:56-0500.466274734
/**
 * See CipherBreaker.txt for instructions.
 * TODO: add your netid to the line below
 * 
 * @author jtmorri2
 */
public class CipherBreaker {

	public static void main(String[] args) {
		int i=0;
		char output;
		int[] alpha = new int[30];
		TextIO.putln("Text?");
		String line = TextIO.getln();
		TextIO.putln(line);
		
		line= line.toLowerCase();
		char[] lineCharArray= line.toCharArray();
		
		while(i < line.length()){
		for(int j=0 ; j<26 ; j++){
			if(lineCharArray[i] == (char) ('a' + j))
				alpha[j]++;
		}
		if(lineCharArray[i] == '.' || lineCharArray[i] == '!' || lineCharArray[i] == ',' || lineCharArray[i] == '-' || lineCharArray[i] == '\"' || lineCharArray[i] == '\'')
			alpha[28]++;
		if(lineCharArray[i] == ' ')
			alpha[27]++;
	
		if(lineCharArray[i] == '0' || lineCharArray[i] == '9' || lineCharArray[i] == '8' || lineCharArray[i] ==  '7' || lineCharArray[i] == '6' || lineCharArray[i] == '5' || lineCharArray[i] == '4' || lineCharArray[i] == '3' || lineCharArray[i] == '2' || lineCharArray[i] == '1')
			alpha[26]++;
		i++;
		}
		for(int j=0 ; j<29 ; j++){
			if(alpha[j]>0 && j<26){
				output = (char)('A' + j);
				System.out.println(output + ":" + alpha[j]);
			}	
			else if(alpha[j]>0 && j==26)
				System.out.println("DIGITS:" + alpha[j]);
			else if(alpha[j]>0 && j==27)
				System.out.println("SPACES:" + alpha[j]);
			else if(alpha[j]>0 && j==28)
				System.out.println("PUNCTUATION:" + alpha[j]);
		}
	
}	}
