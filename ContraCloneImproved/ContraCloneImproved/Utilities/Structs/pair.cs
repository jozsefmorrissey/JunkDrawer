using System;

namespace ContraCloneImproved
{
	public struct Pair
	{
		public double x = 0.0;
		public double y = 0.0;

		public Pair(double x, double y){
			this.x = x;
			this.y = y;
		}

		public override string ToString ()
		{
			return string.Format ("(" + this.x + "," + this.y + ")");
		}

		public static Pair operator +(Pair p1, Pair p2){
			return new Pair (p1.x + p2.x, p1.y + p2.y);
		}

		public static Pair operator -(Pair p1, Pair p2){
			return new Pair (p1.x - p2.x, p1.y - p2.y);
		}

		public static Pair operator *(Pair p1, Pair p2){
			return new Pair (p1.x * p2.x, p1.y * p2.y);
		}

		public static Pair operator /(Pair p, int d){
			return new Pair (p.x / 2, p.y / 2);
		}

		public static Pair operator /(Pair p1, Pair p2){
			return new Pair (p1.x / p2.x, p1.y / p2.y);
		}

		public static Pair operator ++(Pair p){
			p.x += 1;
			p.y += 1;
		}

		public static Pair operator ==(Pair p1, Pair p2){
			return p1.x == p2.x && p1.y == p2.y;
		}

		public static Pair combineStart(Pair p1, Pair p2){
			double newX = p1.x < p2.x ? p1 : p2;
			double newY = p1.y < p2.y ? p1 : p2;
			return new Pair (newX, newY);
		}

		public static Pair combineEnd(Pair p1, Pair p2){
			double newX = p1.x > p2.x ? p1 : p2;
			double newY = p1.y > p2.y ? p1 : p2;
			return new Pair (newX, newY);
		}

		public static Pair combineCenter(Pair p1, Pair p2){
			Pair center = p1 + p2;
			return center / 2;
		}

		public static Dimentions getDementions (Pair p1, Pair p2){
			Pair start = Pair.combineStart (p1, p2);
			Pair end = Pair.combineEnd (p1, p2);
			return new Dimentions (end.x - start.x, end.y - start.y);
		}
	}
}

