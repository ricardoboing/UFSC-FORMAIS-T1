package expressao;

import util.ELinguagem;
import util.LinguagemGerador;

public class Expressao implements LinguagemGerador {
	private String nome;
	private String expressaoOriginal;
	private String expressaoConcatenacaoExplicita;
	
	public Expressao(String expressao) {
		this("sem_nome", expressao);
	}
	public Expressao(String nome, String expressao) {
		this.nome = nome;
		this.expressaoOriginal = expressao;
		this.expressaoConcatenacaoExplicita = expressao.replaceAll(" ", "");
		
		this.explicitarExpressao();
		this.explicitarConcatenacao();
	}
	
	public String getToStringOriginal() {
		return this.expressaoOriginal;
	}
	public String getToStringExplicita() {
		return this.expressaoConcatenacaoExplicita;
	}
	@Override
	public String getNome() {
		return this.nome;
	}
	/*	Remove parenteses desnecessarios que tornam a expressao a ser implicita.
	 * 	Ex: "((ab)c | (d))" resulta em "(ab)c | (d)"
	 */
	private void explicitarExpressao() {
		String expressaoExplicita;
		expressaoExplicita = this.explicitarMenorPrecedencia().toString();
		expressaoExplicita = expressaoExplicita.replaceAll(" ", "");
		
		if (expressaoExplicita.equals("()")) {
			this.expressaoConcatenacaoExplicita = this.expressaoConcatenacaoExplicita.substring(1, this.expressaoConcatenacaoExplicita.length()-1);
			this.explicitarExpressao();
		}
	}
	/*	Explicita as concatenacoes implicitas da expressao.
	 * 	Ex: "(ab)c | d" resulta em "(a.b).c | d"
	 */
	private void explicitarConcatenacao() {
		String expressaoExplicita;
		expressaoExplicita = "";
		
		char[] caracteresAtual = {'(', '|', '.'};
		char[] caracteresPosterior = {')', '|', '?', '*', '.', '+'};
		
		for (int c = 0; c < this.expressaoConcatenacaoExplicita.length()-1; c++) {
			expressaoExplicita += this.expressaoConcatenacaoExplicita.charAt(c);
			
			char caracterAtual, caracterPosterior;
			caracterAtual = this.expressaoConcatenacaoExplicita.charAt(c);
			caracterPosterior = this.expressaoConcatenacaoExplicita.charAt(c+1);
			
			if (!this.containsCaracter(caracteresPosterior, caracterPosterior) &&
				!this.containsCaracter(caracteresAtual, caracterAtual)) {
				expressaoExplicita += ".";
			}
		}
		
		expressaoExplicita += this.expressaoConcatenacaoExplicita.charAt(this.expressaoConcatenacaoExplicita.length()-1);
		
		this.expressaoConcatenacaoExplicita = expressaoExplicita;
	}
	/*	Busca um "(". Caso encontrar, substitui os caracteres
	 * 	posteriores por espacos em branco, ate que se encontre
	 *  o ")" correspondente.
	 *  Ex: "((a | b) c) | (d)" resulta em "(         ) | ( )"
	 */
	public String explicitarMenorPrecedencia() {
		char[] expressaoExplicita;
		expressaoExplicita = this.expressaoConcatenacaoExplicita.toCharArray();
		
		int contadorParentesesAberto;
		contadorParentesesAberto = 0;
		
		for (int c = 0; c < expressaoExplicita.length; c++) {
			char caracter;
			caracter = expressaoExplicita[c];
			
			if (caracter == ')') {
				contadorParentesesAberto--;
			}
			if (contadorParentesesAberto > 0) {
				expressaoExplicita[c] = ' ';
			}
			if (caracter == '(') {
				contadorParentesesAberto++;
			}
		}
		
		return String.valueOf(expressaoExplicita);
	}
	/*	Verifica se um array de caracteres possui um determinado
	 * 	caracter
	 */
	private boolean containsCaracter(char[] caracteres, char caracter) {
		for (int c = 0; c < caracteres.length; c++) {
			if (caracter == caracteres[c]) {
				return true;
			}
		}
		
		return false;
	}
	@Override
	public ELinguagem getELinguagem() {
		return ELinguagem.EXPRESSAO;
	}
	
	// Implementar...
	public static boolean entradaValida(String expressao) {
		return true;
	}
}
