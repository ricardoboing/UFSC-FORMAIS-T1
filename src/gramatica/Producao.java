/*
 *  Trabalho I: Algoritmos para Manipulacao de Linguagens Regulares
 *  
 *  Departamento de Informatica e Estatistica – Universidade Federal de Santa Catarina (UFSC)
 *  Campus Reitor Joao David Ferreira Lima, 88.040-900 – Florianopolis – SC – Brasil
 *  
 *  brunohonnef@gmail.com pedroabcorte@gmail.com ricardoboing.ufsc@gmail.com
 *  
 *  Bruno Gilmar Honnef
 *  Pedro Alexandre Barradas da Corte
 *  Ricardo do Nascimento Boing
 *  
 *  11 de Outubro de 2018
 */
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
		return new Producao(this.terminal, this.naoTerminal);
	}
}
