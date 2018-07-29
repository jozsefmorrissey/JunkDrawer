//UIUC CS125 FALL 2014 MP. File: SimplePublicPair.java, CS125 Project: Challenge5-DataStructures, Version: 2014-10-12T12:12:55-0500.784759526
/** A pair of public integers - the simplest kind of object
 * Normally we encapsulate the data and make it private.
 * Do not modify this file.
 * 
 * @author angrave
 * 
 */
public class SimplePublicPair {
	public int a;
	public int b;

	public void addOneToA() {
		this.a += 1;
	}

	public void addAPair(SimplePublicPair other) {
		this.a += other.a;
		this.b += other.b;
	}

	public boolean isLarger(SimplePublicPair other) {
		return this.a > other.a && this.b > other.b;
	}
}
