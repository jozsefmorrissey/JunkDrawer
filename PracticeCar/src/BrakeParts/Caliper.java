package BrakeParts;

import Cars.Car;
import Cars.CarPart;
import Enums.Location;
import Enums.PartCatagory;

public class Caliper extends CarPart {

	public Caliper(Car c, Location loc){
		initialize(c, PartCatagory.BRAKES, 5, "Caliper", "Caliper0", loc);
	}

	@Override
	public boolean operational() {
		return car.getMasCyl().getPercent() > 20;
	}	
}
