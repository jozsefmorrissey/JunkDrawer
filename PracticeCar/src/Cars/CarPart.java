package Cars;

import Enums.*;

public abstract class CarPart implements Interfaces.System{
	protected PartCatagory catagory;
	protected Car car;
	protected double weight;
	private String partName;
	private String partNumber;
	
	private int lastInspection = 0;
	private Location location = Enums.Location.STORAGE;
	private Status status = Status.FUNCTIONAL;
	
	
	@Override
	public boolean replace(CarPart oldPart, CarPart newPart) {
		if(car == null){
			System.out.println("This part is not on a vehical.");
		}
		if(car.replacePart(oldPart, newPart)){
			oldPart.car = null;
			return true;
		}
		System.out.println("Part was not replaced");
		return false;
	}
	
	
	public Location getLocation(){return location;}
	public Status getStatus(){return status;}

	
	protected void initialize(Car c, PartCatagory cat, double wgt, 
								String pname, String pnum, Location l){
		setCar(c);
		setCatagory(cat);
		weight = wgt;
		partName = pname;
		partNumber = pnum;
		location = l;
	}

	public void inspect(){
		if(status == Status.BROKEN)
			System.out.println(partName + " needs replaced.");
		
		if(status != Status.FUNCTIONAL)
			printPartError(getStatus().toString());
		
		if(car != null)
			lastInspection = car.getMiles();
	}
	
	public static void inspect(CarPart cp, String partName){
		if(cp != null)
			cp.inspect();
		else
			System.out.println(partName + " is not installed.");
	}
	
	@Override
	public void diagnostic() {
		printNoData();		
	}
	
	@Override
	public boolean operational() {
		return !getStatus().equals(Status.BROKEN);
	}
	
	public void lifeTimeStatus(){
		int milesAfterInsp = getCar().getMiles() - lastInspection;
		
		if(milesAfterInsp > 20000)
			System.out.println(getPartName() + "needs to be inspected");
		else
			System.out.println(getPartName() + " inspection in " + milesAfterInsp + "miles.");
	}
	
	public double getWeight(){return weight;}
	
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public String getPartName() {
		return partName;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public int getLastInspection() {
		return lastInspection;
	}
	
	public void breakPart(){
		status = Status.BROKEN;
	}
	
	public PartCatagory getCatagory() {
		return catagory;
	}
	
	public void setCatagory(PartCatagory catagory) {
		this.catagory = catagory;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}

	public static void printNoData(){
		System.out.println("No DATA");
	}
	
	public void printPartError(String error){
		System.out.println("The " + partName + " at " + location.toString() + " is " + error + ".");
	}
}
