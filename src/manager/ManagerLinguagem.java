package manager;

import java.util.ArrayList;

import automato.Automato;
import expressao.Expressao;
import gramatica.Gramatica;

public class ManagerLinguagem {
	public static final char EPSILON = '&';
	
	private int contadorNomeNovoAutomato;
	private int contadorNomeNovoGramatica;
	private int contadorNomeNovoExpressao;
	
	private ArrayList<Automato> conjuntoAutomato;
	private ArrayList<Gramatica> conjuntoGramatica;
	private ArrayList<Expressao> conjuntoExpressao;
	
	public ManagerLinguagem() {
		this.contadorNomeNovoAutomato = 0;
		this.contadorNomeNovoGramatica = 0;
		this.contadorNomeNovoExpressao = 0;
		
		this.conjuntoAutomato = new ArrayList<Automato>();
		this.conjuntoGramatica = new ArrayList<Gramatica>();
		this.conjuntoExpressao = new ArrayList<Expressao>();
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
	public ArrayList<Automato> getConjuntoAutomato() {
		return conjuntoAutomato;
	}
	public ArrayList<Gramatica> getConjuntoGramatica() {
		return conjuntoGramatica;
	}
	public ArrayList<Expressao> getConjuntoExpressao() {
		return conjuntoExpressao;
	}
	
	public String nomeNovoAutomato() {
		this.contadorNomeNovoAutomato++;
		return "A"+this.contadorNomeNovoAutomato;
	}
	public String nomeNovoGramatica() {
		this.contadorNomeNovoGramatica++;
		return "G"+this.contadorNomeNovoGramatica;
	}
	public String nomeNovoExpressao() {
		this.contadorNomeNovoExpressao++;
		return "E"+this.contadorNomeNovoExpressao;
	}
}
