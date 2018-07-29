package SuspensionParts;

import Cars.Car;
import Cars.CarPart;
import Enums.Location;
import Enums.PartCatagory;

public class Shock extends CarPart {

	public Shock(Car c, Location loc){
		initialize(c, PartCatagory.SUSPENSION, 15, "Shock", "Shock0", loc);
	}
}
