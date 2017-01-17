using System;
using System.Collections.Generic;
using System.Drawing;

namespace ContraCloneImproved
{
	public abstract class Level
	{
		// Scaled objects
		protected List<Sprite> sprites;
		protected List<Barrier> barriers;
		protected List<Sprite> viewTargets;
		protected Background background;

		protected Window window;

		protected abstract void createEnemies ();
		protected abstract void createPlatforms ();
		protected abstract void createWalls ();
		protected abstract void createItems ();

		public Level (string backgroundFileName)
		{
			String imagePath = System.IO.Directory.GetCurrentDirectory () + "/../../Images/Levels/" + backgroundFileName +".png";
			Bitmap background = (Bitmap)Image.FromFile (imagePath, true);

			createEnemies ();
			createPlatforms ();
			createWalls ();

			this.background = new Background (background, this.window);
			this.background.calculatePosition (this.getFocalPoint ());
		}

		public void paint()
		{
			background.paint ();
			sprites.ForEach (paint);
		}

		public void resize()
		{
			background.resize (window);
			barriers.ForEach (resize (background.getDimentions));
			sprites.ForEach (resize ());
			viewTargets.ForEach (resize ());
		}

		//Finds the Center of all viewTargets, for display purposes.
		public Pair getFocalPoint (){
			Pair focalPoint;
			Pair count;
			foreach (Sprite sprite in this.viewTargets) {
				focalPoint += sprite.getPosition ();
				count++;
			}
			return focalPoint / count;
		}

		public void addSprite(Sprite sprite)
		{	
			int index = Utilities.locateImageSlot (this.sprites);
			if (index == -1)
				this.sprites.Add (sprite);
			else
				this.sprites [index] = sprite;	
		}
	}
}

