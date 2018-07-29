//UIUC CS125 FALL 2014 MP. File: Queue.java, CS125 Project: Challenge5-DataStructures, Version: 2014-10-12T12:12:55-0500.784759526
/*
 * @author jtmorri2
 */
class Queue {
	/** Adds the value to the front of the queue.
	 * Note the queue automatically resizes as more items are added. */
	private int count;
	private double[] queue;
	public void add(double value) {
		double[] worker = new double[++count];
		//System.out.println("\t" + count);
		worker[0]=value;
		
		for(int i=1; i<worker.length; i++)
			worker[i]=queue[i-1];
		queue=worker;
		//printValues();
	}
	/** Removes the value from the end of the queue. If the queue is empty, returns 0 */
	public double remove() {
		count--;
		if(queue!=null && queue.length>1){
			//System.out.println(count);
			double[] worker = new double[queue.length-1];
			//System.out.println("\t" + count);
			double value=queue[queue.length-1];
			
			for(int i=0;i<worker.length; i++)
				worker[i]=queue[i];
			queue=worker;
			return value;
		}
		else if(queue!=null && queue.length==1){
			double value=queue[queue.length-1];
			queue=null;
			return value;
		}
			return 0;
	}
	
	/** Returns the number of items in the queue. */
	public int length() {
		if(queue==null)
			return 0;
		return queue.length;		
	}
	
	/** Returns true iff the queue is empty */
	public boolean isEmpty() {
		if(queue == null)
			return true;
	
			return false;
	}
	
	/** Returns a comma separated string representation of the queue. */
	public String toString() {
		String returnStr=new Double(queue[count-1]).toString();
		for(int i=1; i<count; i++)
			returnStr+=("," + queue[count-1-i]);
		return returnStr;
	}
	
	public void printValues(){
		for(int i=0; i<count; i++)
			System.out.println("\t" + queue[i]);
	}
}
