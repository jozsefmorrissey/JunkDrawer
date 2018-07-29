//UIUC CS125 FALL 2014 MP. File: InsecurePasswordLockBreaker.java, CS125 Project: Challenge7-RecursiveKnight, Version: 2014-11-24T10:52:38-0600.886603621
public class InsecurePasswordLockBreaker {

	public static char[] breakLock(InsecurePasswordLock lock) {
		char[] key = new char[1];
		// write code here to determine the secret password
		// to unlock the given lock object.
		// You do not need to use recursion.
		// Hint: Read the source code of InsecurePasswordLock
		// The lock has a weakness....
		// Understand it and you can write an algorithm to quickly find the
		// secret password
		// (A brute force guess of a 40 character password would take a long
		// time...
		// as there are 26^40 combinations!
		// Your method should find it in a few seconds.
		int pos=0;
		String wtf = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
		System.out.println(key);
		for(int i=0; i<51; i++){
			if(lock.open(key)==-1){
				wtf +="A";
				key = wtf.toCharArray();
			}
			else
				i=4055;
			
		}
			
		while(pos<key.length){
			for(int i=0; i<26; i++){
				key[pos] = (char)(key[pos] + i);
				//System.out.println(lock.open(key));
				if(lock.open(key)!=pos){
					pos++;
					i=4117;
				}
			}
		}

		// Beginner: You should complete this code in less than an hour

		// Advanced Code-Golf: Can you complete this method in 8 lines
		// (excluding the top and bottom given
		// lines and after autoformating your code)
		
		// Crazy Instructor level:
		// I can write a complete albeit-inefficient solution using single while loop :-)
		// expression: while (____){/*NoCodeHere*/}
		return key;
	}

	public static void main(String[] args) {
		InsecurePasswordLock lock = new InsecurePasswordLock();
		char[] key = breakLock(lock);
		System.out.println(key);
		System.out.println(lock.isUnlocked());
	}
}
