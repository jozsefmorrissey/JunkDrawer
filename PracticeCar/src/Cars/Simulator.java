package Cars;

import IgnitionParts.Starter;

//There is alot more going on in the car classes
//this is just a small demo that demostrates the
//relationships between the ignition,
//Electrical, and fuel systems.
public class Simulator {
	public static void main(String[] args){
		drainBatteryWithStarter();
		alternatorTest();
	}
	
	private static void monitorGas(Car c){
		int loopCount = 0;
		while(c.getElectrical().isRunning() || c.getMotor().operational()){
			System.out.println();
			if(c.getElectrical().isRunning())
				System.out.println("Computer is running.");
			
			if(c.getStarter().isRunning())
				System.out.println("Starter is running.");
			
			if(c.getMotor().isRunning()){
				System.out.println("Motor is running.");
				System.out.println("Alternator is running.");
			}
			System.out.println("Fuel level " + c.getTank().checkLevel() + ".");
			System.out.println("Battery level " + c.getBattery().getPercent()*100 + "%.\n");
			
			if(loopCount++ == 20){
				c.getElectrical().getWire().breakPart();
				c.getIgnition().turnOff();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void alternatorTest(){
		Car c = new Car();
		System.out.println("Alternator test starting");

		c.getTank().drain(11.93);
		c.getBattery().drain(99);
		c.inspect();
		c.getIgnition().turnOn();
		c.getIgnition().start();

		monitorGas(c);
		
	}
	
	public static void drainBatteryWithStarter(){
		Car c = new Car();
		System.out.println("Starter drain starting");
		c.getTank().drain(12);
		c.getBattery().drain(98);
		c.inspect();

		Starter s = c.getStarter();
		s.turnOn();
		monitorGas(c);
	}
}
