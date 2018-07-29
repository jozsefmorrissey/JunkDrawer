package FuelSystemParts;

import Cars.*;
import Enums.*;

public class Filter extends CarPart {

	public Filter(Car c, Location loc){
		initialize(c, PartCatagory.FUEL, .5, "Fuel Filter", "FuelFilter0", loc);
	}

}
