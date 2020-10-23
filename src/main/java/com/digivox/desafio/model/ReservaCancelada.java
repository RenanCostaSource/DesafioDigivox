package com.digivox.desafio.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Desafio Digivox - 19/10/2020
 * Candidato: Renan Costa
 * Arquivo: Modelo de dados de Reservas
 * 
 * Histórico de Alteraçôes:
 * - 19/10/2020 Renan Costa Criação
 */
@Entity
public class ReservaCancelada {
	public ReservaCancelada() {
		
	}
	public ReservaCancelada(Reserva reserva) {
		data_inicial = reserva.getData_inicial();
		data_final = reserva.getData_final();
		cliente_id = reserva.getCliente().getId();
		livro_id = reserva.getLivro().getId();
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "data_inicial", columnDefinition = "DATE")
	private LocalDate data_inicial;
	
	@Column(name = "data_final", columnDefinition = "DATE")
	private LocalDate data_final;
	
	@Column(name = "cliente_id")
	private Long cliente_id;
	
	@Column(name = "livro_id")
	private Long livro_id;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getData_inicial() {
		return data_inicial;
	}
	public void setData_inicial(LocalDate data_inicial) {
		this.data_inicial = data_inicial;
	}
	public LocalDate getData_final() {
		return data_final;
	}
	public void setData_final(LocalDate data_final) {
		this.data_final = data_final;
	}
	public Long getCliente_id() {
		return cliente_id;
	}
	public void setCliente_id(Long cliente_id) {
		this.cliente_id = cliente_id;
	}
	public Long getLivro_id() {
		return livro_id;
	}
	public void setLivro_id(Long livro_id) {
		this.livro_id = livro_id;
	}
	
	
	
	
	
}
