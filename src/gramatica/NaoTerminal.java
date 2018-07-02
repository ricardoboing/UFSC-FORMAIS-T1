package gramatica;

import conjunto.ConjuntoObject;

public class NaoTerminal {
	private String simbolo;
	private ConjuntoObject<Producao> producoes;
	
	public NaoTerminal(String simbolo) {
		this.simbolo = simbolo;
		this.producoes = new ConjuntoObject<Producao>();
	}
	// Metodos Add
	public Producao addProducao(Terminal terminal, NaoTerminal naoTerminal) {
		Producao producao;
		producao = new Producao();
		producao.setTerminal(terminal);
		producao.setNaoTerminal(naoTerminal);
		
		return this.producoes.add(producao);
	}
	public Producao addProducao(Producao producao) {
		return this.producoes.add(producao);
	}
	public void addProducoes(ConjuntoObject<Producao> producoes) {
		this.producoes.add(producoes);
	}
	// Metodos Setters
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	public void setProducoes(ConjuntoObject<Producao> producoes) {
		this.producoes = producoes;
	}
	// Metodos Getters
	public String getSimbolo() {
		return simbolo;
	}
	public ConjuntoObject<Producao> getProducoes() {
		return producoes;
	}
	
	public boolean equals(NaoTerminal naoTerminal) {
		
		return false;
	}
}
