//UIUC CS125 FALL 2014 MP. File: LinkedList.java, CS125 Project: Challenge6-RecursionSee, Version: 2014-11-02T14:20:38-0600.643794355
/**
 * @author jtmorri2
 *
 */
public class LinkedList {
	// Get and Set methods are NOT necessary!

	private LinkedList next; 	
	private final String word;
	private int location = 0;

	/** Constructs this link.
	 * @param word ; a single word (never null).
	 * @param next ; the next item in the chain (null, if there are no more items).
	 */
	public LinkedList(String word, LinkedList next) {
		this.word = word;
		this.next = next;
	}

	/**
	 * Converts the entire linked list into a string representation.
	 */
	public String toString() {
		if (next == null)
			return word;// BASE CASE; no more recursion required

		// Recursive case:
		String restOfString = next.toString(); // Forward Recursion
		return word + ";" + restOfString;
	}

	/**
	 * Returns the number of entries in the linked list.
	 * @return number of entries.
	 */
	public int getCount() {
		if (next == null)
			return 1; // BASE CASE; no more recursion required!

		// Recursive case:
		return 1 + next.getCount(); // Forward recursion
	}
	
	/** Creates a new LinkedList entry at the end of this linked list.
	 * Recursively finds the last entry then adds a new link to the end.
	 * @param word
	 */
	public void append(String word) {
		LinkedList falafal=null;
		if(next!=null)
			next.append(word);
		else{
			falafal = new LinkedList(word, null);
			this.next = falafal;
		}
	}
	/**
	 * Recursively counts the total number of letters used.
	 * 
	 * @return total number of letters in the words of the linked list
	 */
	public int getLetterCount() {
		
		// returns the total number of letters. word1.length() +
		// word2.length()+...
		// "A" -> "CAT" -> null returns 1 + 3 = 4.
		int count = this.word.length();
		if(this.next!=null)
			count+=this.next.getLetterCount();
		return count;
	}

	/**
	 * Recursively searches for and the returns the longest word.
	 * @return the longest word i.e. word.length() is maximal.
	 */
	public String getLongestWord() {
		// recursive searches for the longest word
		String word1 = this.word;
		
		
		//System.out.println("triped up!");
		if(this.next!=null)
			word1 = next.getLongestWord();
		//System.out.println("this.word: " + this.word);
		if(this.next==null)
			return this.word;
		
		//System.out.println("test word: " + word1);
		
		if(this.word.length()>=word1.length()){
			//System.out.println(word1 + "  " + this.word);
			return this.word;
		}	
		else{
			//System.out.println(word1 + "  " + this.word);
			return word1;
		}
		
		
		
	}

	/** Converts linked list into a sentence (a single string representation).
	* Each word pair is separated by a space.
	* A period (".") is appended after the last word.
	* The last link represents the last word in the sentence.*/
	public String getSentence() {
		String story = "";
		
		if(this.next != null)
		story += this.word + " " + next.getSentence();
		
		if(this.next == null)
			story += this.word + ".";

		return story;
	}
	
	/**
	 * Converts linked list into a sentence (a single string representation).
	 * Each word pair is separated by a space. A period (".") is appended after
	 * the last word. The last link represents the first word in the sentence
	 * (and vice versa). The partialResult is the partial string constructed
	 * from earlier links. This partialResult is initially an empty string. 
	 */
	public String getReversedSentence(String partialResult) {
		String story = partialResult;
		boolean start = false;
		
		if(next == null && this.location == 0)
			return this.word + ".";
		
		if(next!=null)
			next.location = this.location + 1;
		
		if(next!=null)
			story = next.getReversedSentence("");
		
		
		if(location != 0)
			story += " " + this.word;
		
		if(location==0){																				
			story += " " + this.word + ".";
			return story.substring(1, story.length());
		}
		return story;
	}
	

	/** Creates a linked list of words from an array of strings.
	 * Each string in the array is a word. */
	public static LinkedList createLinkedList(String[] words) {
		// Hint: This is a wrapper method. You'll need to create your
		// own recursive method.
		// Yes this is possible _without_ loops!
		
		// Hint: This is a wrapper method. You'll need to create your
				// own recursive method.
				// Yes this is possible _without_ loops!
				LinkedList[] hannabalEctor = new LinkedList[words.length];
				hannabalEctor[0] = new LinkedList(words[words.length-1], null);
				
				for(int i = 1; i< words.length; i++)
					hannabalEctor[i] = new LinkedList(words[words.length - i -1], hannabalEctor[i-1]);
				
				
				
				return hannabalEctor[words.length - 1];
	}

	/**
	 * Searches for the following word in the linked list. Hint: use .equals not ==
	 * to compare strings.
	 * 
	 * @param word
	 * @return true if the linked list contains the word (case sensivitive)
	 */
	public boolean contains(String word) {
		if(this.word.equals(word))
			return true;
		else if(next != null)
			return next.contains(word);
		
		return false;
	}

	/** Recursively searches for the given word in the linked list.
	 * If this link matches the given word then return this link.
	 * Otherwise search the next link.
	 * If no matching links are found return null.
	 * @param word the word to search for.
	 * @return The link that contains the search word.
	 */
	public LinkedList find(String word) {
		if(this.word.equals(word))
			return this;
		else if(next != null)
			return next.find(word);
			
		return null;
	}

	/**
	 * Returns the last most link that has the given word, or returns null if
	 * the word cannot be found.
	 * Hint: Would forward recursion be useful?
	 * @param word the word to search for.
	 * @return the last LinkedList object that represents the given word, or null if it is not found.
	 */
	public LinkedList findLast(String word) {
		LinkedList words = null;
		if(next!=null)
			words = next.findLast(word);

		if(this.word.equals(word) && words == null)
			return this;
		
		return words;	
	}

	public LinkedList insert(String string) {
		LinkedList pooper;
		if("A".equals(string)){
			pooper = new LinkedList(string, this);
			return pooper;
		}
		LinkedList party = new LinkedList(string, null);
		
		if(next!=null){
			next.insert(string);
		}		
		else
			this.next = party;
		
		return this;
			
	}

}
