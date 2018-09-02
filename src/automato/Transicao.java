package automato;

public class Transicao {
	private Estado estadoOrigem, estadoDestino;
	private Character simboloEntrada;
	
	public Transicao() {
		this.estadoOrigem = null;
		this.estadoDestino = null;
		this.simboloEntrada = null;
	}
	// Metodos Setters
	public void setSimboloEntrada(char simboloEntrada) {
		this.simboloEntrada = simboloEntrada;
	}
	public void setEstadoOrigem(Estado estadoOrigem) {
		this.estadoOrigem = estadoOrigem;
	}
	public void setEstadoDestino(Estado estadoDestino) {
		this.estadoDestino = estadoDestino;
	}
	
	// Metodos Getters
	public char getSimboloEntrada() {
		return simboloEntrada;
	}
	public Estado getEstadoOrigem() {
		return estadoOrigem;
	}
	public Estado getEstadoDestino() {
		return estadoDestino;
	}
	
	public boolean reconhecerEntrada(char entrada) {
		return this.simboloEntrada.equals(entrada);
	}
	public boolean isEstadoDestino(Estado estadoDestino) {
		return this.estadoDestino.equals(estadoDestino);
	}
	public boolean equals(Transicao transicao) {
		if (!this.estadoOrigem.equals(transicao.estadoOrigem)) {
			return false;
		}
		if (!this.estadoDestino.equals(transicao.estadoDestino)) {
			return false;
		}
		if (!this.simboloEntrada.equals(transicao.simboloEntrada)) {
			return false;
		}
		
		return true;
	}
}
