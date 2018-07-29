package ElectricalParts;

import Cars.*;
import Enums.*;

public class Battery extends ContainerPart {
		
	@Override
	public boolean operational() {
		// TODO Auto-generated method stub
		return super.getPercent() > 0;
	}


	public Battery(Car c, Location loc){
		initialize(c, PartCatagory.ELECTRICAL, 20, "Battery", "Battery0", loc);
		initialize(-1, -1, -1, .2);
	}

	
	@Override
	public void diagnostic() {
		if(getPercent() == 0)
			System.out.println("Your battery is dead.");
		else
			CarPart.printNoData();
	}
	
	public void inspect(){
		if(getPercent() < 50){
			System.out.println("Your battery needs charged it at " + getPercent()*100 + "%.");
		}
		else 
			CarPart.printNoData();
	}
}
