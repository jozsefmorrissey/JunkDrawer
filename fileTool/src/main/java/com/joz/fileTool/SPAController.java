package com.joz.fileTool;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SPAController {
	@Controller
	@RequestMapping("/")
	public class SPAControllerImpl {

		@GetMapping
	    public String getSPA() {

	        return "index.html";
	    }
	}
}
