//UIUC CS125 FALL 2014 MP. File: RobotLink.java, CS125 Project: Challenge7-RecursiveKnight, Version: 2014-11-24T10:52:38-0600.886603621
// @author jtmorri2
public class RobotLink {

	private RobotLink next; 	
	private final Robot robot;

	public Robot getRobot() {
		return robot;
	}
	/** Constructs this link.
	 * @param next ; the next item in the chain (null, if there are no more items).
	 * @param robot ; a single robot (never null).
	 */
	public RobotLink(RobotLink next,Robot robot) {
		this.robot = robot;
		this.next = next;
	}

	/**
	 * Returns the number of entries in the linked list.
	 * @return number of entries.
	 */
	public int count() {
		if (next == null)
			return 1; // BASE CASE; no more recursion required!

		// Recursive case:
		return 1 + next.count();
	}
	/**
	 * Counts the number of flying robots in the linked list.
	 * Hint: robot.isFlying is useful here.
	 */
	public int countFlyingRobots() {
		if(this.robot.isFlying()&&next!=null)
			return 1 + next.countFlyingRobots();
		else if(next!=null)
			return next.countFlyingRobots();
		if(this.robot.isFlying())
			return 1;
		else
			return 0;
	}
	/**
	 * Counts the number of flying robots upto and excluding a sad robot.
	 * (i.e. do not count the sad robot if it is flying).
	 * If there are no sad robots this function will return the same value as countFlyingRobots().
	 * Hint: robot.isHappy() is useful.
	 */
	public int countFlyingRobotsBeforeSadRobot() {
		if(!this.robot.isHappy())
			return 0;
		if(this.robot.isFlying()&&next!=null)
			return 1 + next.countFlyingRobotsBeforeSadRobot();
		else if(next!=null)
			return next.countFlyingRobotsBeforeSadRobot();
		if(this.robot.isFlying())
			return 1;
		else
			return 0;
	}
	/** Creates a new LinkedList entry at the end of this linked list.
	 * Recursively finds the last entry then adds a new link to the end.
	 * @param robot - the robot value of the new last link
	 */
	public void append(Robot robot) {
		if(this.next!=null)
			next.append(robot);
		else{
			RobotLink temp = new RobotLink(next, robot);
			next = temp;
		}
	}
	/**
	 * Returns the first flying unhappy robot, or null
	 * if there are not robots that are flying and unhappy.
	 * @return
	 */
	public Robot getFirstFlyingUnhappyRobot() {
		if(this.robot.isFlying()&&!this.robot.isHappy())
			return this.robot;
		else if (this.next !=null)
			return next.getFirstFlyingUnhappyRobot();
		
		return null;
	}
	/**
	 * Returns the last flying unhappy robotn the linked list, or null
	 * if there are not robots that are flying and unhappy.
	 * @return
	 */
	public Robot getLastFlyingUnhappyRobot() {
		Robot temp = null;
		if(this.next!=null)
			temp = next.getLastFlyingUnhappyRobot();
		if(this.robot.isFlying()&&!this.robot.isHappy() && temp == null)
			return this.robot;
		
		return temp;
	}
	/**
	 * Returns a reference to the happy most distant explorer.
	 * Returns null if there are no happy robots
	 * @return reference to the most distant happy robot
	 */
	public Robot findHappyRobotFurthestFromHome() {
		Robot max = null;
		if(next!=null)
			max = next.findHappyRobotFurthestFromHome();
		if(max!=null){
			if(this.robot.isHappy() && max.getDistanceFromHome()<this.robot.getDistanceFromHome())
				max = this.robot;
		}
		else if(this.robot.isHappy())
			return this.robot;
		return max;
	}
	/**
	 * Returns true if linked list contains the robot.
	 * Hint: Use robot.equals(other).
	 * @param robot
	 * @return
	 */
	public boolean contains(Robot other) {
		if(this.robot.equals(other))
			return true;
		else if(next!=null)
			return next.contains(other);
		
		return false;
	}

	
}
