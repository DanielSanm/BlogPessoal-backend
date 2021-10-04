package com.generation.MyFirstApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@GetMapping("/habment")
	public String habment() {
		return " Mentalide:\n - Persistência\n\n Habilidade:\n - Atenção aos detalhes";
	}
}
