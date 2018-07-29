using System;
using System.Drawing;
using System.Windows;

namespace ContraClone
{
	public static class Utilities
	{
		public static Image ScaleImage(Image image, int maxWidth, int maxHeight)
		{
			var ratioX = (double)maxWidth / image.Width;
			var ratio = ratioX;

			var newWidth = (int)(image.Width * ratio);
			var newHeight = (int)(image.Height * ratio);

			var newImage = new Bitmap(image, new Size(newWidth, newHeight));

			using (var graphics = Graphics.FromImage(newImage))
				graphics.DrawImage(image, 0, 0, newWidth, newHeight);

			return newImage;
		}

		public static Rectangle determineCropedSection(int ImageWidth, int imageHeight, int windowWidth, float focalX)
		{
			int minHeight = 0;
			int maxHeight = imageHeight;

			int minWidth = (int)(focalX) - windowWidth / 2;

			if (minWidth < 0) {
				minWidth = 0;
			}
			if (minWidth + windowWidth >= ImageWidth - 1) {
				minWidth = ImageWidth - windowWidth;
				minWidth = ImageWidth;
			}

			return new Rectangle (minWidth, minHeight, windowWidth, maxHeight);
		}

		public static void applyGravity(SceneObject sceneObject)
		{
			//TODO: Implement.
		}
	}


}

