package com.joz.fileTool.Util;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joz.fileTool.property.PropertyOrganizer;
import com.joz.fileTool.property.entities.Property;

public class Configuration {

	private String fileName;
	private String ext = "config";
	private ArrayList<Property> properties = new ArrayList<Property>();
	
	StoredPreferences storedPref = null;
	private String parent = null;
	
	public Configuration(String fileName){ 
		this.fileName = fileName;
	}
	
	public Configuration(String fileName, String parent, StoredPreferences storedPref){ 
		this.fileName = fileName;
		this.parent = parent;
		this.storedPref = storedPref;
	}
	
	public String getFileName() {
		return fileName + "." + ext;
	}
	
	public Property getProperty(String name) {
		return getProperty(new Property(name, null));
	}
	
	public Property getProperty(Property p) {
		//Return a property that this object has
		int index = properties.indexOf(p);
		if(index >= 0)
			return properties.get(index);
		
		//Return a property form the configuration hierarchy
		if(parent != null)
			return getParentConfig().getProperty(p);
				
		//Consult the collection of all properties
		String value = PropertyOrganizer.getProperty(p.getName());
		if(value != null)
			return new Property(p.getName(), value);
		else
			return null;
	}
	
	public ArrayList<Property> getProperties() {
		return properties;
	}

	public void setProperties(ArrayList<Property> properties) {
		this.properties = properties;
	}

	@JsonIgnore
	public Configuration getParentConfig() {
		return storedPref.getConfiguration(parent);
	}
	
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
}
