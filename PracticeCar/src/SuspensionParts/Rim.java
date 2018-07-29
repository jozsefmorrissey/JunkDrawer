package SuspensionParts;

import Cars.Car;
import Cars.CarPart;
import Enums.Location;
import Enums.PartCatagory;

public class Rim extends CarPart {
	double diameter = 15;

	public Rim(Car c, Location loc){
		initialize(c, PartCatagory.SUSPENSION, 15, "Rim", "Rim0", loc);
	}
	
	public double getDiameter(){return diameter;}
}
