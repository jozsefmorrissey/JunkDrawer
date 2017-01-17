using System;

namespace ContraCloneImproved
{
	public class Shotgun : Weapon
	{
		public static bool fireWeapon(int x, int y, double degrees, teamEnum team){

			double rads = (2 * Math.PI * degrees) / 360;

			double offset = .1;

			create_bullet (x, y, Math.Cos(rads + 2*offset), Math.Sin(rads + 2*offset));
			create_bullet (x, y, Math.Cos(rads + offset), Math.Sin(rads + offset));
			create_bullet (x, y, Math.Cos(rads), Math.Sin(rads));
			create_bullet (x, y, Math.Cos(rads - offset), Math.Sin(rads - offset));
			create_bullet (x, y, Math.Cos(rads - 2*offset), Math.Sin(rads - 2*offset));

			return true;
		}
	}
}

