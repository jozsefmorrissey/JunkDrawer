using System;

namespace ContraCloneImproved
{
	public class Level2 : Level
	{
		public Level2 () : base ("Level2")
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

