package FuelSystemParts;

import Cars.*;
import Connectors.Wire;
import Enums.*;

public class Pump extends ElectronicPart{
	Wire power;
	
	public Pump(Car c, Location loc){
		super(c, loc);
		initialize(c, PartCatagory.FUEL, 1.5, "Fuel Pump", "FuelPump0", loc);
		power = new Wire(c, Location.BATTERY_TO_FUEL_PUMP);
	}

	@Override
	public boolean operational() {
		return power != null && power.operational() && getBattery().operational() 
				&& power.getLocation() == Location.BATTERY_TO_FUEL_PUMP;
	}
	
	@Override
	public boolean operationLoop() {
		getBattery().drain(.000002);
		return false;
	}
}
