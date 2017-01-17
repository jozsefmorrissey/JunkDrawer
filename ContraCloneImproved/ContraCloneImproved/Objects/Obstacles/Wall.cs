using System;

namespace ContraCloneImproved
{
	public class Wall : Barrier
	{
		// Indicates whether wall is interacted with while running to the right or left.
		// Walls with an xDistance less than tolerence should only indicate one direction
		// or else sprites will pass through or become trapped by logic.
		private bool right;
		private bool left;

		public Wall (Pair s, Pair e, Dimentions backgroundDimentions) : base (s, e, backgroundDimentions)
		{
		}

		public override void interact(Sprite sprite)
		{	
			Pair location = sprite.getBottomCenter ();

			if (this.start.y < location.y && this.end.y > location.y) {
				int spriteWidth = sprite.getWidth ();
				if (interactRight (location, spriteWidth))
					sprite.setPosition (new Pair (this.start.x, location.y));
				if (interactLeft (location))
					sprite.setPosition (new Pair (this.end.x, location.y));
			}
		}

		private void interactRight(Pair location, int spriteWidth){
			return right && this.start.x - spriteWidth + this.tolerance > location.x && location.x > this.start.x - spriteWidth;
		}

		private void interactLeft(Pair location){
			return left && this.start.x - tolerance < location.x && location.x < this.start.x;
		}

		public override void resize(Dimentions newDim)
		{
			Dimentions scale = newDim / this.originalDimentions;
			tolerance *= scale.width;
		}

	}
}

