package gramatica;

public class Terminal {
	private String simbolo;
	
	public Terminal(String simbolo) {
		this.simbolo = simbolo;
	}
	public Terminal(char simbolo) {
		this.simbolo = simbolo+"";
	}
	
	// Metodos Setters
	public void setSimbolo(char simbolo) {
		this.simbolo = simbolo+"";
	}
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	
	// Metodos Getters
	public char getCharSimbolo() {
		return simbolo.charAt(0);
	}
	public String getStringSimbolo() {
		return this.simbolo;
	}
	
	public boolean equals(Terminal terminal) {
		return this.simbolo.equals(terminal.simbolo);
	}
	
	@Override
	public Terminal clone() {
		return new Terminal(this.simbolo);
	}
}
