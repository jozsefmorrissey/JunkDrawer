using System;
using System.Windows.Forms;
using System.Drawing;

namespace ContraClone
{
	public class Character : SceneObject
	{
		protected float velocityX;
		protected float velocityY;
		protected bool alive;
		protected int Damage;
		protected int Health;
		protected Weapon weapon;
		protected Animation anim;
		protected Image image;

		public Character ()
		{
		}

		public Character(int startx, int starty, String imageName)
		{
			String imagePath = System.IO.Directory.GetCurrentDirectory () + "/../../Images/cropped_contra_images/idle1.png";
			image = (Bitmap)Image.FromFile (imagePath, true);
			image = Utilities.ScaleImage (image, 75, 75);
			staticX = Init.window.Width / 2 - image.Width;
			staticY = Init.window.Height / 2 - image.Height;

			centerX = x = (float)startx;
			centerY = y = (float)starty;
		}

		public override void calculatePosition (SceneObject focalPoint)
		{

		}

		public void paint(Graphics formGraphics)
		{
			if (this.x > Init.window.Width / 2) {
				staticX = Init.window.Width / 2;
			} else 
			{
				staticX = (int)this.x;
			}	
				
			formGraphics.DrawImage (image, staticX, y);
		}

		public bool move(int rightLeft, int upDown)
		{
			this.x += rightLeft;
			this.y += upDown;

			if (this.x < image.Width / 2)
				this.x = image.Width / 2;
			if (this.x > Init.scene.bkgd.endOfMap - image.Width / 2)
				this.x = image.Width / 2;

				lastX = x;
				lastY = y;
		
				return false;
		}
	}
}