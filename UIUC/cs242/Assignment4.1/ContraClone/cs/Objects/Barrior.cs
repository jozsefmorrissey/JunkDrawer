using System;

namespace ContraClone
{
	public class Barrior
	{
		protected int startX;
		protected int startY;
		protected int endX;
		protected int endY;
		protected bool ground = false;
		protected bool water = false;
		protected bool canDrop = false;  //Indicates whether or not a player can drop to a lower level.
		protected bool active = true;

		public Barrior (int sx, int sy, int ex, int ey)
		{
			startX = sx;
			startY = sy;
			endX = ex;
			endY = ey;
		}

		public Barrior (int sx, int sy, int ex, int ey, bool g, bool w, bool cd)
		{
			startX = sx;
			startY = sy;
			endX = ex;
			endY = ey;
			ground = g;
			water = w;
			canDrop = cd;
		}

		public int getStartX(){ return this.startX;}
		public int getStartY(){ return this.startY;}
		public int getEndX(){ return this.endX;}
		public int getEndY(){ return this.endY;}

		public void setActive(bool a){ active = a;}
		public bool getActive(){ return active;}
	}
}

