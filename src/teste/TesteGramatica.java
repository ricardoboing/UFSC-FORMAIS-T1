package teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import automato.Automato;
import automato.OperarAutomato;
import gramatica.Gramatica;

public class TesteGramatica {
	@Before
	public void setup() {}
	
	//@Test
	public void criarGramatica1() {
		String stringConjuntoProducao;
		stringConjuntoProducao = "S->a|bS";
		stringConjuntoProducao = stringConjuntoProducao.replaceAll(" ", "");
		
		Gramatica gramatica;
		gramatica = new Gramatica(stringConjuntoProducao);
		System.out.println(gramatica.getStringConjuntoProducao());
		assertEquals(stringConjuntoProducao, gramatica.getStringConjuntoProducao());
	}
	//@Test
	public void criarGramatica2() {
		String stringConjuntoProducao;
		stringConjuntoProducao = "S->a|bS";
		stringConjuntoProducao = stringConjuntoProducao.replaceAll(" ", "");
		
		Gramatica gramatica;
		gramatica = new Gramatica("G1", stringConjuntoProducao);
		System.out.println(gramatica.getStringConjuntoProducao());
		assertEquals(gramatica.getNome(), "G1");
	}
	@Test
	public void criarGramaticaAtravesDeAutomatoGeradoPorGramatica() {
		String stringConjuntoProducao;
		stringConjuntoProducao = "S->a|b|aA|bD\n A->a|aA|bB\n B->a|aA|bC\n C->a|aA|bB\n D->b|aE|bD\n E->b|aF|bD\n F->b|aE|bD";
		stringConjuntoProducao += "";
		
		Gramatica gramatica;
		gramatica = new Gramatica("G1", stringConjuntoProducao);
		
		Automato automato;
		automato = new Automato("A1", gramatica);
		
		//System.out.println(gramatica.getStringConjuntoProducao());
		System.out.println("-------------");
		automato.print();
		System.out.println("-------------");
		gramatica = new Gramatica(automato);
		//System.out.println(gramatica.getStringConjuntoProducao());
		
		automato = OperarAutomato.determinizar(automato);
		automato.print();
		System.out.println("-------------");
		automato = OperarAutomato.minimizar(automato);
		automato.print();
	}
	//@Test
	public void criarGramatica3() {
		String stringConjuntoProducao;
		stringConjuntoProducao = "S->a|bS";
		stringConjuntoProducao = stringConjuntoProducao.replaceAll(" ", "");
		
		Gramatica gramatica;
		gramatica = new Gramatica("G1", stringConjuntoProducao);
		System.out.println(gramatica.getStringConjuntoProducao());
		assertNotEquals(gramatica.getConjuntoAlfabeto(), null);
	}
}
