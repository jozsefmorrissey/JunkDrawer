package BrakeParts;

import Cars.*;
import Enums.Location;
import Enums.PartCatagory;

public class Pads extends CarPart {
	private double thickness = 2.5;
	private double initThickness = 2.5;
	
	public Pads(Car c, Location loc){
		initialize(c, PartCatagory.BRAKES, 5, "Pads", "Pad0", loc);
	}

	@Override
	public void inspect() {
		System.out.println("Pards are " + (1 - thickness / initThickness) + "% worn");
	}

	@Override
	public boolean operational() {
		return true;
	}	
}
