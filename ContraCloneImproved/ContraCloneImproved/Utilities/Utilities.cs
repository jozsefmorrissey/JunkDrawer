using System;
using System.Drawing;
using System.Collections.Generic;

namespace ContraCloneImproved
{
	public static class Utilities
	{
		public static Image ScaleImage(Image image, Dimentions size)
		{
			var ratioX = size.width / image.Width;
			var ratioY = size.height / image.Height;

			var ratio = ratioX < ratioY ? ratioX : ratioY;

			var newWidth = (int)(image.Width * ratio);
			var newHeight = (int)(image.Height * ratio);

			var newImage = new Bitmap(image, new Size(newWidth, newHeight));

			using (var graphics = Graphics.FromImage(newImage))
				graphics.DrawImage(image, 0, 0, newWidth, newHeight);

			return newImage;
		}
			
		public static int locateImageSlot(List<Sprite> images)
		{
			for (int index = 0; index < images.Count; index++) {
				Sprite image = images [index];
				if (!image.isAlive ())
					return index;
			}
			return -1;
		}
	}
}

