package BrakeParts;

import Cars.Car;
import Cars.CarPart;
import Enums.Location;
import Enums.PartCatagory;

public class Rotor extends CarPart {
	double thickness = 2.5;
	double initThickness = 2.5;
	
	public Rotor(Car c, Location loc){
		initialize(c, PartCatagory.BRAKES, 5, "Rotors", "Rotors0", loc);
	}

	@Override
	public void inspect() {
		System.out.println("Rotors are " + (1 - thickness / initThickness) + "% worn");
	}

	@Override
	public boolean operational() {
		return true;
	}	
	
}
