using System;

namespace ContraCloneImproved
{
	public class Physics
	{
		private Sprite sprite;

		private Pair constVelocity;
		private Pair position;
		private Pair force;

		private Pair conatantForce;

		public Physics (Sprite s)
		{
			sprite = s;
		}

		public Pair getVelocity() { return this.constVelocity;}
		public Pair getPosition() { return this.position;}

		public void setPosition(Pair pos) { this.position = pos;}

		public void addForce(Pair incForce) { this.force += incForce;}
	}
}

