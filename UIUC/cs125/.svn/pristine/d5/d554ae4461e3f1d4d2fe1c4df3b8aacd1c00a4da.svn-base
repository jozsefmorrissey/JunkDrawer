//UIUC CS125 FALL 2014 MP. File: LinkedList.java, CS125 Project: RevengeOfTheCA, Version: 2014-11-29T17:29:42-0600.994349668

public class LinkedList {
	public LinkedList next;
	public final int value;
	
	public LinkedList(int value) {
		this.next = null;
		this.value = value;
	}
	
	public LinkedList(int value, LinkedList next) {
		this.next = next;
		this.value = value;
	}
	
	//Returns the linked list 1 -> 2 -> 3 -> 4 -> ... -> n
	public static LinkedList createList(int n) {
		LinkedList head = new LinkedList(1, null);
		LinkedList curr = head;
		
		for(int i=2; i<=n; i++) {
			curr.next = new LinkedList(i, null);
			curr = curr.next;
		}
		
		return head;
	}
}
