using System;

namespace ContraCloneImproved
{
	public  abstract class Barrier
	{
		// This should indicate the background image dementions a
		// Barrier was originally designed on. (For resize purposes)
		protected Dimentions originalDimentions;
		private Pair originalStart;
		private Pair originalEnd;

		protected Pair start;
		protected Pair end;

		protected int tolerance = 20;
		protected bool active;

		protected abstract void interact (Sprite sprite);

		public Barrier (Pair s, Pair e, Dimentions backgroundDimentions)
		{
			this.setCharacteristics (s, e, backgroundDimentions);
		}

		protected void setCharacteristics (Pair s, Pair e, Dimentions backgroundDimentions)
		{
			this.start = this.originalStart = s;
			this.end = this.originalEnd = e;
			this.originalDimentions = backgroundDimentions;
		}

		public bool setActive(bool a){ active = a;}
		public bool isActive () { return active;}

		public void setTolerence (int t) { return tolerance = t;}
		public int getTolerence () { return tolerance;}

		// Barriers are scaled to the dimentions of the background image.
		public virtual void resize(Dimentions newDim)
		{
			Pair scale = newDim / this.originalDimentions;
			Pair start = this.originalStart * scale;
			Pair end = this.originalEnd * scale;
		}

		public Dimentions getDimentions ()
		{
			Pair.getDementions (this.start, this.end);
		}
	}
}

