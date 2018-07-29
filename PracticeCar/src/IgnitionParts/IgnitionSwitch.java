package IgnitionParts;

import java.util.Vector;

import Cars.Car;
import Cars.CarPartVector;
import Cars.ElectronicPart;
import Connectors.Wire;
import Enums.Location;
import Enums.PartCatagory;

public class IgnitionSwitch extends ElectronicPart implements Interfaces.Iterative {
	private CarPartVector wires = new CarPartVector();
	private Vector<Location> wireLocs = new Vector<Location>();
	private Wire toStarter = new Wire(car, Location.IGNITION_TO_STARTER);
	private boolean start = false;
	private int turnOvers = 0;
	private int turnOversToStart = 30;
	
	public IgnitionSwitch(Car c, Location loc){
		super(c, loc);
		initialize(c, PartCatagory.IGNITION, .25, "Ignition Switch", "IgnitionSwitch0", loc);
		
		wireLocs.addElement(Location.BATTERY_TO_IGNITION);
		wireLocs.addElement(Location.IGNITION_TO_COMPUTER);
		
		wires.create(Wire.class.toString(), wireLocs, this);
	}

	public void create(String className, Location l){
		if(className == Wire.class.toString())
			wires.add(new Wire(car, Location.COMPUTER_TO_IGNITION_SWITCH));	
	}
	
	@Override
	public boolean operational() {
		return wires.findLocations(wireLocs);
	}

	@Override
	public boolean operationLoop() {
		
		if(car.getMotor().isRunning()){
			return true;
		}
		
		if(!this.operational()){
			System.out.println("Shit broke.");
			return false;
		}
		
		if(this.start && this.turnOvers == 0 && 
				toStarter.getLocation() == Location.IGNITION_TO_STARTER){
			car.getStarter().turnOn();
		}
		if(car.getStarter().isRunning() && toStarter.operational() && this.start
				&& toStarter.getLocation() == Location.IGNITION_TO_STARTER){
			this.turnOvers++;
			if(turnOvers > this.turnOversToStart && car.getMotor().operational()){
				car.getMotor().turnOn();
				car.getElectrical().turnOn();
				car.getStarter().turnOff();
				this.turnOvers = 0;
				this.start = false;
			}
		}
		else
			this.turnOvers = 0;

		
		return true;
	}
	
	public void start(){
		start = true;
	}
	
	@Override
	public boolean turnOff(){
		return super.turnOff() && car.getElectrical().turnOff();
	}

}
