package FuelSystemParts;

import Cars.*;
import Enums.*;

public class Tank extends ContainerPart implements Interfaces.System {
	
	public Tank(Car c, Location loc){
		initialize(c, PartCatagory.FUEL, 12, "Fuel Tank", "FuelTank0", loc);
		initialize(12, 12, -1, 10);
	}
	
	@Override
	public void inspect(){
		System.out.println("Fuel tank is " + checkLevel() + " full.");
	}

	@Override
	public String checkLevel() {		
		return getPercent() * 100 + "%";
	}

	@Override
	public boolean operational() {
		return getPercent() > .005;
	}
}
