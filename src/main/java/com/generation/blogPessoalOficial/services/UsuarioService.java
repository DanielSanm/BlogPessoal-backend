package com.generation.blogPessoalOficial.services;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.generation.blogPessoalOficial.models.Usuario;
import com.generation.blogPessoalOficial.models.UsuarioLoginDTO;
import com.generation.blogPessoalOficial.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repositorio;
	
	private static String encriptaSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
	
	public Optional<Object> cadastrar(Usuario usuario) {
		return repositorio.findByUsuario(usuario.getUsuario()).map(usuarioExistente -> {
			return Optional.empty();
		}).orElseGet(() -> {
			usuario.setSenha(encriptaSenha(usuario.getSenha()));
			return Optional.ofNullable(repositorio.save(usuario));
		});
	}
	
	/**
	 * 
	 * Método gera uma chave token em basic quando o usuario cadastrado se loga na aplicação
	 * 
	 * @param user
	 * @return um token em basic para o usuario ser autenticado
	 * @author Daniel
	 * @since 1.0
	 * 
	 */
	
	public Optional<UsuarioLoginDTO> Logar(Optional<UsuarioLoginDTO> user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repositorio.findByUsuario(user.get().getUsuario());
		
		if(usuario.isPresent()) {
			if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
				String auth = user.get().getUsuario() + ":" + user.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				
				user.get().setToken(authHeader);
				user.get().setId(usuario.get().getId());
				user.get().setNome(usuario.get().getNome());
				user.get().setFoto(usuario.get().getFoto());
				user.get().setTipo(usuario.get().getTipo());
				
				return user;
			}
		}
		return null;
	}
	
	public Optional<Usuario> atualizar(Usuario usuarioParaAtualizar) {
		return repositorio.findById(usuarioParaAtualizar.getId()).map(resp -> {
			resp.setNome(usuarioParaAtualizar.getNome());
			resp.setSenha(encriptaSenha(usuarioParaAtualizar.getSenha()));
			return Optional.ofNullable(repositorio.save(resp));
		}).orElseGet(() -> {
			return Optional.empty();
		});

	}
	
}
