﻿using System;
using System.Windows.Forms;
using System.Collections.Generic;
using System.Linq;

namespace ContraCloneImproved
{
	public class KeyBoardControl
	{
		protected bool[] keysDown;
		protected bool[] keysProcessed;
		protected List<Keys> keys;

		protected Character hero;
		protected Scene scene;

		protected abstract void processInput(int keyIndex){}

		// This function is called once per timer callback 
		// to evaluate the overall state of the keys active.
		protected abstract void determineState ();

		// Specifies when Processing a key event.
		protected virtual void onKeyUp (int index){}
		protected virtual void onKeyDown (int index){}

		public KeyBoardControl(Character h)
		{
			hero = h;
		}


		public void timerCallback(object sender, EventArgs e)
		{
			determineState ();
			for (int index = 0; index < keysDown.Length; index++) 
			{
				if (keysDown [index]) {
					processInput (keys[index]);
				}
			}
		}

		public void keyDownEvent(object sender, KeyEventArgs e)
		{
			if (e.KeyCode == Keys.Space)
				System.Console.WriteLine ("Space");
			for (int index = 0; index < keysDown.Length; index++)
			{
				if(e.KeyCode == keys[index])
				{
					keysDown[index] = true;
				}					
				onKeyDown (e.KeyCode);
			}
		}

		public void keyUpEvent(object sender, KeyEventArgs e)
		{
			for (int index = 0; index < keysDown.Length; index++)
			{
				if(e.KeyCode == keys[index])
				{
					keysDown[index] = false;
					keysProcessed [index] = false;
					onKeyUp (e.KeyCode);
				}
			}
		}

		protected void expandKeys(List<Keys> newKeys)
		{
			this.keys.Concat (newKeys);
			keysDown.Concat(new bool[newKeys.Count]);
			keysProcessed.Concat (new bool[newKeys.Count]);
		}
	}
}
