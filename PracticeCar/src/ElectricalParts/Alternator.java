package ElectricalParts;

import Cars.Car;
import Cars.CarPart;
import Enums.Location;
import Enums.PartCatagory;

public class Alternator extends CarPart {
	public Alternator(Car c, Location loc){
		initialize(c, PartCatagory.ELECTRICAL, 20, "Alternator", "Alternator0", loc);
	}
	
	public double powerProduced(){
		if(getCar().getMotor().isRunning())
			return .005;
		return 0;
	}

}
