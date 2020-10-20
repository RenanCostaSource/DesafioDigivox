package com.digivox.desafio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digivox.desafio.model.Cliente;
import com.digivox.desafio.model.Reserva;
import com.digivox.desafio.model.Livro;

/**
 * Desafio Digivox - 19/10/2020
 * Candidato: Renan Costa
 * Arquivo: Interface de Repositório de Reserva
 * 
 * Histórico de Alteraçôes:
 * - 20/10/2020 Renan Costa Criação
 */
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
	Reserva findById(Long id);
	List<Reserva> findByCliente(Cliente cliente);
	List<Reserva> findByLivro(Livro livro);
	
	Integer deleteById(Long id);
}
