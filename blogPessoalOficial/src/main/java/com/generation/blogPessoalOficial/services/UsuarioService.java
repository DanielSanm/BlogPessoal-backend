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
	
	/**
	 * Método cadastra o usuario e inseri no banco os dados inserido pelo usuário
	 * 
	 * @param usuario
	 * @return cadastra no banco de dados o usuario
	 * @author Daniel
	 * @since 1.0
	 * 
	 */
	
	public Usuario cadastrar(Usuario usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		
		return repositorio.save(usuario);
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
				user.get().setNome(usuario.get().getNome());
				
				return user;
			}
		}
		return null;
	}
	
	public Optional<Usuario> atualizar(Usuario usuarioParaAtualizar) {
		return repositorio.findById(usuarioParaAtualizar.getId()).map(resp -> {
			resp.setNome(usuarioParaAtualizar.getNome());
			resp.setSenha(cadastrar(usuarioParaAtualizar).getSenha());
			return Optional.ofNullable(repositorio.save(resp));
		}).orElseGet(() -> {
			return Optional.empty();
		});

	}
	
}
