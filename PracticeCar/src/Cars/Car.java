package Cars;

import BrakeParts.MasterCylinder;
import ElectricalParts.Battery;
import Enums.Location;
import FuelSystemParts.Tank;
import IgnitionParts.Starter;
import PowerTrainParts.Motor;
import Systems.*;

//I broke the Car up into typical systems comprised
//of parts. See doxygen file for inheritance graph.
public class Car extends ElectronicSystem{
	private int odometer = 0;
	private MasterCylinder masCyl = new MasterCylinder(this, Location.ENGINE_BAY_TOP);
	private Battery battery = new Battery(this, Location.ENGINE_BAY_TOP);
	private Starter starter = new Starter(this, Location.ENGINE_BAY_BOTTOM);
	private Motor motor = new Motor(this, Location.ENGINE_BAY);
	private Tank tank = new Tank(this, Location.UNDERCARRIAGE_CENTER);

	private Brakes brakes;
	private DriveTrain driveTrain;
	private Ignition ignition;
	private Fuel fuel;
	private Electrical electrical;
	private PowerTrain powerTrain;
	private Suspension suspension;
	
	
	public Car(){
		brakes = new Brakes(this, Location.UNDERCARRIAGE_CENTER);
		driveTrain = new DriveTrain(this, Location.UNDERCARRIAGE_CENTER);
		ignition = new Ignition(this, Location.INTERIOR_FRONT_DRIVERS);
		fuel = new Fuel(this, Location.UNDERCARRIAGE_REAR);
		electrical = new Electrical(this, Location.ENGINE_BAY_TOP);
		powerTrain = new PowerTrain(this, Location.ENGINE_BAY);
		suspension = new Suspension(this, Location.UNDERCARRIAGE_CENTER);
	}
	
	


	@Override
	public boolean operational() {
		return ignition.operational();
	}


	@Override
	public boolean replace(CarPart oldPart, CarPart newPart) {
		//Do Nothing.
		return false;
	}


	@Override
	public void diagnostic() {
		brakes.diagnostic();
		driveTrain.diagnostic();
		ignition.diagnostic();
		fuel.diagnostic();
		electrical.diagnostic();
		powerTrain.diagnostic();
		suspension.diagnostic();		
	}


	@Override
	public void inspect() {
		masCyl.inspect();
		battery.inspect();
		starter.inspect();
		motor.inspect();
		tank.inspect();
		
		brakes.inspect();
		driveTrain.inspect();
		ignition.inspect();
		fuel.inspect();
		electrical.inspect();
		powerTrain.inspect();
		suspension.inspect();		
	}


	@Override
	public boolean operationLoop() {
		ignition.turnOn();
		ignition.start();
		return false;
	}
	
	
	public boolean replacePart(CarPart oldPart, CarPart newPart){
		if(oldPart.getPartNumber() == newPart.getPartNumber()){
			if(oldPart == masCyl){
					masCyl = (MasterCylinder) newPart;
					return true;	
			}
			if(oldPart == battery){
					battery = (Battery) newPart;
					return true;
			}
			if(oldPart == starter){
					starter = (Starter) newPart;
					return true;
			}
			if(oldPart == motor){
					motor = (Motor) newPart;
					return true;
			}
			
			boolean partReplaced = false;
			switch(oldPart.getCatagory()){
				case BRAKES: 
					partReplaced = brakes.replace(oldPart, newPart); 
					break;
				case DRIVE_TRAIN: 
					partReplaced = driveTrain.replace(oldPart, newPart); 
					break;
				case ELECTRICAL: 
					partReplaced = electrical.replace(oldPart, newPart); 
					break;
				case FUEL: 
					partReplaced = fuel.replace(oldPart, newPart); 
					break;
				case IGNITION: 
					partReplaced = ignition.replace(oldPart, newPart); 
					break;
				case POWER_TRAIN: 
					partReplaced = powerTrain.replace(oldPart, newPart); 
					break;
				case SUSPENSION: 
					partReplaced = suspension.replace(oldPart, newPart); 
					break;
				case STORAGE:
					System.out.println("Part to be replaced is not crurrently installed on this car.");
			}		
			
			if(partReplaced)
				oldPart.setCar(null);
			
			return partReplaced;			
		}

		System.out.println("New part is not compatable with old part.");
		
		return false;
	}
	
	public synchronized void addHundrethMile(){odometer += .01;}
	
	public synchronized int getMiles(){return odometer;}
	
	
	
	public Brakes getBrakes() {
		return brakes;
	}


	public DriveTrain getDriveTrain() {
		return driveTrain;
	}


	public Ignition getIgnition() {
		return ignition;
	}


	public Fuel getFuel() {
		return fuel;
	}


	public Electrical getElectrical() {
		return electrical;
	}


	public PowerTrain getPowerTrain() {
		return powerTrain;
	}


	public Suspension getSuspension() {
		return suspension;
	}


	public Starter getStarter() {
		return starter;
	}


	public Motor getMotor() {
		return motor;
	}


	public MasterCylinder getMasCyl() {
		return masCyl;
	}


	public Battery getBattery() {
		return battery;
	}

	
	public Tank getTank() {
		return tank;
	}
}
