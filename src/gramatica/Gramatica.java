package gramatica;

import java.util.Iterator;

import automato.Automato;
import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoNaoTerminal;
import conjunto.ConjuntoObject;

public class Gramatica {
	private ConjuntoNaoTerminal naoTerminais;
	private ConjuntoAlfabeto alfabeto;
	private ConjuntoObject<Producao> producoes;
	
	private NaoTerminal simboloInicial;
	private String nome;
	
	public Gramatica(Automato automato) {
		this();
		this.gerarGramatica(automato);
	}
	public Gramatica(String producoes) {
		this();
		this.gerarGramatica(producoes);
	}
	public Gramatica(String nome, String producoes) {
		this();
		
		this.nome = nome;
		this.gerarGramatica(producoes);
	}
	private Gramatica() {
		this.nome = "sem_nome";
		this.simboloInicial = null;
		
		this.alfabeto = new ConjuntoAlfabeto();
		this.naoTerminais = new ConjuntoNaoTerminal();
		this.producoes = new ConjuntoObject<Producao>();
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
	@Override
	public Gramatica clone() {
		return new Gramatica(this.getProducoesString());
	}
	public String getProducoesString() {
		String producoesString;
		producoesString = "";
		
		Iterator<NaoTerminal> naoTerminais;
		naoTerminais = this.naoTerminais.getIterador();
		
		/* Percorre todas as producoes de todos naoTerminais e, para
		 * cada uma, concatena a producao em formato de string.
		 * Ex: S -> aS | a
		 */
		while (naoTerminais.hasNext()) {
			NaoTerminal naoTerminal;
			naoTerminal = naoTerminais.next();
			
			Iterator<Producao> producoes;
			producoes = naoTerminal.getProducoes().getIterador();
			
			String naoTerminalProducoes;
			naoTerminalProducoes = "";
			
			// Percorre as producoes
			while (producoes.hasNext()) {
				// Se alguma outra producao ja foi concatenada
				if (!naoTerminalProducoes.equals("")) {
					naoTerminalProducoes += "|";
				}
				
				Producao producao;
				producao = producoes.next();
				
				// Concatena terminal
				naoTerminalProducoes += producao.getTerminal().getSimbolo();
				
				// Se houver naoTerminal entao concatena
				if (producao.getNaoTerminal() != null) {
					naoTerminalProducoes += producao.getNaoTerminal().getSimbolo();
				}
			}
			
			// Se algum outro naoTerminal ja foi concatenado
			if (!producoesString.equals("")) {
				producoesString += "\n";
			}
			
			// Concatena naoTerminal com suas producoes
			producoesString += naoTerminal.getSimbolo();
			producoesString += "->";
			producoesString += naoTerminalProducoes;
		}
		
		return producoesString;
	}
	
	// Metodos Privados
	private boolean gerarGramatica(String producoes) {
		// Formata a string de producoes, removendo espacos em branco, etc
		producoes = this.formataProducoes(producoes);
		
		// Se a string for vazia entao a gramatica eh invalida
		if (producoes.equals("")) {
			return false;
		}
		
		/* Percorre toda a string e, para cada \n encontrado, quebra a string
		 * em duas para obter as producoes de um dado naoTerminal (e gera-lo).
		 * Ex: S -> aS | b\nA -> b | B\nB -> c eh dividido em tres por duas iteracoes:
		 * S -> aS
		 * A -> b
		 * B -> c
		 */
		boolean continuar;
		do {
			continuar = false;
			
			int fimNaoTerminal;
			fimNaoTerminal = producoes.indexOf("\n");
			
			// Caso nao possua \n: obtem a string inteira
			if (fimNaoTerminal < 0) {
				fimNaoTerminal = producoes.length();
			}
			
			String naoTerminal;
			naoTerminal = producoes.substring(0, fimNaoTerminal);
			
			// Verifica se existe mais algum naoTerminal apos o \n encontrado
			if (producoes.length() > fimNaoTerminal) {
				producoes = producoes.substring(fimNaoTerminal+1);
				continuar = true;
			}
			
			this.gerarNaoTerminal(naoTerminal);
		} while (continuar);
		
		return true;
	}
	private void gerarNaoTerminal(String producoes) {
		// Obtem indice do final do naoTerminal, ou seja, inicio das producoes
		int indiceProducoes;
		indiceProducoes = producoes.indexOf("->");
		
		String naoTerminalSimbolo;
		naoTerminalSimbolo = producoes.substring(0, indiceProducoes);
		producoes = producoes.substring(indiceProducoes+2);
		
		// NaoTerminal a qual as producoes pertencem
		NaoTerminal naoTerminal;
		naoTerminal = new NaoTerminal(naoTerminalSimbolo);
		naoTerminal = this.naoTerminais.add(naoTerminal);
		
		boolean continuar;
		do {
			continuar = false;
			
			int fim;
			fim = producoes.indexOf("|");
			
			if (fim < 0) {
				fim = producoes.length();
			}
			
			String producao;
			producao = producoes.substring(0, fim);
			
			char producaoTerminalSimbolo;
			producaoTerminalSimbolo = producao.charAt(0);
			producaoTerminalSimbolo = this.alfabeto.add(producaoTerminalSimbolo);
			
			String producaoNaoTerminalSimbolo;
			producaoNaoTerminalSimbolo = producao.substring(1);
			
			Terminal producaoTerminal;
			producaoTerminal = new Terminal(producaoTerminalSimbolo);
			
			NaoTerminal producaoNaoTerminal;
			producaoNaoTerminal = null;
			
			// Verifica se producao deriva um naoTerminal
			if (!producaoNaoTerminalSimbolo.equals("")) {
				producaoNaoTerminal = new NaoTerminal(producaoNaoTerminalSimbolo);
				producaoNaoTerminal = this.naoTerminais.add(producaoNaoTerminal);
			}
			naoTerminal.addProducao(producaoTerminal, producaoNaoTerminal);
			
			// Verifica se existe mais alguma producao depois do "|"
			if (producoes.length() > fim) {
				producoes = producoes.substring(fim+1);
				continuar = true;
			}
			
			System.out.println(producao+"|"+producaoTerminalSimbolo+"|"+producaoNaoTerminalSimbolo+"|");
		} while (continuar);
	}
	private String formataProducoes(String producoes) {
		producoes = producoes.replaceAll(" ", "");
		
		int fim;
		fim = producoes.length();
		
		String ultimosChar;
		ultimosChar = producoes.substring(fim-1, fim);
		
		// Remove o \n no final da string, caso possua
		if (ultimosChar.equals("\n")) {
			producoes = producoes.substring(0, fim-1);
		}
		
		return producoes;
	}
	
	private void gerarGramatica(Automato automato) {
		
	}
}
