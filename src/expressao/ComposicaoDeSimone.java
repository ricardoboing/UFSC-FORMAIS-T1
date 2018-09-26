package expressao;

import conjunto.ConjuntoObject;

public class ComposicaoDeSimone {
	private ConjuntoObject<NoDeSimone> conjuntoNoDeSimone;
	private EstadoDeSimone estadoDeSimone;
	
	private char simbolo;
	
	public ComposicaoDeSimone(char simbolo) {
		this.simbolo = simbolo;
		this.estadoDeSimone = new EstadoDeSimone();
		this.conjuntoNoDeSimone = new ConjuntoObject<NoDeSimone>();
	}
	public void addNoDeSimone(NoDeSimone no) {
		this.conjuntoNoDeSimone.add(no);
	}
	public char getSimbolo() {
		return this.simbolo;
	}
	public EstadoDeSimone getEstadoDeSimone() {
		return this.estadoDeSimone;
	}
	public ConjuntoObject<NoDeSimone> getConjuntoNoDeSimone() {
		return this.conjuntoNoDeSimone;
	}
	@Override
	public boolean equals(Object obj) {
		ComposicaoDeSimone estadoEquals;
		estadoEquals = (ComposicaoDeSimone) obj;
		
		if (this.conjuntoNoDeSimone.size() != estadoEquals.conjuntoNoDeSimone.size()) {
			return false;
		}
		//System.out.print(this.estado.getSimbolo()+" -> ");
		
		for (int c = 0; c < this.conjuntoNoDeSimone.size(); c++) {
			NoDeSimone noThis, noEquals;
			noThis = this.conjuntoNoDeSimone.get(c);
			noEquals = estadoEquals.conjuntoNoDeSimone.get(c);
			
			if (!noThis.equals(noEquals)) {
				return false;
			}
		}
		//System.out.println("???: "+this.conjuntoNoDeSimone.size());
		return true;
	}
	public void setEstadoDeSimone(EstadoDeSimone estadoEquivalente) {
		this.estadoDeSimone = estadoEquivalente;
	}
}
