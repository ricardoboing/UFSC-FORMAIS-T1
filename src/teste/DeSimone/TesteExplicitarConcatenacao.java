package teste.DeSimone;

import expressao.NoDeSimone;

public class TesteExplicitarConcatenacao {
	private NoDeSimone simone;
	private String expressao;
	/*
	@Before
	public void setup() {
		this.simone = new NoDeSimone(null);
	}
	
	@Test
	public void testeExplicitarConcatenacao1() {
		expressao = "(a | b)";
		expressao = this.simone.explicitarConcatenacao(expressao);
		assertEquals("(a|b)", expressao);
	}
	@Test
	public void testeExplicitarConcatenacao2() {
		expressao = "a | b";
		expressao = this.simone.explicitarConcatenacao(expressao);
		
		assertEquals("a|b", expressao);
	}
	@Test
	public void testeExplicitarConcatenacao3() {
		expressao = "a";
		expressao = this.simone.explicitarConcatenacao(expressao);
		
		assertEquals("a", expressao);
	}
	@Test
	public void testeExplicitarConcatenacao4() {
		expressao = "a*";
		expressao = this.simone.explicitarConcatenacao(expressao);
		
		assertEquals("a*", expressao);
	}
	@Test
	public void testeExplicitarConcatenacao5() {
		expressao = "(a)";
		expressao = this.simone.explicitarConcatenacao(expressao);
		
		assertEquals("(a)", expressao);
	}
	@Test
	public void testeExplicitarConcatenacao6() {
		expressao = "((a))";
		expressao = this.simone.explicitarConcatenacao(expressao);
		
		assertEquals("((a))", expressao);
	}
	@Test
	public void testeExplicitarConcatenacao7() {
		expressao = "ab";
		expressao = this.simone.explicitarConcatenacao(expressao);
		
		assertEquals("a.b", expressao);
	}
	@Test
	public void testeExplicitarConcatenacao8() {
		expressao = "((a(b)c(a))b)";
		expressao = this.simone.explicitarConcatenacao(expressao);
		
		assertEquals("((a.(b).c.(a)).b)", expressao);
	}
	@Test
	public void testeExplicitarConcatenacao9() {
		expressao = "(a)    | (b) |(      c)";
		expressao = this.simone.explicitarConcatenacao(expressao);
		
		assertEquals("(a)|(b)|(c)", expressao);
	}
	@Test
	public void testeExplicitarConcatenacao10() {
		expressao = "ab | a";
		expressao = this.simone.explicitarConcatenacao(expressao);
		
		assertEquals("a.b|a", expressao);
	}
	@Test
	public void testeExplicitarConcatenacao11() {
		expressao = "(a? b) | c";
		expressao = this.simone.explicitarConcatenacao(expressao);
		
		assertEquals("(a?.b)|c", expressao);
	}
	@Test
	public void testeExplicitarConcatenacao12() {
		expressao = "((a? b) | c)";
		expressao = this.simone.explicitarConcatenacao(expressao);
		
		assertEquals("((a?.b)|c)", expressao);
	}
	@Test
	public void testeExplicitarConcatenacao13() {
		expressao = "a.(b | c)";
		expressao = this.simone.explicitarConcatenacao(expressao);
		
		assertEquals("a.(b|c)", expressao);
	}*/
}
