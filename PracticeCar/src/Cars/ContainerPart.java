package Cars;

public abstract class ContainerPart extends CarPart implements Interfaces.Container {
	private double level = 100;
	private double capacity = 100;
	private double tolerance = 10;
	private double fillAccuracy = 5;
	private double lastFlush = 0;
	
	
	protected void initialize(double l, double c, double t, double fa){
		if(l >= 0)
			level = l;
		if(c >= 0)
			capacity = c;
		if(t >= 0)
			tolerance = t;
		if(fa >= 0)
			fillAccuracy = fa;
	}
	
	public synchronized void charge(double val){
		level += val;
		if(level > capacity + fillAccuracy)
			topOff();
	}
	
	public synchronized boolean drain(double val){
		level -= val;
		if(level > 0)
			return true;
		
		level = 0;
		return false;
	}

	public String checkLevel() {
		double percent = level/capacity;
		if(percent < capacity - tolerance / 100){
			return "level is low";
		}
		if(percent > capacity + tolerance / 100){
			return "over full";
		}
		return "good";
	}
	
	public double getPercent(){return level/capacity;}

	public double getCapacity() {
		return capacity;
	}

	public void topOff() {
		double error = ((Math.random() * fillAccuracy * 2) - fillAccuracy) / 100;
		
		level = capacity + error * capacity;	
	}

	public void replaceContents() {
		lastFlush = car.getMiles();
		topOff();
	}

	public double getLastFlush() {
		return lastFlush;
	}
}
