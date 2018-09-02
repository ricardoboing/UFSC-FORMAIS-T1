package gramatica;

import java.util.Iterator;

import automato.Automato;
import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoNaoTerminal;
import conjunto.ConjuntoObject;

public class Gramatica {
	private ConjuntoNaoTerminal conjuntoNaoTerminal;
	private ConjuntoAlfabeto conjuntoAlfabeto;
	private ConjuntoObject<Producao> conjuntoProducao;
	
	private NaoTerminal naoTerminalInicial;
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
		this.naoTerminalInicial = null;
		
		this.conjuntoAlfabeto = new ConjuntoAlfabeto();
		this.conjuntoNaoTerminal = new ConjuntoNaoTerminal();
		this.conjuntoProducao = new ConjuntoObject<Producao>();
	}
	
	// Metodos Setters
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setNaoTerminalInicial(NaoTerminal naoTerminalInicial) {
		this.naoTerminalInicial = naoTerminalInicial;
	}
	public void setConjuntoAlfabeto(ConjuntoAlfabeto conjuntoAlfabeto) {
		this.conjuntoAlfabeto = conjuntoAlfabeto;
	}
	public void setConjuntoProducao(ConjuntoObject<Producao> conjuntoProducao) {
		this.conjuntoProducao = conjuntoProducao;
	}
	public void setNaoTerminais(ConjuntoNaoTerminal conjuntoNaoTerminal) {
		this.conjuntoNaoTerminal = conjuntoNaoTerminal;
	}
	
	// Metodos Getters
	public String getNome() {
		return nome;
	}
	public NaoTerminal getNaoTerminalInicial() {
		return naoTerminalInicial;
	}
	public ConjuntoNaoTerminal getConjuntoNaoTerminal() {
		return conjuntoNaoTerminal;
	}
	public ConjuntoAlfabeto getConjuntoAlfabeto() {
		return conjuntoAlfabeto;
	}
	public ConjuntoObject<Producao> getConjuntoProducao() {
		return conjuntoProducao;
	}
	@Override
	public Gramatica clone() {
		return new Gramatica(this.getStringConjuntoProducao());
	}
	public String getStringConjuntoProducao() {
		String stringConjuntoProducaoCompleto;
		stringConjuntoProducaoCompleto = "";
		
		Iterator<NaoTerminal> iteratorConjuntoNaoTerminal;
		iteratorConjuntoNaoTerminal = this.conjuntoNaoTerminal.getIterador();
		
		/* Percorre o ConjuntoProducao de todo naoTerminal do ConjuntoNaoTerminal
		 * e, para cada uma, concatena a producao em uma string.
		 * Ex: S -> aS | a
		 */
		while (iteratorConjuntoNaoTerminal.hasNext()) {
			NaoTerminal naoTerminal;
			naoTerminal = iteratorConjuntoNaoTerminal.next();
			
			Iterator<Producao> iteratorConjuntoProducaoDoNaoTerminal;
			iteratorConjuntoProducaoDoNaoTerminal = naoTerminal.getConjuntoProducao().getIterador();
			
			String stringConjuntoProducaoDoNaoTerminal;
			stringConjuntoProducaoDoNaoTerminal = "";
			
			/* Percorre o conjuntoProducao do NaoTerminal e concatena
			 * cada producao em uma string
			 */
			while (iteratorConjuntoProducaoDoNaoTerminal.hasNext()) {
				// Concatena "|" a partir da segunda producao
				if (!stringConjuntoProducaoDoNaoTerminal.equals("")) {
					stringConjuntoProducaoDoNaoTerminal += "|";
				}
				
				Producao producao;
				producao = iteratorConjuntoProducaoDoNaoTerminal.next();
				
				// Concatena o terminal
				stringConjuntoProducaoDoNaoTerminal += producao.getTerminal().getCharSimbolo();
				
				// Concatena o naoTerminal caso exista na producao
				if (producao.getNaoTerminal() != null) {
					stringConjuntoProducaoDoNaoTerminal += producao.getNaoTerminal().getSimbolo();
				}
			}
			
			// Concatena "\n" a partir do segundo naoTerminal
			if (!stringConjuntoProducaoCompleto.equals("")) {
				stringConjuntoProducaoCompleto += "\n";
			}
			
			// Concatena o naoTerminal com suas producoes na string global
			stringConjuntoProducaoCompleto += naoTerminal.getSimbolo();
			stringConjuntoProducaoCompleto += "->";
			stringConjuntoProducaoCompleto += stringConjuntoProducaoDoNaoTerminal;
		}
		
		return stringConjuntoProducaoCompleto;
	}
	
	// Metodos Privados
	private boolean gerarGramatica(String stringConjuntoProducao) {
		// Remove espacos em branco
		stringConjuntoProducao = this.formatarStringConjuntoProducao(stringConjuntoProducao);
		
		// String vazia eh invalida
		if (stringConjuntoProducao.equals("")) {
			return false;
		}
		
		/* Para cada \n encontrado, a string eh quebrada em duas para
		 * obter o conjuntoProducao de um dado naoTerminal (e gera-lo).
		 * Ex: S -> aS | b\nA -> b | B\nB -> c eh dividido em tres por duas iteracoes:
		 * S -> aS
		 * A -> b
		 * B -> c
		 */
		boolean continuar;
		do {
			continuar = false;
			
			int fimNaoTerminal;
			fimNaoTerminal = stringConjuntoProducao.indexOf("\n");
			
			// Caso nao possua \n: obtem a string inteira
			if (fimNaoTerminal < 0) {
				fimNaoTerminal = stringConjuntoProducao.length();
			}
			
			String naoTerminal;
			naoTerminal = stringConjuntoProducao.substring(0, fimNaoTerminal);
			
			// Verifica se existe mais algum naoTerminal apos o \n encontrado
			if (stringConjuntoProducao.length() > fimNaoTerminal) {
				stringConjuntoProducao = stringConjuntoProducao.substring(fimNaoTerminal+1);
				continuar = true;
			}
			
			this.gerarNaoTerminal(naoTerminal);
		} while (continuar);
		
		return true;
	}
	private void gerarNaoTerminal(String stringConjuntoProducao) {
		// Obtem indice do final do naoTerminal, ou seja, inicio das producoes
		int indiceProducoes;
		indiceProducoes = stringConjuntoProducao.indexOf("->");
		
		String stringSimboloNaoTerminal;
		stringSimboloNaoTerminal = stringConjuntoProducao.substring(0, indiceProducoes);
		stringConjuntoProducao = stringConjuntoProducao.substring(indiceProducoes+2);
		
		// Cria o NaoTerminal
		NaoTerminal naoTerminal;
		naoTerminal = new NaoTerminal(stringSimboloNaoTerminal);
		naoTerminal = this.conjuntoNaoTerminal.add(naoTerminal);
		
		boolean existeProducao;
		do {
			existeProducao = false;
			
			int indiceFimConjuntoProducao;
			indiceFimConjuntoProducao = stringConjuntoProducao.indexOf("|");
			
			if (indiceFimConjuntoProducao < 0) {
				indiceFimConjuntoProducao = stringConjuntoProducao.length();
			}
			
			String producao;
			producao = stringConjuntoProducao.substring(0, indiceFimConjuntoProducao);
			
			char simboloTerminalDaProducao;
			simboloTerminalDaProducao = producao.charAt(0);
			simboloTerminalDaProducao = this.conjuntoAlfabeto.add(simboloTerminalDaProducao);
			
			Terminal terminalDaProducao;
			terminalDaProducao = new Terminal(simboloTerminalDaProducao);
			
			String simboloNaoTerminalDaProducao;
			simboloNaoTerminalDaProducao = producao.substring(1);
			
			NaoTerminal naoTerminalDaProducao;
			naoTerminalDaProducao = null;
			
			// Verifica se producao deriva um naoTerminal
			if (!simboloNaoTerminalDaProducao.equals("")) {
				naoTerminalDaProducao = new NaoTerminal(simboloNaoTerminalDaProducao);
				naoTerminalDaProducao = this.conjuntoNaoTerminal.add(naoTerminalDaProducao);
			}
			naoTerminal.addProducao(terminalDaProducao, naoTerminalDaProducao);
			
			// Verifica se existe mais alguma producao (depois do "|")
			if (stringConjuntoProducao.length() > indiceFimConjuntoProducao) {
				stringConjuntoProducao = stringConjuntoProducao.substring(indiceFimConjuntoProducao+1);
				existeProducao = true;
			}
			
			//System.out.println(producao+", T: "+simboloTerminalDaProducao+", NT: "+simboloNaoTerminalDaProducao);
		} while (existeProducao);
	}
	private String formatarStringConjuntoProducao(String stringConjuntoProducao) {
		// Descarta espacos em branco
		stringConjuntoProducao = stringConjuntoProducao.replaceAll(" ", "");
		
		int indiceFimConjuntoProducao;
		indiceFimConjuntoProducao = stringConjuntoProducao.length();
		
		String doisUltimosCharDaProducao;
		doisUltimosCharDaProducao = stringConjuntoProducao.substring(indiceFimConjuntoProducao-1, indiceFimConjuntoProducao);
		
		// Remove os doisUltimosCharDaProducao caso sejam o \n
		if (doisUltimosCharDaProducao.equals("\n")) {
			stringConjuntoProducao = stringConjuntoProducao.substring(0, indiceFimConjuntoProducao-1);
		}
		
		return stringConjuntoProducao;
	}
	
	private void gerarGramatica(Automato automato) {
		
	}
}
