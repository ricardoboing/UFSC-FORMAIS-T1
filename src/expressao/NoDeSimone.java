package expressao;

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
	
	public Automato gerarAutomato(Expressao expressao) {
		
		return null;
	}
	
	public void subir() {
		
	}
	public void descer() {
		
	}
	
	public String arvoreToString(int nivel) {
		nivel++;
		
		String arvore;
		arvore = this.simbolo;
		
		if (this.noFilhoDireito != null) {
			arvore += this.noFilhoEsquerdo.arvoreToString(nivel);
			arvore += this.noFilhoDireito.arvoreToString(nivel);
		}
		
		return arvore;
	}
	public void gerarArvoreSintatica(NoDeSimone noPai) {
		this.noPai = noPai;
		
		Expressao expressao;
		expressao = new Expressao(this.simbolo);
		
		this.simbolo = expressao.getToStringExplicita();
		
		// No Folha
		if (this.simbolo.length() == 1) {
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
				
				this.noFilhoEsquerdo = new NoDeSimone(simboloFilhoEsquerdo);
				this.noFilhoDireito = new NoDeSimone(simboloFilhoDireito);
				
				this.simbolo = expressao.getToStringExplicita().charAt(indicePrimeiraOcorrencia) + "";
				break;
			}
		}
		
		this.noFilhoEsquerdo.gerarArvoreSintatica(this);
		this.noFilhoDireito.gerarArvoreSintatica(this);
	}
}
