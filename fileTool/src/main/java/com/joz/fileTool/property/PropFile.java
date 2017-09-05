package com.joz.fileTool.property;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.web.context.annotation.ApplicationScope;

import com.joz.fileTool.property.entities.PropInstInfo;
import com.joz.fileTool.property.entities.Property;

import lombok.Data;

@ManagedBean
@ApplicationScope
@Data
public class PropFile implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6313434005286996755L;
	private int maxCharCount = 5000;

	public static void main(String...Args) throws FileNotFoundException {
		PropFile pf = new PropFile("C:\\Users\\jozse\\workspace1\\fileTool\\src\\main\\resources\\application.properties");
		

		OutputStream os = new FileOutputStream("garb.properties");
		pf.save(os);
	
	}
	
	private String filePath;
	private String seperator = null;
	
	public PropFile (String filePath, String seperator) {
		this.filePath = filePath;
		this.seperator = seperator;
		
		loadFile(filePath);
	}
	
	public PropFile (String filePath) {
		this.filePath = filePath;
		
		loadFile(filePath);
	}
	
	private void loadFile(String filePath){
		try {
			InputStream stream = new FileInputStream(filePath);
			this.load(stream);
		} catch (IOException e) {
			Logger log = Logger.getRootLogger();
			log.error("Application failed to load '" + filePath + "'");
			log.trace(e.getStackTrace());
		}
		this.save();
	}

	public synchronized void load(InputStream inStream) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
		String line = in.readLine();
		
		if(seperator == null) {
			in.mark(maxCharCount);
			setSeperator(in);
			in.reset();		
		}

		Property prop = new Property();
		int lineCounter = 0;
		while(line != null){
			if(line.startsWith("#") || 
					!parseProperty(line, prop, lineCounter)){
				if(prop.getName() != null){
					setProperty(prop, lineCounter);
				}
				setProperty(new Property(line, null), lineCounter);
			}
			line = in.readLine();
			lineCounter++;
		}
		if(prop.getName() != null){
			setProperty(prop, lineCounter);
		}
	}
	
	private void setProperty(Property prop, int lineNumber) {
		this.setProperty(prop.getName(), prop.getValue(), lineNumber, false);
		prop.setName(null);
		prop.setValue(null);		
	}

	private boolean parseProperty(String line, Property prop, int lineNumber) {
		if(line.contains(seperator) && prop.getName() == null) {
			String [] arr = line.split(seperator, 2);
			prop.setName(arr[0]);
			prop.setValue("");
			line = arr[1];
		}
		if(prop.getName() != null) {
			prop.setValue(prop.getValue() + line);
			
			if(!line.endsWith("\\")){
				setProperty(prop, lineNumber);
			}
			else {
				prop.setValue(prop.getValue() + "\n");
			}
			return true;
		}
		return false;
	}
	
	private void setSeperator(BufferedReader in) {
		String[] common = new String[]{"=:", "=", ":"};
		int[] counters = new int[common.length];
		for(int index = 0; index < counters.length; index++) {
			counters[index] = 1000;
		}
		try {
			String line = in.readLine();
			int charCount = 0;
			if(line != null)
				charCount = line.length();
			while(charCount < maxCharCount/2 && line != null) {
				int[] positions = new int[common.length];
				for(int index = 0; index < common.length; index++) {
					if(line.indexOf(common[index]) >= 0)
						positions[index] = line.indexOf(common[index]);
					else
						positions[index] = -1;
				}
				int lowestIndex = getLowestIndex(positions);
				if(lowestIndex >= 0)
					counters[lowestIndex]--;
				line = in.readLine();
				if(line != null)
					charCount += line.length();
			}	
			int lowestIndex = getLowestIndex(counters);
			if(lowestIndex >= 0)
				this.seperator = common[lowestIndex];

		} catch (IOException e) {
			Logger log = Logger.getRootLogger();
			log.error("Application failed to read file '" + filePath + "'");
			log.trace(e.getStackTrace());
		}
	
	}

	private int getLowestIndex(int[] positions) {
		int lowestIndex = 0;
		for(int index = 1; index < positions.length; index++) {
			if(positions[lowestIndex] < 0 || (positions[lowestIndex] > positions[index] && positions[index] >= 0))
				lowestIndex = index;
		}
		if(positions[lowestIndex] < 0)
			return -1;
		
		return lowestIndex;
	}

	public void setProperty(String key, String value) {
		int lineCount = 0;
		List<PropInstInfo> info = PropertyOrganizer.getVFPByFileName(filePath);
		for(PropInstInfo pii : info) {
			if(pii.getLineNumber() > lineCount)
				lineCount = pii.getLineNumber();
		}

		setProperty(key, value, lineCount + 1, false);
	}
	
	public void setProperty(String key, String value, int lineNumber, boolean def) {
		PropertyOrganizer.getProperty(key, value, filePath, lineNumber, def);
	}
	
	public String getProperty(String name) {
		return PropertyOrganizer.getProperty(name, filePath);
	}
	
	public Set<String> keySet() {
		Collection<Property> properties = PropertyOrganizer.getPropertyByFileName(filePath);
		Set<String> keys = new HashSet<String>();
		for(Property p : properties) {
			keys.add(p.getName());
		}
		return keys;
	}
	
	public void overWrite() {
		save(this.getFilePath());
	}
	
	public void save() {
		save(this.getFilePath() + "~");
	}
	
	public void save(String fileName) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.save(os);	
	}
	
	public void save(OutputStream out) {
		BufferedWriter o = new BufferedWriter(new OutputStreamWriter(out));
		List<PropInstInfo> properties = PropertyOrganizer.getVFPByFileName(filePath);
		Collections.sort(properties);
		
		int lineCounter = -1;
		try {		
			String fileStr = this.toString();
			String rmvSerId = fileStr.substring(fileStr.indexOf("\n") + 1);
			o.write(rmvSerId);

			o.close();
			out.close();
		} catch (IOException e) {
			Logger log = Logger.getRootLogger();
			log.error("Application failed to save to file '" + filePath + "'");
			log.trace(e.getStackTrace());
		}

	}
	
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropFile other = (PropFile) obj;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + maxCharCount;
		result = prime * result + ((seperator == null) ? 0 : seperator.hashCode());
		return result;
	}

	@Override
	public String toString() {
		List<PropInstInfo> properties = PropertyOrganizer.getVFPByFileName(filePath);
		String file = "FileToolObjSerialId" + seperator + serialVersionUID + "\n";
		int lineCounter = -1;
		for(PropInstInfo key : properties) {
			String line;
			if(key.getValue() != null)
				file +=  key.getName() + seperator + key.getValue(); 
			else
				file += key.getName();

			while(lineCounter < key.getLineNumber()) {
				file+= "\n";
				lineCounter++;
			}
		}
		
		return file;
	}
}
