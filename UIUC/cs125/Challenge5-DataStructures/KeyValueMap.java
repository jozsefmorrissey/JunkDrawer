//UIUC CS125 FALL 2014 MP. File: KeyValueMap.java, CS125 Project: Challenge5-DataStructures, Version: 2014-10-12T12:12:55-0500.784759526
/*
 * @author jtmorri2
 */
import java.awt.Color;

public class KeyValueMap { // aka Dictionary or Map
	
	private Color[] color = new Color[20];
	private String[] colorArray = new String[20];
	private int id;
	private int count;
	/**
	 * Adds a key and value. If the key already exists, it replaces the original
	 * entry.
	 */
	public void add(String key, Color value) {
		
		id=count++;
		colorArray[id]=key;
		color[id]=value;
	}

	/**
	 * Returns particular Color object previously added to this map.
	 */
	public Color find(String key) {
		for(int i=0; i<count; i++)
			if(key.equals(colorArray[i]))
				return color[i];
		return null;
		
	}

	/**
	 * Returns true if the key exists in this map.
	 */
	public boolean exists(String key) {
		for(int i=0; i<count; i++)
			if(key.equals(colorArray[i]))
			return true;
		return false;
		
	}

	/**
	 * Removes the key (and the color) from this map.
	 */
	public void remove(String key) {
		for(int i=0; i<count; i++)
			if(key.equals(colorArray[i]))
				colorArray[i]=null;
	}

}
