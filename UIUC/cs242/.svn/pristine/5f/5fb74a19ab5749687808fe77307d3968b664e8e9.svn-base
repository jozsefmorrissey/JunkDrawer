using System;
using System.Windows.Forms;
using System.Drawing;


namespace ContraClone
{
	public class Scene
	{
		protected Object[] objects;
		public Timer time;
		public SceneObject view_target;
		public Character hero;
		public Window window;
		protected Background[] backgrounds;
		protected Barrior[] barriors;
		public Background bkgd;

		public Scene (Window win)
		{
			window = win;
			backgrounds = new Background[5];
			objects = new Object[20];
			level1 ();
		}

		protected void level1()
		{
			String imagePath = System.IO.Directory.GetCurrentDirectory () + "/../../Images/level1.png";
			Image background = (Bitmap)Image.FromFile (imagePath, true);

			hero = new Character (1000, 200, "");
			view_target = hero;
			bkgd = new Background (background, Init.window);
			bkgd.calculatePosition (view_target);
			backgrounds [backgrounds.Length - 1] = bkgd;
		}
			
		public void paint(Graphics graphics)
		{
			//TODO: Create loops that update all objects present in the scene.
			//bkgd.paint_background (graphics, hero);
			hero.paint (graphics);
		}

		public void updateGraphics(int rightLeft, int upDown)
		{
			hero.move (rightLeft, upDown);
			//view_target.move (hero.x);
			bkgd.calculatePosition (hero);
			Init.window.Invalidate ();
		}
	}

}

