package gramatica;

public class Producao {
	private Terminal terminal;
	private NaoTerminal naoTerminal;
	
	public Producao() {
		this(null, null);
	}
	public Producao(Terminal terminal, NaoTerminal naoTerminal) {
		this.terminal = terminal;
		this.naoTerminal = naoTerminal;
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
	
	@Override
	public Producao clone() {
		return new Producao(this.terminal, this.naoTerminal.clone());
	}
}
