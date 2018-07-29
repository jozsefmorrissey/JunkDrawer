package Systems;

import java.util.Vector;

import Cars.*;
import Connectors.Line;
import Enums.*;
import FuelSystemParts.*;

public class Fuel implements Interfaces.System, Interfaces.Iterative{
	private Car car;
	private Pump fuelPump;
	private CarPartVector fuelFilters = new CarPartVector();;
	private CarPartVector fuelLines = new CarPartVector();
	private Vector<Location> filterLocs = new Vector<Location>();
	private Vector<Location> lineLocs = new Vector<Location>();
	
	public Fuel(Car c, Location loc){
		car = c;
		fuelPump = new Pump(car, Location.UNDERCARRIAGE_REAR_DRIVERS);
		
		filterLocs.addElement(Location.TANK_TO_MOTOR);
		filterLocs.addElement(Location.MOTOR);
		fuelLines.create(Filter.class.toString(), filterLocs, this);
		
		lineLocs.addElement(Location.TANK_TO_FILTER);
		lineLocs.addElement(Location.FILTER_TO_PUMP);
		lineLocs.addElement(Location.PUMP_TO_MOTOR);
		fuelLines.create(Line.class.toString(), lineLocs, this);
	}
	
	public boolean operational(){
		boolean exists = car.getTank() != null && fuelPump != null;
		
		if(exists){
			boolean functional = car.getTank().operational() && fuelPump.operational() && 
									fuelFilters.findLocations(filterLocs) &&
									fuelFilters.allOperational() && 
									fuelLines.allOperational() && 
									fuelLines.findLocations(lineLocs);
			
			return functional;
		}

		return exists;
	}

	@Override
	public void diagnostic() {
		if(fuelPump.turnOn())
			System.out.println("System seems to be operational");
		else
			System.out.println("Fuel Pump is not turing on.");
	}

	@Override
	public void inspect() {
		if(fuelPump != null)
			fuelPump.inspect();
		
		fuelFilters.inspectAll();
		fuelLines.inspectAll();
	}

	@Override
	public boolean replace(CarPart oldPart, CarPart newPart) {
	
		if(oldPart == fuelPump){
			fuelPump = (Pump)newPart;
			return true;
		}
			
		if(fuelFilters.replace(oldPart, newPart))
			return true;
		
		if(fuelLines.replace(oldPart, newPart))
			return true;
						
		return false;
	}

	@Override
	public void create(String className, Location locs) {
		if(Filter.class.toString() == className)
			fuelFilters.addElement(new Filter(car, locs));
		if(Filter.class.toString() == className)
			fuelFilters.addElement(new Filter(car, locs));		
	}
}
