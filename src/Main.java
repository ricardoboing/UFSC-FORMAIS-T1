import automato.Automato;

public class Main {
	/*	[] Determinizacao AFND -> AFD
	 * 	[X] Minimizacao AFD
	 * 	[] Uniao AFD
	 * 	[] Intersecao de AFD
	 * 	[X] GR -> AFND
	 * 	[X] AFD -> GR
	 * 	[] ER -> AFD
	 */
	
	public static void main(String[] args) {
		/*
		String producoes;
		//producoes = "S -> aA | c | d \n A -> aS | b\n";
		//producoes = "S -> aA | c\n A -> aB | b\nB-> aC | aA | dB\nC -> aC | nS\n";
		producoes = "S->aA | b\n A-> b| c| &";
		
		Gramatica gramatica;
		gramatica = new Gramatica(producoes);
		
		System.out.println(gramatica.getStringConjuntoProducao());
		*/
		String stringAutomato;
		stringAutomato = "*>S->aA|bB|cC|&A\n_>A->aS|bA|cC\n*_C->aA|bB";
		
		Automato automato;
		automato = new Automato("oi", stringAutomato);
		
		System.out.println(stringAutomato);
		System.out.println();
		System.out.println(automato.getStringConjuntoTransicao());
		
		/*
		System.out.println("---------------");
		
		Automato automato;
		automato = new Automato("oi", gramatica);
		automato.print();
		
		System.out.println("---------------");
		
		gramatica = new Gramatica(automato);
		System.out.println(gramatica.getStringConjuntoProducao());*/
	}
}
