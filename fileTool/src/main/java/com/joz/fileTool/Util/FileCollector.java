package com.joz.fileTool.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FileCollector {

	private File node;
	private ArrayList<String> allFiles = new ArrayList<String>();
	private Map<String, ArrayList<String>> byExt = new HashMap<String, ArrayList<String>>();
	
	public static void main(String...args) {
		FileCollector fc = new FileCollector(new File("C:/Users/"));
		System.out.println(FileNameParser.getByExt(fc.getAllFiles(), "properties"));
	}

	private static void genatePropertyValues() {
		String chars = "0123456789_-...--__abcdefghijklmnopqrstuvwxyz"
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(int i = 0; i < Math.random()*250 + 100; i++) {
			String key = "";
			String value = "";
			for(int j = 0; j < Math.random()*32; j++)
				key += chars.charAt((int) (Math.random()*chars.length()));
			for(int j = 0; j < Math.random()*250; j++)
				value += chars.charAt((int) (Math.random()*chars.length()));
			System.out.println(key + "=" + value);
		}
	}
	
	public FileCollector(File node) {
		search(node);

	}
	
	public ArrayList<String> getAllFiles() {
		return allFiles;
	}
	
	private void search(File node) {
		String[] ignore = new String[]{"fileTool", ".metadata", ".recommenders", "RemoteSystemsTempFiles"};

		boolean traverse = true;
		for(int i = 0; i < ignore.length; i++) {
			if(node.getName().equals(ignore[i]))
				traverse = false;
		}
		
		if(traverse && node.isDirectory()){
			String[] subNote = node.list();
			if(subNote != null) {
				for(String filename : subNote){
					search(new File(node, filename));
				}
			}
		}
		if(node.isFile()) {
			sortFile(node);
		}
	}

	private void sortFile(File node) {
		try {

			String filePath = node.getCanonicalPath();
			allFiles.add(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
