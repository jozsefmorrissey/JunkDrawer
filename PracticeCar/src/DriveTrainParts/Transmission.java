package DriveTrainParts;

import Cars.*;
import Enums.Location;
import Enums.PartCatagory;

public class Transmission extends ContainerPart {
	
	public Transmission(Car c, Location loc){
		initialize(c, PartCatagory.DRIVE_TRAIN, 20, "Transmission", "Transmission0", loc);
		initialize(3, 3, -1, -1);
	}
}
