package conjunto;

import view.principal.ManagerLinguagem;

public class ConjuntoAlfabeto extends Conjunto<Character> {
	public ConjuntoAlfabeto() {
		super();
	}
	
	@Override
	public Character add(Character simbolo) {
		if (simbolo == ManagerLinguagem.EPSILON) {
			//return null;
		}
		
		return super.add(simbolo);
	}
	
	@Override
	public ConjuntoAlfabeto clone() {
		ConjuntoAlfabeto clone;
		clone = new ConjuntoAlfabeto();
		
		for (int c = 0; c < this.array.size(); c++) {
			clone.array.add(this.array.get(c));
		}
		
		return clone;
	}
}
