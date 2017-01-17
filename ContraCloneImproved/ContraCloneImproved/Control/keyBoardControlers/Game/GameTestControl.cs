using System;
using System.Collections.Generic;
using System.Windows.Forms;
using KeyBoardControlEnum;

namespace ContraCloneImproved
{
	// Added functionality save and load player location with ENTER, L keys respectivly.
	public class GameTestControl : GameControl
	{
		private List<Keys> classKeys = new List<Keys>(){Keys.Enter,	Keys.L};

		Pair savedLocation;
		private int keyOffset;

		public GameTestControl () : base(hero)
		{
			keyOffset = keys.Count;
			base.expandKeys (classKeys);
		}

		protected override void processInput (int globalIndex)
		{
			Pair position = hero.getPosition ();
			switch (getLocalIndex(globalIndex)) {
			case (int)GameTestEnum.ENTER:
				savedLocation = new Pair (position);
				break;
			case (int)GameTestEnum.LOAD:
				hero.setPosition(savedLocation);
				hero.setPlatform(new Barrier (0,0,0,0));
				break;
			}
			base.processInput (globalIndex);
		}

		private int getLocalIndex (int index) { return index - keyOffset;}
		private int getGlobalIndex (int index) {return index - keyOffset;}

		override void determineState (){}
	}
}

