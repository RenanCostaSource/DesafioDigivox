package com.digivox.desafio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	public void setCPF(String cPF) {
		CPF = cPF;
	}
	
	
}
