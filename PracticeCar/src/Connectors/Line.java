package Connectors;

import Cars.Car;
import Enums.*;

public class Line extends Cars.CarPart {

	public Line(Car c, Location loc){
		initialize(c, PartCatagory.FUEL, .5, "Fuel Line", "Fuel Line0", loc);
	}

}
