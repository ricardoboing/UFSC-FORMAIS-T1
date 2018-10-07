package conjunto;

import java.util.ArrayList;
import java.util.Collections;

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
			transicoes = estado.getConjuntoTransicao();
			
			adicionado.addConjuntoTransicao(transicoes);
			if (estado.isInicial()) {
				adicionado.setInicial(true);
			}
			if (estado.isFinal()) {
				adicionado.setFinal(true);
			}
		}
		
		return adicionado;
	}
	
	@Override
	public ConjuntoEstado clone() {
		ConjuntoEstado clone;
		clone = new ConjuntoEstado();
		clone.array = super.clone().array;
		
		return clone;
	}
	
	public String getToString() {
		String retorno;
		retorno = "";
		
		ArrayList<String> array;
		array = new ArrayList<String>();
		
		for (int c = 0; c < this.array.size(); c++) {
			Estado estado;
			estado = this.array.get(c);
			
			array.add(estado.getSimbolo());
		}
		
		Collections.sort(array);
		
		for (int c = 0; c < array.size(); c++) {
			if (c > 0) {
				retorno += ",";
			}
			retorno += array.get(c);
		}
		
		return retorno;
	}
}
