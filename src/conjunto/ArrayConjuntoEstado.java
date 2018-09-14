package conjunto;

import java.util.ArrayList;

import automato.Estado;
import automato.Transicao;

public class ArrayConjuntoEstado {
	private ArrayList<ConjuntoEstado> arrayConjuntoEstado;
	
	public ArrayConjuntoEstado() {
		this.arrayConjuntoEstado = new ArrayList<ConjuntoEstado>();
	}
	
	public boolean add(ConjuntoEstado conjuntoEstado) {
		return this.arrayConjuntoEstado.add(conjuntoEstado);
	}
	public ConjuntoEstado remove(int i) {
		return this.arrayConjuntoEstado.remove(i);
	}
	public boolean remove(ConjuntoEstado conjuntoEstado) {
		return this.arrayConjuntoEstado.remove(conjuntoEstado);
	}
	
	public boolean pertenceMesmoConjuntoEstado(Estado estado1, Estado estado2) {
		ConjuntoEstado conjuntoEstado1;
		conjuntoEstado1 = this.getConjuntoEstado(estado1);
		
		if (conjuntoEstado1 == null) {
			return false;
		}
		
		if (conjuntoEstado1.contains(estado2)) {
			return true;
		}
		
		return false;
	}
	public ConjuntoEstado getConjuntoEstado(Estado estado) {
		for (int c = 0; c < this.arrayConjuntoEstado.size(); c++) {
			ConjuntoEstado conjuntoEstado;
			conjuntoEstado = this.arrayConjuntoEstado.get(c);
			
			if (conjuntoEstado.contains(estado)) {
				return conjuntoEstado;
			}
		}
		
		return null;
	}
	public boolean conjuntoTransicaoEquivalente(Estado estado1, Estado estado2) {
		ConjuntoObject<Transicao> conjuntoTransicaoEstado1;
		conjuntoTransicaoEstado1 = estado1.getConjuntoTransicao();
		
		ConjuntoObject<Transicao> conjuntoTransicaoEstado2;
		conjuntoTransicaoEstado2 = estado1.getConjuntoTransicao();
		
		// Verifica se um estado possui mais transicoes do que o outro
		if (conjuntoTransicaoEstado1.size() != conjuntoTransicaoEstado2.size()) {
			return false;
		}
		
		// Verifica se todas transicoes levam para um estadoDestino pertencente ao mesmo ConjuntoEstado
		for (int c = 0; c < conjuntoTransicaoEstado1.size(); c++) {
			Transicao transicao;
			transicao = conjuntoTransicaoEstado1.get(c);
			
			ConjuntoObject<Transicao> conjuntoTransicaoEstado2ParaUmaDadaEntrada;
			conjuntoTransicaoEstado2ParaUmaDadaEntrada = estado2.getConjuntoTransicao(transicao.getSimboloEntrada());
			
			ConjuntoEstado conjuntoEstadoDoEstadoDestinoDoEstado1;
			conjuntoEstadoDoEstadoDestinoDoEstado1 = this.getConjuntoEstado(transicao.getEstadoDestino());
			
			// Considera-se que o automato seja deterministico (transita apenas para um estado por entrada)
			Estado estadoDestinoDoEstado2ParaUmaDadaEntrada;
			estadoDestinoDoEstado2ParaUmaDadaEntrada = conjuntoTransicaoEstado2ParaUmaDadaEntrada.get(0).getEstadoDestino();
			
			/* Se alguma transicao do estado1 levar para um estado diferente
			 * da transicao do estado 2, entao os estados nao sao equivalentes
			 */
			if (!conjuntoEstadoDoEstadoDestinoDoEstado1.contains(estadoDestinoDoEstado2ParaUmaDadaEntrada)) {
				return false;
			}
		}
		
		return true;
	}
	public int size() {
		return this.arrayConjuntoEstado.size();
	}
	public ConjuntoEstado get(int i) {
		return this.arrayConjuntoEstado.get(i);
	}
	
	public static boolean conjuntoEstadoPossuiEstadoInicial(ConjuntoEstado conjuntoEstado) {
		for (int c = 0; c < conjuntoEstado.size(); c++) {
			Estado estado;
			estado = conjuntoEstado.get(c);
			
			if (estado.isInicial()) {
				return true;
			}
		}
		
		return false;
	}
	public static boolean conjuntoEstadoPossuiEstadoFinal(ConjuntoEstado conjuntoEstado) {
		for (int c = 0; c < conjuntoEstado.size(); c++) {
			Estado estado;
			estado = conjuntoEstado.get(c);
			
			if (estado.isFinal()) {
				return true;
			}
		}
		
		return false;
	}
}