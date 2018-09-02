package automato;

import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoEstado;
import conjunto.ConjuntoObject;

public class OperarAutomato {
	public OperarAutomato() {}
	
	public Automato determinizar(Automato automatoAFND) {
		Estado estadoInicialAFND;
		estadoInicialAFND = automatoAFND.getEstadoInicial();
		
		Estado estadoInicialAFD;
		estadoInicialAFD = new Estado(estadoInicialAFND.getSimbolo());
		
		ConjuntoAlfabeto conjuntoAlfabeto;
		conjuntoAlfabeto = automatoAFND.getConjuntoAlfabeto();
		
		Automato automatoAFD;
		automatoAFD = new Automato();
		automatoAFD.setEstadoInicial(estadoInicialAFD);
		automatoAFD.setConjuntoAlfabeto(conjuntoAlfabeto);
		
		ConjuntoEstado conjuntoEstadoAFD;
		conjuntoEstadoAFD = automatoAFD.getConjuntoEstado();
		
		for (int c = 0; c < conjuntoEstadoAFD.size(); c++) {
			Estado estadoAFD;
			estadoAFD = conjuntoEstadoAFD.get(c);
			
			String simboloEstadoAFD;
			simboloEstadoAFD = estadoAFD.getSimbolo();
			
			for (int i = 0; i < simboloEstadoAFD.length()-1; i++) {
				String simboloEstadoAFND;
				simboloEstadoAFND = simboloEstadoAFD.substring(i, i+1);
				
				Estado estadoAFND;
				estadoAFND = automatoAFND.getEstado(simboloEstadoAFND);
				
				if (estadoAFND.isFinal()) {
					estadoAFD.setFinal(true);
					automatoAFD.addEstadoFinal(estadoAFD);
				}
				
				for (int j = 0; j < conjuntoAlfabeto.size(); j++) {
					char entradaAlfabeto;
					entradaAlfabeto = conjuntoAlfabeto.get(j);
					
					String nomeNovoEstadoAFD;
					nomeNovoEstadoAFD = "";
					
					ConjuntoObject<Transicao> conjuntoTransicaoEstadoAFND;
					conjuntoTransicaoEstadoAFND = estadoAFND.getTransicoes();
					
					for (int k = 0; k < conjuntoTransicaoEstadoAFND.size(); k++) {
						Transicao transicaoAFND;
						transicaoAFND = conjuntoTransicaoEstadoAFND.get(k);
						
						if (transicaoAFND.reconhecerEntrada(entradaAlfabeto)) {
							Estado estadoDestino;
							estadoDestino = transicaoAFND.getEstadoDestino();
							
							nomeNovoEstadoAFD += estadoDestino.getSimbolo();
						}
					}
					
					Estado novoEstadoAFD;
					novoEstadoAFD = new Estado(nomeNovoEstadoAFD);
					novoEstadoAFD = automatoAFD.addEstado(novoEstadoAFD);
					
					estadoAFD.addTransicao(entradaAlfabeto, novoEstadoAFD);
				}
			}
		}
		/* 	Criar automatoD
		 * 	criar estadoInicial
		 * 	automatoD recebe estadoInicial
		 * 	Para cada estadoD do automatoD
		 * 		Para cada simboloI do estadoD do automatoD
		 * 			Busca o estadoO no automatoOriginal que possui o simboloI
		 * 			Se o estadoO isFinal
		 * 				estadoD se torna final
		 * 			Fim
		 * 			Para cada entradaF do alfabeto
		 * 				nomeNovoEstadoD
		 * 				Busca as transicoes do estadoO que reconhecem a entradaF
		 * 				Para cada transicao encontrada
		 * 					Adiciona simbolo do estadoDestino no nomeNovoEstadoD
		 * 				Fim
		 * 				Cria novoEstadoD
		 * 				estadoRetorno = Adiciona novoEstadoD no automatoD
		 * 				Cria transicao do estadoD para o novoEstadoD
		 * 			Fim
		 * 		Fim
		 * 	Fim
		 */
		
		return null;
	}
	public Automato minimizar(Automato automato) {
		Automato novoAutomato;
		novoAutomato = automato.clone();
		
		
		
		return novoAutomato;
	}
}
