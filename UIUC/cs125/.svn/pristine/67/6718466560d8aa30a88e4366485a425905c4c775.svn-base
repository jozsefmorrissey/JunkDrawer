//UIUC CS125 FALL 2014 MP. File: Effects.java, CS125 Project: Challenge4-Photoscoop, Version: 2014-10-05T14:54:14-0500.815781667
/*A class to delegate each effect request.
 * The process method is called whenever the user selects a menu item.
 * However it wouldnt be hard to build a batch program that also uses this process method.
 * 
 * Todo: Put your netid (i.e. username) in the line below
 * 
 * @author jtmorri2
 */
public class Effects {
	/**
	 * Returns a new image (2D array) based on the command and the source parameters.
	 * This method delegates all of the work to PixelEffects class
	 * @param cmd - the command to execute
	 * @param source - the primary image source (not changed)
	 * @param background - the secondary (background) image (not changed)
	 * @return the new image or null if the command failed.
	 */
	public static int[][] process(String cmd, int[][] source, int[][] background) {
		// The basic form of this method is one long if-else if else if....
		// To turn a string into a particular method call

		//if(cmd is ___ call some PixelEffects.____ method )
		//else if ... (repeat for all strings
		switch (cmd) {
		case "half":
			return PixelEffects.half(source);
			
			
		case "rotate":
			return PixelEffects.rotateLeft(source);
			
			
		case "flip":
			return PixelEffects.flip(source);
			
			
		case "redeye":
			return PixelEffects.redeye(source, background);
			

		case "mirror":
			return PixelEffects.mirror(source);
			
		
		case "merge":
			return PixelEffects.merge(source, background);
			
			
		case "funky":
			return PixelEffects.funky(source, background);
			
			
		case "resize":
			return PixelEffects.resize(source, background);
			

		case "key":
			return PixelEffects.chromaKey(source, background);
			
		default:
			PixelEffects.resize(source, background);
		}
		// See the end of Photoscoop.java for a list strings
		// For example the Stenography ones should be ...
		//e.g.  if(cmd.equals("hide")) return Stenography.hide(source,background);
		//e.g. else if(cmd.equals("extract")) return Stenography.extract(source);
		// And add the others for PixelEffects
		
		// hint: for some commands the string is slightly different than the method name
		// hint: remember to use string.equals not == to compare strings
		// This message and the exception should not occur if you match all possible command strings
		System.out.println("Todo: Implement Effects.process("+cmd+")");
		throw new RuntimeException("Unknown command:"+cmd);
	}// end method

} // end class
