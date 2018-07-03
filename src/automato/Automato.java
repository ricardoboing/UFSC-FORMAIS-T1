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
	
	private ConjuntoEstado estados, estadosFinais;
	private ConjuntoAlfabeto alfabeto;
	private ConjuntoObject<Transicao> transicoes;
	
	public Automato() {
		this("sem_nome");
	}
	public Automato(String nome) {
		this.nome = nome;
		this.estadoInicial = null;
		
		this.estados = new ConjuntoEstado();
		this.estadosFinais = new ConjuntoEstado();
		this.alfabeto = new ConjuntoAlfabeto();
		this.transicoes = new ConjuntoObject<Transicao>();
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
		return this.estados.add(estado);
	}
	public Estado addEstadoFinal(Estado estado) {
		Estado adicionado;
		adicionado = this.estadosFinais.add(estado);
		adicionado.setFinal(true);
		
		return adicionado;
	}
	public Character addEntrada(char entrada) {
		return this.alfabeto.add(entrada);
	}
	public Transicao addTransicao(Transicao transicao) {
		return this.transicoes.add(transicao);
	}
	public void addEstados(ConjuntoEstado estados) {
		this.estados.add(estados);
	}
	public void addEstadosFinais(ConjuntoEstado estados) {
		this.estadosFinais.add(estados);
	}
	public void addTransicoes(ConjuntoObject<Transicao> transicoes) {
		this.transicoes.add(transicoes);
	}
	// Metodos Setters
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setEstadoInicial(Estado estado) {
		this.estadoInicial = estado;
		this.estadoInicial.setInicial(true);
	}
	public boolean addEstadoInicial(Estado estado) {
		if (this.possuiEstadoInicial()) {
			return false;
		}
		
		this.setEstadoInicial(estado);
		
		return true;
	}
	public void setEstados(ConjuntoEstado estados) {
		this.estados = estados;
	}
	public void setEstadosFinais(ConjuntoEstado estadosFinais) {
		this.estadosFinais = estadosFinais;
	}
	public void setAlfabeto(ConjuntoAlfabeto alfabeto) {
		this.alfabeto = alfabeto;
	}
	public void setTransicoes(ConjuntoObject<Transicao> transicoes) {
		this.transicoes = transicoes;
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
	public ConjuntoEstado getEstados() {
		return estados;
	}
	public ConjuntoEstado getEstadosFinais() {
		return estadosFinais;
	}
	public ConjuntoAlfabeto getAlfabeto() {
		return alfabeto;
	}
	public ConjuntoObject<Transicao> getTransicoes() {
		return transicoes;
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
		
		this.estados = gerado.estados;
		this.alfabeto = gerado.alfabeto;
		this.transicoes = gerado.transicoes;
		this.estadosFinais = gerado.estadosFinais;
		this.estadoInicial = gerado.estadoInicial;
	}
}
