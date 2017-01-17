using System;
using System.Drawing;
using System.Collections.Generic;
using System.Windows.Forms;
using KeyBoardControlEnum;

namespace ContraCloneImproved
{
	public class BuilderControl : KeyBoardControl
	{
		private List <Keys> classKeys = new List<Keys> (){Keys.Right, Keys.Left, Keys.Up, Keys.Down, Keys.A, 
														  Keys.S, Keys.D, Keys.F, Keys.Space};
		private int keyOffset;

		private Control currentControl;

		private int getLocalIndex (int index) { return index - keyOffset;}
		private int getGlobalIndex (int index) {return index - keyOffset;}

		public BuilderControl(Hero h) : base(h)
		{
			keyOffset = this.keys.Count;
			expandKeys (classKeys);
		}

		// Detect all keys used during game play.
		protected override void processInput(int globalIndex)
		{
			int movementOffset = 2;
			switch (globalIndex)
			{
			case (int)BuilderEnum.RIGHT:
				hero.move (movementOffset, 0);
				break;
			case (int)BuilderEnum.LEFT:
				hero.move (-movementOffset, 0);
				break;
			case (int)BuilderEnum.UP:
				hero.move (0, -movementOffset);
				break;
			case (int)BuilderEnum.DOWN:
				hero.move (0, movementOffset);
				break;
			case (int)BuilderEnum.FAST_LEFT:
				hero.move (20 * -movementOffset, 0);
				break;
			case (int)BuilderEnum.FAST_RIGHT:
				hero.move (20 * movementOffset, 0);
				break;
			case (int)BuilderEnum.FAST_DOWN:
				hero.move (0, 20 * movementOffset);
				break;
			case (int)BuilderEnum.FAST_UP:
				hero.move (0, 20 * -movementOffset);
				break;
			case (int)BuilderEnum.SPACE:
				this.rotateBuilderControl ();
				break;
			}
		}

		private void rotateBuilderControl ()
		{
			Window win = scene.getWindow ();

			win.removeControl (currentControl.timerCallback);
			currentControl = new BarrierControl (hero);
			win.addControl ((int)Barrier.REFRESH_RATE, currentControl);
		}

		protected override void determineState (){}
	}
}

