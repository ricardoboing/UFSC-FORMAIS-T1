package expressao;

public class Expressao {
	private String nome;
	private String expressaoOriginal;
	private String expressaoExplicita;
	
	public Expressao(String expressao) {
		this("sem_nome", expressao);
	}
	public Expressao(String nome, String expressao) {
		this.nome = nome;
		this.expressaoOriginal = expressao;
		this.expressaoExplicita = expressao.replaceAll(" ", "");
		
		this.explicitarExpressao();
		this.explicitarConcatenacao();
	}
	
	public String getToStringOriginal() {
		return this.expressaoOriginal;
	}
	public String getToStringExplicita() {
		return this.expressaoExplicita;
	}
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
			this.expressaoExplicita = this.expressaoExplicita.substring(1, this.expressaoExplicita.length()-1);
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
		
		for (int c = 0; c < this.expressaoExplicita.length()-1; c++) {
			expressaoExplicita += this.expressaoExplicita.charAt(c);
			
			char caracterAtual, caracterPosterior;
			caracterAtual = this.expressaoExplicita.charAt(c);
			caracterPosterior = this.expressaoExplicita.charAt(c+1);
			
			if (!this.containsCaracter(caracteresPosterior, caracterPosterior) &&
				!this.containsCaracter(caracteresAtual, caracterAtual)) {
				expressaoExplicita += ".";
			}
		}
		
		expressaoExplicita += this.expressaoExplicita.charAt(this.expressaoExplicita.length()-1);
		
		this.expressaoExplicita = expressaoExplicita;
	}
	/*	Busca um "(". Caso encontrar, substitui os caracteres
	 * 	posteriores por espacos em branco, ate que se encontre
	 *  o ")" correspondente.
	 *  Ex: "((a | b) c) | (d)" resulta em "(         ) | ( )"
	 */
	public String explicitarMenorPrecedencia() {
		char[] expressaoExplicita;
		expressaoExplicita = this.expressaoExplicita.toCharArray();
		
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
}
