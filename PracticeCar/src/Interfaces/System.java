package Interfaces;

import Cars.CarPart;

public interface System {
	public boolean operational();
	public boolean replace(CarPart oldPart, CarPart newPart);
	public void diagnostic();
	public void inspect();
}
