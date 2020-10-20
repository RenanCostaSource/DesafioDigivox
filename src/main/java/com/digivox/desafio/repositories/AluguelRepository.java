package com.digivox.desafio.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.digivox.desafio.model.Aluguel;
import com.digivox.desafio.model.Cliente;
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
public interface AluguelRepository extends JpaRepository<Aluguel, Integer> {
	Aluguel findById(Long id);
	List<Aluguel> findByCliente(Cliente cliente);
	List<Aluguel> findByLivro(Livro livro);
	
	@Query(value="select * from reserva r where data_inicial >=:segunda and data_inicial <=:domingo or data_final >=:segunda and data_final <=:domingo ",nativeQuery=true)
	List<Aluguel> findByWeek(@Param("segunda")LocalDate segunda,@Param("domingo") LocalDate domingo); 
	
	Integer deleteById(Long id);
}
