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
		simone.gerarArvoreSintatica();
		
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
	@Test
	public void criarArvore7() {
		expressao = "((((a)(b)(c | d) | e) | (f)))*";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "*||.a.b|cdef");
	}
	@Test
	public void criarArvore8() {
		expressao = "((((a?)(b)(c | d) | e) | (f)))*";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "*||.?a.b|cdef");
	}
	@Test
	public void criarArvore9() {
		expressao = "((((a)?(b)(c | d) | e) | (f)))*";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "*||.?a.b|cdef");
	}
	@Test
	public void criarArvore10() {
		expressao = "((((a)?(b)(c | d) | e) | (f*)))*";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "*||.?a.b|cde*f");
	}
	@Test
	public void criarArvore11() {
		expressao = "((((a)?(((((b)))))(c | d) | e) | (f*)))*";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "*||.?a.b|cde*f");
	}
	@Test
	public void criarArvore12() {
		expressao = "(((((a)?(((((b)))))(c | d) | e)) | (f*)))*";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "*||.?a.b|cde*f");
	}
	@Test
	public void criarArvore13() {
		expressao = "(((((a)?(((((b | k)))))((c?v) | d*) | e)) | (f*)))*";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "*||.?a.|bk|.?cv*de*f");
	}
	@Test
	public void criarArvore14() {
		expressao = "(((((a)?(((((b? | k)))))((c?v) | d*) | e)) | (f*)))*";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "*||.?a.|?bk|.?cv*de*f");
	}
	@Test
	public void criarArvore15() {
		expressao = "(((((a)?(((((b? | k)))))((c?v*) | d*) | e)) | (f*)))*";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "*||.?a.|?bk|.?c*v*de*f");
	}
	@Test
	public void criarArvore16() {
		expressao = "abc";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, ".a.bc");
	}
	@Test
	public void criarArvore17() {
		expressao = "(((((a)?(z)(((((b? | k)))))((c?v*) | d*) | e)) | (f*)))*";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "*||.?a.z.|?bk|.?c*v*de*f");
	}
	@Test
	public void criarArvore18() {
		expressao = "a | b | c";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "|a|bc");
	}
	@Test
	public void criarArvore19() {
		expressao = "a . b | c";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "|.abc");
	}
	@Test
	public void criarArvore20() {
		expressao = "(((((aw)?(z)(((((b? | k)))))((c?v*) | d*) | e)) | (f*)))*";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "*||.?.aw.z.|?bk|.?c*v*de*f");
	}
	@Test
	public void criarArvore21() {
		expressao = "ab|cd?e";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "|.ab.c.?de");
	}
	@Test
	public void criarArvore22() {
		expressao = "((a)(b))|(c)(d)?(e)";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "|.ab.c.?de");
	}
	@Test
	public void criarArvore23() {
		expressao = "(((a)(b))|((c)(d)?(e)))";
		arvore = this.obterArvore(expressao);
		
		assertEquals(arvore, "|.ab.c.?de");
	}
}
