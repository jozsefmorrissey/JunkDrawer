package Cars;

import ElectricalParts.Battery;
import Enums.Location;
import Enums.Status;

public abstract class ElectronicPart extends CarPart implements Interfaces.Electronic{
	private boolean exit = false;
	private double sleepTime = 100;
	private boolean running = false;
	private Thread thread;
	

	
	public ElectronicPart(Car c, Location loc){
	    WorkerThread runner = new WorkerThread(this);
		thread = new Thread(runner);
	}	
	
	public boolean turnOn(){
		if(car.getBattery() == null || car.getBattery().getStatus() == Status.BROKEN)
			return false;

		try{
			thread.start();
		} catch (IllegalThreadStateException e){
			
		}
			running = true;
	    
		return true;
	}
	
	public boolean turnOff(){
		exit = true;
		running = false;
		return true;
	}
	
	public abstract boolean operationLoop();	
	
	@Override
	public boolean run() {
		while(!exit){
			if(!this.operational() || !operationLoop())
				this.turnOff();
			ElectronicSystem.sleep(sleepTime);
		}
		running = false;
		exit = false;
		return false;
	}
	
	public boolean keepGoing(){return !exit;}
	
	public static Boolean sleep(double milSec){
		try {
			Thread.sleep((long) milSec);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean setSleepTime(double st){
		if(st > 0){
			sleepTime = st;
			return true;
		}
		return false;
	}
	
	public Battery getBattery(){return car.getBattery();}

	public boolean isRunning() {
		return running;
	}
}
