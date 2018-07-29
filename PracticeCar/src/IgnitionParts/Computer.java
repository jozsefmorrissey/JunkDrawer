package IgnitionParts;

import Cars.*;
import Connectors.Wire;
import ElectricalParts.Battery;
import Enums.Location;
import Enums.PartCatagory;

public class Computer extends ElectronicPart{
	private Wire power;
	private Battery battery;
	
	public Computer(Car c, Location loc){
		super(c, loc);
		initialize(c, PartCatagory.IGNITION, 5, "Computer", "Computer0", loc);
		power = new Connectors.Wire(c, Location.BATTERY_TO_COMPUTER);
		battery = c.getBattery();
	}

	@Override
	public boolean operational() {		
		return power != null && power.operational() && battery.operational()
				&& power.getLocation() == Location.BATTERY_TO_COMPUTER;
	}

	@Override
	public boolean operationLoop(){
		battery.drain(.00005);
		return true;
	}
}
