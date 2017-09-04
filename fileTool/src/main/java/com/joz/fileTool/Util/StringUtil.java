package com.joz.fileTool.Util;

import java.util.ArrayList;
import java.util.Arrays;

public class StringUtil {
	
	public static void main(String...args) {
		System.out.println(Arrays.toString(disect("this-is.the-Song_that/never\\ends", "/","-", "\\.", "_", ",","\\\\")));
	}
	
	/**
	 * This function uses regex split method. If results are not
	 * as intended consult regex format documentation.
	 * (https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/RegExp)
	 * 
	 * @param str
	 * @param cutPoints
	 * @return
	 */
	public static String[] disect(String str, String...cutPoints) {
		ArrayList<String> bits = new ArrayList<String>();
		bits.add(str);
		for(String cp : cutPoints) {
			ArrayList<String> newBits = new ArrayList<String>();
			for(String b : bits) {
				String[] arrBits = b.split(cp);
				if(arrBits.length > 0)
					newBits.addAll(Arrays.asList(arrBits));
				else
					newBits.add(b);
			}
			bits = newBits;
		}
		return bits.toArray(new String[0]);
	}
}
