package Systems;

import Cars.*;
import DriveTrainParts.*;
import Enums.Location;

public class DriveTrain implements Interfaces.System {

	Transmission trans;
	DriveAxel rAxel;
	DriveShaft rDriveS;
	
	public DriveTrain(Car c, Location loc){
		trans = new Transmission(c, Location.UNDERCARRIAGE_CENTER);
		rAxel = new DriveAxel(c, Location.UNDERCARRIAGE_REAR);
		rDriveS = new DriveShaft(c, Location.UNDERCARRIAGE_REAR);
		
	}
	
	@Override
	public boolean operational() {
		return trans.operational() && rAxel.operational() && rDriveS.operational();
	}
	
	@Override
	public boolean replace(CarPart oldPart, CarPart newPart) {
		if(trans == oldPart){
			trans = (Transmission) newPart;
			return true;
		}
		
		if(rAxel == oldPart){
			rAxel = (DriveAxel) newPart;
			return true;
		}
		
		if(rDriveS == oldPart){
			rDriveS = (DriveShaft) newPart;
			return true;
		}
		
		return false;
	}
	@Override
	public void diagnostic() {
		CarPart.printNoData();
	}
	
	@Override
	public void inspect() {
		CarPart.inspect(trans, "Transmission");
		CarPart.inspect(rAxel, "Rear Axel");
		CarPart.inspect(rDriveS, "Rear drive shaft");
	}	
}
