package com.joz.fileTool.property.entities;

import javax.annotation.ManagedBean;

import org.springframework.web.context.annotation.ApplicationScope;

import lombok.Data;

@ManagedBean
@ApplicationScope
@Data
public class ValueFilePair implements Comparable<ValueFilePair>{

	String value;
	String file;
	int lineNumber;
	boolean def;
	
	public ValueFilePair(String value, String file, int lineNumber, boolean def) {
		super();
		this.value = value;
		this.file = file;
		this.lineNumber = lineNumber;
		this.def = def;
	}
	
	public String getValue(){
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (def ? 1231 : 1237);
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		ValueFilePair other = (ValueFilePair) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		
		return true;
	}

	@Override
	public int compareTo(ValueFilePair arg0) {
		return this.lineNumber - arg0.lineNumber;
	}	
}
