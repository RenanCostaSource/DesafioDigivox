package com.digivox.desafio.model;

/**
 * Desafio Digivox - 19/10/2020
 * Candidato: Renan Costa
 * Arquivo: Modelo de dados de requisição para Reservas
 * 
 * Histórico de Alteraçôes:
 * - 20/10/2020 Renan Costa Criação
 */
public class ReservaRequest {
	
	private long id;
	
	private int[] dataInicial;// [0] ano [1] mês [2] dia
	
	private int[] dataFinal;// [0] ano [1] mês [2] dia
	
	private long clienteId =-1;
	
	private long livroId =-1;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int[] getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(int[] dataInicial) {
		this.dataInicial = dataInicial;
	}

	public int[] getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(int[] dataFinal) {
		this.dataFinal = dataFinal;
	}

	public long getClienteId() {
		return clienteId;
	}

	public void setClienteId(long clienteId) {
		this.clienteId = clienteId;
	}

	public long getLivroId() {
		return livroId;
	}

	public void setLivroId(long livroId) {
		this.livroId = livroId;
	}
	
	
}
