using System;
using System.Windows.Forms;
using System.Collections.Generic;
using KeyBoardControlEnum;

namespace ContraCloneImproved
{
	public class GameControl : KeyBoardControl
	{
		private List<Keys> classKeys = new List<Keys>(){Keys.Right, Keys.Left, Keys.Up, 
														Keys.Down, Keys.S, Keys.F, Keys.D};
		private int keyOffset;

		private int getLocalIndex (int index) { return index - keyOffset;}
		private int getGlobalIndex (int index) {return index - keyOffset;}

		public GameControl(Hero h) : base (h)
		{
			keyOffset = this.keys.Count;
			this.expandKeys (classKeys);
		}

		// Specifies when Processing a key event.
		protected virtual void onKeyUp (Keys key)
		{
			if (key == classKeys[(int)gameKeyEnum.UP])
				hero.getView ().UP = false;
			else if (key == classKeys[(int)gameKeyEnum.LEFT])
				hero.getView ().Left = false;
			else if (key == classKeys[(int)gameKeyEnum.DOWN])
				hero.getView ().Down = false;
			else if (key == classKeys[(int)gameKeyEnum.RIGHT])
				hero.getView ().Right = false;
		}

		// This indicates the current orientation of the player.
		protected virtual void onKeyDown (Keys key)
		{
			if (key == classKeys[(int)gameKeyEnum.UP])
				hero.getView ().UP = true;
			else if (key == classKeys[(int)gameKeyEnum.DOWN])
				hero.getView ().Down = true;
			else if (key == classKeys[(int)gameKeyEnum.RIGHT]) {
				hero.getView ().Right = true;
				hero.getView ().Last_Right_Left = true;
			} else if (key == classKeys[(int)gameKeyEnum.LEFT]) {
				hero.getView ().Left = true;
				hero.getView ().Last_Right_Left = false;
			}
		}

		// Determins animation state.
		private override void determineState()
		{
			if (!hero.getState ()) {
				if (keysDown [(int)gameKeyEnum.DOWN]) {
					if (hero.getView ().Last_Right_Left)
						hero.setState(animStateEnum.CROUCH_RIGHT);
					else
						hero.setState(animStateEnum.CROUCH_LEFT);
				}else if ((keysDown [(int)keyEnum.LEFT] && keysDown [(int)keyEnum.RIGHT]) || (!keysDown [(int)keyEnum.LEFT] && !keysDown [(int)keyEnum.RIGHT])) {
					if (hero.getView ().Last_Right_Left)
						hero.setState(animStateEnum.IDLE_RIGHT);
					else
						hero.setState(animStateEnum.IDLE_LEFT);
				}else if (keysDown [(int)keyEnum.RIGHT])
					hero.setState(animStateEnum.WALK_RIGHT);
				else if (keysDown [(int)keyEnum.LEFT])
					hero.setState(animStateEnum.WALK_LEFT);
			}		
		}

		protected override void processInput(int globalIndex)
		{
			int localIndex = getLocalIndex (globalIndex);

			int movementOffset = 1;
			switch (getLocalIndex(globalIndex))
			{
			case (int)GameEnum.RIGHT:
				hero.move (movementOffset, 0);
				break;
			case (int)GameEnum.LEFT:
				hero.move (-movementOffset, 0);
				break;
			case (int)GameEnum.JUMP:
				jump (movementOffset);
				break;
			case (int)GameEnum.DROP_DOWN:
				hero.dropDown();
				break;
			case (int)GameEnum.SHOOT:
				if (!keysProcessed[globalIndex]){
					hero.shoot  ();
					keysProcessed [globalIndex] = true;
			}
			break;
			}
		}

		private void jump(int movementOffset)
		{
			hero.jump = true;
			if (hero.getView ().Last_Right_Left)
				hero.setState(animStateEnum.TUCK_RIGHT);
			else
				hero.setState(animStateEnum.TUCK_LEFT);

			hero.move (0, -movementOffset);
		}
	}
}
	
