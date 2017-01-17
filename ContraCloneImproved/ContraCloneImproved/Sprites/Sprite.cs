using System;
using System.Drawing;

namespace ContraCloneImproved
{
	public abstract class Sprite
	{
		private Bitmap originalImage;

		// Scaled attributes.
		private Bitmap scaledImage;
		private Pair center;

		protected Physics physics;

		private teamEnum team;
		private bool alive;
		private bool jumping;
		private TimedEventControl deathControl;

		public abstract void calculatePosition (Sprite focalPoint);
		private abstract void paint(Graphics graphics);

		public Sprite (Bitmap img)
		{
			originalImage = img;
			scaledImage = img;
			center = new Pair (scaledImage.Width / 2, scaledImage.Height / 2);
		}

		public int getHeight (){ return this.scaledImage.Height;}
		public int getWidth () {return this.scaledImage.Width;}

		public Pair getCenter () { return this.center;}

		public Pair getPosition () { return this.physics.getPosition ();}
		public void setPosition (Pair p) {this.physics.setPosition (p);}

		public Pair getVelocity () { return this.physics.getVelocity();}
		public void setVelocity (Pair v) {this.physics.setVelocity (v);}

		//public Pair getForce () { return this.force;}
		public void addForce (Pair f) {this.physics.addForce (f);}

		public bool isAlive () {return this.alive;}
		public bool setAlive (bool b) {this.alive = b;}

		public bool isJumping () {return this.jumping;}
		public void setJumping (bool b) {this.jumping = b;}

		public teamEnum getTeam () {return this.team;}
		public void setTeam (teamEnum t) { this.team = t;}

		public void resize (Dimentions newSize)
		{
			scaledImage = Utilities.ScaleImage (this.originalImage, newSize);
			center = new Pair (scaledImage.Width / 2, scaledImage.Height / 2);
		}


		public void setDeath(int time)
		{
			// TODO: setup timed event;
		}

		public Pair getBottomCenter()
		{
			Pair position = this.physics.getPosition ();
			double positionX = position.x + center.x;
			double positionY = position.y + this.getHeight ();
			return new Pair (positionX, positionY);
		}
	}
}

