using System;
using System.Drawing;
using System.Windows.Forms;

namespace ContraClone
{
	public class Background : SceneObject
	{
		public int endOfMap;
		protected int bottomOffset;
		protected int topOffset;
		protected Bitmap image;
		protected Window window;

		public Background (Image img, Window win)
		{
			double scale = (double)win.Height / (double)img.Height;
			image = new Bitmap(img, new Size((int)(img.Width*scale), (int)(img.Height*scale)));			
			window = win;
			layer = 0;
			visible = true;
		}

		public override void calculatePosition (SceneObject focalPoint)
		{
			bottomOffset = 18;
			float height = image.Height - bottomOffset;	

			double scale = (double)window.Height / (double)height;
			Bitmap clip = new Bitmap (image, new Size ((int)(image.Width * scale), (int)(height * scale + bottomOffset*scale)));

			lastX = focalPoint.x;
			endOfMap = clip.Width;

			Rectangle cropSection = Utilities.determineCropedSection (clip.Width, (int)(clip.Height - bottomOffset*scale), window.Width, focalPoint.x);
			clip = clip.Clone (cropSection, clip.PixelFormat);

			window.BackgroundImage = clip;
			//clip.Dispose ();
		}


			
		protected bool update_bacground (SceneObject focalPoint)	
		{
			if (lastX < focalPoint.x - refreshDist || lastX > focalPoint.x + refreshDist)
				return true;
			return false;
		}
	}
}

