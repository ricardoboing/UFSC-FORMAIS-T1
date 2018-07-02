package manager;

import java.util.ArrayList;

import automato.Automato;
import expressao.Expressao;
import gramatica.Gramatica;

public class ManagerLinguagem {
	public static final char EPSILON = '&';
	
	private int nomeAutomato;
	private int nomeGramatica;
	private int nomeExpressao;
	
	private ArrayList<Automato> automatos;
	private ArrayList<Gramatica> gramaticas;
	private ArrayList<Expressao> expressoes;
	
	public ManagerLinguagem() {
		this.nomeAutomato = 0;
		this.nomeGramatica = 0;
		this.nomeExpressao = 0;
		
		this.automatos = new ArrayList<Automato>();
		this.gramaticas = new ArrayList<Gramatica>();
		this.expressoes = new ArrayList<Expressao>();
	}
	// Metodos Add
	public void addAutomato(Automato automato) {
		this.automatos.add(automato);
	}
	public void addGramatica(Gramatica gramatica) {
		this.gramaticas.add(gramatica);
	}
	public void addExpressao(Expressao expressao) {
		this.expressoes.add(expressao);
	}
	// Metodos Getters
	public ArrayList<Automato> getAutomatos() {
		return automatos;
	}
	public ArrayList<Gramatica> getGramaticas() {
		return gramaticas;
	}
	public ArrayList<Expressao> getExpressoes() {
		return expressoes;
	}
	public String novoNomeAutomato() {
		this.nomeAutomato++;
		return "A"+this.nomeAutomato;
	}
	public String novoNomeGramatica() {
		this.nomeGramatica++;
		return "G"+this.nomeGramatica;
	}
	public String novoNomeExpressao() {
		this.nomeExpressao++;
		return "E"+this.nomeExpressao;
	}
}
