/*
 *  Trabalho I: Algoritmos para Manipulacao de Linguagens Regulares
 *  
 *  Departamento de Informatica e Estatistica – Universidade Federal de Santa Catarina (UFSC)
 *  Campus Reitor Joao David Ferreira Lima, 88.040-900 – Florianopolis – SC – Brasil
 *  
 *  brunohonnef@gmail.com pedroabcorte@gmail.com ricardoboing.ufsc@gmail.com
 *  
 *  Bruno Gilmar Honnef
 *  Pedro Alexandre Barradas da Corte
 *  Ricardo do Nascimento Boing
 *  
 *  11 de Outubro de 2018
 */
package janela.principal;

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
	
	public void removerAutomato(Automato automato) {
		for (int c = 0; c < this.conjuntoAutomato.size(); c++) {
			Automato automatoDoConjunto;
			automatoDoConjunto = (Automato)this.conjuntoAutomato.get(c);
			
			if (automato.getNome().equals(automatoDoConjunto.getNome())) {
				this.conjuntoAutomato.remove(c);
				return;
			}
		}
	}
	public void removerGramatica(Gramatica gramatica) {
		this.conjuntoGramatica.remove(gramatica);
	}
	public void removerExpressao(Expressao expressao) {
		this.conjuntoExpressao.remove(expressao);
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
	
	public Automato getAutomato(String nome) {
		Linguagem linguagem;
		linguagem = this.getLinguagem(this.conjuntoAutomato, nome);
		
		if (linguagem == null) {
			return null;
		}
		
		return (Automato)linguagem;
	}
	public Gramatica getGramatica(String nome) {
		Linguagem linguagem;
		linguagem = this.getLinguagem(this.conjuntoGramatica, nome);
		
		if (linguagem == null) {
			return null;
		}
		
		return (Gramatica)linguagem;
	}
	public Expressao getExpressao(String nome) {
		Linguagem linguagem;
		linguagem = this.getLinguagem(this.conjuntoExpressao, nome);
		
		if (linguagem == null) {
			return null;
		}
		
		return (Expressao)linguagem;
	}
	private Linguagem getLinguagem(ArrayList<Linguagem> arrayLinguagem, String nomeLinguagem) {
		for (int c = 0; c < arrayLinguagem.size(); c++) {
			Linguagem linguagem;
			linguagem = arrayLinguagem.get(c);
			
			if (linguagem.getNome().equals(nomeLinguagem)) {
				return linguagem;
			}
		}
		
		return null;
	}
	
	public Gramatica getUltimaGramatica() {
		if (this.conjuntoGramatica.size() > 0) {
			return (Gramatica) this.conjuntoGramatica.get(this.conjuntoGramatica.size()-1);
		}
		
		return null;
	}
	public Automato getUltimaAutomato() {
		if (this.conjuntoAutomato.size() > 0) {
			return (Automato) this.conjuntoAutomato.get(this.conjuntoAutomato.size()-1);
		}
		
		return null;
	}
	public Expressao getUltimaExpressao() {
		if (this.conjuntoExpressao.size() > 0) {
			return (Expressao) this.conjuntoExpressao.get(this.conjuntoExpressao.size()-1);
		}
		
		return null;
	}
	
	public void gerarNomeNovoAutomato() {
		this.contadorNomeNovoAutomato++;
	}
	public void gerarNomeNovoGramatica() {
		this.contadorNomeNovoGramatica++;
	}
	public void gerarNomeNovoExpressao() {
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
