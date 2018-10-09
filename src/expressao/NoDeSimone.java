package expressao;

import java.util.ArrayList;

import automato.Automato;
import automato.Estado;
import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoObject;

public class NoDeSimone {
	private String simbolo;
	
	private NoDeSimone noFilhoDireito;
	private NoDeSimone noFilhoEsquerdo;
	private boolean nodoFimDaArvore;
	
	public NoDeSimone(String simbolo) {
		this(simbolo, false);
	}
	public NoDeSimone(Expressao expressao) {
		this(expressao.getToStringOriginal());
	}
	private NoDeSimone(String simbolo, boolean nodoFimDaArvore) {
		this.simbolo = simbolo;
		this.noFilhoDireito = null;
		this.noFilhoEsquerdo = null;
		this.nodoFimDaArvore = nodoFimDaArvore;
	}
	
	private void atualizarEstadoDeSimoneEquivalente(ConjuntoObject<ComposicaoDeSimone> conjuntoComposicao, EstadoDeSimone estadoEquivalente) {
		for (int i = 0; i < conjuntoComposicao.size(); i++) {
			ComposicaoDeSimone composicao;
			composicao = conjuntoComposicao.get(i);
			
			if (estadoEquivalente.equals(composicao.getEstadoDeSimone())) {
				composicao.setEstadoDeSimone(estadoEquivalente);
			}
		}
	}
	public Automato gerarAutomato() {
		// Alterar nome depois
		Automato novoAutomato;
		novoAutomato = new Automato();
		
		ConjuntoAlfabeto conjuntoAlfabeto;
		conjuntoAlfabeto = novoAutomato.getConjuntoAlfabeto();
		
		EstadoDeSimone estadoInicial;
		estadoInicial = new EstadoDeSimone(true);
		
		ConjuntoObject<EstadoDeSimone> conjuntoEstadoDeSimone;
		conjuntoEstadoDeSimone = new ConjuntoObject<EstadoDeSimone>();
		conjuntoEstadoDeSimone.add(estadoInicial);
		
		ConjuntoObject<ComposicaoDeSimone> conjuntoComposicao;
		conjuntoComposicao = new ConjuntoObject<ComposicaoDeSimone>();
		
		ConjuntoObject<NoDeSimone> conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir;
		conjuntoNoVisitadoDescer = new ConjuntoObject<NoDeSimone>();
		conjuntoNoVisitadoSubir = new ConjuntoObject<NoDeSimone>();
		
		this.descer(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, estadoInicial);
		estadoInicial.atualizarConjuntoComposicao(conjuntoComposicao);
		
		// Cria as composicoes
		for (int c = 0; c < conjuntoComposicao.size(); c++) {
			ComposicaoDeSimone composicao;
			composicao = conjuntoComposicao.get(c);
			
			ConjuntoObject<NoDeSimone> conjuntoNoDeSimone;
			conjuntoNoDeSimone = composicao.getConjuntoNoDeSimone();
			
			EstadoDeSimone estadoDaComposicao;
			estadoDaComposicao = composicao.getEstadoDeSimone();
			
			for (int i = 0; i < conjuntoNoDeSimone.size(); i++) {
				conjuntoNoVisitadoDescer = new ConjuntoObject<NoDeSimone>();
				conjuntoNoVisitadoSubir = new ConjuntoObject<NoDeSimone>();
				
				NoDeSimone noDeSimone;
				noDeSimone = conjuntoNoDeSimone.get(i);
				noDeSimone.subir(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, estadoDaComposicao);
			}
			
			estadoDaComposicao = conjuntoEstadoDeSimone.add(estadoDaComposicao);
			estadoDaComposicao.atualizarConjuntoComposicao(conjuntoComposicao);
			this.atualizarEstadoDeSimoneEquivalente(conjuntoComposicao, estadoDaComposicao);
		}
		
		// Criar o automato
		for (int c = 0; c < conjuntoEstadoDeSimone.size(); c++) {
			EstadoDeSimone estadoDeSimone;
			estadoDeSimone = conjuntoEstadoDeSimone.get(c);
			
			Estado estadoAf;
			estadoAf = estadoDeSimone.gerarEstadoAf();
			
			novoAutomato.addEstado(estadoAf);
			
			if (estadoAf.isInicial()) {
				novoAutomato.setEstadoInicial(estadoAf);
			}
		}
		
		novoAutomato.alterarSimboloDosEstados();
		return novoAutomato;
	}
	
	public boolean nodoFimDaArvore() {
		return this.nodoFimDaArvore;
	}
	
	String getSimbolo() {
		return this.simbolo;
	}
	
	private void descer(ConjuntoObject<NoDeSimone> conjuntoNoVisitadoDescer, ConjuntoObject<NoDeSimone> conjuntoNoVisitadoSubir, ConjuntoAlfabeto conjuntoAlfabeto, EstadoDeSimone composicao) {
		if (this.nodoFimDaArvore) {
			composicao.addNoDeSimone(this);
			return;
		}
		if (conjuntoNoVisitadoDescer.contains(this)) {
			return;
		}
		
		conjuntoNoVisitadoDescer.add(this);
		
		switch (this.simbolo) {
			case "|":
				this.noFilhoEsquerdo.descer(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, composicao);
				this.noFilhoDireito.descer(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, composicao);
				break;
			case ".":
				this.noFilhoEsquerdo.descer(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, composicao);
				break;
			case "?":
				this.noFilhoEsquerdo.descer(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, composicao);
				this.noFilhoDireito.subir(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, composicao);
				break;
			case "*":
				this.noFilhoEsquerdo.descer(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, composicao);
				this.noFilhoDireito.subir(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, composicao);
				break;
			default:
				conjuntoAlfabeto.add(this.simbolo.charAt(0));
				composicao.addNoDeSimone(this);
				break;
		}
	}
	private void subir(ConjuntoObject<NoDeSimone> conjuntoNoVisitadoDescer, ConjuntoObject<NoDeSimone> conjuntoNoVisitadoSubir, ConjuntoAlfabeto conjuntoAlfabeto, EstadoDeSimone composicao) {
		if (this.nodoFimDaArvore) {
			composicao.addNoDeSimone(this);
			return;
		}
		if (conjuntoNoVisitadoSubir.contains(this)) {
			return;
		}
		
		conjuntoNoVisitadoSubir.add(this);
		
		switch (this.simbolo) {
			case "|":
				this.noFilhoDireito.descerAteOFim(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, composicao);
				break;
			case ".":
				this.noFilhoDireito.descer(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, composicao);
				break;
			case "?":
				this.noFilhoDireito.subir(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, composicao);
				break;
			case "*":
				this.noFilhoEsquerdo.descer(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, composicao);
				this.noFilhoDireito.subir(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, composicao);
				break;
			default:
				this.noFilhoDireito.subir(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, composicao);
				break;
		}
	}
	private void descerAteOFim(ConjuntoObject<NoDeSimone> conjuntoNoVisitadoDescer, ConjuntoObject<NoDeSimone> conjuntoNoVisitadoSubir, ConjuntoAlfabeto conjuntoAlfabeto, EstadoDeSimone composicao) {
		if (this.nodoFimDaArvore) {
			composicao.addNoDeSimone(this);
			return;
		}
		
		if (this.noFilhoEsquerdo == null || this.simbolo.equals("?") || this.simbolo.equals("*")) {
			this.noFilhoDireito.subir(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, composicao);
			return;
		}
		
		this.noFilhoDireito.descerAteOFim(conjuntoNoVisitadoDescer, conjuntoNoVisitadoSubir, conjuntoAlfabeto, composicao);
	}
	
	public String arvoreToString() {
		ArrayList<NoDeSimone> arrayVisitados;
		arrayVisitados = new ArrayList<NoDeSimone>();
		
		return this.arvoreToString(arrayVisitados, -1);
	}
	private String arvoreToString(ArrayList<NoDeSimone> arrayVisitados, int nivel) {
		if (arrayVisitados.contains(this)) {
			return "";
		}
		
		arrayVisitados.add(this);
		
		nivel++;
		
		String arvore;
		arvore = this.simbolo;
		
		if (this.noFilhoEsquerdo != null) {
			arvore += this.noFilhoEsquerdo.arvoreToString(arrayVisitados, nivel);
		}
		if (this.noFilhoDireito != null) {
			arvore += this.noFilhoDireito.arvoreToString(arrayVisitados, nivel);
		}
		
		return arvore;
	}
	public void gerarArvoreSintatica() {
		ArrayList<NoDeSimone> arrayPrecedentes, arrayVisitados;
		arrayPrecedentes = new ArrayList<NoDeSimone>();
		arrayVisitados = new ArrayList<NoDeSimone>();
		
		this.gerarArvoreSintaticaSemCostura();
		this.criarCostura(arrayPrecedentes, arrayVisitados);
		
		System.out.println("Preced: "+arrayPrecedentes.size());
	}
	private void criarCostura(ArrayList<NoDeSimone> arrayPrecedentes, ArrayList<NoDeSimone> arrayVisitados) {
		if (arrayVisitados.contains(this) || this.nodoFimDaArvore) {
			return;
		}
		arrayVisitados.add(this);
		
		char simbolo;
		simbolo = this.simbolo.charAt(0);
		
		if (simbolo != '|' && simbolo != '.') {
			// Caso que leva ao nodo final
			if (arrayPrecedentes.size() == 0) {
				arrayPrecedentes.add(new NoDeSimone("#", true));
			}
			
			System.out.println(simbolo+" "+arrayPrecedentes.get(arrayPrecedentes.size()-1).getSimbolo());
			this.noFilhoDireito = arrayPrecedentes.remove(arrayPrecedentes.size()-1);
		}
		if (simbolo == '*' || simbolo == '.' || simbolo == '|' || simbolo == '?') {
			arrayPrecedentes.add(this);
		}
		
		if (this.noFilhoEsquerdo != null) {
			this.noFilhoEsquerdo.criarCostura(arrayPrecedentes, arrayVisitados);
		}
		if (this.noFilhoDireito != null) {
			this.noFilhoDireito.criarCostura(arrayPrecedentes, arrayVisitados);
		}
	}
	private void gerarArvoreSintaticaSemCostura() {
		Expressao expressao;
		expressao = new Expressao(this.simbolo);
		
		this.simbolo = expressao.getToStringExplicita();
		
		// No Folha
		if (this.simbolo.length() == 1) {
			// Define o filhoDireito como sendo um no de subida, que corresponde a um noPrecedente na arvore
			this.noFilhoDireito = null;
			return;
		}
		
		String expressaoExplicita;
		expressaoExplicita = expressao.explicitarMenorPrecedencia();
		
		/*	Busca o simbolo de menorPrecedencia na expressao.
		 * 	O simbolo de menorPrecedencia eh o simbolo do no,
		 *	a parte direita corresponde ao filhoDireito e
		 *	a parte esquerda corresponde ao filhoEsquerdo
		 *
		 * 	Ordem da menor para maior precedencia: '|' < '.' < '?' = '*'
		 */
		char simbolosDePrecedencia[] = {'|', '.', '?', '*'};
		for (int c = 0; c < simbolosDePrecedencia.length; c++) {
			char simboloDePrecedencia;
			simboloDePrecedencia = simbolosDePrecedencia[c];
			
			int indicePrimeiraOcorrencia;
			indicePrimeiraOcorrencia = expressaoExplicita.indexOf(simboloDePrecedencia);
			
			if (indicePrimeiraOcorrencia > 0) {
				String simboloFilhoEsquerdo, simboloFilhoDireito;
				simboloFilhoEsquerdo = expressao.getToStringExplicita().substring(0, indicePrimeiraOcorrencia);
				simboloFilhoDireito = expressao.getToStringExplicita().substring(indicePrimeiraOcorrencia+1);
				
				this.simbolo = expressao.getToStringExplicita().charAt(indicePrimeiraOcorrencia) + "";
				
				this.noFilhoEsquerdo = new NoDeSimone(simboloFilhoEsquerdo);
				
				if (simboloDePrecedencia != '?' && simboloDePrecedencia != '*') {
					this.noFilhoDireito = new NoDeSimone(simboloFilhoDireito);
				}
				
				break;
			}
		}
		
		this.noFilhoEsquerdo.gerarArvoreSintaticaSemCostura();
		if (this.noFilhoDireito != null) {
			this.noFilhoDireito.gerarArvoreSintaticaSemCostura();
		}
	}
}
