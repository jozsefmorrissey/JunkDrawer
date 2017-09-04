package com.joz.fileTool.property.entities;

import javax.annotation.ManagedBean;

import org.springframework.web.context.annotation.ApplicationScope;

import com.joz.fileTool.marker.HasName;

import lombok.Data;

@ManagedBean
@ApplicationScope
@Data
public class Property implements HasName{
	String name;
	String value;
	
	public Property(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	public Property() {
		super();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Property other = (Property) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	} 
	
	
}
