



//UIUC CS125 FALL 2014 MP. File: StaticMethodsAreEasy.java, CS125 Project: Challenge5-DataStructures, Version: 2014-10-12T12:12:55-0500.784759526
/*
 * @author jtmorri2
 */
public class StaticMethodsAreEasy {

private static Geocache[] arr;
// Oh no... Someone removed  the methods but left the comments!!
// Hint: Get the Geocache class working and passing its tests first.
public static Geocache getCache(int i){
	return arr[i];
}

	/**
	 * Returns an array of num geocaches. Each geocache is initialized to a random
	 * (x,y) location.
	 * if num is less than zero, just return an empty array of length 0.
	 * 
	 * @param num
	 *            number of geocaches to create
	 * @return array of newly minted Points
	 */
//write the method here...
public static Geocache [] createGeocaches(int num){
	
	if(num>-1){
		arr = new Geocache[num];
		for(int i=0; i<num; i++){
			arr[i] =new Geocache(Math.random()*9999, Math.random()*9999);
	}
	return arr;
	}
	arr = new Geocache[0];
	return arr;
}
public static int countEqual(Geocache[] pts, Geocache origin) {
	// TODO Auto-generated method stub
	int count=0;
	
	for(int i=0; i<pts.length; i++){
		
		if(pts[i].equals(origin))
			count++;
		}
	
	return count;
}
	
	/**
	 * Modifies geocaches if the geocache's X value is less than the allowable minimum
	 * value.
	 * 
	 * @param p
	 *            array of geocaches
	 * @param minX
	 *            minimum X value.
	 * @return number of modified geocaches (i.e. x values were too small).
	 */
	//write the method here...
public static void ensureMinimumXValue(Geocache[] fred, double value){
	if(fred!=null){
		for(int i=0; i<fred.length; i++){
			if(fred[i].getX()<value)
				fred[i].setX(value);
		}
	}
}

	/**
	 * Counts the number of geocaches that are equal to the given geocache
	 * Hint: Use geocache.equals() method 
	 * @param p -
	 *            geocache array
	 * @param test -
	 *            test geocache (compared using .equal)
	 * @return number of matching geocaches
	 */
	//write the method here...
}