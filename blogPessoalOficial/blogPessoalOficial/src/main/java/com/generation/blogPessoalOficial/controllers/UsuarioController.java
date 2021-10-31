package com.generation.blogPessoalOficial.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogPessoalOficial.models.Usuario;
import com.generation.blogPessoalOficial.models.UsuarioLoginDTO;
import com.generation.blogPessoalOficial.repositories.UsuarioRepository;
import com.generation.blogPessoalOficial.services.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repositorio;
	
	@Autowired
	private UsuarioService service;
	
	@PostMapping("/logar")
	public ResponseEntity<UsuarioLoginDTO> autentication(@RequestBody Optional<UsuarioLoginDTO> user) {
		return service.Logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElseThrow(() -> {
					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
							"Não tem autorização para acessar");
				});
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(service.cadastrar(usuario));
	}
	
	@GetMapping("/pesquisar/{nome}")
	public ResponseEntity<List<Usuario>> findByNome(@PathVariable String nome) {
		List<Usuario> lista = repositorio.findAllByNomeContainingIgnoreCase(nome);
		
		if(lista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(201).body(lista);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Usuario>> findAll() {
		return ResponseEntity.ok(repositorio.findAll());
	}
	
}
