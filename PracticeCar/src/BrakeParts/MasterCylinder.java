package BrakeParts;

import Cars.*;
import Enums.Location;
import Enums.PartCatagory;

public class MasterCylinder extends ContainerPart {
	
	public MasterCylinder(Car c, Location loc){
		initialize(c, PartCatagory.BRAKES, 5, "MasterCylinder", "MasterCylinder0", loc);
		initialize(-1, -1, -1, .5);
	}
	
}
