package automato;

import gramatica.NaoTerminal;

public class Transicao {
	private Estado partida, destino;
	private Character entrada;
	
	public Transicao() {
		this.partida = null;
		this.destino = null;
		this.entrada = null;
	}
	// Metodos Setters
	public void setEntrada(char terminal) {
		this.entrada = terminal;
	}
	public void setPartida(Estado partida) {
		this.partida = partida;
	}
	public void setDestino(Estado destino) {
		this.destino = destino;
	}
	// Metodos Getters
	public char getTerminal() {
		return entrada;
	}
	public Estado getPartida() {
		return partida;
	}
	public Estado getDestino() {
		return destino;
	}
	
	public boolean reconhece(char entrada) {
		return this.entrada.equals(entrada);
	}
	public boolean destino(Estado destino) {
		return this.destino.equals(destino);
	}
	public boolean equals(NaoTerminal naoTerminal) {
		
		return false;
	}
}
