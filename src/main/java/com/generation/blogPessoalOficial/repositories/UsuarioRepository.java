package com.generation.blogPessoalOficial.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.blogPessoalOficial.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	/**
	 * Método que pesquisa um usuário em especifico
	 * 
	 * @param usuario
	 * @return
	 * @author Daniel
	 * @since 1.0
	 * 
	 */

	public Optional<Usuario> findByUsuario(String usuario);

	/**
	 * Método para listar nomes de acordo com que foi passado como parametro
	 * 
	 * @param nome
	 * @return uma lista de nomes
	 * @author Daniel
	 * @since 1.0
	 * 
	 */

	public List<Usuario> findAllByNomeContainingIgnoreCase(String nome);

}
