package gramatica;

import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoNaoTerminal;
import conjunto.ConjuntoObject;

public class Gramatica {
	private ConjuntoNaoTerminal naoTerminais;
	private ConjuntoAlfabeto alfabeto;
	private ConjuntoObject<Producao> producoes;
	
	private NaoTerminal simboloInicial;
	private String producoesString;
	private String nome;
	
	public Gramatica(String producoes) {
		this(null, producoes);
	}
	public Gramatica(String nome, String producoes) {
		this.nome = nome;
		this.simboloInicial = null;
		
		this.alfabeto = new ConjuntoAlfabeto();
		this.naoTerminais = new ConjuntoNaoTerminal();
		this.producoes = new ConjuntoObject<Producao>();
		
		this.gerarGramatica(producoes);
	}
	// Metodos Setters
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setSimboloInicial(NaoTerminal simboloInicial) {
		this.simboloInicial = simboloInicial;
	}
	public void setAlfabeto(ConjuntoAlfabeto alfabeto) {
		this.alfabeto = alfabeto;
	}
	public void setProducoes(ConjuntoObject<Producao> producoes) {
		this.producoes = producoes;
	}
	public void setNaoTerminais(ConjuntoNaoTerminal naoTerminais) {
		this.naoTerminais = naoTerminais;
	}
	// Metodos Getters
	public String getNome() {
		return nome;
	}
	public NaoTerminal getSimboloInicial() {
		return simboloInicial;
	}
	public ConjuntoNaoTerminal getNaoTerminais() {
		return naoTerminais;
	}
	public ConjuntoAlfabeto getAlfabeto() {
		return alfabeto;
	}
	public ConjuntoObject<Producao> getProducoes() {
		return producoes;
	}
	// Metodos Boolean
	public boolean equals(Gramatica gramatica) {
		
		return false;
	}
	
	// Metodos Privados
	private boolean gerarGramatica(String producoes) {
		producoes = this.formataProducoes(producoes);
		this.producoesString = producoes;
		
		if (producoes.equals("")) {
			return false;
		}
		
		boolean continuar;
		do {
			continuar = false;
			
			if (producoes.equals("")) {
				break;
			}
			
			int fim;
			fim = producoes.indexOf("\n");
			
			if (fim < 0) {
				fim = producoes.length();
			}
			
			String naoTerminal;
			naoTerminal = producoes.substring(0, fim);
			
			if (producoes.length() > fim) {
				producoes = producoes.substring(fim+1);
				continuar = true;
			}
			
			//System.out.println("NT: \""+naoTerminal+"\" | P: \""+producoes+"\"");
			this.gerarNaoTerminal(naoTerminal);
		} while (continuar);
		
		return true;
	}
	private void gerarNaoTerminal(String producoes) {
		int indiceProducoes;
		indiceProducoes = producoes.indexOf("->");
		
		String simboloNaoTerminal;
		simboloNaoTerminal = producoes.substring(0, indiceProducoes);
		
		producoes = producoes.substring(indiceProducoes+2);
		
		NaoTerminal naoTerminal;
		naoTerminal = new NaoTerminal(simboloNaoTerminal);
		naoTerminal = this.naoTerminais.add(naoTerminal);
		
		System.out.println("NT: \""+simboloNaoTerminal+"\" | P: \""+producoes+"\"");
		
		boolean continuar;
		do {
			continuar = false;
			
			if (producoes.equals("")) {
				break;
			}
			
			int fim;
			fim = producoes.indexOf("|");
			
			if (fim < 0) {
				fim = producoes.length();
			}
			
			String producao;
			producao = producoes.substring(0, fim);
			
			char simboloTerminal;
			simboloTerminal = producao.charAt(0);
			
			String simboloNaoTermin;
			simboloNaoTermin = producao.substring(1);
			
			Terminal terminal;
			terminal = new Terminal(simboloTerminal);
			
			NaoTerminal naoTermin;
			naoTermin = null;
			
			if (!simboloNaoTermin.equals("")) {
				naoTermin = new NaoTerminal(simboloNaoTermin);
				naoTermin = this.naoTerminais.add(naoTermin);
			}
			naoTerminal.addProducao(terminal, naoTermin);
			
			if (producoes.length() > fim) {
				producoes = producoes.substring(fim+1);
				continuar = true;
			}
			
			System.out.println(producao+"|"+simboloTerminal+"|"+simboloNaoTermin+"|");
		} while (continuar);
	}
	private String formataProducoes(String producoes) {
		producoes = producoes.replaceAll(" ", "");
		
		int fim;
		fim = producoes.length();
		
		String ultimosChar;
		ultimosChar = producoes.substring(fim-1, fim);
		
		if (ultimosChar.equals("\n")) {
			producoes = producoes.substring(0, fim-1);
		}
		
		return producoes;
	}
}
