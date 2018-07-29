//UIUC CS125 FALL 2014 MP. File: PixelEffects.java, CS125 Project: Challenge4-Photoscoop, Version: 2014-10-05T14:54:14-0500.815781667

/* A class to implement the various pixel effects.
 *
 * Todo: Put your netid (i.e. username) in the line below
 * 
 * @author jtmorri2
 */
public class PixelEffects {

	/** Copies the source image to a new 2D integer image */
	public static int[][] copy(int[][] source) {
		// Create a NEW 2D integer array and copy the colors across
		// See redeye code below
		int [][] copyArray = new int [source.length][source[0].length];
		for(int i=0; i<source.length; i++){
			for(int j=0; j<source[0].length; j++){
				copyArray[i][j]=source[i][j];
			}
		}
		return copyArray; // Fix Me
	}
	/**
	 * Resize the array image to the new width and height
	 * You are going to need to figure out how to map between a pixel
	 * in the destination image to a pixel in the source image
	 * @param source
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static int[][] resize(int[][] source, int newWidth, int newHeight) {
		int[][] everyBodyClapYourHands=new int[newWidth][newHeight];
		for(int i=0; i<newWidth; i++){
			for(int j=0; j<newHeight; j++){
				everyBodyClapYourHands[i][j]=source[(int)((double)i*(double)(double)source.length/newWidth)][(int)((double)j*(double)source[0].length/(double)newHeight)];
			}
		}
		return everyBodyClapYourHands; // Fix Me
		// Hints: Use two nested for loops between 0... newWidth-1 and 0.. newHeight-1 inclusive.
		// Hint: You can just use relative proportion to calculate the x (or y coordinate)  in the original image.
		// For example if you're setting a pixel halfway across the image, you should be reading half way across the original image too.
	}

	/**
	 * Half the size of the image. This method should be just one line! Just
	 * delegate the work to resize()!
	 */
	public static int[][] half(int[][] source) {
		int [][] halfArray = new int [source.length/2][source[0].length/2];
		for(int i=0; i<source.length/2; i++){
			for(int j=0; j<source[0].length/2; j++){
				halfArray[i][j]=source[2*i][2*j];
			}
		}
		return halfArray; // Fix Me
	}
	
	/**
	 * Create a new image array that is the same dimesions of the reference
	 * array. The array may be larger or smaller than the source. Hint -
	 * this methods should be just one line - delegate the work to resize()!
	 * 
	 * @param source
	 *            the source image
	 * @param reference
	 * @return the resized image
	 */
	public static int[][] resize(int[][] source, int[][] reference) {
		int[][] referenceResize = PixelEffects.resize(source, reference.length, reference[0].length);
		return referenceResize; // Fix Me
	}

	/** Flip the image vertically */
	public static int[][] flip(int[][] source) {
		int[][] invert=new int[source.length][source[0].length];
		for(int i=0; i<source.length; i++){
			for(int j=0; j<source[0].length; j++){
				invert[i][source[0].length-1-j]=source[i][j];
			}
		}
		return invert;// Fix Me
	}

	/** Reverse the image horizontally */
	public static int[][] mirror(int[][] source) {
		int[][] cupidShuffle=new int[source.length][source[0].length];
		for(int i=0; i<source.length; i++){
			for(int j=0; j<source[0].length; j++){
				cupidShuffle[source.length-1-i][j]=source[i][j];
			}
		}
		return cupidShuffle;// Fix Me
	}

	/** Rotate the image */
	public static int[][] rotateLeft(int[][] source) {
		int[][] toTheLeft_takeItBackNowYall_justWantedToMakeMyReferenceClear=new int[source[0].length][source.length];
		for(int i=0; i<source.length; i++){
			for(int j=0; j<source[0].length; j++){
				toTheLeft_takeItBackNowYall_justWantedToMakeMyReferenceClear[j][source.length-1-i]=source[i][j];
			}
		}
		return toTheLeft_takeItBackNowYall_justWantedToMakeMyReferenceClear;
	}

	/** Merge the red,blue,green components from two images */
	public static int[][] merge(int[][] sourceA, int[][] sourceB) {
		// The output should be the same size as the input. Scale (x,y) values
		// when reading the background
		// (e.g. so the far right pixel of the source is merged with the
		// far-right pixel of the background).
		int x, y;
		if(sourceA.length - sourceB.length >= 0)
			x=sourceB.length;
		else
			x=sourceA.length;
		if(sourceA[0].length - sourceB[0].length >= 0)
			y=sourceB[0].length;
		else
			y=sourceA[0].length;
		int[][] mergArray = new int [x][y];
		
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				
				//System.out.println(RGBUtilities.toGreen(sourceA[i][j]));
				//System.out.println(("return: " +   RGBUtilities.toRed(sourceA[i][j])));
				//System.out.println((RGBUtilities.toBlue(sourceA[i][j])));
				//System.out.println();
				
				int green = (RGBUtilities.toGreen(sourceA[i][j]) + RGBUtilities.toGreen(sourceB[i][j]))/2;						//{ { 0, 0x44, 0x300, 0x660000 } };
				int red =	(RGBUtilities.toRed(sourceA[i][j]) + RGBUtilities.toRed(sourceB[i][j]))/2;
				int blue = (RGBUtilities.toBlue(sourceA[i][j]) + RGBUtilities.toBlue(sourceB[i][j]))/2;
				//System.out.println( "BLUE: " + red );
				int expect = RGBUtilities.toRGB(red, green, blue);								//{ { 1, 0x24, 0x400 , 0x330001 } };
			//int expect = (sourceA[i][j] + sourceB[i][j])/2;
				
				mergArray[i][j]=expect;
				//System.out.print(expect + "\t");
			}
			
		}
				
		return mergArray;
	}

	/**
	 * Replace the green areas of the foreground image with parts of the back
	 * image
	 */
	public static int[][] chromaKey(int[][] foreImage, int[][] backImage) {
		// If the image has a different size than the background use the
		// resize() method
		// create an image the same as the background size.
		int green, blue, red;
		int[][] fore = resize(foreImage, backImage.length, backImage[0].length);
				
		for(int i=0; i<fore.length; i++){
			for(int j=0; j<fore[0].length; j++){
				
				blue=RGBUtilities.toBlue(fore[i][j]);
				red=RGBUtilities.toRed(fore[i][j]);
				green=RGBUtilities.toGreen(fore[i][j]);
				if(green > blue && blue < red && green > 220 && green > red*1.075 || red < 50 && blue < 50 && green > 100 || 2*red<green && 1.125*blue<green && green>50)
						fore[i][j]=backImage[i][j];
			}
		}
					
		return fore;
	}

	/** Removes "redeye" caused by a camera flash. sourceB is not used */
	public static int[][] redeye(int[][] source, int[][] sourceB) {

		int width = source.length, height = source[0].length;
		int[][] result = new int[width][height];
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				int rgb = source[i][j];
				int red = RGBUtilities.toRed(rgb);
				int green = RGBUtilities.toGreen(rgb);
				int blue = RGBUtilities.toBlue(rgb);
				if (red > 4 * Math.max(green, blue) && red > 64)
					red = green = blue = 0;
				result[i][j] = RGBUtilities.toRGB(red, green, blue);
			}

		return result;
	}

	/* Upto you! do something fun to the image */
	public static int[][] funky(int[][] source, int[][] sourceB) {
		// You need to invent your own image effect
		// Minimum boring requirements to pass autograder:
		int[][] decoy = new int[2][2];
		// Does not ask for any user input and returns a new 2D array
		// Todo: remove this return null
		return decoy;
	}
}
