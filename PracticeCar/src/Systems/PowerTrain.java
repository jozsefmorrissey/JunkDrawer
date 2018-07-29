package Systems;

import Cars.*;
import Enums.Location;
import PowerTrainParts.*;

public class PowerTrain implements Interfaces.System{
	Car car;

	AirFilter airFilter;
	OilFilter oilFilter;
	
	public PowerTrain(Car c, Location loc){
		oilFilter = new OilFilter(car, Location.ENGINE_BAY_BOTTOM);
		airFilter = new AirFilter(car, Location.ENGINE_BAY_TOP);
	}
	

	
	@Override
	public boolean operational() {
		return car.getMotor() != null && car.getMotor().operational() && 
				airFilter.operational() && oilFilter.operational();
	}

	@Override
	public void diagnostic() {
		CarPart.printNoData();
	}

	@Override
	public void inspect() {
		CarPart.inspect(airFilter, "Air filter");
		CarPart.inspect(oilFilter, "Oil filter");			
	}

	@Override
	public boolean replace(CarPart oldPart, CarPart newPart) {
		if(oldPart == airFilter){
			airFilter = (AirFilter) newPart;
			return true;
		}
		if(oldPart == oilFilter){
			oilFilter = (OilFilter) newPart;
			return true;
		}
		
		
		return false;
	}

}
