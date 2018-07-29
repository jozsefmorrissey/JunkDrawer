//UIUC CS125 FALL 2014 MP. File: MolecularSort.java, CS125 Project: Challenge7-RecursiveKnight, Version: 2014-11-24T10:52:38-0600.886603621
//@author jtmorri2
public class MolecularSort {

	/** Sorts each xyz coordinate using it's Z value (coord[i][2] <= coord[j][2] for i<j). */
	static void sortCoordsByZ(double[][] coords) {
		// TODO: Implement this wrapper method.
		//All the work is performed by recursiveSort
		recursiveSort(coords, 0, coords.length-1);
	}

	/**
	 * recursively sorts coordinates entries by their z value. Entries between
	 * lo and hi inclusive are considered.
	 */
	static void recursiveSort(double[][] coords, int lo, int hi) {
		// TODO: write the four lines of a recursive selection sort here.
		swap(coords, lo, findIndexOfZMinimum(coords, lo, hi));
		if(lo<hi)
			recursiveSort(coords, lo+1, hi);
		
	}	

	/**
	 * returns the index of the entry with the lowest Z value. Entries between
	 * lo and hi inclusive are considered.
	 */
	static int findIndexOfZMinimum(double[][] coords, int lo, int hi) {
		int min = hi;
		if(lo < hi)
			min = findIndexOfZMinimum(coords, lo+1, hi);
		if(coords[min][2]>coords[lo][2])
				min = lo;
		return min; // TODO: Replace this with your three lines of recursive code
	}
	

	/* Swaps the (x,y and z) values of the i-th and j-th coordinates.*/
	static void swap(double[][] coords, int i, int j) {
		double[] temp = coords [i];
		coords [i] = coords[j];
		coords[j] = temp;
		// TODO: write your swap implementation here
	}
}
