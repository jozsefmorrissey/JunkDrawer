package com.joz.fileTool.property;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.joz.fileTool.Util.FileCollector;
import com.joz.fileTool.Util.FileNameParser;
import com.joz.fileTool.comparator.CmpName;
import com.joz.fileTool.property.entities.Property;

public class PropFileInterperter {

	public static void main(String...args) {
		PropFileInterperter pfi = new PropFileInterperter();
		String fileName = pfi.propertyFiles.get(0).getFilePath();

		pfi.propertyFiles.get(0).setProperty("YOF4LL", "NOLO");
		pfi.propertyFiles.get(0).setProperty("YOF4LL", "hello");
		pfi.setProperty(fileName, "YOF4LL", "holo");

		pfi.overWrite(fileName);
		System.out.println(ValidateProperties.findBadPaths());
		System.out.println(PropertyOrganizer.getProperty("YOF4LL"));
		System.out.println(pfi.propertyFiles.get(0));

	}
	
	public void save(String fileName) {
		int index = propertyFiles.indexOf(new PropFile(fileName));
		if(index >= 0)
			propertyFiles.get(index).save();
	}
	
	public void overWrite(String fileName) {
		for(PropFile pf : propertyFiles) {
			if(pf.getFilePath().equals(fileName)) {
				pf.overWrite();
				break;
			}
		}
	}
	
	public void setProperty(String fileName, String name, String value) {
		int index = propertyFiles.indexOf(new PropFile(fileName));
		PropertyOrganizer.getProperty(name, fileName);
		if(index >= 0)
			propertyFiles.get(index).setProperty(name, value);
	}
	
	private String directory;
	private List<PropFile> propertyFiles = new ArrayList<PropFile>();
	
	public PropFileInterperter() {
		directory = "../";
		initialize();
	}
	
	public PropFileInterperter(String directory) {
		this.directory = directory;
		initialize();
	}
	
	public List<String> getAllFileNames() {
		List<String> fileNames = new ArrayList<String>();
		for(PropFile pf : propertyFiles) {
			fileNames.add(pf.getFilePath());
		}
		Collections.sort(fileNames);
		return fileNames;
	}
	
	public Map<String, ArrayList<String>> getFilesByKeyWord(Collection<String> keyWords) {
		List<String> files = getAllFileNames();
		return FileNameParser.getByKeyWord(files, keyWords.toArray(new String[0]));
	}
	
	private void initialize() {
		FileCollector fc = new FileCollector(new File(directory));
		List<String> propFileNames = FileNameParser.getByExt(fc.getAllFiles(), "properties");
		for(String fileName: propFileNames)
			propertyFiles.add(new PropFile(fileName));
	}
	
	public Map<String, ArrayList<Property>> fileToPropMap() {
		Map<String, ArrayList<Property>> retVal = new HashMap<String, ArrayList<Property>>();
		for(PropFile p : propertyFiles) {
			if(!retVal.containsKey(p.getFilePath()))
				retVal.put(p.getFilePath(), new ArrayList<Property>());
			
			Set<String> keys = p.keySet();
			for(String key : keys) {
				String value = p.getProperty(key);
				Property prop = new Property(key, value);

				retVal.get(p.getFilePath()).add(prop);
			}
		}
		return retVal;
	}
	
	public Map<Property, List<String>> propToFileMap() {
		List<Property> props = getAllProperties();
		Map<Property, List<String>> map = new HashMap<Property, List<String>>();
		
		for(Property p : props) {
			List<String> files = PropertyOrganizer.getFilesByProperty(p);
			map.put(p, files);
		}
		
		return map;
		
	}
	
	/**
	 * Gets the properties represented in all files. If there are
	 * multiple files with same property, default value is substituted...
	 * (note: default value many not be represented in either file)
	 * @return
	 */
	public List<Property> getAllProperties() {
		List<String> fileNames = this.getAllFileNames();
		List<Property> props	= new ArrayList<Property>();
		for(String fn : fileNames) {
			Collection<Property> cp = PropertyOrganizer.getPropertyByFileName(fn);
			for(Property p : cp) {
				props.add(p);
			}
		}
		Collections.sort(props, new CmpName());
		return props;
	}
	
	
	
	private void printVars() {
		List<Property> props = getAllProperties();
		for(Property p : props) {
				System.out.println("Key: " + p.getName() + "\tValue: " + p.getValue());
		}
	}
	
	
}
