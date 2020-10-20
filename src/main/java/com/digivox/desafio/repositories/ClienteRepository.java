package com.digivox.desafio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digivox.desafio.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	List<Cliente> findByCPF(String CPF);
	List<Cliente> findAll();
	
	Integer deleteByCPF(String CPF);
}
