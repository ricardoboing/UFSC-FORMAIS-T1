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
	
	private ConjuntoEstado conjuntoEstado;
	private ConjuntoAlfabeto conjuntoAlfabeto;
	private ConjuntoObject<Transicao> conjuntoTransicao;
	
	public Automato() {
		this("sem_nome");
	}
	public Automato(String nome) {
		this.nome = nome;
		this.estadoInicial = null;
		
		this.conjuntoEstado = new ConjuntoEstado();
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
	public Character addEntradaAlfabeto(char entradaAlfabeto) {
		return this.conjuntoAlfabeto.add(entradaAlfabeto);
	}
	public Transicao addTransicao(Transicao transicao) {
		return this.conjuntoTransicao.add(transicao);
	}
	public void addConjuntoEstado(ConjuntoEstado conjuntoEstado) {
		this.conjuntoEstado.add(conjuntoEstado);
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
		
		this.conjuntoEstado.add(estadoInicial);
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
		Automato automatoClone;
		automatoClone = new Automato();
		automatoClone.conjuntoAlfabeto = this.conjuntoAlfabeto;
		
		// Clona todos os estadoOriginal do automatoOriginal para o automatoClone
		for (int c = 0; c < this.conjuntoEstado.size(); c++) {
			Estado estadoOriginal;
			estadoOriginal = this.conjuntoEstado.get(c);
			
			Estado estadoClone;
			estadoClone = new Estado(estadoOriginal.getSimbolo());
			
			if (estadoOriginal.isInicial()) {
				automatoClone.setEstadoInicial(estadoClone);
			}
			if (estadoOriginal.isFinal()) {
				estadoClone.setFinal(true);
			}
			
			automatoClone.addEstado(estadoClone);
		}
		
		// Clona as transicoes do estadoOriginal para o estadoClone
		for (int c = 0; c < this.conjuntoEstado.size(); c++) {
			// Busca estadoOriginal
			Estado estadoOriginal;
			estadoOriginal = this.conjuntoEstado.get(c);
			
			// Busca estadoClone
			Estado estadoClone;
			estadoClone = automatoClone.conjuntoEstado.get(c);
			
			ConjuntoObject<Transicao> conjuntoTransicaoDoEstadoOriginal;
			conjuntoTransicaoDoEstadoOriginal = estadoOriginal.getConjuntoTransicao();
			
			/* Todas transicoes do automatoOriginal sao repassadas ao
			 * automatoClone, porem o estadoOrigem e estadoDestino das
			 * transicoes clonadas fazem referencia a estados do automatoClone
			 * 
			 */
			for (int i = 0; i < conjuntoTransicaoDoEstadoOriginal.size(); i++) {
				Transicao transicaoDoEstadoOriginal;
				transicaoDoEstadoOriginal = conjuntoTransicaoDoEstadoOriginal.get(i);
				
				Estado estadoDestinoOriginal;
				estadoDestinoOriginal = transicaoDoEstadoOriginal.getEstadoDestino();
				
				// Busca estadoDestinoClone equivalente ao estadoDestinoOriginal
				Estado estadoDestinoClone;
				estadoDestinoClone = automatoClone.getEstado(estadoDestinoOriginal.getSimbolo());
				
				// Cria transicaoClone
				estadoClone.addTransicao(transicaoDoEstadoOriginal.getSimboloEntrada(), estadoDestinoClone);
			}
		}
		
		return automatoClone;
	}
	public Estado getEstadoInicial() {
		return this.estadoInicial;
	}
	public ConjuntoEstado getConjuntoEstado() {
		return this.conjuntoEstado;
	}
	public ConjuntoEstado getConjuntoEstadoFinal() {
		ConjuntoEstado conjuntoEstadoFinal;
		conjuntoEstadoFinal = new ConjuntoEstado();
		
		for (int c = 0; c < this.conjuntoEstado.size(); c++) {
			Estado estado;
			estado = this.conjuntoEstado.get(c);
			
			if (estado.isFinal()) {
				conjuntoEstadoFinal.add(estado);
			}
		}
		
		return conjuntoEstadoFinal;
	}
	public ConjuntoEstado getConjuntoEstadoNaoFinal() {
		ConjuntoEstado conjuntoEstadoNaoFinal;
		conjuntoEstadoNaoFinal = new ConjuntoEstado();
		
		for (int c = 0; c < this.conjuntoEstado.size(); c++) {
			Estado estado;
			estado = this.conjuntoEstado.get(c);
			
			if (!estado.isFinal()) {
				conjuntoEstadoNaoFinal.add(estado);
			}
		}
		
		return conjuntoEstadoNaoFinal;
	}
	
	public ConjuntoAlfabeto getConjuntoAlfabeto() {
		return this.conjuntoAlfabeto;
	}
	public ConjuntoObject<Transicao> getConjuntoTransicao() {
		return this.conjuntoTransicao;
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
	@Override
	public boolean equals(Object object) {
		Automato automato;
		automato = (Automato)object;
		
		
		
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
		this.estadoInicial = gerado.estadoInicial;
	}
	
	public void print() {
		for (int c = 0; c < this.conjuntoEstado.size(); c++) {
			Estado estado;
			estado = this.conjuntoEstado.get(c);
			
			ConjuntoObject<Transicao> conjuntoTransicao;
			conjuntoTransicao = estado.getConjuntoTransicao();
			
			if (conjuntoTransicao.size() == 0) {
				if (estado.isInicial()) {
					System.out.print("->");
				}
				if (estado.isFinal()) {
					System.out.print("*");
				}
				
				System.out.println(estado.getSimbolo()+": ");
			}
			
			for (int i = 0; i < conjuntoTransicao.size(); i++) {
				Transicao transicao;
				transicao = conjuntoTransicao.get(i);
				
				if (transicao.getEstadoOrigem().isInicial()) {
					System.out.print("->");
				}
				if (transicao.getEstadoOrigem().isFinal()) {
					System.out.print("*");
				}
				
				System.out.print(transicao.getEstadoOrigem().getSimbolo()+": ");
				System.out.print("("+transicao.getEstadoOrigem().getSimbolo());
				System.out.print(", "+transicao.getSimboloEntrada());
				System.out.println(") -> "+transicao.getEstadoDestino().getSimbolo());
			}
		}
	}
}
