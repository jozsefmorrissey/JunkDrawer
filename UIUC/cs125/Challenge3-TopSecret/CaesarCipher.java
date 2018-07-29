//UIUC CS125 FALL 2014 MP. File: CaesarCipher.java, CS125 Project: Challenge3-TopSecret, Version: 2014-09-22T23:06:56-0500.466274734
/**
 * A program to search for to encrypt and decrypt lines of text. See
 * CaesarCipher.txt
 * Hints: line.charAt( int ) is useful.
 * You'll need loops, and conditions and variables
 * You'll need to read the Test cases to understand how your program should work.
 * Good Programming Hints: "DRY: Don't Repeat Yourself"
 * Try to make your program as short as possible.
 * TODO: add your netid to the line below
 * @author jtmorri2
 */
public class CaesarCipher {

	public static void main(String[] strings) {
		int count = 0, location = 0, offSet = 0, offSetCount = 0;
		boolean validInput = false, keepGoing=true;
		String meassage, incodedMeassage = "";
		//TODO: Delete the following line and write your implementation here- 
		while(!validInput){
			System.out.println("Please enter the shift value (between -25..-1 and 1..25)");
			offSet = TextIO.getlnInt();
			if(offSet == 999)
				validInput = true;
			else if (offSet == -999)
				validInput = true;
			else if(offSet > 25 || offSet < -25 || offSet ==0){
				System.out.println(offSet + " is not a valid shift value.");
				validInput = false;
			}
			else 
				validInput = true;
		}
		if(offSet == 999 || offSet == -999)
			System.out.println("Using position shift");
		else
			System.out.println("Using shift value of " + offSet);
		while(keepGoing){
			System.out.println("Please enter the source text (empty line to quit)");
			meassage=TextIO.getln();
			
			String meassageCaps=meassage.toUpperCase();
			if (meassage.isEmpty()){
				System.out.println("Bye.");
				keepGoing = !keepGoing;
			}
			else if(offSet == 999){
				char[] meassageChars = meassageCaps.toCharArray();
				offSet = 0;
				while(count < meassage.length()){
					if	(meassageChars[count]-'A'>-1 && meassageChars[count]-'A'<26){
				
						location = (int)(meassageChars[count]- 'A');
						if(location + offSet >=0)
							location = (location + offSet) % 26;
						else
							location = (location + offSet + 26);
						incodedMeassage += (char)('A' + location);
						}
					else
						incodedMeassage += meassageChars[count];
					count++;
					offSet++;
					if(offSet == 26)
						offSet = 0;
				}	
			}
			else if(offSet == -999){
				char[] meassageChars = meassageCaps.toCharArray();
				offSet = 0;
				while(count < meassage.length()){
					
					if	(meassageChars[count]-'A'>-1 && meassageChars[count]-'A'<26){
				
						location = (int)(meassageChars[count]- 'A');
						if(location - offSet >=0)
							location = (location - offSet) % 26;
						else
							location = (location - offSet + 26);
						incodedMeassage += (char)('A' + location);
						}
					else
						incodedMeassage += meassageChars[count];
					count++;
					offSet++;
					if(offSet == 26)
						offSet = 0;
				}
			}
			else{
				char[] meassageChars = meassageCaps.toCharArray();
				while(count < meassage.length()){
					if	(meassageChars[count]-'A'>-1 && meassageChars[count]-'A'<26){
				
						location = (int)(meassageChars[count]- 'A');
						if(location + offSet >=0)
							location = (location + offSet) % 26;
						else
							location = (location + offSet + 26);
						incodedMeassage += (char)('A' + location);
						}
					else
						incodedMeassage += meassageChars[count];
					count++;
				}	
			}
			if(keepGoing){
				System.out.println("Source   :" + meassage);
				System.out.println("Processed:" + incodedMeassage);
			}
				location=0;
				incodedMeassage = "";
				count=0;
			
		}
	}

}
