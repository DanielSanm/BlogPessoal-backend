package com.generation.MySecondApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@GetMapping("/obj")
	public String objetivoAprendizagem() {
		return "Apreender e entender Spring";
	}
}
