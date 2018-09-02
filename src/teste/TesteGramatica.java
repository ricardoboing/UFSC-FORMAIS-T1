package teste;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import gramatica.Gramatica;

public class TesteGramatica {
	@Before
	public void setup() {}
	
	@Test
	public void criarGramatica1() {
		String stringConjuntoProducao;
		stringConjuntoProducao = "S-> a | bA\n A-> b | c";
		stringConjuntoProducao = stringConjuntoProducao.replaceAll(" ", "");
		
		Gramatica gramatica;
		gramatica = new Gramatica(stringConjuntoProducao);
		
		assertEquals(stringConjuntoProducao, gramatica.getStringConjuntoProducao());
	}
}
