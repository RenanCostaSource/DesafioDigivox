package com.digivox.desafio.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.digivox.desafio.model.ReservaCancelada;


/**
 * Desafio Digivox - 19/10/2020
 * Candidato: Renan Costa
 * Arquivo: Interface de Repositório de Reserva
 * 
 * Histórico de Alteraçôes:
 * - 20/10/2020 Renan Costa Criação
 */
@Repository
public interface ReservaCanceladaRepository extends JpaRepository<ReservaCancelada, Integer> {
	
}
