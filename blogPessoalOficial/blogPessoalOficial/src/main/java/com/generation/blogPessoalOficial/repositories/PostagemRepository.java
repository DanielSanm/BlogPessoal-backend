package com.generation.blogPessoalOficial.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.blogPessoalOficial.models.PostagemModel;

public interface PostagemRepository extends JpaRepository<PostagemModel, Long>{
	
	public List<PostagemModel> findAllByTituloContainingIgnoreCase(String titulo);
}
