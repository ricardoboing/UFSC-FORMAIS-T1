import automato.Automato;
import gramatica.Gramatica;

public class Main {
	/*	[] Determinizacao AFND -> AFD
	 * 	[] Minimizacao AFD
	 * 	[] Uniao AFD
	 * 	[] Intersecao de AFD
	 * 	[] GR -> AFND
	 * 	[] AFD -> GR
	 * 	[] ER -> AFD
	 */
	
	public static void main(String[] args) {
		String producoes;
		//producoes = "S -> aA | c | d \n A -> aS | b\n";
		producoes = "S -> aA | c\n A -> aB | b\nB-> aC | aA | dB\nC -> aC | nS\n";
		
		Gramatica gramatica;
		gramatica = new Gramatica(producoes);
		
		System.out.println(gramatica.getStringConjuntoProducao());
		
		System.out.println("---------------");
		
		Automato automato;
		automato = new Automato("oi", gramatica);
		automato.print();
		
		System.out.println("---------------");
		
		gramatica = new Gramatica(automato);
		System.out.println(gramatica.getStringConjuntoProducao());
	}
}
