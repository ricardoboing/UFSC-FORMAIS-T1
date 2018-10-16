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
package gramatica;

import conjunto.ConjuntoObject;

public class NaoTerminal {
	private String simbolo;
	private ConjuntoObject<Producao> conjuntoProducao;
	
	public NaoTerminal(String simbolo) {
		this.simbolo = simbolo;
		this.conjuntoProducao = new ConjuntoObject<Producao>();
	}
	// Metodos Add
	public Producao addProducao(Terminal terminal, NaoTerminal naoTerminal) {
		Producao producao;
		producao = new Producao();
		producao.setTerminal(terminal);
		producao.setNaoTerminal(naoTerminal);
		
		return this.conjuntoProducao.add(producao);
	}
	public Producao addProducao(Producao producao) {
		return this.conjuntoProducao.add(producao);
	}
	public void addConjuntoProducao(ConjuntoObject<Producao> conjuntoProducao) {
		this.conjuntoProducao.add(conjuntoProducao);
	}
	// Metodos Setters
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	public void setConjuntoProducao(ConjuntoObject<Producao> conjuntoProducao) {
		this.conjuntoProducao = conjuntoProducao;
	}
	// Metodos Getters
	public String getSimbolo() {
		return simbolo;
	}
	public ConjuntoObject<Producao> getConjuntoProducao() {
		return conjuntoProducao;
	}
	
	@Override
	public boolean equals(Object object) {
		NaoTerminal naoTerminal;
		naoTerminal = (NaoTerminal) object;
		
		String simbolo;
		simbolo = naoTerminal.getSimbolo();
		
		if (this.simbolo.equals(simbolo)) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public NaoTerminal clone() {
		NaoTerminal naoTerminalClone;
		naoTerminalClone = new NaoTerminal(this.simbolo+"_clone");
		
		for (int c = 0; c < this.conjuntoProducao.size(); c++) {
			Producao producaoOriginal;
			producaoOriginal = this.conjuntoProducao.get(c);
			
			Producao producaoClone;
			producaoClone = producaoOriginal.clone();
			
			naoTerminalClone.addProducao(producaoClone);
		}
		
		return naoTerminalClone;
	}
}
