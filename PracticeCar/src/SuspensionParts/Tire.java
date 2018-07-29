package SuspensionParts;

import Cars.Car;
import Cars.ContainerPart;
import Enums.Location;
import Enums.PartCatagory;

public class Tire extends ContainerPart{
	double diameter = 15;
	
	public Tire(Car c, Location loc){
		initialize(c, PartCatagory.SUSPENSION, 15, "Tire", "Tire0", loc);
		initialize(42.5, 42.5, 2, 0.05);
	}
	
	public double getDiameter(){return diameter;}
}
