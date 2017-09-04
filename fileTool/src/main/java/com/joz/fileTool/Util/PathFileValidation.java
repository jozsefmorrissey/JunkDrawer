package com.joz.fileTool.Util;

import java.io.File;

public class PathFileValidation {

	public static boolean isDirectory(String filePath) {
		File f = new File(filePath);
		if(f.isDirectory())
			return true;
		return false;
	}
	
	public static boolean isFile(String filePath) {		
		File f = new File(filePath);
		if(f.isFile())
			return true;
		return false;
	}
	
	public static boolean isFileOrDir(String filePath) {
		return isFile(filePath) || isDirectory(filePath);
	}
}
