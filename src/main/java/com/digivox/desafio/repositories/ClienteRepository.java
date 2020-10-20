package com.digivox.desafio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digivox.desafio.model.Cliente;

/**
 * Desafio Digivox - 19/10/2020
 * Candidato: Renan Costa
 * Arquivo: Interface de Repositório de Clientes
 * 
 * Histórico de Alteraçôes:
 * - 19/10/2020 Renan Costa Criação
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	Cliente findById(Long id);
	List<Cliente> findByCPF(String CPF);
	List<Cliente> findAll();
	
	Integer deleteByCPF(String CPF);
}
