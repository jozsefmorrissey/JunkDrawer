using System;
using System.Windows.Forms;
using System.Drawing;

namespace ContraCloneImproved
{
	public class Character : Sprite
	{
		// Scaled parameters
		private Pair jumpForce = new Pair();

		private bool visible;
		private Pair draw;
		Animation animation;

		// Used to determine directions character is facing/shooting
		private View view;

		private Platform currentPlatform;
		private Weapon weapon;

		public abstract void move (int rightLeft, int upDown){}

		public void setWeapon(Weapon w){ weapon = w;}
		public void setJumpForce(Pair jf){ jumpForce = jf;}

		public View getView () { return this.view;}
		public void setView (View v) { this.view = v;}

		public Platform getPlatform () { return this.currentPlatform;}
		public void setPlatform (Platform plat) {this.currentPlatform = plat;}

		public void jump (){this.addForce (jumpForce);}

		public override void resize(Dimentions newSize){ 
			jumpForce *= newSize;
			base.resize (newSize);
		}

	}
}

