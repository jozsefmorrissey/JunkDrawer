using System;
using System.Drawing;

namespace ContraClone
{
	public static class TestClass
	{
		private static bool allPassed = true;

		public static void testAll()
		{
			testCropImage ();
			testScaleImage ();
			if (allPassed)
				System.Console.WriteLine ("Success: All Tests Passed");
		}

		public static void testCropImage()
		{
			Rectangle size = Utilities.determineCropedSection (2000, 500, 300, 1000);
			if (size.X != 850 || size.Y != 0 || size.Width != 300 || size.Height != 500)
				testFailed ("Crop Image", 1);
		}

		public static void testScaleImage()
		{
			String imagePath = System.IO.Directory.GetCurrentDirectory () + "/../../Images/level1.png";
			Image original = (Bitmap)Image.FromFile (imagePath, true);

			Image scaled = Utilities.ScaleImage (original, 10000, 1000);

			if (scaled.Height != 708 || scaled.Width != 10000)
				testFailed ("Scale Image", 1);

			scaled = Utilities.ScaleImage (original, 1000, 10000);

			if (scaled.Height != 70 || scaled.Width != 1000)
				testFailed ("Scale Image", 2);

			scaled = Utilities.ScaleImage (original, 100, 100);

			if (scaled.Height != 7 || scaled.Width != 100)
				testFailed ("Scale Image", 3);

			scaled = Utilities.ScaleImage (original, 1000, 100);

			if (scaled.Height != 70 || scaled.Width != 1000)
				testFailed ("Scale Image", 4);
		}

		public static void testFailed(string testName, int testNumber)
		{
			System.Console.WriteLine ("TEST FAILED: " + testName + "(test number: " + testNumber + ")");
			allPassed = false;
		}
	}
}

