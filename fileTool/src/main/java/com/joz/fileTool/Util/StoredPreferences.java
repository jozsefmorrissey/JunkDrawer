package com.joz.fileTool.Util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;

import org.springframework.web.context.annotation.ApplicationScope;

import com.joz.fileTool.property.PropFile;
import com.joz.fileTool.property.PropFileInterperter;
import com.joz.fileTool.property.PropertyOrganizer;
import com.joz.fileTool.property.entities.PropInstInfo;
import com.joz.fileTool.property.entities.Property;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ManagedBean
@ApplicationScope
@Data
public class StoredPreferences {

	public static void main(String...args) {
		PropFileInterperter pi = new PropFileInterperter();
		StoredPreferences sPrefs = new StoredPreferences();
		sPrefs.build();
		System.out.println(sPrefs.configs.size());
	}
	
	Configuration root;
	ArrayList<Configuration> configs = new ArrayList<Configuration>();
	String fileName = "global.properties";
	String configFileName;
	PropFile globProp;
	
	
	public StoredPreferences () {
		initialize();
	}
	
	public StoredPreferences (String fileName) {
		this.fileName = fileName;
		initialize();
	}
	
	private void initialize() {
		globProp = new PropFile(fileName);
		configFileName = globProp.getProperty("FileToolConfigFile");
		root = new Configuration("default");
	}
	
	public void build() {
		List<PropInstInfo> piis = PropertyOrganizer.getAllPII();
		
		List<Property> rootP = root.getProperties();
		for(PropInstInfo pii : piis) {
			String name = pii.getName();
			String value = pii.getValue();
			Property p = new Property(name, value);
			int index = rootP.indexOf(p);
			if("var".equals(name)){
				System.out.println("value: " + value + "\tindex: " + index);
			}
			if(index < 0) {
				rootP.add(p);
			}
			else if(!rootP.get(index).getValue().equals(value)) {
				System.out.println("\t1) " + (index < 0) + "\t2) " + !rootP.get(index).getValue().equals(value));
				Configuration config = getConfiguration(pii.getFile());
				if(config == null) {
					config = new Configuration(pii.getFile(), root.getFileName(), this);
					this.configs.add(config);
				}
				config.getProperties().add(p);
			}
				
		}
		
	}
	
	public Configuration getConfiguration(String fileName) {
		Configuration config = new Configuration(fileName, root.getFileName(), this);
		int index = configs.indexOf(config);
		if(index >= 0)
			return configs.get(index);
		else
			return null;
	}
}
