package com.digivox.desafio.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Desafio Digivox - 19/10/2020
 * Candidato: Renan Costa
 * Arquivo: Modelo de dados de Clientes
 * 
 * Histórico de Alteraçôes:
 * - 19/10/2020 Renan Costa Criação
 */

@Entity
public class Cliente {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name= "nome")
	private String nome;
	
	@Column(name= "CPF", unique=true)
	private String CPF;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "cliente_id")
	@JsonIgnore
	private List<Aluguel> alugueis;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "cliente_id")
	@JsonIgnore
	private List<Reserva> reservas;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public List<Aluguel> getAlugueis() {
		return alugueis;
	}

	public void setAlugueis(List<Aluguel> alugueis) {
		this.alugueis = alugueis;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	
}
