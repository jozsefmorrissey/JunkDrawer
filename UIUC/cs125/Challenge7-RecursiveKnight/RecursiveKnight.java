//UIUC CS125 FALL 2014 MP. File: RecursiveKnight.java, CS125 Project: Challenge7-RecursiveKnight, Version: 2014-11-24T10:52:38-0600.886603621
//@author jtmorri2
public class RecursiveKnight {

	/**
	 * This method recursively determines which 
	 * board positions the knight can reach in the
	 * next few moves.
	 * 
	 * Base cases: Return immediately if 
	 * 1) x or y are invalid
	 * i.e. visited[x][y] would cause IndexOutOfBounds exceptions.
	 * 
	 * or, 2) visited[x][y] is true and step is a positive integer
	 * i.e. The current coordinates do not represent a valid square that we can hop to.
	 * 
	 * or, 3) steps[x][y] already has a positive integer, which is less than the parameter value. 
	 * i.e. There is a shorter path to this point than the one we are considering.
	 *
	 *Recursive case:
	 *Update steps[x][y]
	 *Recursively call explore() using the eight possible knight moves
	 * {1,2},{-1,-2},{2,1} etc (Work it out!)
	 * 
	 * The recursive call will use a different step value
	 * because it will be evaluating the next move.
	 * 
	 * The 'visited' array is unchanged by this method:.
	 * Assume visited and steps are already initialized to a square array and are the same size.
	 */
	public static void explore(boolean[][] visited, int x, int y, int[][] steps, int step) {
	//Todo: Implement RecursiveKnight.explore
		boolean explored = false;
		if(x>7 || x<0 || y<0 || y>7)
			return;
		//if(step == 11)
			//explored = false;
		
		if(!visited[x][y]){
			visited[x][y]=true;
			steps[x][y]=step;
		}
		//else if(steps[x][y]>step)
			//steps[x][y]=step;
		
		if(x<6 && y<7 && steps[x+2][y+1]>step+1){
			explored = true;
			visited[x+2][y+1] = true;
			steps[x+2][y+1]=step+1;
		}
		if(x>1 && y>0 && steps[x-2][y-1]>step+1){
			explored = true;
			visited[x-2][y-1]=true;
			steps[x-2][y-1]=step+1;
		}	
		if(x>0 && y>1 && steps[x-1][y-2]>step+1){
			explored = true;
			visited[x-1][y-2]=true;
			steps[x-1][y-2] = step+1;
		}
		if(x<7 && y<6 && (steps[x+1][y+2]>step+1 || !visited[x+1][y+2])){
			explored = true;
			visited[x+1][y+2]=true;
			steps[x+1][y+2]=step+1;
		}
		if(x+2<8 && y-1>-1 && (steps[x+2][y-1]>step+1 || !visited[x+2][y-1])){
			explored = true;
			visited[x+2][y-1]=true;
			steps[x+2][y-1]=step+1;
		}
		if(x-1>-1 && y+2<8 && (steps[x-1][y+2]>step+1 || !visited[x-1][y+2])){
			explored = true;
			visited[x-1][y+2]=true;
			steps[x-1][y+2]=step+1;
		}
		if(x-2>-1 && y+1<8 && (steps[x-2][y+1]>step+1 || !visited[x-2][y+1])){
			explored = true;
			visited[x-2][y+1]=true;
			steps[x-2][y+1]=step+1;
		}
		if(x+1<8 && y-2>=0 && (steps[x+1][y-2]>step+1 || !visited[x+1][y-2])){
			explored = true;
			visited[x+1][y-2]=true;
			steps[x+1][y-2]=step+1;
		}
		if(explored){
			explore(visited, x+2, y+1, steps, step+1);
			explore(visited, x-2, y-1, steps, step+1);
			explore(visited, x-1, y-2, steps, step+1);
			explore(visited, x+1, y+2, steps, step+1);
			explore(visited, x+2, y-1, steps, step+1);
			explore(visited, x-1, y+2, steps, step+1);
			explore(visited, x-2, y+1, steps, step+1);
			explore(visited, x+1, y-2, steps, step+1);
		}
	}	
}
