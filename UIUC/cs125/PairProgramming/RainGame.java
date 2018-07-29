import java.awt.Toolkit;

//UIUC CS125 FALL 2014 MP. File: RainGame.java, CS125 Project: PairProgramming, Version: 2014-09-29T23:16:22-0500.873556189
/**
 * @author jleonrd2, jtmorri2
 */
public class RainGame {

	public static void main(String[] args) {
		//We have to seperate these out into groups so we can better differentiate which variables apply to what part of the program
		int x, y, dx, dy, score, level, answer, counter, answerStringLocation, gameMode, numberOfModes, wrong, right, grade, red, blue, green, colorCounter, screens, stringCounter;
		double  probNumber, probLowerCase, wordLength;
		boolean lose = false, mode2change=false, alreadyAssessed=false;//activates game over screen.
		String text = "";
		stringCounter=0;//sets location of character in text to be tested in color game.
		answer=0;//is the final answer once an operation (+ - X) is chosen
		counter=0;//counts the number of correct entries the user has input and is set back to zero with the first wrong key. to ensure that entire string is entered in order.
		answerStringLocation=0;//tells the computer where to look for the correct input.
		wrong=0;//# of wrong answers
		right=0;//# of right answers
		grade=0;//ratio of right and wrong+right(total).
		colorCounter=0;//please comment the purpose of these variables so I get what's happening
		red=0;//Variable that determines the intensity of red.
		blue=0;//Variable that determines the intensity of blue.
		green=0;//Variable that determines the intensity of green.
		x=0;
		y=0;
		dx=0;
		dy=0;
		score=0;//score starts at -1 so the first time it increments it will be 0
		level=1;//level will increment every time score increases by 10
		wordLength=6;
		probNumber=.15;
		probLowerCase=.6;
		gameMode=1;//instead of having a boolean type variable (which only allows for 2 modes), we can have an int
		//that will give us more flexibility
		numberOfModes=3;
		//added level variable, Changed score variable initialization to make it
		//work for how it's incremented in the while loop
		String levelSelect = "";
		Zen.setFont("Helvetica-64");
		while(levelSelect.equals("")){
			Zen.drawText("What Level Do You", 10, 100);
			Zen.drawText("Want to Start At?",10,200);
			levelSelect = Zen.getEditText();
			Zen.setEditText("");
		}
		level = Integer.parseInt(levelSelect);
		
		while (Zen.isRunning()) {
			
			if(score<0){//Don't want the score to be negative
				score = 0;
			}
			// Find out what keys the user has been pressing.
			String user = Zen.getEditText();
			// Reset the keyboard input to an empty string So next iteration we will only get the most recently pressed keys.
			Zen.setEditText("");
			
			if(!lose){
				if (text.length() == 0) {
					x = 0;
					y = Zen.getZenHeight() / 2;	
					dy = 0;//will cause the text to fall vertically if set to something >0

					if(gameMode==0){
						dx = level;
						text = genRandString(probNumber,probLowerCase,wordLength);
					}
					else if(gameMode==1){
						dx = level;
						String[] expression = genExpression();
						text = expression[0];
						answer = Integer.parseInt(expression[1]);	
					}
					else if(gameMode==2)
						dx=2;
		
					if(score%10==0&&score>0){//increment level when score increases by 10
						level++;
					}
				}
				Zen.setColor(red, green, blue);
				Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());
	
				Zen.setColor(255-red, 255-green, 255-blue);
				
				if(gameMode!=2)
				Zen.drawText(text, x, y);
				
				Zen.drawText("Level: "+level,10,50);
				Zen.drawText("Score: "+score,10,100);
				Zen.drawText("Grade: "+ grade,10,400);
				
				x += dx;
				y += dy;
				screens=2+level;
				
				
				
				if(gameMode==0){
					for(int i=0;i < user.length();i++) {
						char c = user.charAt(i);
						if(c == text.charAt(0)){
							text = text.substring(1,text.length()); // all except first character
							score++;//increments score when correct key is pressed
							right++;
						}
						else if(c != '/'){
							wrong++;
							score--;//decrements score when incorrect key is pressed
						}
							if(c=='/'){
							gameMode=(gameMode+1)%numberOfModes;//cycles through game modes (will still work as more modes are added)
							text = "";
						}
					}
				}
				
				else if(gameMode==1){
					//Clears text after all characters of strSum have been entered in order.
					for(int i=0;i < user.length();i++) {
						char c = user.charAt(i);
						if(c == (answer+"").charAt(answerStringLocation)){//does away with strSum
							answerStringLocation++;
							counter++;
						}
						else if(c!='/'){//decrements score and restarts string when wrong value is entered.
							score--;
							wrong++;
							answerStringLocation=0;
						}
						if(counter == (int) Math.log10(answer)+1){ //clears text resets values and increments score 
							text = ""; 					//only after all characters have successfully been 
							counter=0;					//entered in the correct order.
							answerStringLocation=0;
							score++;
							right++;
						}
						if(c=='/'){
							gameMode=(gameMode+1)%numberOfModes;//cycles through game modes (will still work as more modes are added)
							text = "";
						}
					}
				}
				//the color game: player hits the letter that corresponds to the color of the screen
				else if (gameMode==2){
					if(text.length() == 0)//resets color counter at the beginning of each round.
						colorCounter=0;
					if(colorCounter<((int)(150))*screens){//Determines if there are still screens that need to be displayed based on the level the player has reached.
					
						if(colorCounter%(int)(150)==0){//changes the color display every 150 cycles giving the player a moment to take note of the current color.
							int randColor=(int) (Math.random()*4);//randomizes color to be displayed(red, yellow, green, blue)
								red=green=blue=0;
								if(randColor==0){
									text+='r';
									red=999;
								}
									else if(randColor==1){
										text+='y';
										red=999;
										green=999;
									}
										else if(randColor==2){
											text+='b';
										    blue=999;
										}
										    else if(randColor==3){
												text+='g';
												green=999;
										    }
								Toolkit.getDefaultToolkit().beep();//makes a sound as every color is displayed
								screens=level+2;//level 1 displays 3 colors and the number is incremented once for every level obtained.
						}
					}
					if(colorCounter>150*screens){//displays a input screen and requires all screens be displayed before allowing input.
						red=blue=green=0;//makes the background black
						Zen.drawText("PATTERN?(r,b,g,y)",10,200);//requests input and supplies basic input value expectations
						Zen.drawText("Give Up?(space-bar)",10,300);
					
						for(int i=0;i < user.length();i++) {//tests users input
							char c = user.charAt(i);
							if(c=='/'){
								gameMode=(gameMode+1)%numberOfModes;//cycles through game modes (will still work as more modes are added)
								text = "";
								mode2change=true;
							}
							else if(c==text.charAt(stringCounter)){
								right++;
								stringCounter++;
								if(score%3==0&&score>0 && !alreadyAssessed){
									level++;
									alreadyAssessed=true;
								}
								if(stringCounter==text.length()){
									text="";
									stringCounter=0;
									score++;
									alreadyAssessed=false;
								}
							}
							else if(c==' '){//allows player to give up.
								wrong++;
								text="";
								
							}	
							else
								stringCounter=0;
						}
					}
						
				}
				if(x>Zen.getZenWidth() && gameMode!=2 && !mode2change){//if the text moves outside the window, the score will go to zero, and the game will restart
					//we could definitely build in a feature to have the window change colors and it say you lost, and ask if you want to try again
					text="";
					lose=true;
				}
				mode2change=false;
				
				if(right+wrong != 0)//calculates a grade once keys have been entered.
					grade = (int)(((double)(right)/(double)(right+wrong))*100);

				
				//Randomizes the values that express the color concentrations of the display.
				
				if(colorCounter%2==0 && gameMode!=2){
					red=((int)(Math.random()*2))*999;
					blue=((int)(Math.random()*2))*999;
					green=((int)(Math.random()*2))*999;
					
				}
				colorCounter++;
			}
			//creates a game over display.
			else if(lose){
					//Gives a letter grade determined by the ratio grade.
					char gradeChar;
					if(grade>89)
						gradeChar='A';
					else if(grade>79)
						gradeChar='B';
					else if(grade>69)
						gradeChar='C';
					else if(grade>59)
						gradeChar='D';
					else
						gradeChar='F';
					//Black background
					Zen.setColor(0, 0, 0);
					Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());
					
					//Red text
					Zen.setColor(255, 0, 0);
					Zen.drawText(text, x, y);
					
					Zen.drawText("GAME OVER",10,50);
					Zen.drawText("LEVEL   "+level,10,150);
					Zen.drawText("SCORE  "+score,10,250);
					Zen.drawText("Press Space-Bar",75,350);
					Zen.drawText("Play Again",160,450);
					Zen.drawText("" + gradeChar ,475,200);
					
					Zen.sleep(9);
					
					
					//Space bar allows user to restart.
					for(int i=0;i < user.length();i++){ 
						char c = user.charAt(i);
						if(c == ' '){
							lose=false;
							score=right=wrong=grade=0;
						}	
					}	
			}
			
			Zen.flipBuffer();//Fixes flickering
			Zen.sleep(9);// sleep for 90 milliseconds
		}
	}
	
	public static String genRandString(double probNumber, double probLower, double probLength){
		String text = "";
		int randLength = (int) (Math.random()*probLength+1);
		for(int i = 0; i<randLength;i++){
			//Picks one character at a time depending on the probabilities in the if statements
			double prob = Math.random();
			if(prob < probNumber){
				text+=(int) (Math.random()*10);
			}
			else if(prob < probLower+probNumber){
				text+=(char)((int) ((Math.random()*26)+'a'));
			}
			else if(prob < 1){
				text+=(char)((int) ((Math.random()*26)+'A'));
			}	
		}
		return text;
	}
	
	public static String[] genExpression(){
		String[] output = new String[2];
		int term1 = (int) (Math.random() * 9)+1;
		int term2 = (int) (Math.random() * 9)+1;
		int operation = (int) (Math.random() * 3);

		if(operation == 0){
			output[0] = term1 + " + " + term2;//Displays an addition equation.
			output[1] = (term1 + term2)+"";//does the same thing as string.valueOf()
		}
		if(operation == 1){
			output[0] = term1 + " - " + term2;//Displays an addition equation.
			output[1] = (term1 - term2)+"";//does the same thing as string.valueOf()
		}
		if(operation == 2){
			output[0] = term1 + " * " + term2;//Displays an addition equation.
			output[1] = (term1 * term2)+"";//does the same thing as string.valueOf()
		}
		return output;
	}
}
