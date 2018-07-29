//UIUC CS125 FALL 2014 MP. File: GridCounting.java, CS125 Project: Challenge7-RecursiveKnight, Version: 2014-11-24T10:52:38-0600.886603621
//@author jtmorri2
public class GridCounting {
	/** Returns the total number of possible routes (paths) from
	 * (x,y) to (tx,ty).
	 * There are only three valid kinds of moves:
	 *  Increment x by one.
	 *  Increment x by two.
	 *  Increment y by one.
	 *  
	 *  Hint: You'll need to test two base cases.
	 */
	public static int count(int x,int y, int tx, int ty) {
		int countPath = 0;
		if(x>tx || y>ty)
			return countPath;
		if(x==tx && y==ty)
			countPath = 1;
		if(x+1<=tx)
			countPath += count(x+1, y, tx, ty);
		if(x+2<=tx)
			countPath += count(x+2, y, tx, ty);
		if(y+1<ty+1)
			countPath += count(x, y+1, tx, ty);
		
		return countPath;
	}
}
