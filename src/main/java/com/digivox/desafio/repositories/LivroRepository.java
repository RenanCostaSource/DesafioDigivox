package com.digivox.desafio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digivox.desafio.model.Livro;

/**
 * Desafio Digivox - 19/10/2020
 * Candidato: Renan Costa
 * Arquivo: Interface de Repositório de livros
 * 
 * Histórico de Alteraçôes:
 * - 19/10/2020 Renan Costa Criação
 */
@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {
	Livro findById(Long id);
	List<Livro> findByNome(String nome);
	List<Livro> findAll();
	
	Integer deleteBynome(String nome);
}
