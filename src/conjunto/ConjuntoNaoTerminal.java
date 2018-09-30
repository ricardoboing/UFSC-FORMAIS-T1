package conjunto;

import gramatica.NaoTerminal;
import gramatica.Producao;

public class ConjuntoNaoTerminal extends ConjuntoObject<NaoTerminal> {
	public ConjuntoNaoTerminal() {
		super();
	}
	
	@Override
	public NaoTerminal add(NaoTerminal naoTerminal) {
		NaoTerminal adicionado;
		adicionado = super.add(naoTerminal);
		
		/* Caso seja um naoTerminal equivalente, ja existente
		 * no conjunto, mas nao igual
		 */
		if (adicionado != naoTerminal) {
			ConjuntoObject<Producao> producoes;
			producoes = naoTerminal.getConjuntoProducao();
			
			adicionado.addConjuntoProducao(producoes);
		}
		
		return adicionado;
	}
	
	@Override
	public ConjuntoNaoTerminal clone() {
		ConjuntoNaoTerminal clone;
		clone = new ConjuntoNaoTerminal();
		
		for (int c = 0; c < this.array.size(); c++) {
			clone.array.add(this.array.get(c));
		}
		
		return clone;
	}
}
