using System;
using System.Drawing;

namespace ContraCloneImproved
{
	public abstract class Weapon
	{
		protected delegate bool fireMode (int a, int b, double c, teamEnum t);
		fireMode fireFunc;
		teamEnum team;
		Sprite shooter;
		Image bulletImage;
		Dimentions velocityMagnitude = new Dimentions(20,20);

		private abstract void fireWeapon (Pair start, double degrees);

		public Weapon (Func<int, int, double, teamEnum, bool> function, Sprite sh)
		{
			this.team = team;
			fireFunc = new fireMode(function);
			shooter = sh;

			String imagePath = System.IO.Directory.GetCurrentDirectory () + "/../../Images/cropped_contra_images/hero/bullet.png";
			bulletImage = (Bitmap)Image.FromFile (imagePath, true);
			bulletImage = Utilities.ScaleImage (bulletImage, 10, 10);
		}

		public void create_bullet(int x, int y, double incX, double incY, teamEnum team){
			Sprite sprite = new Sprite (bulletImage, (int)timeEnum.BULLET_LIFE);
			sprite.setTeam (shooter.getTeam ());

			sprite.setPosition (new Pair (x, y));
			sprite.calculatePosition ();

			double c = Math.Sqrt(incX * incX + incY * incY);
			Pair velocity = new Pair (incX * velocityMagnitude / c, incY * velocityMagnitude / c);
			sprite.setVelocity (velocity);

			Scene.getLevel ().addSprite (sprite);
		}

		/// <summary>
		/// Scale references the scale relitive to changes to the background.
		/// </summary>
		/// <param name="scale">Scale.</param>
		public void resize(Dimentions scale){
			velocityMagnitude *= scale;
			bulletImage = Utilities.ScaleImage (bulletImage, scale);
		}

		public void shootRight ()
		{
			Pair position = shooter.getPosition ();
			double x = position.x + shooter.getWidth ();
			double y = position.y + shooter.getHeight () / 4;
			fireWeapon(new Pair(x,y),0);
		}

		public void shootRightUp ()
		{
			Pair position = shooter.getPosition ();
			double x = position.x + shooter.getWidth ();
			double y = position.y;
			fireWeapon(new Pair(x,y),-45);
		}

		public void shootUp ()
		{
			Pair position = shooter.getPosition ();
			double x = position.x + shooter.getWidth () / 2;
			double y = position.y;
			fireWeapon(new Pair(x,y),-90);
		}

		public void shootLeftUp ()
		{
			Pair position = shooter.getPosition ();
			double x = position.x;
			double y = position.y;
			fireWeapon(new Pair(x,y),-135);
		}

		public void shootLeft ()
		{
			Pair position = shooter.getPosition ();
			double x = position.x;
			double y = position.y + shooter.getHeight () / 4;
			fireWeapon(new Pair(x,y),-180);
		}

		public void shootLeftDown ()
		{
			Pair position = shooter.getPosition ();
			double x = position.x;
			double y = position.y + 3 * shooter.getHeight () / 4;
			fireWeapon(new Pair(x,y),-225);
		}

		public void shootDown ()
		{
			Pair position = shooter.getPosition ();
			double x = position.x + shooter.getWidth () / 2;
			double y = position.y + shooter.getHeight ();
			fireWeapon(new Pair(x,y),-270, this.team);
		}

		public void shootRightDown ()
		{
			Pair position = shooter.getPosition ();
			double x = position.x + shooter.getWidth ();
			double y = position.y + shooter.getHeight ();
			fireWeapon(new Pair(x,y),-315);
		}
	}
}

