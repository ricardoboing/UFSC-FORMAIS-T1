import gramatica.Gramatica;

public class Main {
	public static void main(String[] args) {
		String producoes;
		producoes = "S->aS\nA->S\n";
		
		Gramatica gramatica;
		gramatica = new Gramatica(producoes);
		
		System.out.println("\n\n");
		System.out.println(gramatica.getStringConjuntoProducao());
	}
}
