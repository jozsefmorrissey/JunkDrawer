package DriveTrainParts;

import Cars.*;
import Enums.Location;
import Enums.PartCatagory;

public class DriveShaft extends CarPart {
	public DriveShaft(Car c, Location loc){
		initialize(c, PartCatagory.DRIVE_TRAIN, 20, "DriveShaft", "DriveShaft0", loc);
	}
}
