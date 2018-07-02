package gramatica;

public class Producao {
	private Terminal terminal;
	private NaoTerminal naoTerminal;
	
	public Producao() {
		this.terminal = null;
		this.naoTerminal = null;
	}
	
	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
	public void setNaoTerminal(NaoTerminal naoTerminal) {
		this.naoTerminal = naoTerminal;
	}
	
	public Terminal getTerminal() {
		return terminal;
	}
	public NaoTerminal getNaoTerminal() {
		return naoTerminal;
	}
}
