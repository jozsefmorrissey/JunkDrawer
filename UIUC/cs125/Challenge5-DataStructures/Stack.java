//UIUC CS125 FALL 2014 MP. File: Stack.java, CS125 Project: Challenge5-DataStructures, Version: 2014-10-12T12:12:55-0500.784759526
/*
 * @author jtmorri2
 */
class Stack {
	private String [] stacks;
	/** Adds a value to the top of the stack.*/
	public void push(String value){
		int x;
		if(stacks==null)
			x=1;
		else
			x=stacks.length+1;
		String[] temp = new String[x];
		for(int i=0; i<temp.length-1; i++)
			temp[i]=stacks[i];
		temp[temp.length-1]=value;
		stacks=temp;
		stacks.toString();
	}
	
	/** Removes the topmost string. If the stack is empty, returns null. */
	public String pop() {
		if(stacks!=null && stacks.length!=0){
			//System.out.println(stacks.length);
		String removed=stacks[stacks.length-1];
		String temp[]= new String[stacks.length-1];
		for(int i=0; i<temp.length; i++){
			temp[i]=stacks[i];
		}
		stacks=temp;
		return removed;
		}
		
			return null;
	}
	
	/** Returns the topmost string but does not remove it. If the stack is empty, returns null. */
	public String peek() {
		if(stacks!=null)
			return 	stacks[stacks.length-1];
		else
			return null;
	}
	
	/** Returns true iff the stack is empty */
	public boolean isEmpty() {
		if(stacks == null || stacks.length==0)
			return true;
		else
			return false;
	}

	/** Returns the number of items in the stack. */
	public int length() {
		if(stacks!=null)
		return stacks.length;
		
		return 0;
	}
	
	/** Returns a string representation of the stack. Each string is separated by a newline. Returns an empty string if the stack is empty. */
	public String toString() {
		String temp = "";
		//System.out.println(stacks.length);
		for(int i=0; i<stacks.length; i++){
			temp+=stacks[i] + "\n";
		}
		//System.out.println(temp);
		return temp;
	}
}

