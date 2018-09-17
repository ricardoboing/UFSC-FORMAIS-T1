package teste.DeSimone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import expressao.NoDeSimone;

public class TesteCriarArvore {
	private NoDeSimone simone;
	private String expressao;
	private String arvore;
	
	@Before
	public void setup() {}
	
	private String obterArvore(String expressao) {
		simone = new NoDeSimone(expressao);
		simone.gerarArvoreSintatica(null);
		
		return simone.arvoreToString(-1);
	}
	
	@Test
	public void criarArvore1() {
		expressao = "(a | b)";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "|ab");
	}
	@Test
	public void criarArvore2() {
		expressao = "(a | b) | c";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "||abc");
	}
	@Test
	public void criarArvore3() {
		expressao = "(a.b) | c";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "|.abc");
	}
	@Test
	public void criarArvore4() {
		expressao = "((((a).(b)) | (c)))";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "|.abc");
	}
	@Test
	public void criarArvore5() {
		expressao = "((((a)(b)) | (c)))";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "|.abc");
	}
	@Test
	public void criarArvore6() {
		expressao = "((((a)(b)(c | d) | e) | (f)))";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "||.a.b|cdef");
	}
}
