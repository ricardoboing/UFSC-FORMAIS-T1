package view.principal;

import java.util.ArrayList;

import automato.Automato;
import expressao.Expressao;
import gramatica.Gramatica;
import util.Linguagem;

public class ManagerLinguagem {
	public static final char EPSILON = '&';
	
	private int contadorNomeNovoAutomato;
	private int contadorNomeNovoGramatica;
	private int contadorNomeNovoExpressao;
	
	private ArrayList<Linguagem> conjuntoAutomato;
	private ArrayList<Linguagem> conjuntoGramatica;
	private ArrayList<Linguagem> conjuntoExpressao;
	
	public ManagerLinguagem() {
		this.contadorNomeNovoAutomato = 0;
		this.contadorNomeNovoGramatica = 0;
		this.contadorNomeNovoExpressao = 0;
		
		this.conjuntoAutomato = new ArrayList<Linguagem>();
		this.conjuntoGramatica = new ArrayList<Linguagem>();
		this.conjuntoExpressao = new ArrayList<Linguagem>();
	}
	
	// Metodos Add
	public void addAutomato(Automato automato) {
		this.conjuntoAutomato.add(automato);
	}
	public void addGramatica(Gramatica gramatica) {
		this.conjuntoGramatica.add(gramatica);
	}
	public void addExpressao(Expressao expressao) {
		this.conjuntoExpressao.add(expressao);
	}
	
	// Metodos Getters
	public ArrayList<Linguagem> getConjuntoAutomato() {
		return conjuntoAutomato;
	}
	public ArrayList<Linguagem> getConjuntoGramatica() {
		return conjuntoGramatica;
	}
	public ArrayList<Linguagem> getConjuntoExpressao() {
		return conjuntoExpressao;
	}
	
	public void gerarNomeNovoAutomato() {
		this.contadorNomeNovoAutomato++;
	}
	public void gerarNomeNovoGramatica() {
		this.contadorNomeNovoGramatica++;
	}
	public void criarNomeNovoExpressao() {
		this.contadorNomeNovoExpressao++;
	}
	
	public String getNomeNovoAutomato() {
		return "A"+this.contadorNomeNovoAutomato;
	}
	public String getNomeNovoGramatica() {
		return "G"+this.contadorNomeNovoGramatica;
	}
	public String getNomeNovoExpressao() {
		return "E"+this.contadorNomeNovoExpressao;
	}
}
