package com.rootech.msolver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {

	@RequestMapping(value = "/error/systemError", method = RequestMethod.GET)
	public String systemError() {

//		model.addAttribute("error", error );
		return "applicationError";
	}
	
	@RequestMapping(value = "/error/badRequest", method = RequestMethod.GET)
	public String badRequest() {

//		model.addAttribute("error", error );
		return "applicationError";
	}
	
	@RequestMapping(value = "/error/pageNotFound", method = RequestMethod.GET)
	public String pageNotFound() {

//		model.addAttribute("error", error );
		return "applicationError";
	}
	
}
