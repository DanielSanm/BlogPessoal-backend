package com.generation.blogPessoalOficial.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogPessoalOficial.models.TemaModel;
import com.generation.blogPessoalOficial.repositories.TemaRepository;


@RestController
@RequestMapping("api/v1/tema")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class TemaController {

	@Autowired
	private TemaRepository repositorio;
	
	@GetMapping("/tudo")
	public ResponseEntity<List<TemaModel>> getByAll() {
		return ResponseEntity.ok(repositorio.findAll());
	}
	
	@GetMapping("/{id_tema}") 
	public ResponseEntity<TemaModel> getById(@PathVariable(value = "id_tema") long idTema) {
		Optional<TemaModel> optional = repositorio.findById(idTema);
		
		if(optional.isPresent()) {
			return ResponseEntity.status(200).body(optional.get());
		} else {
			return ResponseEntity.status(204).build();
		}
	}
	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<TemaModel>> getByDescricao(@PathVariable(value = "descricao") String descricao) {
		List<TemaModel> lista = repositorio.findAllByDescricaoContainingIgnoreCase(descricao);
		
		if(lista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(lista);
		}
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<TemaModel> salvar(@RequestBody TemaModel tema) {
		return ResponseEntity.status(201).body(repositorio.save(tema));
	}
	
	@PutMapping("atualizar") 
	public ResponseEntity<TemaModel> atualizar(@RequestBody TemaModel tema) {
		return ResponseEntity.status(201).body(repositorio.save(tema));
	}
	
	@DeleteMapping("/deletar/{idTema}")
	public void deletar(@PathVariable long idTema) {
		repositorio.deleteById(idTema);
	}
}
