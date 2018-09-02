package expressao;

import automato.Automato;
import conjunto.ConjuntoAlfabeto;
import gramatica.Gramatica;

public class Expressao {
	private String nome;
	private ConjuntoAlfabeto alfabeto;
	
	public Expressao() {
		this("sem_nome");
	}
	public Expressao(String nome) {
		this.nome = nome;
		
		this.alfabeto = new ConjuntoAlfabeto();
	}
	public Expressao(Gramatica gramatica) {
		this();
		this.gerarExpressao(gramatica);
	}
	public Expressao(Automato automato) {
		this();
		this.gerarExpressao(automato);
	}
	
	public String getNome() {
		return this.nome;
	}
	
	private void gerarExpressao(Automato automato) {
		this.alfabeto = automato.getConjuntoAlfabeto();
		
		
	}
	private void gerarExpressao(Gramatica gramatica) {
		this.alfabeto = gramatica.getConjuntoAlfabeto();
		
		
	}
}
