package Cars;

import java.util.Vector;

import Interfaces.Iterative;

public class CarPartVector extends Vector<CarPart>{

	private static final long serialVersionUID = 1L;

	public boolean replace(CarPart eleOld, CarPart eleNew){
		if(this.remove(eleOld)){
			this.addElement(eleNew);
			return true;
		}
		return false;		
	}
	
	public void inspectAll(){
		for(int index = 0; index < this.size(); index++){
			this.elementAt(index).inspect();
		}
	}

	public boolean allOperational(){
		boolean isOp = true;
		for(int index = 0; index < this.size(); index++){
			isOp = isOp && this.elementAt(index).operational();
		}
		return isOp;
	}

	
	public boolean oneOperational(){
		boolean isOp = true;
		for(int index = 0; index < this.size(); index++){
			isOp = isOp || this.elementAt(index).operational();
		}
		return isOp;
	}
	
	public static CarPartVector convert(Vector<CarPart> vcp){
		CarPartVector convert = new CarPartVector();
		for(int index = 0; index < vcp.size(); index++)
			convert.add(vcp.elementAt(index));
		
		return convert;
	}
	
	public boolean findLocations(Vector<Enums.Location> locations){
		boolean foundAll = true;
		
		for(int thisIndex = 0; thisIndex < this.size(); thisIndex++){
		
			CarPart cp = this.elementAt(thisIndex);
			boolean found = false;
		
			for(int locIndex = 0; locIndex < locations.size(); locIndex++){
		
				if(cp.getLocation() == locations.elementAt(locIndex)){
					found = true;
					break;
				}
			}
			foundAll = foundAll && found;
		}
		
		return foundAll;
	}

	public void create(String className, Vector<Enums.Location> locations, Iterative it) {
		
		for(int locIndex = 0; locIndex < locations.size(); locIndex++){
			it.create(className, locations.elementAt(locIndex));
		}
	}
}
