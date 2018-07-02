package conjunto;

import manager.ManagerLinguagem;

public class ConjuntoAlfabeto extends Conjunto<Character> {
	public ConjuntoAlfabeto() {
		super();
	}
	
	@Override
	public Character add(Character simbolo) {
		if (simbolo == ManagerLinguagem.EPSILON) {
			return null;
		}
		
		return super.add(simbolo);
	}
}
