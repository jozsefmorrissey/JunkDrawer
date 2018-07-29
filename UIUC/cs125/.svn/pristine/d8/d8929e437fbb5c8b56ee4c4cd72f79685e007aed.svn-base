//UIUC CS125 FALL 2014 MP. File: RGBUtilities.java, CS125 Project: Challenge4-Photoscoop, Version: 2014-10-05T14:54:14-0500.815781667
/* Manipulates RGB values
 * 
 * Todo: Put your netid (i.e. username) in the line below
 * 
 * @author jtmorri2
 */

public class RGBUtilities {

/**
 * Extracts the red component (0..255)
 * Hint: see ch13.1.2 Working With Pixels 
 * http://math.hws.edu/javanotes/c13/s1.html#GUI2.1.2
 * 
 * ... also see the notes in READ-ME-FIRST
 * 
 * @param rgb the encoded color int
 * @return the red component (0..255)
 */
	public static int toRed(int rgb) {
		String hex=Integer.toHexString(rgb);
		//System.out.println(hex);
		
		if(hex.length() >= 6){
			hex = hex.substring(hex.length()-6, hex.length() -4);
			return (int)(Long.parseLong(hex.substring(0, 2), 16));// FIX ME
		}
			else if(hex.length() == 5)
				return (int)(Long.parseLong(hex.substring(0, 1), 16));
				
				else
					return 0;
	}

	public static int toGreen(int rgb) {
		
		
		String hex=Integer.toHexString(rgb);
		if(hex.length() >= 4){
			hex = hex.substring(hex.length()-4, hex.length() -2);
			return (int)(Long.parseLong(hex.substring(0, 2), 16));// FIX THIS
		}
			else if(hex.length() == 3){
				return (int)(Long.parseLong(hex.substring(0, 1), 16));
			}
				else
					return 0;
	}

	public static int toBlue(int rgb) {
		String hex=Integer.toHexString(rgb);
		//System.out.println(hex.length());
		//System.out.println(hex);
		if(hex.length() >= 2){
			hex = hex.substring(hex.length()-2, hex.length());
			//System.out.println(hex);
			return (int)(Long.parseLong(hex.substring(0, 2), 16));// FIX THIS
		}
			else if(hex.length() == 1)
				return (int)(Long.parseLong(hex.substring(hex.length()-1, hex.length()), 16));
			
				else
						return 0;
	}

	/**
	 * 
	 * @param r the red component (0..255)
	 * @param g the green component (0..255)
	 * @param b the blue component (0..255)
	 * @return a single integer representation the rgb color (8 bits per component) rrggbb
	 */
	static int toRGB(int r, int g, int b) {
		String hex = "";
		//System.out.println("\n" + r + "\t" + g + "\t"+ b);
		if(r < 15)
			hex+= "0";
		hex += Integer.toHexString(r);
		//System.out.println(hex);
		if(g < 15)
			hex+= "0";
		hex += Integer.toHexString(g);
		//System.out.println(hex);
		if(b < 15)
			hex+= "0";
		hex += Integer.toHexString(b);
		//System.out.println(hex);
		
		return Integer.parseInt(hex, 16); // FIX THIS
	}

}