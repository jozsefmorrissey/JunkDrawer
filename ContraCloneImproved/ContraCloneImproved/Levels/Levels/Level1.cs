using System;
using System.Drawing;

namespace ContraCloneImproved
{
	public class Level1 : Level
	{
		public Level1 () : base ("Level1")
		{
			Character hero = new Hero (200, 0);
			this.sprites.Add (Character);
			this.viewTargets.Add (hero);



			GameControl control = new GameControl (hero);
			window.addControl ((int)GameControlEnum.REFRESH_RATE, control);
		}

		protected override void createEnemys()
		{
			
		}

		protected override void createPlatforms()
		{

		}

		protected override void createWalls()
		{

		}

		protected override void createItems()
		{

		}
	}
}

