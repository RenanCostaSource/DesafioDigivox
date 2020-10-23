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
	
	@Query(value="select * from aluguel r where data_inicial >=:segunda and data_inicial <=:domingo",nativeQuery=true)
	List<Aluguel> findByRentWeek(@Param("segunda")LocalDate segunda,@Param("domingo") LocalDate domingo); 
	@Query(value="select * from aluguel r where data_final >=:segunda and data_final <=:domingo ",nativeQuery=true)
	List<Aluguel> findByReturnWeek(@Param("segunda")LocalDate segunda,@Param("domingo") LocalDate domingo); 
	
	@Query(value="select * from aluguel r where data_inicial >=:segunda and data_inicial <=:domingo or data_final >=:segunda and data_final <=:domingo ",nativeQuery=true)
	List<Aluguel> findByWeek(@Param("segunda")LocalDate segunda,@Param("domingo") LocalDate domingo); 
	
	@Query(value="select count(*) from aluguel where data_inicial >=:inicio and data_inicial <=:fim and livro_id=:livro and devolvido is null or data_final >=:inicio and data_final <=:fim and livro_id=:livro and devolvido is null",nativeQuery=true)
	int findIfBookRented(@Param("inicio")LocalDate inicio,@Param("fim") LocalDate fim,@Param("livro") Long livro);
	
	Integer deleteById(Long id);
}
