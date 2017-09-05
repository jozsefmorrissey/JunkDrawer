package com.joz.fileTool.property;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.joz.fileTool.Util.PathFileValidation;
import com.joz.fileTool.property.entities.PropInstInfo;
import com.joz.fileTool.property.entities.ValueFilePair;

public class ValidateProperties {
	
	public static List<PropInstInfo> findBadPaths() {
		List<PropInstInfo> badPaths = new ArrayList<PropInstInfo>();
		List<PropInstInfo> piiList = variabless();
		
		for(PropInstInfo pii : piiList) {
			String value = pii.getValue();
			if(value == null)
				continue;
			
			if(value.contains("\\") 
					&& value.contains(".") 
					&& !value.startsWith("http")) {
				if(!PathFileValidation.isFileOrDir(value)) {
					badPaths.add(pii);
				}
			}
		}
		return badPaths;
	}
	
	public static List<PropInstInfo> variabless() {
		List<PropInstInfo> variabless = new ArrayList<PropInstInfo>();
		List<PropInstInfo> piiList = PropertyOrganizer.getAllPII();
		for(PropInstInfo pii : piiList) {
			String value = pii.getValue();
			if(value == null)
				continue;
			
			value = replaceVars(value, pii);

			ValueFilePair vfp = new ValueFilePair(value, pii.getFile(), pii.getLineNumber(), pii.isDef());
			variabless.add(new PropInstInfo(pii.getName(), vfp));
		}
		return variabless;
	}

	private static String replaceVar(PropInstInfo pii, String value, int index) {
		String prefix = value.substring(0, index);
		String var = value.substring(index + 2);

		index = var.indexOf("}");
		String suffix;
		if(index + 1 >= var.length())
			suffix = "";
		else
			suffix = var.substring(index + 1);
	
		if(index >= 0) {
			var = var.substring(0, index);
			var = PropertyOrganizer.getProperty(var, pii.getFile());
		}
		else
			var = "";
		return prefix + var + suffix;
	}
	
	public List<PropInstInfo> getVariablePaths() {
		List<PropInstInfo> varPaths = new ArrayList<PropInstInfo>();
		
		List<PropInstInfo> allFiles = PropertyOrganizer.getAllPII();
		HttpURLConnection connection;
		for(PropInstInfo pii : allFiles) {
			if(pii.getValue().contains("${"))
				varPaths.add(pii);
		}
		return varPaths;
	}
	
	public static List<PropInstInfo> invalidUrls() {
		List<PropInstInfo> badUrls = new ArrayList<PropInstInfo>();
		
		List<PropInstInfo> allFiles = PropertyOrganizer.getAllPII();
		HttpURLConnection connection;
		for(PropInstInfo pii : allFiles) {
			String value = pii.getValue();
			if(value == null)
				continue;
			
			value = replaceVars(value, pii);
			
			if(!value.startsWith("http"))
				continue;
			
			try {
				connection = (HttpURLConnection) new URL("http://www.google.com").openConnection();
				connection.setRequestMethod("HEAD");
				int responseCode = connection.getResponseCode();
				if (responseCode != 200) {
				    badUrls.add(pii);
				}
			} catch (IOException e) {
				badUrls.add(pii);
			}
		}
		return badUrls;
	}
	
	private static String replaceVars(String value, PropInstInfo pii) {
		int index = value.indexOf("${");
		while(index >= 0) {
			value = replaceVar(pii, value, index);
			index = value.indexOf("${");
		}
		return value;
	}
}
