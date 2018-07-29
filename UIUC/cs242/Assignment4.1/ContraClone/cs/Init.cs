using System;
using System.Windows.Forms;
using System.Drawing;
//using Gtk;




namespace ContraClone
{
	public static class Init
	{
		public static Window window;
		public static Scene scene;

		static int Main(string[] args)
		{
			//TestClass.testAll (); return 0;
			
			window = new Window ();
			scene = new Scene (window);
			GameControl.initControls ();
 
			// Display the form as a modal dialog box.
			Application.Run(window);
			//window.Show();
			return 0;
		}
	}
}
