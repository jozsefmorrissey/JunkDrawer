package SuspensionParts;

import Cars.Car;
import Cars.CarPart;
import Enums.Location;
import Enums.PartCatagory;

public class Strut extends CarPart {
		
	public Strut(Car c, Location loc){
		initialize(c, PartCatagory.SUSPENSION, 15, "Strut", "Strut", loc);
	}
}
