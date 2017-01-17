using System;
using System.Windows.Forms;
using System.Drawing;
using System.Collections.Generic;
using KeyBoardControlEnum;

namespace ContraCloneImproved
{
	public class EnemyBuilderContol : BuilderControl
	{
		private Bitmap image;

		List<Keys> classKeys = new List<Keys>{Keys.Enter, Keys.Q, Keys.W};
		private int keyOffset;

		private Pair point;

		private int getLocalIndex (int index) { return index - keyOffset;}
		private int getGlobalIndex (int index) { return index - keyOffset;}

		public EnemyBuilderContol ()
		{
			keyOffset = this.keys.Count;
			expandKeys (classKeys);
		}

		protected void processIndput(int globalIndex)
		{
			switch (getLocalIndex(globalIndex))
			{
			case (EnemyEnum.ENTER):
				this.point = hero.getPosition ();
				break;
			case (EnemyEnum.DRAW):
				
				break;
			case (EnemyEnum.PRINT):
				break;

			}
			base.processInput (globalIndex);
		}

		private static void displayEnemyState ()
		{
			scene.getWindow().Text = "Point: " + point; 
		}


		private void drawEnemy()
		{
			if (!this.keysProcessed[getGlobalIndex ((int)Enemy.Q)]){
				scene.drawImage(this.image, this.point);
			}
		}

		private void printEnemy()
		{
			String location = point.x + "," + point.y;
			System.Console.WriteLine ("info.controlableObjects [info.controlObjectCount++] = new Enemy (" + location + ", \"red_enemy\");");
		}
	}
}

