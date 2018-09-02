package automato;

import conjunto.ConjuntoObject;

public class Estado {
	private String simbolo;
	private boolean isInicial, isFinal;
	
	private ConjuntoObject<Transicao> transicoes;
	
	public Estado(String nome) {
		this.simbolo = nome;
		this.isInicial = false;
		this.isFinal = false;
		
		this.transicoes = new ConjuntoObject<Transicao>();
	}
	// Metodos Add
	public Transicao addTransicao(Transicao transicao) {
		return this.transicoes.add(transicao);
	}
	public Transicao addTransicao(char entrada, Estado destino) {
		Transicao transicao;
		transicao = new Transicao();
		transicao.setSimboloEntrada(entrada);
		transicao.setEstadoOrigem(this);
		transicao.setEstadoDestino(destino);
		
		return this.transicoes.add(transicao);
	}
	public void addTransicoes(ConjuntoObject<Transicao> transicoes) {
		this.transicoes.add(transicoes);
	}
	// Metodos Setters
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	public void setInicial(boolean isInicial) {
		this.isInicial = isInicial;
	}
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	// Metodos Getters
	public String getSimbolo() {
		return simbolo;
	}
	public boolean isInicial() {
		return isInicial;
	}
	public boolean isFinal() {
		return isFinal;
	}
	public ConjuntoObject<Transicao> getTransicoes() {
		return transicoes;
	}
	public ConjuntoObject<Transicao> getTransicoes(char entrada) {
		ConjuntoObject<Transicao> reconhecedores;
		reconhecedores = new ConjuntoObject<Transicao>();
		/* Percorre todas as producoes e add no conjunto aquelas
		 * que transitao com a entrada
		 */
		for (int c = 0; c < this.transicoes.size(); c++) {
			Transicao transicao;
			transicao = this.transicoes.get(c);
			
			if (transicao.reconhecerEntrada(entrada)) {
				reconhecedores.add(transicao);
			}
		}
		
		return reconhecedores;
	}
	
	public boolean reconhece(char entrada) {
		/* Percorre todas as producoes e, caso alguma transite
		 * com o terminal buscado entao reconhece
		 */
		for (int c = 0; c < this.transicoes.size(); c++) {
			Transicao transicao;
			transicao = this.transicoes.get(c);
			
			if (transicao.reconhecerEntrada(entrada)) {
				return true;
			}
		}
		
		return false;
	}
	public boolean equals(Estado estado) {
		
		return false;
	}
}
