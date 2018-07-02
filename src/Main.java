import gramatica.Gramatica;

public class Main {
	public static void main(String[] args) {
		Gramatica gramatica;
		gramatica = new Gramatica("S -> b S | aS | a\nA -> b  a\n");
	}
}
