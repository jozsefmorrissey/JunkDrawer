package PowerTrainParts;

import Cars.Car;
import Cars.CarPart;
import Enums.Location;
import Enums.PartCatagory;

public class AirFilter extends CarPart {
	public AirFilter(Car c, Location loc){
		initialize(c, PartCatagory.POWER_TRAIN, .5, "Air Filter", "AirFilter0", loc);
	}
}
