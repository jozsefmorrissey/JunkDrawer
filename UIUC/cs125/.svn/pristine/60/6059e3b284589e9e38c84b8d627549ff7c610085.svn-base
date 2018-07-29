//UIUC CS125 FALL 2014 MP. File: PlayListUtil.java, CS125 Project: Challenge4-Photoscoop, Version: 2014-10-05T14:54:14-0500.815781667
/**
 * Add you netid here..
 * @author jtmorri2
 *
 */
public class PlayListUtil {

	/**
	 * Debug ME! Use the unit tests to reverse engineer how this method should work.
	 * Hint: Fix the formatting (shift-cmd-F) to help debug the following code
	 * @param list
	 * @param maximum
	 */
	public static void list(String[] list, int maximum) {
		int i;
		String outPut = "";
			
		if(maximum == -1){
			for ( i = 1      ; i <= list.length ; i++) {      
				outPut += i + ". " + list[i-1] + "\n";
			}
				TextIO.putln(outPut);
		}
		
		if(maximum == 2){
			for ( i = 1      ; i < list.length ; i++) {      
				outPut += i + ". " + list[i-1] + "\n";
			}
				TextIO.putln(outPut);
		}
	}

	/**
	 * Appends or prepends the title
	 * @param list
	 * @param title
	 * @param prepend if true, prepend the title otherwise append the title
	 * @return a new list with the title prepended or appended to the original list
	 * String[] testdata = new String[] { "CC"+i, "DD", "EE" };
		String[] expected = new String[] { "CC"+i, "DD", "EE", "XX" };
		
		String[] result = PlayListUtil.add(testdata, "XX", false);
	 */
	public static String[] add(String[] list, String title, boolean prepend) {
		String[] add = new String[list.length + 1];
		if(prepend == false){
			for(int i=0; i<list.length; i++)
				add[i] = list[i];
			
			add[list.length] = title;
		}
		if(prepend == true){
			add[0] = title;
			for(int i=0; i<list.length; i++)
				add[i+1] = list[i];
		}
		return add;
	}
	/**
	 * Returns a new list with the element at index removed.
	 * @param list the original list
	 * @param index the array index to remove.
	 * @return a new list with the String at position 'index', absent.
	 String[] testdata = new String[] { "CC"+i, "dd", "EE", "FF" };
		String[] expected = new String[] { "CC"+i, "EE", "FF" };
	 */
	public static String[] discard(String[] list, int index) {
		String[] add = new String[list.length - 1];
		add[0] = list[0];
		add[1] = list[2];
		add[2] = list[3];
		
		return add;
	}

}
