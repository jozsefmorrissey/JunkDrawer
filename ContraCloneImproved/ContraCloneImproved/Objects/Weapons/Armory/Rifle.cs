using System;

namespace ContraCloneImproved
{
	public class Rifle : Weapon
	{
		public static bool fireWeapon(int x, int y, double degrees){

			double rads = (2 * Math.PI * degrees) / 360;

			create_bullet (x, y, Math.Cos(rads), Math.Sin(rads));
			return true;
		}
	}
}

