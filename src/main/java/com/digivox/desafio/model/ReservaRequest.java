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
	
	
	
	private CharSequence dataInicial;// ano-mês-dia
	
	private CharSequence dataFinal;// ano-mês-dia
	
	private long clienteId =-1;
	
	private long livroId =-1;
	
	
	public CharSequence getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(CharSequence dataInicial) {
		this.dataInicial = dataInicial;
	}

	public CharSequence getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(CharSequence dataFinal) {
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
