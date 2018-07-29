//UIUC CS125 FALL 2014 MP. File: Geocache.java, CS125 Project: Challenge5-DataStructures, Version: 2014-10-12T12:12:55-0500.784759526
/**
 * Create a class 'Geocache' with two private instance variables 'x' 'y' of type double and a private integer 'id'
 * .
 * A class variable 'count' will also be required to count the number of times a Geocache object has been created (instantiated).
 * Create two constructors - one which takes two double parameters (the initial values of x,y). The
 * second constructor will take another Geocache and initialize itself based on that geocache.
 * 
 * For both constructors set the Geocache's id to the current value of count (thus the first geocache will have the value zero)
 * when the Geocache is created, then increment the count value. 
 * 
 * Also create 'resetCount()' and getNumGeocachesCreated() which returns an int - the number of geocaches created since 
 * resetCount() method was called.
 * 
 * Create an equals method that takes an object reference and returns true if the given object equals this object.
 * Hint: You'll need 'instanceof' and cast to a (Geocache)
 * 
 * Create a toString() method that returns a string representation of this object in the form '(x,y)' where 'x' and 'y'
 * are the current values of x,y.
 * 
 * Create the four getX/getY/setX/setY methods for x,y.
 * However the setX() method will only change the Geocache's x value if the given value is between -1000 and 1000 exclusive (i.e. -1000<x<1000).
 * If the value is outside of this range, the new value is ignored and the Geocache's x value remains unchanged.
 *   
 * Create a get method for id.
 * 
 * @author jtmorri2
 */

//Whats the differance between a pile of dead babies and a corvette...... I don't have a corvette in my garage moohahahaha.
class Geocache {
	private int id;
	private static int count;
	private double x;
	private double y;
	

	
	public Geocache(double d, double e) {
		x=d;
		y=e;
		id=count++;
		// TODO Auto-generated constructor stub
	}



	public Geocache(Geocache other) {
		x = other.x;
		y = other.y;
		id=count++;
		// TODO Auto-generated constructor stub
	}



	public String toString(){
		String returnString = "";
		return returnString += "(" + x + "," + y + ")";
	}
	public double getX(){
			
		return x;
	}
	public double getY(){
		return y;
	}
	public void setX(double i){
		if(i>-1000 && i<1000)
			x=i;
	}
	public void setY(double i){
		y=i;
	}
	public static int getNumGeocachesCreated(){
		return count;
	}
	public static void resetCount(){
		count=0;
	}
	public int getId(){
		return id;
	}
	public boolean equals(Geocache other){
		if(x == other.x && y == other.y)
			return true;
		return false;
	}
	
}
