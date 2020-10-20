package com.digivox.desafio.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Desafio Digivox - 19/10/2020
 * Candidato: Renan Costa
 * Arquivo: Modelo de dados de Reservas
 * 
 * Histórico de Alteraçôes:
 * - 20/10/2020 Renan Costa Criação
 */
@Entity
public class Aluguel {
	public Aluguel() {
		
	}
	public Aluguel ( LocalDate data_final, Cliente cliente, Livro livro) {
		this.data_final = data_final;
		this.cliente = cliente;
		this.livro = livro;
	}
	
	public Aluguel (Long id,LocalDate data_inicial, LocalDate data_final, Cliente cliente, Livro livro) {
		this.id = id;
		this.data_inicial = data_inicial;
		this.data_final = data_final;
		this.cliente = cliente;
		this.livro = livro;
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "data_inicial", columnDefinition = "DATE")
	private LocalDate data_inicial = LocalDate.now();
	
	@Column(name = "data_final", columnDefinition = "DATE")
	private LocalDate data_final;
	
	@ManyToOne
	private Cliente cliente;
	
	@ManyToOne
	private Livro livro;

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
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
}
