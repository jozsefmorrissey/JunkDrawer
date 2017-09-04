package com.joz.fileTool.property.entities;

import javax.annotation.ManagedBean;

import org.springframework.web.context.annotation.ApplicationScope;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ManagedBean
@ApplicationScope
@Data
@EqualsAndHashCode(callSuper=true)
public class PropInstInfo extends ValueFilePair{
	
	private String name;
	
	public PropInstInfo(String name, ValueFilePair vfp) {
		super(vfp.getValue(), vfp.getFile(), vfp.getLineNumber(), vfp.isDef());
		this.name = name;
	}

}
