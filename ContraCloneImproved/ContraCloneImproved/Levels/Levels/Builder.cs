using System;

namespace ContraCloneImproved
{
	public class Builder : Level
	{
		public Builder (string fileName) : base(fileName)
		{
			Hero target = new Hero ("target");
			sprites.Add (target);
			viewTargets.Add (target);

			BuilderControl control = new BuilderControl (target);
			window.addControl ((int)BuilderControlEnum.REFRESH_RATE, control);
		}

		protected override void createEnemys(){}
		protected override void createPlatforms(){}
		protected override void createWalls(){}
		protected override void createItems(){}
	}
}

