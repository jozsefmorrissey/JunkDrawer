//UIUC CS125 FALL 2014 MP. File: BinarySearch.java, CS125 Project: Challenge7-RecursiveKnight, Version: 2014-11-24T10:52:38-0600.886603621
//@author jtmorri2
public class BinarySearch {
	/** Wrapper method. Just runs the recursive search method below for the entire array*/
	public static boolean search(int[] array, int key) {
		return search(array, key, 0, array.length - 1);
	}

	/**
	 * Recursive search using Divide and Conquer approach:
	 * The given array is already sorted so we can very quickly discover if the key is in the array or not.
	 * Hint: For the recursive case check the value at (lo+hi)/2
	 * and proceed accordingly.
	 */
	static boolean search(int[] array, int key, int lo, int hi) {
		if(lo>hi)
			return false;
		if(array[(lo+hi)/2]==key)
			return true;
		else if(array[(lo+hi)/2]>key)
			return search(array, key, lo, (lo+hi)/2-1);
		else
			return search(array, key, (lo+hi)/2+1, hi);
	}
}
