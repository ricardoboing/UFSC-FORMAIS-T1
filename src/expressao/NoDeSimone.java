package expressao;

import java.util.ArrayList;

import automato.Automato;

public class NoDeSimone {
	private String simbolo;
	
	private NoDeSimone noPai;
	private NoDeSimone noFilhoDireito;
	private NoDeSimone noFilhoEsquerdo;
	
	public NoDeSimone(String simbolo) {
		this.simbolo = simbolo;
		
		this.noPai = null;
		this.noFilhoDireito = null;
		this.noFilhoEsquerdo = null;
	}
	
	public Automato gerarAutomato() {
		
		return null;
	}
	
	public void subir() {
		
	}
	public void descer() {
		
	}
	
	public String arvoreToString(int nivel) {
		ArrayList<NoDeSimone> arrayNoVisitado;
		arrayNoVisitado = new ArrayList<NoDeSimone>();
		
		return this.arvoreToString(arrayNoVisitado, nivel);
	}
	private String arvoreToString(ArrayList<NoDeSimone> arrayNoVisitado, int nivel) {
		if (arrayNoVisitado.contains(this)) {
			return "";
		}
		
		arrayNoVisitado.add(this);
		
		nivel++;
		
		String arvore;
		arvore = this.simbolo;
		
		if (this.noFilhoEsquerdo != null) {
			arvore += this.noFilhoEsquerdo.arvoreToString(arrayNoVisitado, nivel);
			arvore += this.noFilhoDireito.arvoreToString(arrayNoVisitado, nivel);
		}
		
		return arvore;
	}
	public void gerarArvoreSintatica() {
		ArrayList<NoDeSimone> arrayPrecedentes;
		arrayPrecedentes = new ArrayList<NoDeSimone>();
		
		this.gerarArvoreSintatica(arrayPrecedentes, null);
	}
	private void gerarArvoreSintatica(ArrayList<NoDeSimone> arrayPrecedentes, NoDeSimone noPai) {
		this.noPai = noPai;
		
		arrayPrecedentes.add(this);
		
		Expressao expressao;
		expressao = new Expressao(this.simbolo);
		
		this.simbolo = expressao.getToStringExplicita();
		
		// No Folha
		if (this.simbolo.length() == 1) {
			// Define o filhoDireito como sendo um no de subida, que corresponde a um noPrecedente na arvore
			this.noFilhoDireito = arrayPrecedentes.remove(arrayPrecedentes.size()-1);
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
				
				if (simboloDePrecedencia == '?' || simboloDePrecedencia == '*') {
					this.noFilhoDireito = arrayPrecedentes.remove(arrayPrecedentes.size()-1);
				} else {
					this.noFilhoDireito = new NoDeSimone(simboloFilhoDireito);
				}
				
				break;
			}
		}
		
		this.noFilhoEsquerdo.gerarArvoreSintatica(arrayPrecedentes, this);
		this.noFilhoDireito.gerarArvoreSintatica(arrayPrecedentes, this);
	}
}
