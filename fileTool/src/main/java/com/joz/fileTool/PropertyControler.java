package com.joz.fileTool;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.TextNode;
import com.joz.fileTool.Util.StoredPreferences;
import com.joz.fileTool.property.PropFileInterperter;
import com.joz.fileTool.service.PropertySrvc;
import com.joz.fileTool.ui.PropertyInfo;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

@RestController
@RequestMapping("property")
public class PropertyControler {
	
	@Autowired
	PropertySrvc propSrvc;
	
	@PostMapping("setup")
	@RequestMapping(value="{name}", method = RequestMethod.POST)
	public ResponseEntity<StoredPreferences> configCtrl(@RequestBody String name) {
		if(name.contains("name")){
			name = name.substring(name.indexOf("name"));
			name = name.substring(name.indexOf(':'));
			name = name.substring(name.indexOf("\"") + 1);
			name = name.substring(0, name.indexOf("\""));
		}
		else
			name = "";
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + name + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		if("".equals(name)){
			new PropFileInterperter();
			System.out.println("shallow");
		}
		else{
			new PropFileInterperter(name);
			System.out.println("deep");
		}
		StoredPreferences sPrefs = new StoredPreferences();
		sPrefs.build();
		return ResponseEntity.ok(sPrefs);
	}
	
	@GetMapping("fileMap")
	public Map<String, List<String>> getFiles() {
		return propSrvc.getFileMap();
	}
	
	@GetMapping("map/all")
	public  Map<String, List<PropertyInfo>> getAllProperties() {
		return propSrvc.getAllProperties();
	}
	
	@PostMapping("map")
	public  Map<String, List<PropertyInfo>> getAllPropByFile(@RequestBody String name) {
		return propSrvc.getPropertysByFile(name);
	}
}
