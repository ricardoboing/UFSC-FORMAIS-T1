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
			producoes = naoTerminal.getProducoes();
			
			adicionado.addProducoes(producoes);
		}
		
		return adicionado;
	}
}
