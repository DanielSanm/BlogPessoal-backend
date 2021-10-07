package com.generation.blogPessoalOficial.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogPessoalOficial.models.PostagemModel;
import com.generation.blogPessoalOficial.repositories.PostagemRepository;

@RestController
@RequestMapping("/api/v1/postagens")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class PostagemController {
	
	@Autowired
	private PostagemRepository repositorio;
	
	@GetMapping("/tudo")
	public ResponseEntity<List<PostagemModel>> getAll() {
		return ResponseEntity.ok(repositorio.findAll());
	}
	
	@GetMapping("/{id_postagem}")
	public ResponseEntity<PostagemModel> getById(@PathVariable(value = "id_postagem") long idPostagem) {
		Optional<PostagemModel> optional = repositorio.findById(idPostagem);
		
		if(optional.isPresent()) {
			return ResponseEntity.status(200).body(optional.get());
		} else {
			return ResponseEntity.status(204).build();
		}
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<PostagemModel>> getByTitulo(@RequestBody @PathVariable(value = "titulo") String titulo) {
		List<PostagemModel> lista = repositorio.findAllByTituloContainingIgnoreCase(titulo);
		
		if(lista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(lista);
		}
	}
}
