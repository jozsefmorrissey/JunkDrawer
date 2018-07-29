using System;
using System.Windows.Forms;
using System.Drawing;


namespace ContraClone
{
	public class GameControl
	{
		private static bool[] keysDown;
		private static Keys[] keys;

		public static void initControls()
		{
			keys = new []{Keys.Right, Keys.Left, Keys.Up, Keys.Down, Keys.Space};
			keysDown = new bool[keys.Length];
		}

		public static void keyTimerCallback(object sender, EventArgs e)
		{
			for (int index = 0; index < keysDown.Length; index++) 
			{
				if (keysDown [index]) 
				{
					gamePlayKeyPress (index);
					Window.rePaint = true;
				}
			}
		}

		public static void keyDownEvent(object sender, KeyEventArgs e)
		{
			for (int index = 0; index < keysDown.Length; index++)
			{
				if(e.KeyCode == keys[index])
				{
					keysDown[index] = true;
				}
			}
		}

		public static void keyUpEvent(object sender, KeyEventArgs e)
		{
			for (int index = 0; index < keysDown.Length; index++)
			{
				if(e.KeyCode == keys[index])
				{
					keysDown[index] = false;
				}
			}
		}

		//TODO: Figure out a non-delayed key detection method.
		// Detect all keys used during game play.
		protected static void gamePlayKeyPress(int keyIndex)
		{
			int movementOffset = 10;
			switch (keyIndex)
			{
				case (int)keyEnum.RIGHT:
					Init.scene.updateGraphics (movementOffset, 0);
					break;
				case (int)keyEnum.LEFT:
					Init.scene.updateGraphics (-movementOffset, 0);
					break;
				case (int)keyEnum.UP:
					Init.scene.updateGraphics (0, -movementOffset);
					break;
				case (int)keyEnum.DOWN:
					Init.scene.updateGraphics (0, movementOffset);
					break;
				case (int)keyEnum.SPACE:
					Init.scene.updateGraphics (0, -movementOffset);
					break;
			}
		}
	}
}

