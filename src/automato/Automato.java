package automato;

import java.util.Iterator;

import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoEstado;
import conjunto.ConjuntoNaoTerminal;
import conjunto.ConjuntoObject;
import expressao.Expressao;
import expressao.NoDeSimone;
import gramatica.Gramatica;
import gramatica.NaoTerminal;
import gramatica.Producao;
import gramatica.Terminal;
import util.Linguagem;
import util.LinguagemGerador;
import view.principal.ManagerLinguagem;

public class Automato implements Linguagem {
	private String nome;
	private Estado estadoInicial;
	
	private ConjuntoEstado conjuntoEstado;
	private ConjuntoAlfabeto conjuntoAlfabeto;
	private ConjuntoObject<Transicao> conjuntoTransicao;
	
	private String nomePai1, nomePai2;
	private String nomeOperacaoGerador1;
	
	public Automato() {
		this("sem_nome");
	}
	public Automato(String nome) {
		this.nome = nome;
		this.estadoInicial = null;
		
		this.nomePai1 = "";
		this.nomePai2 = "";
		this.nomeOperacaoGerador1 = "";
		
		this.conjuntoEstado = new ConjuntoEstado();
		this.conjuntoAlfabeto = new ConjuntoAlfabeto();
		this.conjuntoTransicao = new ConjuntoObject<Transicao>();
	}
	public Automato(String nome, LinguagemGerador linguagemGerador) {
		this(nome);
		
		switch (linguagemGerador.getELinguagem()) {
			case GRAMATICA:
				this.gerarAutomato((Gramatica)linguagemGerador);
				break;
			case EXPRESSAO:
				this.gerarAutomato((Expressao)linguagemGerador);
				break;
			default:
				break;
		}
	}
	public Automato(String nome, Gramatica gramatica) {
		this(nome);
		this.gerarAutomato(gramatica);
	}
	public Automato(String nome, Expressao expressao) {
		this(nome);
	}
	// Construtor para facilitar a criacao de Automatos em testes JUnit
	public Automato(String nome, String stringAutomato) {
		this(nome);
		this.gerarAutomato(stringAutomato);
	}
	
	// Metodo para gerar um Automato salvo em disco
	private void gerarAutomato(String stringAutomato) {
		stringAutomato = stringAutomato.replaceAll(" ", "");
		
		/*	Transforma stringAutomato em array de estados.
		 * 	Ex: array[0] = "S-> a | bA"; array[1] = "A -> b";
		 */
		String[] arrayNaoTerminal;
		arrayNaoTerminal = stringAutomato.split("\n");
		
		// Cria os naoTerminais, um a um, e cria suas producoes
		for (int c = 0; c < arrayNaoTerminal.length; c++) {
			String stringEstado;
			stringEstado = arrayNaoTerminal[c];
			
			char isFinal, isInicial;
			isFinal = stringEstado.charAt(0);
			isInicial = stringEstado.charAt(1);
			
			String simboloDoEstado;
			simboloDoEstado = stringEstado.substring(2,3);
			
			Estado estado;
			estado = new Estado(simboloDoEstado);
			estado = this.addEstado(estado);
			
			if (isFinal == '*') {
				estado.setFinal(true);
			}
			if (isInicial == '>') {
				estado.setInicial(true);
				this.setEstadoInicial(estado);
			}
			
			String stringTransicoes;
			stringTransicoes = stringEstado.substring(5);
			
			/*	Transforma stringTransicoes em array de producoes.
			 * 	Ex (S-> a | bS): array[0] = "a"; array[1] = "bS";
			 */
			String[] arrayTransicao;
			arrayTransicao = stringTransicoes.split("\\|");
			
			for (int i = 0; i < arrayTransicao.length; i++) {
				String stringTransicao;
				stringTransicao = arrayTransicao[i];
				
				char entradaTransicao;
				entradaTransicao = stringTransicao.charAt(0);
				
				this.conjuntoAlfabeto.add(entradaTransicao);
				
				Estado estadoTransicao;
				estadoTransicao = new Estado(stringTransicao.substring(1));
				estadoTransicao = this.addEstado(estadoTransicao);
				
				estado.addTransicao(entradaTransicao, estadoTransicao);
			}
		}
	}
	// Metodo para facilitar a criacao de Automatos em testes JUnit
	public String getStringConjuntoTransicao() {
		String stringConjuntoTransicaoCompleto;
		stringConjuntoTransicaoCompleto = "";
		
		Iterator<Estado> iteratorConjuntoEstado;
		iteratorConjuntoEstado = this.conjuntoEstado.getIterador();
		
		/* Percorre o ConjuntoProducao de todo naoTerminal do ConjuntoNaoTerminal
		 * e, para cada uma, concatena a producao em uma string.
		 * Ex: S -> aS | a
		 */
		while (iteratorConjuntoEstado.hasNext()) {
			Estado estado;
			estado = iteratorConjuntoEstado.next();
			
			Iterator<Transicao> iteratorConjuntoTransicao;
			iteratorConjuntoTransicao = estado.getConjuntoTransicao().getIterador();
			
			String stringConjuntoTransicao;
			stringConjuntoTransicao = "";
			
			/* Percorre o conjuntoProducao do NaoTerminal e concatena
			 * cada producao em uma string
			 */
			while (iteratorConjuntoTransicao.hasNext()) {
				// Concatena "|" a partir da segunda producao
				if (!stringConjuntoTransicao.equals("")) {
					stringConjuntoTransicao += "|";
				}
				
				Transicao transicao;
				transicao = iteratorConjuntoTransicao.next();
				
				// Concatena o terminal
				stringConjuntoTransicao += transicao.getSimboloEntrada();
				stringConjuntoTransicao += transicao.getEstadoDestino().getSimbolo();
			}
			
			// Concatena "\n" a partir do segundo naoTerminal
			if (!stringConjuntoTransicaoCompleto.equals("")) {
				stringConjuntoTransicaoCompleto += "\n";
			}
			
			// Concatena o naoTerminal com suas producoes na string global
			if (estado.isFinal()) {
				stringConjuntoTransicaoCompleto += "*";
			} else {
				stringConjuntoTransicaoCompleto += "_";
			}
			if (estado.isInicial()) {
				stringConjuntoTransicaoCompleto += ">";
			} else {
				stringConjuntoTransicaoCompleto += "_";
			}
			
			stringConjuntoTransicaoCompleto += estado.getSimbolo();
			stringConjuntoTransicaoCompleto += "->";
			stringConjuntoTransicaoCompleto += stringConjuntoTransicao;
		}
		
		return stringConjuntoTransicaoCompleto;
	}
	
	public void alterarSimboloDosEstados() {
		// Altera o simbolo dos estados. Essa etapa nao pode ser realizada durante o mapeamento de "NaoTerminal" para "Estado"
		for (int c = 0; c < this.conjuntoEstado.size(); c++) {
			Estado estado;
			estado = this.conjuntoEstado.get(c);
			estado.setSimbolo("q"+c);
		}
	}
	private void gerarAutomato(Gramatica gramatica) {
		this.conjuntoAlfabeto = gramatica.getConjuntoAlfabeto().clone();
		
		Estado estadoFinal;
		estadoFinal = new Estado("alterado_no_fim_do_metodo");
		estadoFinal.setFinal(true);
		estadoFinal = this.addEstado(estadoFinal);
		
		ConjuntoNaoTerminal conjuntoNaoTerminal;
		conjuntoNaoTerminal = gramatica.getConjuntoNaoTerminal();
		
		for (int c = 0; c < conjuntoNaoTerminal.size(); c++) {
			NaoTerminal naoTerminal;
			naoTerminal = conjuntoNaoTerminal.get(c);
			
			Estado estado;
			estado = new Estado(naoTerminal.getSimbolo());
			estado = this.addEstado(estado);
			
			if (c == 0) {
				estado.setInicial(true);
				this.setEstadoInicial(estado);
			}
			
			ConjuntoObject<Producao> conjuntoProducao;
			conjuntoProducao = naoTerminal.getConjuntoProducao();
			
			for (int i = 0; i < conjuntoProducao.size(); i++) {
				Producao producao;
				producao = conjuntoProducao.get(i);
				
				Terminal terminal;
				terminal = producao.getTerminal();
				
				if (terminal.getCharSimbolo() == ManagerLinguagem.EPSILON) {
					estado.setFinal(true);
					continue;
				}
				
				NaoTerminal naoTerminalDaProducao;
				naoTerminalDaProducao = producao.getNaoTerminal();
				
				Estado estadoDestinoDaTransicao;
				
				if (naoTerminalDaProducao == null) {
					estadoDestinoDaTransicao = estadoFinal;
				} else {
					estadoDestinoDaTransicao = new Estado(naoTerminalDaProducao.getSimbolo());
					estadoDestinoDaTransicao = this.addEstado(estadoDestinoDaTransicao);
				}
				
				Transicao transicao;
				transicao = new Transicao();
				transicao.setEstadoOrigem(estado);
				transicao.setEstadoDestino(estadoDestinoDaTransicao);
				transicao.setSimboloEntrada(terminal.getCharSimbolo());
				
				estado.addTransicao(transicao);
			}
		}
		
		// Altera a ordem dentro do conjunto
		this.conjuntoEstado.remove(estadoFinal);
		this.conjuntoEstado.add(estadoFinal);
		
		this.alterarSimboloDosEstados();
	}
	private void gerarAutomato(Expressao expressao) {
		NoDeSimone no;
		no = new NoDeSimone(expressao);
		no.gerarArvoreSintatica();
		
		Automato automato;
		automato = no.gerarAutomato();
		
		this.estadoInicial = automato.estadoInicial;
		
		this.conjuntoAlfabeto = automato.conjuntoAlfabeto;
		this.conjuntoTransicao = automato.conjuntoTransicao;
		this.conjuntoEstado = automato.conjuntoEstado;
	}
	
	// Metodos Add
	public Estado addEstado(Estado estado) {
		return this.conjuntoEstado.add(estado);
	}
	public void addEntradaAlfabeto(char entradaAlfabeto) {
		this.conjuntoAlfabeto.add(entradaAlfabeto);
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
	@Override
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
		
		// Implementar... (Necessario???)
		
		return false;
	}
	
	public void setNomePai1(String nomePai) {
		this.nomePai1 = nomePai;
	}
	public void setNomePai2(String nomePai2) {
		this.nomePai2 = nomePai2;
	}
	public void setNomeOperacaoGerador1(String nomeOperacaoGerador1) {
		this.nomeOperacaoGerador1 = nomeOperacaoGerador1;
	}
	
	public String getNomePai1() {
		return this.nomePai1;
	}
	public String getNomePai2() {
		return this.nomePai2;
	}
	public String getGeradorPor1() {
		return this.nomeOperacaoGerador1;
	}
	
	
	public void print() {
		for (int c = 0; c < this.conjuntoEstado.size(); c++) {
			Estado estado;
			estado = this.conjuntoEstado.get(c);
			
			ConjuntoObject<Transicao> conjuntoTransicao;
			conjuntoTransicao = estado.getConjuntoTransicao();
			
			if (conjuntoTransicao.size() == 0) {
				if (estado.isInicial()) {
					System.out.print(">");
				} else {
					System.out.print(" ");
				}
				if (estado.isFinal()) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
				
				System.out.println(estado.getSimbolo()+": ");
			}
			
			for (int i = 0; i < conjuntoTransicao.size(); i++) {
				Transicao transicao;
				transicao = conjuntoTransicao.get(i);
				
				if (transicao.getEstadoOrigem().isInicial()) {
					System.out.print(">");
				} else {
					System.out.print(" ");
				}
				if (transicao.getEstadoOrigem().isFinal()) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
				
				System.out.print(transicao.getEstadoOrigem().getSimbolo()+": ");
				System.out.print("("+transicao.getEstadoOrigem().getSimbolo());
				System.out.print(", "+transicao.getSimboloEntrada());
				System.out.println(") -> "+transicao.getEstadoDestino().getSimbolo());
			}
		}
	}
	
	// Implementar...
	public static boolean entradaValida(Automato automato) {
		return true;
	}
}
