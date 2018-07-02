package conjunto;

import automato.Estado;
import automato.Transicao;

public class ConjuntoEstado extends ConjuntoObject<Estado> {
	public ConjuntoEstado() {
		super();
	}
	
	@Override
	public Estado add(Estado estado) {
		Estado adicionado;
		adicionado = super.add(estado);
		
		/* Caso seja um estado equivalente, ja existente
		 * no conjunto, mas nao igual
		 */
		if (adicionado != estado) {
			ConjuntoObject<Transicao> transicoes;
			transicoes = estado.getTransicoes();
			
			adicionado.addTransicoes(transicoes);
		}
		
		return adicionado;
	}
}
