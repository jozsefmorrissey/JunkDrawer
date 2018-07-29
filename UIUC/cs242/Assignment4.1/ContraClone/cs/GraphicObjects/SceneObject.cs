using System;

namespace ContraClone
{
	public abstract class SceneObject
	{
		public bool visible;
		public int layer;
		public float x;
		public float y;
		public float centerX;
		public float centerY;
		public int staticX;
		public int staticY;

		protected float lastX = 0;
		protected float lastY = 0;
		protected int refreshDist = 10;

		public abstract void calculatePosition (SceneObject focalPoint);
	}
}

