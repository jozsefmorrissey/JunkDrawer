package DriveTrainParts;

import Cars.*;
import Enums.Location;
import Enums.PartCatagory;

public class DriveAxel extends CarPart {
	public DriveAxel(Car c, Location loc){
		initialize(c, PartCatagory.DRIVE_TRAIN, 20, "DriveAxel", "DriveAxel0", loc);
	}
}
