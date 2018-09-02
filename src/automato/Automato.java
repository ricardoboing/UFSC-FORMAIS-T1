package automato;

import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoEstado;
import conjunto.ConjuntoObject;
import expressao.DeSimone;
import expressao.Expressao;
import gramatica.Gramatica;

public class Automato {
	private String nome;
	private Estado estadoInicial;
	
	private ConjuntoEstado conjuntoEstado, conjuntoEstadoFinal;
	private ConjuntoAlfabeto conjuntoAlfabeto;
	private ConjuntoObject<Transicao> conjuntoTransicao;
	
	public Automato() {
		this("sem_nome");
	}
	public Automato(String nome) {
		this.nome = nome;
		this.estadoInicial = null;
		
		this.conjuntoEstado = new ConjuntoEstado();
		this.conjuntoEstadoFinal = new ConjuntoEstado();
		this.conjuntoAlfabeto = new ConjuntoAlfabeto();
		this.conjuntoTransicao = new ConjuntoObject<Transicao>();
	}
	public Automato(Gramatica gramatica) {
		this();
		this.gerarAutomato(gramatica);
	}
	public Automato(Expressao expressao) {
		this();
		this.gerarAutomato(expressao);
	}
	
	// Metodos Add
	public Estado addEstado(Estado estado) {
		return this.conjuntoEstado.add(estado);
	}
	public Estado addEstadoFinal(Estado estadoFinal) {
		Estado adicionado;
		adicionado = this.conjuntoEstadoFinal.add(estadoFinal);
		adicionado.setFinal(true);
		
		return adicionado;
	}
	public Character addSimboloEntrada(char simboloEntrada) {
		return this.conjuntoAlfabeto.add(simboloEntrada);
	}
	public Transicao addTransicao(Transicao transicao) {
		return this.conjuntoTransicao.add(transicao);
	}
	public void addConjuntoEstado(ConjuntoEstado conjuntoEstado) {
		this.conjuntoEstado.add(conjuntoEstado);
	}
	public void addConjuntoEstadoFinal(ConjuntoEstado conjuntoEstadoFinal) {
		this.conjuntoEstadoFinal.add(conjuntoEstadoFinal);
	}
	public void addConjuntoTransicao(ConjuntoObject<Transicao> conjuntoTransicao) {
		this.conjuntoTransicao.add(conjuntoTransicao);
	}
	
	// Metodos Setters
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setEstadoInicial(Estado estadoInicial) {
		this.estadoInicial = estadoInicial;
		this.estadoInicial.setInicial(true);
	}
	public boolean addEstadoInicial(Estado estadoInicial) {
		if (this.possuiEstadoInicial()) {
			return false;
		}
		
		this.setEstadoInicial(estadoInicial);
		
		return true;
	}
	public void setConjuntoEstado(ConjuntoEstado conjuntoEstado) {
		this.conjuntoEstado = conjuntoEstado;
	}
	public void setConjuntoEstadoFinal(ConjuntoEstado conjuntoEstadoFinal) {
		this.conjuntoEstadoFinal = conjuntoEstadoFinal;
	}
	public void setConjuntoAlfabeto(ConjuntoAlfabeto conjuntoAlfabeto) {
		this.conjuntoAlfabeto = conjuntoAlfabeto;
	}
	public void setConjuntoTransicao(ConjuntoObject<Transicao> conjuntoTransicao) {
		this.conjuntoTransicao = conjuntoTransicao;
	}
	
	// Metodos Getters
	public String getNome() {
		return nome;
	}
	@Override
	public Automato clone() {
		// IMPLEMENTAR
		
		return null;
	}
	public Estado getEstadoInicial() {
		return estadoInicial;
	}
	public ConjuntoEstado getConjuntoEstado() {
		return conjuntoEstado;
	}
	public ConjuntoEstado getConjuntoEstadoFinal() {
		return conjuntoEstadoFinal;
	}
	public ConjuntoAlfabeto getConjuntoAlfabeto() {
		return conjuntoAlfabeto;
	}
	public ConjuntoObject<Transicao> getConjuntoTransicao() {
		return conjuntoTransicao;
	}
	public Estado getEstado(String simbolo) {
		for (int c = 0; c < this.conjuntoEstado.size(); c++) {
			Estado estado;
			estado = this.conjuntoEstado.get(c);
			
			if (simbolo.equals(estado.getSimbolo())) {
				return estado;
			}
		}
		
		return null;
	}
	
	public boolean possuiEstadoInicial() {
		return (this.estadoInicial != null);
	}
	public boolean equals(Automato automato) {
		
		return false;
	}
	
	private void gerarAutomato(Gramatica gramatica) {
		
	}
	private void gerarAutomato(Expressao expressao) {
		DeSimone deSimone;
		deSimone = new DeSimone();
		
		Automato gerado;
		gerado = deSimone.gerarAutomato(expressao);
		
		this.conjuntoEstado = gerado.conjuntoEstado;
		this.conjuntoAlfabeto = gerado.conjuntoAlfabeto;
		this.conjuntoTransicao = gerado.conjuntoTransicao;
		this.conjuntoEstadoFinal = gerado.conjuntoEstadoFinal;
		this.estadoInicial = gerado.estadoInicial;
	}
}
