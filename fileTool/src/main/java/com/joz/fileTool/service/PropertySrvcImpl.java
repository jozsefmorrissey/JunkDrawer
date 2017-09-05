package com.joz.fileTool.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.joz.fileTool.Util.Configuration;
import com.joz.fileTool.property.PropertyOrganizer;
import com.joz.fileTool.property.ValidateProperties;
import com.joz.fileTool.property.entities.PropInstInfo;
import com.joz.fileTool.ui.PropertyInfo;
import com.joz.fileTool.ui.PropertyInfo.Property;

@Service
public class PropertySrvcImpl implements PropertySrvc {

	@Override
	public Map<String, List<String>> getFileMap() {
		Map<String, List<PropertyInfo>> map = getAllProperties();
		Map<String, List<String>> fileMap = new HashMap<String, List<String>>();
		for(Entry<String, List<PropertyInfo>> entry : map.entrySet()) {
			List<String> files = new ArrayList<String>();
			for(PropertyInfo pi : entry.getValue()) {
				files.add(pi.getFileName());
			}
			fileMap.put(entry.getKey(), files);
		}
		return fileMap;
	}

	@Override
	public Map<String, List<PropertyInfo>> getAllProperties() {
		List<PropInstInfo> badpaths = ValidateProperties.findBadPaths();
		List<PropInstInfo> badurls = ValidateProperties.invalidUrls();
		Map<String, List<PropertyInfo>> map = new HashMap<String, List<PropertyInfo>>();
		map.put("Bad Paths", buildPropertyInfo(badpaths));
		map.put("Bad Urls", buildPropertyInfo(badurls));
		return map;
	}

	@Override
	public Map<String, List<PropertyInfo>> getPropertysByFile(String name) {
		Map<String, List<PropertyInfo>> map = getAllProperties();
		for(Entry<String, List<PropertyInfo>> entry : map.entrySet()) {
			for(PropertyInfo pi : entry.getValue()) {
				if(!pi.getFileName().equals(name)) {
					entry.getValue().remove(pi);
				}
			}
		}
		return map;
	}
	
	private List<PropertyInfo> buildPropertyInfo(List<PropInstInfo> propInstInfo) {
		List<PropertyInfo> propInfo = new ArrayList<PropertyInfo>();
		for(PropInstInfo pii : propInstInfo) {
			String configFile = pii.getFile() + ".config";
			String recValue = new Configuration(configFile).getProperty(pii.getName()).getValue();
			
			
			List<Property> dependent = getDependents(pii);
			List<Property> fileLocations = getFiles(pii);
			
			propInfo.add(new PropertyInfo(pii, recValue, dependent, fileLocations));
		}
		return propInfo;
	}

	private List<Property> getFiles(PropInstInfo propInst) {
		List<PropInstInfo> piiList = PropertyOrganizer.getAllPII();
		List<Property> otherDec = new ArrayList<Property>();
		for(PropInstInfo pii : piiList) {
			if(pii.getName().equals(propInst)) {
				otherDec.add(new PropertyInfo().getProperty(pii));				
			}
		}
		
		return otherDec;
	}

	private List<PropertyInfo.Property> getDependents(PropInstInfo propInst) {
		List<PropInstInfo> variable = ValidateProperties.variabless();
		List<PropertyInfo.Property> dependents = new ArrayList<PropertyInfo.Property>();
		for(PropInstInfo pii : variable) {
			String value = pii.getValue().replaceAll("\\s+","");
			if(value.contains("${" + propInst.getName() + "}")) {
				dependents.add(new PropertyInfo().getProperty(propInst));
			}
		}
		
		return dependents;
	}

}
