package PowerTrainParts;

import Cars.*;
import Enums.Location;
import Enums.PartCatagory;

public class OilFilter extends CarPart {
	public OilFilter(Car c, Location loc){
		initialize(c, PartCatagory.POWER_TRAIN, .5, "Oil Filter", "OilFilter0", loc);
	}
}
