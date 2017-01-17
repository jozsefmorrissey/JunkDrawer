using System;
using System.Drawing;
using System.Collections.Generic;
using System.Windows.Forms;
using KeyBoardControlEnum;

namespace ContraCloneImproved
{ 
	public class BarrierControl : BuilderControl
	{
		private List<Keys> classKeys = new List<Keys>{Keys.Enter, Keys.Q, Keys.C, Keys.W, Keys.E, 
														Keys.R, Keys.X, Keys.V, Keys.Z};
		private int keyOffset;

		private Pair point1;
		private Pair point2;

		// Keeps track of which point to modify with modulous.
		private static int barriorPointCount = 0;

		private static int barriorLevel = 0;

		private static bool ground;
		private static bool water;
		private static bool canDrop;
		private static List<Bitmap> platform;

		private int getLocalIndex (int index) { return index - keyOffset;}
		private int getGlobalIndex (int index) { return index - keyOffset;}

		public BarrierControl ()
		{
			keyOffset = this.keys.Count;
			this.expandKeys (classKeys);

			String imagePath = System.IO.Directory.GetCurrentDirectory () + "/../../Images/buildingBlock.png";
			platform = (Bitmap)Image.FromFile (imagePath, true);
			platform = (Bitmap)Utilities.ScaleImage (platform, 2, 2);
		}

		protected void processIndput(int globalIndex)
		{
			int movementOffset = 2;
			switch (getLocalIndex(globalIndex))
			{
			case (int)BarrierEnum.ENTER:
				updatePoint ();
				break;
			case (int)BarrierEnum.CAN_DROP:
				updateBool (ref keysProcessed [globalIndex], ref canDrop);
				break;
			case (int)BarrierEnum.GROUND:
				updateBool (ref keysProcessed [globalIndex], ref ground);
				break;
			case (int)BarrierEnum.WATER:
				updateBool (ref keysProcessed [globalIndex], ref water);
				break;
			case (int)BarrierEnum.INC:
				updateLevel (keysProcessed [globalIndex], 1);
				break;
			case(int)BarrierEnum.DEC:
				updateLevel (keysProcessed [globalIndex], -1);
				break;
			case (int)BarrierEnum.PRINT:
				printBarrior ();
				break;
			case (int)BarrierEnum.DRAW:
				drawPlatform ();
				break;
			case (int)BarrierEnum.UNDO:
				removeLastPlatfrom ();
				break;
			}
			base.processInput (globalIndex);
		}

		private void drawPlatform()
		{
			if (!keysProcessed [(int)keyEnum.Q]) {
				scene.drawPlatform ();
				keysProcessed [(int)keyEnum.Q] = true;
			}
		}

		// This allows removal of the last drawn platform, printed platform commands not removed.
		private void removeLastPlatfrom()
		{
			if (platform.Count > 0)
				scene.removeSprite (platform [platform.Count - 1]);
		}

		private void updateLevel(ref bool processed, int incDec)
		{
			if (!processed){
				barriorLevel += incDec;
				processed = true;
			}
		}

		// Updates message displayed on the window heading to correspond with values indicated by barrierBuilder state.
		private static void displayBarriorState()
		{
			string type = "";
			if (water)
				type += "    water";
			if (ground)
				type += "    ground";
			if (canDrop)
				type += "    can drop";

			scene.getWindow ().Text = "startPoint: " + point1 + "\tendPoint: " + point2 + "    Level: " + barriorLevel + type;
		}

		// Updates the least recently changed point iff hero has changed position.
		private static void updatePoint()
		{				
			if (!keysProcessed [(int)keyEnum.ENTER]) {
				Pair heroPosition = hero.getPosition ();
				if (barriorPointCount % 2 == 0 && (point1.x != heroPosition.x || point1.y != heroPosition.y)) {
					point1.x = (int)hero.getPosition ().x + hero.getWidth() / 2;
					point1.y = (int)hero.getPosition ().x + hero.getHeight / 2;
					barriorPointCount++;
				} else if (barriorPointCount % 2 == 1 && (point2.x != heroPosition.x || point2.y != heroPosition.y)) {
					point2.x = heroPosition.x + hero.getWidth () / 2;
					point2.y = heroPosition.y + hero.getWidth () / 2;
					barriorPointCount++;
				}
				keysProcessed [(int)keyEnum.ENTER] = true;
			}
		}

		private void printBarrior()
		{
			if (!keysProcessed [(int)keyEnum.P]) {
				string addBarriorPrefix = "info.barriers [" + barriorLevel + "] [info.barrierCounts[" + barriorLevel + "]++] = new Barrier(";

				Pair start = Pair.combineStart (point1, point2);
				Pair end = Pair.combineEnd (point1, point2);
				string locatioinArguments = start.x + ", " + start.y + ", " + end.x + ", " + end.y + ", ";

				string typeArguments = ground + ", " + water + ", " + canDrop + ");";
				System.Console.WriteLine (addBarriorPrefix + locatioinArguments + typeArguments);
				water = "false";
				canDrop = "false";
				ground = "false";
				keysProcessed [(int)keyEnum.P] = true;
			}
		}

		private static void updateBool(ref bool processed, ref bool b)
		{
			if(!processed){ 
				b = !b; 
				processed = true;
			}
		}

		public static Bitmap getImage() {return platform;}
	}
}