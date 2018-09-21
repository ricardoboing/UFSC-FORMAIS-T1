package teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import gramatica.Gramatica;

public class TesteGramatica {
	@Before
	public void setup() {}
	
	@Test
	public void criarGramatica1() {
		String stringConjuntoProducao;
		stringConjuntoProducao = "S->a;bS";
		stringConjuntoProducao = stringConjuntoProducao.replaceAll(" ", "");
		
		Gramatica gramatica;
		gramatica = new Gramatica(stringConjuntoProducao);
		System.out.println(gramatica.getStringConjuntoProducao());
		assertEquals(stringConjuntoProducao, gramatica.getStringConjuntoProducao());
	}
	@Test
	public void criarGramatica2() {
		String stringConjuntoProducao;
		stringConjuntoProducao = "S->a;bS";
		stringConjuntoProducao = stringConjuntoProducao.replaceAll(" ", "");
		
		Gramatica gramatica;
		gramatica = new Gramatica("G1", stringConjuntoProducao);
		System.out.println(gramatica.getStringConjuntoProducao());
		assertEquals(gramatica.getNome(), "G1");
	}
	@Test
	public void criarGramatica3() {
		String stringConjuntoProducao;
		stringConjuntoProducao = "S->a;bS";
		stringConjuntoProducao = stringConjuntoProducao.replaceAll(" ", "");
		
		Gramatica gramatica;
		gramatica = new Gramatica("G1", stringConjuntoProducao);
		System.out.println(gramatica.getStringConjuntoProducao());
		assertNotEquals(gramatica.getConjuntoAlfabeto(), null);
	}
}
