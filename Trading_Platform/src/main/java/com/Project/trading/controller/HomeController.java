package com.Project.trading.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping
	public String home() {
		return "Hello";
	}
	
	@GetMapping("/api")
	public String secure() {
		return "Scurity";
	}
}
