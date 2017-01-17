using System;
using System.Drawing;

namespace ContraCloneImproved
{
	public class Background
	{
		private Bitmap originalImage;
		private Bitmap scaledImage;

		private Bitmap currentBackground;
		private Bitmap previousBacground;

		private Window window;

		private Pair position;

		public Background (Bitmap img, Window win)
		{
			this.scaledImage = img;
			this.window = win;
		}

		public Background (Image img, Window win)
		{
			double scale = (double)win.Height / (double)img.Height;
			scaledImage = new Bitmap(img, new Size((int)(img.Width*scale), (int)(img.Height*scale)));			
			window = win;
		}

		public void resize()
		{
			float height = scaledImage.Height;	

			Dimentions scale = Dimentions.calculateScale (window.getDimentions, this.getDimentions);
			scaledImage = new Bitmap (originalImage, new Size ((int)(originalImage.Width * scale.width), (int)(height * scale.width)));
		}

		// determins the section of background that should be displayed given a scene object focal point
		public void calculatePosition (Pair focalPoint)
		{
			position.x = -(focalPoint.x - window.Width / 2) ;
			if (position.x > 0)
				position.x = 0;
			if (-position.x > this.scaledImage.Width - this.window.Width)
				position.x = -(this.scaledImage.Width - this.window.Width);
			position.y = 0;
		}

		public override void paint(Graphics g){
			g.DrawImage (currentBackground, (int)position.x, (int)position.y);
		}

		public Dimentions getDimentions(){ return new Dimentions(scaledImage.Width, scaledImage.Height);}
		private Dimentions getOriginalDimentions (){ return new Dimentions(originalImage.Width, originalImage.Height);}
	}
}

