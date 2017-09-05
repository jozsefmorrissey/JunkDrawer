package com.joz.fileTool.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;

import org.springframework.web.context.annotation.ApplicationScope;

import com.joz.fileTool.property.entities.PropInstInfo;

import lombok.Data;

@ManagedBean
@ApplicationScope
@Data
public class PropertyInfo {
	
	public class Property {
		String value;
		String fileName;
		int lineNumber;
		
		public Property() {
			super();
		}
		public Property(PropInstInfo pii) {
			super();
			this.value = pii.getValue();
			this.fileName = pii.getFile();
			this.lineNumber = pii.getLineNumber();
		}
	}
	
	private String name;
	String value;
	String fileName;
	private String recValue;
	List<Property> dependent;
	List<Property> fileLocations;

	public PropertyInfo(PropInstInfo pii, String recValue, List<Property> dependent, List<Property> fileLocations) {
		super();
		this.name = pii.getName();
		this.value = pii.getValue();
		this.fileName = pii.getFile();
		this.fileLocations = fileLocations;
		this.dependent = dependent;
	}


	public PropertyInfo() {
		super();
	}

	public Property getProperty(PropInstInfo pii) {
		return new Property(pii);
	}
}
