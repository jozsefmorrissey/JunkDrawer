package com.joz.fileTool.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileNameParser {
	
	public static List<String> getByExt(Collection<String> fileNames, String...extensions) {
		ArrayList<String> retVal = new ArrayList<String>();
		for(String fileName : fileNames) {
			for(String extension : extensions) {
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
				if(ext.equals(extension))
					retVal.add(fileName);
			}
		}
		Collections.sort(retVal);
		return retVal;
	}
	
	public static Map<String, ArrayList<String>> getByKeyWord(Collection<String> fileNames, String...keyWords) {
		HashMap<String, ArrayList<String>> retVal = new HashMap<String, ArrayList<String>>();
		String[] cutPoints = new String[]{"-", "\\.", "_", ",","\\+", "\\\\"};
		for(String fileName : fileNames) {
			String[] bits = StringUtil.disect(fileName, cutPoints);
			for(String bit : bits) {
				for(String extension : keyWords) {
					if(extension.contains(bit)){
						ArrayList<String> mapList = retVal.get(extension);
						if(mapList == null){
							mapList = new ArrayList<String>();
							retVal.put(extension, mapList);
						}
						mapList.add(fileName);
					}
				}				
			}
		}
		return retVal;
	}
}
