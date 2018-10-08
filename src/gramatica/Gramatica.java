package gramatica;

import java.util.Iterator;

import automato.Automato;
import automato.Estado;
import automato.OperarAutomato;
import automato.Transicao;
import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoEstado;
import conjunto.ConjuntoNaoTerminal;
import conjunto.ConjuntoObject;
import janela.principal.ManagerLinguagem;
import util.AlfabetoPortuguesMaiusculo;
import util.ELinguagem;
import util.LinguagemGerador;

public class Gramatica implements LinguagemGerador {
	public static final String SEPARADOR_NT = "\n";
	public static final String SEPARADOR_PRODUCAO = "|";
	public static final String SEPARADOR_PRODUCAO_SPLIT = "\\|";
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
		this.load();
	}
	private void load() {
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
	@Override
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
		String stringConjuntoProducao;
		stringConjuntoProducao = "";
		
		Iterator<NaoTerminal> iteratorConjuntoNaoTerminal;
		iteratorConjuntoNaoTerminal = this.conjuntoNaoTerminal.getIterador();
		
		/* Percorre o ConjuntoProducao de todo naoTerminal do ConjuntoNaoTerminal
		 * e, para cada uma, concatena a producao em uma string.
		 * Ex: S -> aS | a
		 */
		while (iteratorConjuntoNaoTerminal.hasNext()) {
			NaoTerminal naoTerminal;
			naoTerminal = iteratorConjuntoNaoTerminal.next();
			
			Iterator<Producao> iteratorConjuntoProducao;
			iteratorConjuntoProducao = naoTerminal.getConjuntoProducao().getIterador();
			
			String stringConjuntoProducaoDoNaoTerminal;
			stringConjuntoProducaoDoNaoTerminal = "";
			
			/* Percorre o conjuntoProducao do NaoTerminal e concatena
			 * cada producao em uma string
			 */
			while (iteratorConjuntoProducao.hasNext()) {
				// Concatena "|" a partir da segunda producao
				if (!stringConjuntoProducaoDoNaoTerminal.equals("")) {
					stringConjuntoProducaoDoNaoTerminal += Gramatica.SEPARADOR_PRODUCAO;
				}
				
				Producao producao;
				producao = iteratorConjuntoProducao.next();
				
				// Concatena o terminal
				stringConjuntoProducaoDoNaoTerminal += producao.getTerminal().getCharSimbolo();
				
				// Concatena o naoTerminal caso exista na producao
				if (producao.getNaoTerminal() != null) {
					stringConjuntoProducaoDoNaoTerminal += producao.getNaoTerminal().getSimbolo();
				}
			}
			
			// Concatena "\n" a partir do segundo naoTerminal
			if (!stringConjuntoProducao.equals("")) {
				stringConjuntoProducao += Gramatica.SEPARADOR_NT;
			}
			
			// Concatena o naoTerminal com suas producoes na string global
			stringConjuntoProducao += naoTerminal.getSimbolo();
			stringConjuntoProducao += Gramatica.SEPARADOR_NT_PRODUCAO;
			stringConjuntoProducao += stringConjuntoProducaoDoNaoTerminal;
		}
		
		return stringConjuntoProducao;
	}
	
	public void gerarGramatica(String stringGramatica) {
		this.load();
		stringGramatica = stringGramatica.replaceAll(" ", "");
		
		/*	Transforma stringGramatica em array de naoTerminais.
		 * 	Ex: array[0] = "S-> a | bA"; array[1] = "A -> b";
		 */
		String[] arrayNaoTerminal;
		arrayNaoTerminal = stringGramatica.split(Gramatica.SEPARADOR_NT);
		
		// Cria os naoTerminais, um a um, e cria suas producoes
		for (int c = 0; c < arrayNaoTerminal.length; c++) {
			String stringNaoTerminal;
			stringNaoTerminal = arrayNaoTerminal[c];
			
			String simboloNaoTerminal;
			simboloNaoTerminal = stringNaoTerminal.substring(0,1);
			
			NaoTerminal naoTerminal;
			naoTerminal = new NaoTerminal(simboloNaoTerminal);
			naoTerminal = this.addNaoTerminal(naoTerminal);
			
			String stringProducoes;
			stringProducoes = stringNaoTerminal.substring(Gramatica.SEPARADOR_NT_PRODUCAO.length()+1);
			
			/*	Transforma stringProducoes em array de producoes.
			 * 	Ex (S-> a | bS): array[0] = "a"; array[1] = "bS";
			 */
			String[] arrayProducao;
			arrayProducao = stringProducoes.split(Gramatica.SEPARADOR_PRODUCAO_SPLIT);
			
			for (int i = 0; i < arrayProducao.length; i++) {
				String stringProducao;
				stringProducao = arrayProducao[i];
				
				char simboloTerminal;
				simboloTerminal = stringProducao.charAt(0);
				
				Terminal terminalDaProducao;
				terminalDaProducao = new Terminal(simboloTerminal);
				
				// Adiciona os terminais (exceto Epsilon) no alfabeto
				if (simboloTerminal != ManagerLinguagem.EPSILON) {
					this.conjuntoAlfabeto.add(simboloTerminal);
				}
				
				NaoTerminal naoTerminalDaProducao;
				naoTerminalDaProducao = null;
				
				// Caso seja uma producao do tipo "aA"
				if (stringProducao.length() == 2) {
					naoTerminalDaProducao = new NaoTerminal(stringProducao.substring(1));
					naoTerminalDaProducao = this.addNaoTerminal(naoTerminalDaProducao);
				}
				
				naoTerminal.addProducao(terminalDaProducao, naoTerminalDaProducao);
			}
		}
	}
	/*	Cria uma Gramatica a partir de um Automato
	 */
	private void gerarGramatica(Automato automatoOriginal) {
		Automato automatoClone;
		automatoClone = OperarAutomato.determinizar(automatoOriginal.clone());
		
		this.conjuntoAlfabeto = automatoClone.getConjuntoAlfabeto().clone();
		this.conjuntoAlfabeto.remove(ManagerLinguagem.EPSILON);
		
		ConjuntoEstado conjuntoEstado;
		conjuntoEstado = automatoClone.getConjuntoEstado();
		
		// Para cada estadoDoAutomato eh criado um naoTerminalDaGramatica correspondente.
		for (int c = 0; c < conjuntoEstado.size(); c++) {
			Estado estado;
			estado = conjuntoEstado.get(c);
			
			NaoTerminal naoTerminalNovo;
			naoTerminalNovo = new NaoTerminal(estado.getSimbolo());
			naoTerminalNovo = this.addNaoTerminal(naoTerminalNovo);
			
			ConjuntoObject<Transicao> conjuntoTransicao;
			conjuntoTransicao = estado.getConjuntoTransicao();
			
			// Para cada transicao dos estadosDoAutomato eh criado uma producao correspondente.
			for (int i = 0; i < conjuntoTransicao.size(); i++) {
				Transicao transicaoDoEstado;
				transicaoDoEstado = conjuntoTransicao.get(i);
				
				Terminal terminalDaTransicao;
				terminalDaTransicao = new Terminal(transicaoDoEstado.getSimboloEntrada());
				
				Estado estadoDestinoDaTransicao;
				estadoDestinoDaTransicao = transicaoDoEstado.getEstadoDestino();
				
				NaoTerminal naoTerminalDaTransicao;
				naoTerminalDaTransicao = new NaoTerminal(estadoDestinoDaTransicao.getSimbolo());
				naoTerminalDaTransicao = this.addNaoTerminal(naoTerminalDaTransicao);
				
				// Cria uma producao do formato "A -> bB"
				if (estadoDestinoDaTransicao.getConjuntoTransicao().size() > 0) {
					Producao producaoDoNaoTerminalNovo;
					producaoDoNaoTerminalNovo = new Producao();
					producaoDoNaoTerminalNovo.setTerminal(terminalDaTransicao);
					producaoDoNaoTerminalNovo.setNaoTerminal(naoTerminalDaTransicao);
					
					naoTerminalNovo.addProducao(producaoDoNaoTerminalNovo);
				}
				// Cria uma producao do formato "A -> b"
				if (estadoDestinoDaTransicao.isFinal()) {
					Producao producao2DoNaoTerminalNovo;
					producao2DoNaoTerminalNovo = new Producao();
					producao2DoNaoTerminalNovo.setTerminal(terminalDaTransicao.clone());
					
					naoTerminalNovo.addProducao(producao2DoNaoTerminalNovo);
				}
			}
			
			if (naoTerminalNovo.getConjuntoProducao().size() == 0) {
				this.conjuntoNaoTerminal.remove(naoTerminalNovo);
			}
		}
		
		Estado estadoInicialDoAutomato;
		estadoInicialDoAutomato = automatoClone.getEstadoInicial();
		
		// Caso o estado S, inicial, seja final, entao adiciona a producao "S -> epsilon"
		if (estadoInicialDoAutomato.isFinal()) {
			NaoTerminal naoTerminalInicialNovo;
			naoTerminalInicialNovo = this.naoTerminalInicial.clone();
			naoTerminalInicialNovo.setSimbolo("sera_alterado_posteriormente");
			
			Terminal terminalEpsilon;
			terminalEpsilon = new Terminal(ManagerLinguagem.EPSILON);
			
			Producao producaoEpsilon;
			producaoEpsilon = new Producao();
			producaoEpsilon.setTerminal(terminalEpsilon);
			
			naoTerminalInicialNovo.addProducao(producaoEpsilon);
			
			this.naoTerminalInicial = naoTerminalInicialNovo;
			this.conjuntoNaoTerminal.add(naoTerminalInicialNovo);
			
			ConjuntoNaoTerminal conjuntoNaoTerminalClone;
			conjuntoNaoTerminalClone = this.conjuntoNaoTerminal.clone();
			
			// Poe o naoTerminalInicial no topo do conjunto
			this.conjuntoNaoTerminal = new ConjuntoNaoTerminal();
			this.conjuntoNaoTerminal.add(this.naoTerminalInicial);
			this.conjuntoNaoTerminal.add(conjuntoNaoTerminalClone);
		}
		
		AlfabetoPortuguesMaiusculo alfabetoPortugues;
		alfabetoPortugues = new AlfabetoPortuguesMaiusculo();
		
		/* "Renomeia" o simbolo dos NaoTerminal.
		 *  Essa etapa nao pode ser feita durante o mapeamento dos "Estado" para "NaoTerminal"
		 */
		for (int c = 0; c < this.conjuntoNaoTerminal.size(); c++) {
			NaoTerminal naoTerminal;
			naoTerminal = this.conjuntoNaoTerminal.get(c);
			naoTerminal.setSimbolo(alfabetoPortugues.proximaLetra());
		}
	}
	@Override
	public ELinguagem getELinguagem() {
		return ELinguagem.GRAMATICA;
	}
	
	
	
	
	
	
	
	
	
	// Implementar...
	public static boolean entradaValida(String stringGramatica) {
		stringGramatica = stringGramatica.replaceAll(" ", "");
		
		/*	Transforma stringGramatica em array de naoTerminais.
		 * 	Ex: array[0] = "S-> a | bA"; array[1] = "A -> b";
		 */
		String[] arrayNaoTerminal;
		arrayNaoTerminal = stringGramatica.split(Gramatica.SEPARADOR_NT);
		
		char simboloNaoTerminalInicial;
		simboloNaoTerminalInicial = ' ';
		
		boolean naoTerminalInicialPossuiEpsilon;
		naoTerminalInicialPossuiEpsilon = false;
		
		// Cria os naoTerminais, um a um, e cria suas producoes
		for (int c = 0; c < arrayNaoTerminal.length; c++) {
			String stringNaoTerminal;
			stringNaoTerminal = arrayNaoTerminal[c];
			
			String simboloNaoTerminal;
			simboloNaoTerminal = stringNaoTerminal.substring(0,1);
			
			if (stringNaoTerminal.length() < 4) {
				System.out.println("erro 0");
				return false;
			}
			if (!AlfabetoPortuguesMaiusculo.validar(simboloNaoTerminal.charAt(0))) {
				System.out.println("erro 1");
				return false;
			}
			if (stringNaoTerminal.charAt(1) != '-' || stringNaoTerminal.charAt(2) != '>') {
				System.out.println("erro 2");
				return false;
			}
			
			if (c == 0) {
				simboloNaoTerminalInicial = simboloNaoTerminal.charAt(0);
			}
			
			String stringProducoes;
			stringProducoes = stringNaoTerminal.substring(Gramatica.SEPARADOR_NT_PRODUCAO.length()+1);
			
			/*	Transforma stringProducoes em array de producoes.
			 * 	Ex (S-> a | bS): array[0] = "a"; array[1] = "bS";
			 */
			String[] arrayProducao;
			arrayProducao = stringProducoes.split(Gramatica.SEPARADOR_PRODUCAO_SPLIT);
			
			for (int i = 0; i < arrayProducao.length; i++) {
				String stringProducao;
				stringProducao = arrayProducao[i];
				
				char simboloTerminal;
				simboloTerminal = stringProducao.charAt(0);
				
				if (simboloTerminal == ManagerLinguagem.EPSILON && i == 0) {
					naoTerminalInicialPossuiEpsilon = true;
				}
				
				if (simboloTerminal == ManagerLinguagem.EPSILON && i != 0) {
					System.out.println("erro 3");
					return false;
				}
				
				if (AlfabetoPortuguesMaiusculo.validar(simboloTerminal)) {
					System.out.println("erro 4");
					return false;
				}
				if (stringProducao.length() > 2) {
					System.out.println("erro 5");
					return false;
				}
				
				if (stringProducao.length() == 2) {
					char simboloNaoTerminalDaProducao;
					simboloNaoTerminalDaProducao = stringProducao.charAt(1);
					
					if (!AlfabetoPortuguesMaiusculo.validar(simboloNaoTerminalDaProducao)) {
						System.out.println("erro 6");
						return false;
					}
					if (naoTerminalInicialPossuiEpsilon && simboloNaoTerminalDaProducao == simboloNaoTerminalInicial) {
						System.out.println("erro 7");
						return false;
					}
				}
			}
		}
		
		
		return true;
	}
}
