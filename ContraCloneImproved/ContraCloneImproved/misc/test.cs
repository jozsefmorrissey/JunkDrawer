using System;
using System.Windows.Forms;
using System.Drawing;
using System.Data;




namespace ContraClone
{
	public class Init
	{
		public Init ()
		{
		}
	
		static int Main(string[] args)
		{
			System.Console.WriteLine("Hello, World!");
			//Application.Run (new HelloWorld ());
			return 0;
		}

		private void Form1_Paint(object sender, System.Windows.Forms.PaintEventArgs pe) 
		{
			Bitmap myPng = (Bitmap)Image.FromFile ("contra_images.png", true);
			PictureBox pb1 = new PictureBox();            
			pb1.ImageLocation = "contra_images.png";
			pb1.SizeMode = PictureBoxSizeMode.AutoSize;
		}
	}
}
