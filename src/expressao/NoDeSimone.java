package expressao;

import java.util.ArrayList;

import automato.Automato;
import automato.Estado;
import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoObject;

public class NoDeSimone {
	private String simbolo;
	
	private NoDeSimone noPai;
	private NoDeSimone noFilhoDireito;
	private NoDeSimone noFilhoEsquerdo;
	private boolean nodoFimDaArvore;
	
	public NoDeSimone(String simbolo) {
		this(simbolo, false);
	}
	private NoDeSimone(String simbolo, boolean nodoFimDaArvore) {
		this.simbolo = simbolo;
		this.noPai = null;
		this.noFilhoDireito = null;
		this.noFilhoEsquerdo = null;
		this.nodoFimDaArvore = nodoFimDaArvore;
	}
	
	public Automato gerarAutomato() {
		// Alterar nome depois
		Automato novoAutomato;
		novoAutomato = new Automato();
		
		ConjuntoAlfabeto conjuntoAlfabeto;
		conjuntoAlfabeto = novoAutomato.getConjuntoAlfabeto();
		
		ComposicaoDeSimone composicaoInicial;
		composicaoInicial = new ComposicaoDeSimone(0, true);
		
		ConjuntoObject<ComposicaoDeSimone> conjuntoComposicao;
		conjuntoComposicao = new ConjuntoObject<ComposicaoDeSimone>();
		conjuntoComposicao.add(composicaoInicial);
		
		this.descer(conjuntoAlfabeto, composicaoInicial);
		composicaoInicial.atualizarConjuntoComposicao(conjuntoComposicao);
		
		// Cria as composicoes
		for (int c = 0; c < conjuntoComposicao.size(); c++) {
			ComposicaoDeSimone composicaoDoConjunto;
			composicaoDoConjunto = conjuntoComposicao.get(c);
			
			ConjuntoObject<NoDeSimone> conjuntoNoDeSimone;
			conjuntoNoDeSimone = composicaoDoConjunto.getConjuntoNoDeSimone();
			
			for (int i = 0; i < conjuntoNoDeSimone.size(); i++) {
				NoDeSimone noDeSimone;
				noDeSimone = conjuntoNoDeSimone.get(i);
				noDeSimone.subir(conjuntoAlfabeto, composicaoDoConjunto);
			}
			
			composicaoDoConjunto.atualizarConjuntoComposicao(conjuntoComposicao);
		}
		
		// Cria o automato
		for (int c = 0; c < conjuntoComposicao.size(); c++) {
			ComposicaoDeSimone composicaoDoConjunto;
			composicaoDoConjunto = conjuntoComposicao.get(c);
			
			Estado estadoComposicao;
			estadoComposicao = composicaoDoConjunto.gerarEstado();
			
			novoAutomato.addEstado(estadoComposicao);
		}
		
		return novoAutomato;
	}
	
	public boolean nodoFimDaArvore() {
		return this.nodoFimDaArvore;
	}
	
	String getSimbolo() {
		return this.simbolo;
	}
	
	private void descer(ConjuntoAlfabeto conjuntoAlfabeto, ComposicaoDeSimone composicao) {
		if (this.nodoFimDaArvore) {
			return;
		}
		
		//System.out.println("descer "+this.simbolo);
		
		switch (this.simbolo) {
			case "|":
				this.noFilhoEsquerdo.descer(conjuntoAlfabeto, composicao);
				this.noFilhoDireito.descer(conjuntoAlfabeto, composicao);
				break;
			case ".":
				this.noFilhoEsquerdo.descer(conjuntoAlfabeto, composicao);
				break;
			case "?":
				this.noFilhoEsquerdo.descer(conjuntoAlfabeto, composicao);
				this.noFilhoDireito.subir(conjuntoAlfabeto, composicao);
				break;
			case "*":
				this.noFilhoEsquerdo.descer(conjuntoAlfabeto, composicao);
				this.noFilhoDireito.subir(conjuntoAlfabeto, composicao);
				break;
			default:
				conjuntoAlfabeto.add(this.simbolo.charAt(0));
				composicao.addNoDeSimone(this);
				break;
		}
	}
	private void subir(ConjuntoAlfabeto conjuntoAlfabeto, ComposicaoDeSimone composicao) {
		if (this.nodoFimDaArvore) {
			return;
		}
		
		//System.out.println("subir "+this.simbolo);
		
		switch (this.simbolo) {
			case "|":
				this.noFilhoDireito.descerAteOFim(conjuntoAlfabeto, composicao);
				break;
			case ".":
				this.noFilhoDireito.descer(conjuntoAlfabeto, composicao);
				break;
			case "?":
				this.noFilhoDireito.subir(conjuntoAlfabeto, composicao);
				break;
			case "*":
				this.noFilhoEsquerdo.descer(conjuntoAlfabeto, composicao);
				this.noFilhoDireito.subir(conjuntoAlfabeto, composicao);
				break;
			default:
				this.noFilhoDireito.subir(conjuntoAlfabeto, composicao);
				break;
		}
	}
	private void descerAteOFim(ConjuntoAlfabeto conjuntoAlfabeto, ComposicaoDeSimone composicao) {
		if (this.noFilhoDireito != null && !this.nodoFimDaArvore) {
			this.noFilhoDireito.descerAteOFim(conjuntoAlfabeto, composicao);
		} if (this.nodoFimDaArvore) {
			composicao.addNoDeSimone(this);
		}
	}
	
	public String arvoreToString() {
		ArrayList<NoDeSimone> arrayVisitados;
		arrayVisitados = new ArrayList<NoDeSimone>();
		
		return this.arvoreToString(arrayVisitados, -1);
	}
	private String arvoreToString(ArrayList<NoDeSimone> arrayVisitados, int nivel) {
		if (this.nodoFimDaArvore || arrayVisitados.contains(this)) {
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
		
		this.gerarArvoreSintatica(null);
		this.criarCostura(arrayPrecedentes, arrayVisitados);
		
		System.out.println("Preced: "+arrayPrecedentes.size());
	}
	private void criarCostura(ArrayList<NoDeSimone> arrayPrecedentes, ArrayList<NoDeSimone> arrayVisitados) {
		if (arrayVisitados.contains(this) || this.nodoFimDaArvore) {
			return;
		}
		System.out.println(simbolo);
		arrayVisitados.add(this);
		
		char simbolo;
		simbolo = this.simbolo.charAt(0);
		
		if (simbolo != '|' && simbolo != '.') {
			// Caso * que leva ao nodo final
			if (arrayPrecedentes.size() == 0) {
				this.noFilhoDireito = new NoDeSimone("#", true);
			} else {
				this.noFilhoDireito = arrayPrecedentes.remove(arrayPrecedentes.size()-1);
			}
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
	private void gerarArvoreSintatica(NoDeSimone noPai) {
		this.noPai = noPai;
		
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
		
		this.noFilhoEsquerdo.gerarArvoreSintatica(this);
		if (this.noFilhoDireito != null) {
			this.noFilhoDireito.gerarArvoreSintatica(this);
		}
	}
}
