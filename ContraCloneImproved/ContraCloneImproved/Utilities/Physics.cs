using System;

namespace ContraCloneImproved
{
	public class Physics
	{
		private Pair force;
		private Pair position;
		private Pair constVelocity;

		private double mass;
		private double timeElapsed = 20 / 1000;
		private double gravity = 29.8;
		private double horizontalCoeficient = 55;
		private double verticalCoeficient = 52;

		//private static double kHorizontal = .008;
		//private static double kVerical = 1.6;

		public Physics (double positionX, double positionY)
		{
			this.position.x = positionX;
			this.position.y = positionY;
		}

		public void setVelocity(Pair v){ this.constVelocity = v;}
		public void addForce(Pair f) { this.force += f;}

		public void calculateFinalPositionHorizontal (){
			double totalForce = force.x * 10 + (constVelocity * mass);
			calculateFinalPositionX (totalForce);
		}

		public void calculateFinalPositionVertical(){
			double totalForce = force.y + (constVelocity * mass) + mass * gravity;
			calculateFinalPositionY (totalForce);
		}

		private void calculateFinalPositionX(double totalForce){
			double currVelocity = totalForce;//(velocity + force * timeElapsed / mass);
			position.x = position.x + ((currVelocity + constVelocity.x) * timeElapsed) * horizontalCoeficient;
			force.x = 0;
		}

		private void calculateFinalPositionY(Pair totalForce){
			Pair currVelocity = totalForce * (timeElapsed / mass);
			position.y = position.y + ((currVelocity + constVelocity) * timeElapsed / (2)) * verticalCoeficient;
			force.y = 0;
		}

		public void calculateFinalPosition (bool grounded)
		{
			if(this.force.x != 0)
				calculateFinalPositionHorizontal ();
			if (!grounded || this.force.y < 0)
				calculateFinalPositionVertical ();
		}
	}
}

