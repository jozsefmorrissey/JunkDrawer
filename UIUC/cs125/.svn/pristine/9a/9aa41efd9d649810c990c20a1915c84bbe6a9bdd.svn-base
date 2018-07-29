//UIUC CS125 FALL 2014 MP. File: MazeRunner.java, CS125 Project: Challenge7-RecursiveKnight, Version: 2014-11-24T10:52:38-0600.886603621
//@author jtmorri2
public class MazeRunner {

	private int x, y;
	private static int markx, marky;
	private static boolean deadEnd = true;

	/** Initializes the MazeRunner with the x,y values */
	public MazeRunner(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}


	/** Moves the runner one unit. No error checking is performed.
	 * 'N':go North (increment y), S:decrement y, E(increment x), W(decrement x)
	 * character values other than N,S,E or W are ignored.
	 */
	void moveOne(char dir) {
		if(dir == 'N')
			this.y++;
		if(dir == 'S')
			this.y--;
		if(dir == 'E')
			this.x++;
		if(dir == 'W')
			this.x--;
//		 TODO: Implement moveOne
	}
	/** Returns true if this maze runner is on the same (x,y) square
	 * as the parameter. Assumes that the parameter is non-null.
	 */
	public boolean caught(MazeRunner other) {
		if(this.x == other.x && this.y == other.y)
			return true;
		return false; // TODO: Implement caught
	}

	/**
	 * Uses recursion to find index of the shortest string.
	 * Null strings are treated as infinitely long.
	 * Implementation notes:
	 * The base case if lo == hi.
	 * Use safeStringLength(paths[xxx]) to determine the string length.
	 * Invoke recursion to test the remaining paths (lo +1)
	 */
	static int findShortestString(String[] paths, int lo, int hi) {
		int shrt = hi;
		if(paths[lo] == null)
			paths[lo] = "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk";
		if(lo<hi)
			shrt = findShortestString(paths, lo+1, hi);
		if(paths[shrt].length()>paths[lo].length())
			shrt = lo;
		
		return shrt; // TODO: findShortestString
	}

	/** Returns the length of the string or Integer.MAX_VALUE
	 * if the string is null.
	 * @param s
	 * @return
	 */
	static int safeStringLength(String s) {
		if(s == null)
			return Integer.MAX_VALUE;

		return s.length();
	}


	/* Returns a string representation of the shortest path between
	 * (x,y) and (tx,ty). e.g. a result of "NNEE"
	 * means to travel from (x,y) -> (tx,ty) go North twice, then East twice.
	 * blocked is a square boolean grid of points that cannot be used.
	 * If(x,y) are invalid coords (outside of the grid array) this method returns null.
	 * If(x,y) is on a blocked square, this method returns null. Otherwise,
	 * If(x,y) are already the same as the target position, returns an empty string.
	 * If there is no path between (x,y) and (tx,ty) the method returns null.
	 * 
	 * Implementation notes:
	 * Use the statements above for the base cases.
	 * For the recursion part:
	 * 1. Set the current position to blocked (so that the recursive method does not
	 * attempt to re-use this square again)
	 * 2. Collect all paths from the NSEW neighbors
	 * 3. Reset the current blocked position to false.
	 * 4. Use findShortestString to determine the shortest path
	 * 5. If its non-null then PREPEND the compass direction of that neighbor's path.
	 * e.g. if the Northern neighbor returned "EWWS" 
	 * the East neighbor returned "NWWWWWWWSEEEESS" and W and S Neighbor return null
	 * then return "N" + "EWWS"
	 * Otherwise, just return null as none of the neighbors found a path.
	 */
	public static String shortestPath(int x, int y, int tX, int tY, boolean blocked[][]) {
		// TODO: BASE CASES HERE
				if(deadEnd){
					markx =x;
					marky = y;
					deadEnd = false;
				}
				String pathShort = null;
				String check = "";
				if(x<0 || y<0 || tX<0 || tY<0 || x>blocked.length || y>blocked[x].length)
					return null;
				if(x == tX && y == tY && !blocked[x][y])
					return "";
				if((y+1>blocked[x].length-1 ||( y+1<blocked[x].length && y+1>-1 && blocked[x][y+1])) && (y-1<0 || ( y-1<blocked[x].length && y-1>-1 && blocked[x][y-1])) && (x+1>blocked.length-1 || (x+1<blocked.length && x+1>0 && blocked[x+1][y])) && (x-1<0 || (x-1<blocked.length && x-1>0 && blocked[x-1][y])))
					return null;
				
				blocked[x][y] = true;
				if(y+1<blocked[x].length && !blocked[x][y+1]){
					check = "N" + shortestPath(x, y+1, tX, tY, blocked);
					if((check != null && !check.contains("null")) && (pathShort == null || check.length()<pathShort.length()))
						pathShort = check;
				}
				if(y-1>-1 && !blocked[x][y-1]){
					check = "S" + shortestPath(x, y-1, tX, tY, blocked);
					if((check != null && !check.contains("null")) && (pathShort == null || check.length()<pathShort.length()))
						pathShort = check;
				}
				if(x+1<blocked.length && !blocked[x+1][y]){
					check = "E" + shortestPath(x+1, y, tX, tY, blocked);
					if((check != null && !check.contains("null")) && (pathShort == null || check.length()<pathShort.length()))
						pathShort = check;
				}
				if(x-1>-1 && !blocked[x-1][y]){
					check = "W" + shortestPath(x-1, y, tX, tY, blocked);
					if((check != null && !check.contains("null")) && (pathShort == null || check.length()<pathShort.length()))
						pathShort = check;
				}
				
				
				
				
					
				//String[] paths = { 
					//TODO: COLLECT RECURSIVE RESULTS HERE
				//};
				blocked[x][y] = false;

				// TODO: Use findShortestString on paths
				// TODO: Return correct string with Compass direction prepended (or null)
				if(x == markx && y == marky){
				deadEnd = true;
				System.out.println(pathShort);
				}
	
				return pathShort;
	}

	/** Moves the runner towards the target position, if the
	 * shortest path can be found between the current and target position.
	 * Implementation notes: calls shortestPath, 
	 * if result is not null then send the first char to moveOne()
	 * Hint: watch out for the empty string when target = current position...
	 */
	public void chase(boolean maze[][], int targetX, int targetY) {
		// TODO: Implement chase
		// Use shortestPath, string.charAt,  moveOne
	}

}
