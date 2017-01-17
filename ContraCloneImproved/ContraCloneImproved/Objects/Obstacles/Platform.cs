using System;

namespace ContraCloneImproved
{
	public class Platform : Barrier
	{
		private bool canDrop, ground, water;
		private int reactivateInterval = 100;

		private Control activateControl;

		public Platform ()
		{
		}

		public Platform (Pair s, Pair e, Dimentions backgroundDimentions, bool g, bool wr, bool cd)
		{
			this.setCharacteristics (s, e, backgroundDimentions);
			ground = g;
			water = wr;
			canDrop = cd;
		}

		// Locates platform, platforms are located in a 2 dementional array, all members of a row possess the sime height.
		public override void interact(Sprite sprite)
		{
			Pair bottomOfSprite = sprite.getBottomCenter ();

			if (this.active && this.withinRange(Sprite.getBottomCenter)) {
				sprite.setPosition(new Pair(sprite.getPosition().x, Pair.combineCenter(start, end).y));
				sprite.setVelocity (new Pair ());
				sprite.setJumping = false;
				createReactivateTimer ();
			}
		}

		public bool withinRange(Pair location){
			bool withinRangeX = location.x () >= this.start.x - this.tolerance && location.x <= this.end.x + this.tolerance;
			bool withinRangeY = location.x () >= this.start.x - this.tolerance && location.x <= this.end.x () + this.tolerance;
			return withinRangeX && withinRangeY;
		}

		public void createReactivateTimer ()
		{
			//TODO: Reactivate platform event;
		}

		public override void resize(Dimentions newDim)
		{
			Dimentions scale = newDim / this.originalDimentions;
			tolerance *= scale.height;
		}
	}
}

