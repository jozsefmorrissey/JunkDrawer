package Systems;

import java.util.Vector;
import Cars.*;
import Connectors.Wire;
import ElectricalParts.Battery;
import Enums.Location;
import IgnitionParts.*;
import Interfaces.Iterative;

public class Ignition extends ElectronicSystem implements Iterative{
	private Car c;
	private Starter starter;
	private Computer computer;
	private CarPartVector wires = new CarPartVector();
	private IgnitionSwitch ignSw;
	private Vector<Location> wireLocs = new Vector<Location>();
	
	public Ignition(Car car, Location loc){
		super(car, loc);
		c = car;
		this.starter = (Starter) new Starter(car, Location.ENGINE_BAY_BOTTOM);
		this.computer = (Computer) new Computer(car, Location.INTERIOR_FRONT_DRIVERS);
		this.ignSw = new IgnitionSwitch(car, Location.INTERIOR_FRONT_DRIVERS);
		
		wireLocs.addElement(Location.COMPUTER_TO_STARTER);
		wireLocs.addElement(Location.COMPUTER_TO_MOTOR);
		wireLocs.addElement(Location.COMPUTER_TO_IGNITION_SWITCH);

		wires.create(Wire.class.toString(), wireLocs, this);
	}
	
	public void create(String className, Location l){
		if(className == Wire.class.toString())
			wires.add(new Wire(c, Location.COMPUTER_TO_IGNITION_SWITCH));	
	}

	@Override
	public boolean operational() {
		boolean correctWires = wires.findLocations(wireLocs);
		
		return computer.operational() && starter.operational() && 
				wires.allOperational() && correctWires;
	}


	@Override
	public boolean replace(CarPart oldPart, CarPart newPart) {
		if(starter == oldPart){
			starter = (Starter) newPart;
			return true;
		}
		if(computer == oldPart){
			computer = (Computer) newPart;
			return true;
		}
		if(wires.replace(oldPart, newPart)){
			return true;
		}

		return false;
	}


	@Override
	public void diagnostic() {
		computer.diagnostic();
		starter.diagnostic();		
	}


	@Override
	public void inspect() {
		CarPart.inspect(computer, "Computer");
		CarPart.inspect(starter, "Starter");
		CarPart.inspect(ignSw, "Ignition Switch");
	}

	@Override
	public boolean operationLoop() {
		return ignSw.turnOn() && computer.turnOn();
	}
	
	public void start(){ignSw.start();}

	@Override
	public Battery getBattery() {
		return c.getBattery();
	}
	
	@Override
	public boolean turnOff(){
		return super.turnOff() && ignSw.turnOff() 
				&& computer.turnOff();
	}
}
