package com.joz.fileTool.property;

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
					System.out.println("value: " + value);
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
			
			int index = value.indexOf("${");
			while(index >= 0) {
				value = replaceVar(pii, value, index);
				index = value.indexOf("${");
			}
			ValueFilePair vfp = new ValueFilePair(value, pii.getFile(), pii.getLineNumber(), pii.isDef());
			variabless.add(new PropInstInfo(pii.getName(), vfp));
		}
		return variabless;
	}

	private static String replaceVar(PropInstInfo pii, String value, int index) {
		String prefix = value.substring(0, index);
		String var = value.substring(index + 2);
		System.out.println("var: " + var);

		index = var.indexOf("}");
		String suffix;
		if(index + 1 >= var.length())
			suffix = "";
		else
			suffix = var.substring(index + 1);
	
		if(index >= 0) {
		var = var.substring(0, index);
		System.out.println("var: " + var);
		var = PropertyOrganizer.getProperty(var, pii.getFile());
		System.out.println("var: " + var);
		}
		else
			var = "";
		return prefix + var + suffix;
	}
}
