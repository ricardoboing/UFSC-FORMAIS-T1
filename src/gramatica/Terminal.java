package gramatica;

public class Terminal {
	private String simbolo;
	
	public Terminal(char simbolo) {
		this.simbolo = simbolo+"";
	}
	// Metodos Setters
	public void setSimbolo(char simbolo) {
		this.simbolo = simbolo+"";
	}
	// Metodos Getters
	public char getSimbolo() {
		return simbolo.charAt(0);
	}
	
	public boolean equals(Terminal terminal) {
		
		return false;
	}
}
