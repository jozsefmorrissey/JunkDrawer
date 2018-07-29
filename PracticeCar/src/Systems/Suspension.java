package Systems;

import Cars.*;
import Enums.Location;
import SuspensionParts.*;

public class Suspension implements Interfaces.System{
	Rim rim;
	Strut strut;
	Shock shock;
	Tire tire;
	
	public Suspension(Car car, Location loc){
		rim = new Rim(car, loc);
		shock = new Shock(car, loc);
		strut = new Strut(car, loc);
		tire = new Tire(car, loc);
	}

	@Override
	public boolean operational() {
		return true;
	}
	@Override
	public boolean replace(CarPart oldPart, CarPart newPart) {
		
		if(rim == oldPart){
			rim = (Rim) newPart;
			return true;
		}
		if(strut == oldPart){
			strut = (Strut) newPart;
			return true;
		}
		if(shock == oldPart){
			shock = (Shock) newPart;
			return true;
		}
		if(tire == oldPart){
			tire = (Tire) newPart;
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
		rim.inspect();
		strut.inspect();
		shock.inspect();
		tire.inspect();		
	}
	
	
}
