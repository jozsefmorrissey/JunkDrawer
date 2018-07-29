package Connectors;

import Cars.*;
import Enums.Location;
import Enums.PartCatagory;

public class Wire extends CarPart{
	public Wire(Car c, Location loc){
		initialize(c, PartCatagory.IGNITION, 5, "Wire", "Wire0", loc);
	}
}
