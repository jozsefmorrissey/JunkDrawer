package PowerTrainParts;

import Cars.*;
import Enums.Location;
import Enums.PartCatagory;

public class Motor extends ElectronicPart implements Interfaces.Container{
	
	private double level = 5;
	private double capacity = 15;
	private double tolerance = 10;
	private double fillAccuracy = 5;
	private double lastFlush = 0;

	public Motor(Car c, Location loc){
		super(c,loc);
		initialize(c, PartCatagory.POWER_TRAIN, 500, "Motor", "Motor0", loc);
	}
	
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

		
	@Override
	public boolean operational() {
		// TODO Auto-generated method stub
		return super.operational() && car.getFuel().operational();
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

	public double getCapacity() {
		return capacity;
	}

	public void topOff() {
		double error = (Math.random() * fillAccuracy * 2 - fillAccuracy) / 100;
		
		level = capacity + error * capacity;	
	}

	public void replaceContents() {
		lastFlush = car.getMiles();
		topOff();
	}

	public double getLastFlush() {
		return lastFlush;
	}


	@Override
	public boolean operationLoop() {
		if(!car.getTank().drain(0.0001))
			turnOff();			
		return true;
	}
}
