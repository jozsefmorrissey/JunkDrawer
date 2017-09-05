package com.joz.fileTool.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.joz.fileTool.property.entities.PropInstInfo;
import com.joz.fileTool.property.entities.Property;
import com.joz.fileTool.property.entities.ValueFilePair;

public class PropertyOrganizer {
	private static ArrayList<PropertyOrganizer> properties = new ArrayList<PropertyOrganizer>();
	
	String name;
	ArrayList<ValueFilePair> vfp = new ArrayList<ValueFilePair>();
	
	private PropertyOrganizer(String name){this.name = name;}
	
	private PropertyOrganizer(String name, String value, String filePath, int lineNumber) {
			this.name = name;
			vfp.add(new ValueFilePair(value, filePath, lineNumber, true));
	}
	
	/**
	 * 
	 * @param name
	 * @param filePath
	 * @return - 1) The Property corresponding to the file path if it exists
	 * 			 2) Null if property can not be found.
	 */
	public static String getProperty(String name, String filePath) {
		String value = getProperty(name);
		PropertyOrganizer pO = getPO(name);
		ValueFilePair v = getVFP(pO, filePath);
		if(v == null || value == null || pO == null)
			return null;

		else {
			return v.getValue();
		}
	}
	
	public static List<String> getFilesByProperty(Property property) {
		ArrayList<ValueFilePair> vfpList = getPO(property.getName()).vfp;
		List<String> files = new ArrayList<String>();
		for(ValueFilePair v : vfpList) {
			files.add(v.getFile());
		}
		return files;
	}
	
	/**
	 * 
	 * @param name
	 * @return - The track PropertyOrganizer corresponding to the name.
	 * 			null if no such organizer is found.
	 */
	private static PropertyOrganizer getPO(String name) {
		PropertyOrganizer p = new PropertyOrganizer(name);
		int index = properties.indexOf(p);
		
		if(index < 0 || name == null)
			return null;

		return properties.get(index);
	}
	
	/**
	 * 
	 * @param pO
	 * @param filePath
	 * @return - The ValueFilePair corresponding to the filePath.
	 * 			null if no such pair is found.
	 */
	private static ValueFilePair getVFP(PropertyOrganizer pO, String filePath) {
		if(pO == null || filePath == null)
			return null;
		
		ValueFilePair v = new ValueFilePair(null, filePath, 0, false);
		int index = pO.vfp.indexOf(v);
		if(index < 0)
			return null;
		return pO.vfp.get(index);
	}
	
	
	/** If property cannot be found new PropertyOrganizer is created and added 
	 * to those that are be ing tracked. If property is found with value 
	 * corresponding to the given file, value may be updated. If property 
	 * found but no link to file link is created with given value.
	 * 
	 * @param name
	 * @param value
	 * @param filePath
	 * @param def
	 * @return - value.
	 */
	public static String getProperty(String name, String value, String filePath, int lineNumber, boolean def) {
		if(name == null || filePath == null)
			return null;
		
		PropertyOrganizer pO = getPO(name);
		if(pO != null) {
			ValueFilePair pair = getVFP(pO, filePath);
			if(pair != null){
				if(pair.getValue() != value)
					pair.setValue(value);
			}
			else {
				pO.addValueFilePair(value, filePath, lineNumber, false);
			}
		}
		else {
			pO = new PropertyOrganizer(name);
			pO.addValueFilePair(value, filePath, lineNumber, false);
			properties.add(pO);
		}
		
		if(def)
			pO.setDefault(filePath);
		
		return value;
	}
	
	/** If property cannot be found new PropertyOrganizer is created and added 
	 * to those that are be ing tracked. If property is found with value 
	 * corresponding to the given file, value may be updated. If property 
	 * found but no link to file link is created with given value.
	 * 
	 * @param name
	 * @param value
	 * @param filePath
	 * @param def
	 * @return - value.
	 */
	public static String getProperty(String name, String value, String filePath, int lineNumber) {
		return getProperty(name, value, filePath, lineNumber, false);
	}
	
	/**
	 * Gets get default value for property if property is being tracked.
	 * 
	 * @param name
	 * @return - default value or null if no property is found.
	 */
	public static String getProperty(String name) {
		PropertyOrganizer po = getPO(name);
		if(po == null)
			return null;
		
		String retVal = null;
		for(ValueFilePair v : po.vfp) {
			if(v.isDef() || retVal == null) 
				retVal = v.getValue();
		}
		return retVal;
	}
	
	public static Collection<Property> getPropertyByFileName(String fileName) {
		ArrayList<Property> props = new ArrayList<Property>();
		for(PropertyOrganizer po : properties) {
			for(ValueFilePair pair : po.vfp) {
				if(pair.getFile().equals(fileName))
					props.add(new Property(po.name, pair.getValue()));
			}
		}
		return props;
	}
	
	public static List<PropInstInfo> getVFPByFileName(String fileName) {
		ArrayList<PropInstInfo> props = new ArrayList<PropInstInfo>();
		for(PropertyOrganizer po : properties) {
			for(ValueFilePair pair : po.vfp) {
				if(pair.getFile().equals(fileName))
					props.add(new PropInstInfo(po.name, pair));
			}
		}
		return props;
	}
	
	public static List<PropInstInfo> getAllPII() {
		ArrayList<PropInstInfo> props = new ArrayList<PropInstInfo>();
		for(PropertyOrganizer po : properties) {
			for(ValueFilePair pair : po.vfp) {
				props.add(new PropInstInfo(po.name, pair));
			}
		}
		return props;
	}
	
	
	/**
	 * Creates and adds new value pair or updates existing pair corresponding
	 * to the given file path.
	 * 
	 * @param value
	 * @param filePath
	 * @param def
	 */
	private void addValueFilePair(String value, String filePath, int lineNumber, boolean def) {
		ValueFilePair pair = new ValueFilePair(value, filePath, lineNumber, def);
		int index = vfp.indexOf(pair);
		if(index < 0){
			vfp.add(pair);
		}
		else {
			vfp.get(index).setValue(value);
		}
		
		if(def)
			setDefault(filePath);
	}
	
	/**
	 *  Sets the indicated filePath as the default value if file path has a value.
	 *  
	 * @param filePath
	 */
	public void setDefault(String filePath) {
		ValueFilePair vd = getVFP(this, filePath);
		
		if(vd == null)
			return;
		
		for(ValueFilePair v : vfp) {
			v.setDef(false);
		}
		vd.setDef(true);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		PropertyOrganizer other = (PropertyOrganizer) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		
		return true;
	}
}
