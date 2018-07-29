package Systems;

import Cars.*;
import Connectors.Wire;
import ElectricalParts.*;
import Enums.Location;

public class Electrical extends ElectronicSystem implements Interfaces.System{
	private Alternator alternator;
	private Wire wire;
	
	public Electrical(Car car, Location loc){
		super(car, loc);
		alternator = new Alternator(car, Location.ENGINE_BAY_TOP);
		wire = new Wire(car, Location.BATTERY_TO_ALTERNATOR);
	}

	@Override
	public boolean operationLoop() {
		getBattery().charge(alternator.powerProduced());
		return true;
	}
	
	@Override
	public boolean operational() {
		boolean exists = getBattery() != null && alternator != null &&
				wire.getLocation() == Location.BATTERY_TO_ALTERNATOR;
		
		if(exists)
			return getBattery().operational() && alternator.operational() 
					&& wire.operational();
		
		return false;
	}
	@Override
	public boolean replace(CarPart oldPart, CarPart newPart) {
		
		if(alternator == oldPart){
			alternator = (Alternator) newPart;
			return true;
		}
		
		if(wire == oldPart){
			wire = (Wire) newPart;
			return true;	
		}
		
		return false;
	}
	
	@Override
	public void diagnostic() {
	}
	
	@Override
	public void inspect() {
		CarPart.inspect(alternator, alternator.getPartName());
		CarPart.inspect(wire, wire.getPartName());
	}
	
	public Electrical() {
		// TODO Auto-generated constructor stub
	}
	
	public Battery getBattery(){
		return getCar().getBattery();
	}
	
	public Wire getWire(){return wire;}
}
