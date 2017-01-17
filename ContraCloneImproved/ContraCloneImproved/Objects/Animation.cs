using System;
using System.Collections.Generic;
using System.Drawing;

namespace ContraCloneImproved
{
	public class Animation
	{
		private List<Image>[] images;
		protected int index;
		protected animStateEnum state;

		protected Sprite player;

		public Animation (String characterFileName, Dimentions size, Sprite p)
		{
			player = p;
			state = animStateEnum.IDLE_RIGHT;	

			characterFileName += "/";
			images = new List<Image>[7];

			addSprites (ref images[(int)animImageEnum.WALK_IMAGES], characterFileName + "run", size);	
			addSprites (ref images[(int)animImageEnum.JUMP_IMAGES], characterFileName + "jump", size);
			addSprites (ref images[(int)animImageEnum.LAND_IMAGES], characterFileName + "land", size);
			addSprites (ref images[(int)animImageEnum.TUCK_IMAGES], characterFileName + "tuck", size);
			addSprites (ref images[(int)animImageEnum.IDLE_IMAGES], characterFileName + "idle", size);
			addSprites (ref images[(int)animImageEnum.CROUCH_IMAGES], characterFileName + "crouch", size);
			addSprites (ref images[(int)animImageEnum.DIE_IMAGES], characterFileName + "dead", size);
		}

		protected void addSprites(ref List<Image> arrayImages, String fileNamePrefix, Dimentions size)
		{
			bool spriteFound = true;
			while (spriteFound) 
			{
				//TODO: create standardized file name system and automate image import.
				String imagePath = System.IO.Directory.GetCurrentDirectory () + "/../../Images/cropped_contra_images/" + fileNamePrefix + (count + 1) + ".png";
				try
				{
					Image image = Image.FromFile (imagePath, true);
					image = Utilities.ScaleImage (image, size);
					arrayImages.Add(image);
				}
				catch 
				{
					spriteFound = false;
				}
			}
		}

		public Image updateAnim(animStateEnum state)
		{
			if (this.state != state) 
			{
				index = 0;
				this.state = state;
			}

			if ((int)this.state % 2 == 0)
				return updateFaceRight;
			else
				return updateFaceLeft;
			}

		private Image updateFaceRight(animStateEnum state)
		{
			switch ((int)state) 
			{
				case (int)animStateEnum.WALK_RIGHT:
					return getImage (images[(int)animImageEnum.WALK_IMAGES]);

				case (int)animStateEnum.JUMP_RIGHT:
					return getImage (images [(int)animImageEnum.JUMP_IMAGES]);

				case (int)animStateEnum.TUCK_RIGHT:
					return getImage (images[(int)animImageEnum.TUCK_IMAGES]);

				case (int)animStateEnum.CROUCH_RIGHT:
					return getImage (images[(int)animImageEnum.CROUCH_IMAGES]);

				case (int)animStateEnum.DIE_RIGHT:
					return getImage(images[(int)animImageEnum.DIE_IMAGES]);

				case (int)animStateEnum.IDLE_RIGHT:
					return getImage(images[(int)animImageEnum.IDLE_IMAGES][index++]);
			}
			return null;
		}

		private Image getImage(List<Image> images)
		{
			if (index > images.Count) {
				index = 0;

				//kills player at the end of death animation.
				if (images == this.images[(int)animImageEnum.DIE_IMAGES]){
					player.kill ();

					//TODO: figure out if neccisary.
					return images [images.Count - 1];
				}
			}

			return images [index++];
		}

		private Image updateFaceLeft(animStateEnum state){
			switch ((int)state) 
			{
			case (int)animStateEnum.WALK_LEFT:
				return flipImage (images[(int)animImageEnum.WALK_IMAGES]);

			case (int)animStateEnum.JUMP_LEFT:
				return flipImage (images[(int)animImageEnum.JUMP_IMAGES]);

			case (int)animStateEnum.TUCK_LEFT: 
				return flipImage (images[(int)animImageEnum.TUCK_IMAGES]);

			case (int)animStateEnum.CROUCH_LEFT:
				return flipImage (images[(int)animImageEnum.CROUCH_IMAGES]);

			case (int)animStateEnum.DIE_LEFT:
				return flipImage (images[(int)animImageEnum.DIE_IMAGES]);

			case (int)animStateEnum.IDLE_LEFT:
				return flipImage (images[(int)animImageEnum.IDLE_IMAGES]);

			case (int)animStateEnum.LAND_RIGHT:
				return flipImage (images[(int)animImageEnum.LAND_IMAGES]);
			}
			return null;
		}

		private Image flipImage(List<Image> images, bool fr)
		{
				Image image = getImage (images);
				image.RotateFlip (RotateFlipType.Rotate180FlipY);

			return image;
		}
	}
}

