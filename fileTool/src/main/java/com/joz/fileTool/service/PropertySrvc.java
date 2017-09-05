package com.joz.fileTool.service;

import java.util.List;
import java.util.Map;

import com.joz.fileTool.ui.PropertyInfo;

public interface PropertySrvc {
	public Map<String, List<String>> getFileMap();
	public Map<String, List<PropertyInfo>> getAllProperties();
	public Map<String, List<PropertyInfo>> getPropertysByFile(String name);
}
