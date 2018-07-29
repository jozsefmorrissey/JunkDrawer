package Systems;

import Cars.*;
import Connectors.Line;
import Enums.Location;
import BrakeParts.*;

public class Brakes implements Interfaces.System{
	Caliper caliper;
	Line line;
	Pads padPairs;
	Rotor rotor;
	MasterCylinder masterCylinder;
	
	public Brakes(Car car, Location loc){
		masterCylinder = car.getMasCyl();
		caliper = new Caliper(car, loc);
		line = new Line(car, loc.addBrake(Location.MASTER));
		padPairs = new Pads(car, loc);
		rotor = new Rotor(car, loc);
	}
	
	
	@Override
	public boolean operational() {
		return caliper.operational() && line.operational() && masterCylinder.getPercent() > .2;
	}
	
	
	@Override
	public boolean replace(CarPart oldPart, CarPart newPart) {
		if(caliper == oldPart){
			caliper = (Caliper) newPart;
			return true;
		}
		if(line == oldPart){
			line = (Line) newPart;
			return true;
		}
		if(padPairs == oldPart){
			padPairs = (Pads) newPart;
			return true;
		}
		if(rotor == oldPart){
			rotor = (Rotor) newPart;
			return true;
		}
		if(masterCylinder == oldPart){
			masterCylinder = (MasterCylinder) newPart;
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public void diagnostic() {
		CarPart.printNoData();
	}
	
	
	@Override
	public void inspect() {
		caliper.inspect();
		line.inspect();
		padPairs.inspect();
		rotor.inspect();
		masterCylinder.inspect();
	}

}
