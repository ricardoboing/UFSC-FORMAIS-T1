package gramatica;

import java.util.Iterator;

import automato.Automato;
import automato.Estado;
import automato.Transicao;
import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoEstado;
import conjunto.ConjuntoNaoTerminal;
import conjunto.ConjuntoObject;
import util.AlfabetoPortuguesMaiusculo;

public class Gramatica {
	public static final String SEPARADOR_NT = "\n";
	public static final String SEPARADOR_PRODUCAO = "|";
	public static final String SEPARADOR_NT_PRODUCAO = "->";
	
	private ConjuntoNaoTerminal conjuntoNaoTerminal;
	private ConjuntoAlfabeto conjuntoAlfabeto;
	
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
	private NaoTerminal addNaoTerminal(NaoTerminal naoTerminal) {
		naoTerminal = this.conjuntoNaoTerminal.add(naoTerminal);
		
		if (this.naoTerminalInicial == null) {
			this.naoTerminalInicial = naoTerminal;
		}
		
		return naoTerminal;
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
					stringConjuntoProducaoDoNaoTerminal += Gramatica.SEPARADOR_PRODUCAO;
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
				stringConjuntoProducaoCompleto += Gramatica.SEPARADOR_NT;
			}
			
			// Concatena o naoTerminal com suas producoes na string global
			stringConjuntoProducaoCompleto += naoTerminal.getSimbolo();
			stringConjuntoProducaoCompleto += Gramatica.SEPARADOR_NT_PRODUCAO;
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
			fimNaoTerminal = stringConjuntoProducao.indexOf(Gramatica.SEPARADOR_NT);
			
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
		indiceProducoes = stringConjuntoProducao.indexOf(Gramatica.SEPARADOR_NT_PRODUCAO);
		
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
			indiceFimConjuntoProducao = stringConjuntoProducao.indexOf(Gramatica.SEPARADOR_PRODUCAO);
			
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
			
			// Verifica se existe mais alguma producao (depois do Gramatica.SEPARADOR_PRODUCAO)
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
		
		// Remove os doisUltimosCharDaProducao caso sejam o Gramatica.SEPARADOR_NT
		if (doisUltimosCharDaProducao.equals(Gramatica.SEPARADOR_NT)) {
			stringConjuntoProducao = stringConjuntoProducao.substring(0, indiceFimConjuntoProducao-1);
		}
		
		return stringConjuntoProducao;
	}
	/*	Cria uma Gramatica a partir de um Automato
	 */
	private void gerarGramatica(Automato automato) {
		this.conjuntoAlfabeto = automato.getConjuntoAlfabeto().clone();
		
		ConjuntoEstado conjuntoEstado;
		conjuntoEstado = automato.getConjuntoEstado();
		
		// Para cada estadoDoAutomato eh criado um naoTerminalDaGramatica correspondente.
		for (int c = 0; c < conjuntoEstado.size(); c++) {
			Estado estadoDoAutomato;
			estadoDoAutomato = conjuntoEstado.get(c);
			
			NaoTerminal naoTerminalNovo;
			naoTerminalNovo = new NaoTerminal(estadoDoAutomato.getSimbolo());
			naoTerminalNovo = this.addNaoTerminal(naoTerminalNovo);
			
			ConjuntoObject<Transicao> conjuntoTransicao;
			conjuntoTransicao = estadoDoAutomato.getConjuntoTransicao();
			
			// Para cada transicao dos estadosDoAutomato eh criado uma producao correspondente.
			for (int i = 0; i < conjuntoTransicao.size(); i++) {
				Transicao transicaoDoEstadoDoAutomato;
				transicaoDoEstadoDoAutomato = conjuntoTransicao.get(i);
				
				Terminal terminalDaTransicao;
				terminalDaTransicao = new Terminal(transicaoDoEstadoDoAutomato.getSimboloEntrada());
				
				Estado estadoDestinoDaTransicao;
				estadoDestinoDaTransicao = transicaoDoEstadoDoAutomato.getEstadoDestino();
				
				NaoTerminal naoTerminalDaTransicao;
				naoTerminalDaTransicao = new NaoTerminal(estadoDestinoDaTransicao.getSimbolo());
				naoTerminalDaTransicao = this.addNaoTerminal(naoTerminalDaTransicao);
				
				// Cria uma producao do formato "A -> bB"
				Producao producaoDoNaoTerminalNovo;
				producaoDoNaoTerminalNovo = new Producao();
				producaoDoNaoTerminalNovo.setTerminal(terminalDaTransicao);
				producaoDoNaoTerminalNovo.setNaoTerminal(naoTerminalDaTransicao);
				
				naoTerminalNovo.addProducao(producaoDoNaoTerminalNovo);
				
				// Cria uma producao do formato "A -> b"
				if (estadoDestinoDaTransicao.isFinal()) {
					Producao producao2DoNaoTerminalNovo;
					producao2DoNaoTerminalNovo = new Producao();
					producao2DoNaoTerminalNovo.setTerminal(terminalDaTransicao.clone());
					
					naoTerminalNovo.addProducao(producao2DoNaoTerminalNovo);
				}
			}
		}
		
		Estado estadoInicialDoAutomato;
		estadoInicialDoAutomato = automato.getEstadoInicial();
		
		// Caso o estado S, inicial, seja final, entao adiciona a producao "S -> epsilon"
		if (estadoInicialDoAutomato.isFinal()) {
			NaoTerminal naoTerminalInicialNovo;
			naoTerminalInicialNovo = this.naoTerminalInicial.clone();
			naoTerminalInicialNovo.setSimbolo("sera_alterado_posteriormente");
			
			Terminal terminalEpsilon;
			terminalEpsilon = new Terminal(Automato.EPSILON);
			
			Producao producaoEpsilon;
			producaoEpsilon = new Producao();
			producaoEpsilon.setTerminal(terminalEpsilon);
			
			naoTerminalInicialNovo.addProducao(producaoEpsilon);
			
			this.naoTerminalInicial = naoTerminalInicialNovo;
		}
		
		AlfabetoPortuguesMaiusculo alfabetoPortugues;
		alfabetoPortugues = new AlfabetoPortuguesMaiusculo();
		
		// Altera o simbolo dos NaoTerminal. Essa etapa nao pode ser feita durante o mapeamento dos "Estado" para "NaoTerminal"
		for (int c = 0; c < this.conjuntoNaoTerminal.size(); c++) {
			NaoTerminal naoTerminal;
			naoTerminal = this.conjuntoNaoTerminal.get(c);
			//naoTerminal.setSimbolo(alfabetoPortugues.proximaLetra());
		}
	}
}
