package IgnitionParts;

import Cars.*;
import Connectors.Wire;
import ElectricalParts.Battery;
import Enums.Location;
import Enums.PartCatagory;

public class Starter extends ElectronicPart{
	private Wire power;
	private Battery battery;
	
	public Starter(Car c, Location loc){
		super(c, loc);
		initialize(c, PartCatagory.IGNITION, 5, "Computer", "Computer0", loc);
		battery = car.getBattery();
		power = new Wire(c, Location.BATTERY_TO_STARTER);
	}
	
	@Override
	public boolean operational() {
		return power != null && power.operational() && battery.operational()
				&& power.getLocation() == Location.BATTERY_TO_STARTER;
	}

	@Override
	public boolean operationLoop(){
		battery.drain(.01);
		return true;
	}
}
