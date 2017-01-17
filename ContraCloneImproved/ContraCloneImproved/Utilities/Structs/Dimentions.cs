using System;

namespace ContraCloneImproved
{
	public struct Dimentions
	{
		public int width;
		public int height;

		public Dimentions(int w, int h)
		{
			width = w;
			height = h;
		}

		public override string ToString ()
		{
			return string.Format ("Width: " + this.width + "\tHeight: " + this.height + ")");
		}

		public static Dimentions calculateScale(Dimentions newDim, Dimentions oldDim)
		{
			Dimentions scale;
			scale.width = (double)newDim / (double)oldDim;
			scale.height = (double)newDim.height / (double)oldDim.height;
			return scale;
		}

		public static Dimentions operator +(Pair d1, Pair d2){
			return new Pair (d1.x + d2.x, d1.y + d2.y);
		}

		public static Pair operator -(Pair d1, Pair d2){
			return new Pair (d1.x - d2.x, d1.y - d2.y);
		}

		public static Pair operator *(Pair d1, Pair d2){
			return new Pair (d1.x * d2.x, d1.y * d2.y);
		}

		public static Pair operator /(Pair d1, Pair d2){
			return new Pair (d1.x / d2.x, d1.y / d2.y);
		}

		public static Pair operator ++(Pair d){
			d.x += 1;
			d.y += 1;
		}

		public static Pair operator ==(Pair d1, Pair d2){
			return d1.x == d2.x && d1.y == d2.y;
		}
	}
}

