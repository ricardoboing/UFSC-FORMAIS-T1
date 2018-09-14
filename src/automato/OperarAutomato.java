package automato;

import conjunto.ArrayConjuntoEstado;
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
		
		/* Cria as transicoes para cada estadoAFD. Se o estadoDestino
		 * nao existir, cria e adiciona no conjuntoEstadoAFD
		 */
		for (int c = 0; c < conjuntoEstadoAFD.size(); c++) {
			Estado estadoAFD;
			estadoAFD = conjuntoEstadoAFD.get(c);
			
			String simboloEstadoAFD;
			simboloEstadoAFD = estadoAFD.getSimbolo();
			
			/* Para cada entradaDoAlfabeto eh criado (caso nao exista) um
			 * novoEstadoAFD. O simbolo do novoEstadoAFD eh a concatenacao
			 * dos simbolos dos estadoDestinoAFND para o qual o estadoAFND
			 * transita.
			 */
			for (int j = 0; j < conjuntoAlfabeto.size(); j++) {
				char entradaDoAlfabeto;
				entradaDoAlfabeto = conjuntoAlfabeto.get(j);
				
				String simboloNovoEstadoAFD;
				simboloNovoEstadoAFD = "";
				
				/* Cada caracter do simbolo do estadoAFD corresponde
				 * a um estadoAFND.
				 */
				for (int i = 0; i < simboloEstadoAFD.length(); i++) {
					String simboloEstadoAFND;
					simboloEstadoAFND = simboloEstadoAFD.substring(i, i+1);
					
					Estado estadoAFND;
					estadoAFND = automatoAFND.getEstado(simboloEstadoAFND);
					
					// EstadoAFD eh final se algum estadoAFND que o gerou for final
					if (estadoAFND.isFinal()) {
						estadoAFD.setFinal(true);
					}
					
					ConjuntoObject<Transicao> conjuntoTransicaoEstadoAFND;
					conjuntoTransicaoEstadoAFND = estadoAFND.getConjuntoTransicao();
					
					/* O novoEstadoAFD possui como simbolo a concatenacao do
					 * simbolo de todos estadosDestinoAFND cuja transicao
					 * reconhece a entradaDoAlfabeto
					 */
					for (int k = 0; k < conjuntoTransicaoEstadoAFND.size(); k++) {
						Transicao transicaoAFND;
						transicaoAFND = conjuntoTransicaoEstadoAFND.get(k);
						
						if (transicaoAFND.reconhecerEntrada(entradaDoAlfabeto)) {
							Estado estadoDestinoAFND;
							estadoDestinoAFND = transicaoAFND.getEstadoDestino();
							
							simboloNovoEstadoAFD += estadoDestinoAFND.getSimbolo();
						}
					}
				}
				
				/* Cria o novoEstadoAFD somente caso seja formado por
				 * pelo menos um estadoDestinoAFND
				 */
				if (!simboloNovoEstadoAFD.equals("")) {
					Estado novoEstadoAFD;
					novoEstadoAFD = new Estado(simboloNovoEstadoAFD);
					
					// Retorna o novoEstadoAFD ou seu equivalente (mesmo simbolo), caso exista
					novoEstadoAFD = automatoAFD.addEstado(novoEstadoAFD);
					
					estadoAFD.addTransicao(entradaDoAlfabeto, novoEstadoAFD);
				}
			}
		}
		
		return automatoAFD;
	}
	public Automato minimizar(Automato automato) {
		Automato novoAutomato;
		novoAutomato = automato.clone();
		
		
		
		return novoAutomato;
	}
	
	public Automato eliminarEstadosInalcansaveis(Automato automatoOriginal) {
		Automato automatoOriginalClone;
		automatoOriginalClone = automatoOriginal.clone();
		
		Automato automatoSemEstadoInalcansavel;
		automatoSemEstadoInalcansavel = new Automato();
		automatoSemEstadoInalcansavel.setEstadoInicial(automatoOriginalClone.getEstadoInicial());
		
		ConjuntoEstado conjuntoEstadoAlcansavel;
		conjuntoEstadoAlcansavel = automatoSemEstadoInalcansavel.getConjuntoEstado();
		conjuntoEstadoAlcansavel.add(automatoOriginalClone.getEstadoInicial());
		
		/* Percorre as transicoes de cada estadoAlcansavel e adiciona
		 * os estadoDestino das transicoes no conjuntoEstadoAlcansavel
		 */
		for (int c = 0; c < conjuntoEstadoAlcansavel.size(); c++) {
			Estado estadoAlcansavel;
			estadoAlcansavel = conjuntoEstadoAlcansavel.get(c);
			
			ConjuntoObject<Transicao> conjuntoTransicaoDoEstadoAlcansavel;
			conjuntoTransicaoDoEstadoAlcansavel = estadoAlcansavel.getConjuntoTransicao();
			
			// Percorre as transicoes
			for (int i = 0; i < conjuntoTransicaoDoEstadoAlcansavel.size(); i++) {
				Transicao transicaoDoEstadoAlcansavel;
				transicaoDoEstadoAlcansavel = conjuntoTransicaoDoEstadoAlcansavel.get(i);
				
				// Adiciona estadoDestino como alcansavel
				conjuntoEstadoAlcansavel.add(transicaoDoEstadoAlcansavel.getEstadoDestino());
			}
		}
		
		return automatoSemEstadoInalcansavel;
	}
	public Automato eliminarEstadosMortos(Automato automatoOriginal) {
		Automato automatoClone;
		automatoClone = automatoOriginal.clone();
		
		ConjuntoEstado conjuntoEstadoClone;
		conjuntoEstadoClone = automatoClone.getConjuntoEstado();
		
		Automato automatoSemEstadoMorto;
		automatoSemEstadoMorto = new Automato();
		automatoSemEstadoMorto.setEstadoInicial(automatoClone.getEstadoInicial());
		
		ConjuntoEstado conjuntoEstadoVivo;
		conjuntoEstadoVivo = automatoSemEstadoMorto.getConjuntoEstado();
		conjuntoEstadoVivo.add(automatoClone.getConjuntoEstadoFinal());
		conjuntoEstadoVivo.add(automatoSemEstadoMorto.getEstadoInicial());
		
		int numeroTotalEstadoVivoAnterior;
		numeroTotalEstadoVivoAnterior = -1;
		
		do {
			numeroTotalEstadoVivoAnterior = conjuntoEstadoVivo.size();
			
			for (int c = 0; c < conjuntoEstadoClone.size(); c++) {
				Estado estadoClone;
				estadoClone = conjuntoEstadoClone.get(c);
				
				// EstadoClone ja eh vivo
				if (conjuntoEstadoVivo.contains(estadoClone)) {
					continue;
				}
				
				ConjuntoObject<Transicao> conjuntoTransicaoDoEstadoClone;
				conjuntoTransicaoDoEstadoClone = estadoClone.getConjuntoTransicao();
				
				// Verifica se algum estado para o qual transita eh vivo
				for (int i = 0; i < conjuntoTransicaoDoEstadoClone.size(); i++) {
					Transicao transicaoDoEstadoClone;
					transicaoDoEstadoClone = conjuntoTransicaoDoEstadoClone.get(i);
					
					// EstadoClone eh vivo se algum estadoDestino for vivo
					if (conjuntoEstadoVivo.contains(transicaoDoEstadoClone.getEstadoDestino())) {
						conjuntoEstadoVivo.add(estadoClone);
						break;
					}
				}
			}
			
			// Continuar enquanto novos estados forem adicionados como vivo
		} while (numeroTotalEstadoVivoAnterior != conjuntoEstadoVivo.size());
		
		return automatoSemEstadoMorto;
	}
	
	public void eliminarEstadosDuplicados(Automato automatoOriginal) {
		Automato automatoClone;
		automatoClone = automatoOriginal.clone();
		
		ConjuntoEstado conjuntoEstadoFinal, conjuntoEstadoNaoFinal;
		conjuntoEstadoFinal = new ConjuntoEstado();
		conjuntoEstadoFinal.add(automatoClone.getConjuntoEstadoFinal());
		
		conjuntoEstadoNaoFinal = new ConjuntoEstado();
		conjuntoEstadoNaoFinal.add(automatoClone.getConjuntoEstadoNaoFinal());
		
		ArrayConjuntoEstado arrayConjuntoEstadoAnterior, arrayConjuntoEstadoNovo;
		arrayConjuntoEstadoNovo = new ArrayConjuntoEstado();
		arrayConjuntoEstadoNovo.add(conjuntoEstadoFinal);
		arrayConjuntoEstadoNovo.add(conjuntoEstadoNaoFinal);
		
		boolean alterouArrayConjuntoEstado;
		alterouArrayConjuntoEstado = false;
		
		do {
			alterouArrayConjuntoEstado = false;
			
			arrayConjuntoEstadoAnterior = arrayConjuntoEstadoNovo;
			arrayConjuntoEstadoNovo = new ArrayConjuntoEstado();
			
			for (int c = 0; c < arrayConjuntoEstadoAnterior.size(); c++) {
				ConjuntoEstado conjuntoEstadoAnterior;
				conjuntoEstadoAnterior = arrayConjuntoEstadoAnterior.get(c);
				
				Estado primeiroEstadoDoConjuntoEstado;
				primeiroEstadoDoConjuntoEstado = conjuntoEstadoAnterior.get(0);
				
				ConjuntoEstado conjuntoEstadoNovo;
				conjuntoEstadoNovo = new ConjuntoEstado();
				
				arrayConjuntoEstadoNovo.add(conjuntoEstadoNovo);
				
				ConjuntoEstado conjuntoEstadoAnteriorClone;
				conjuntoEstadoAnteriorClone = conjuntoEstadoAnterior.clone();
				
				arrayConjuntoEstadoNovo.add(conjuntoEstadoAnteriorClone);
				
				for (int i = 1; i < conjuntoEstadoAnteriorClone.size(); i++) {
					Estado estadoDoConjuntoEstadoAnteriorClone;
					estadoDoConjuntoEstadoAnteriorClone = conjuntoEstadoAnteriorClone.get(i);
					
					if (arrayConjuntoEstadoAnterior.conjuntoTransicaoEquivalente(primeiroEstadoDoConjuntoEstado, estadoDoConjuntoEstadoAnteriorClone)) {
						conjuntoEstadoAnteriorClone.remove(estadoDoConjuntoEstadoAnteriorClone);
						conjuntoEstadoNovo.add(estadoDoConjuntoEstadoAnteriorClone);
						i--;
						alterouArrayConjuntoEstado = true;
					}
				}
				
				if (conjuntoEstadoNovo.size() == 0) {
					arrayConjuntoEstadoNovo.remove(conjuntoEstadoNovo);
				}
			}
		} while(alterouArrayConjuntoEstado);
		
		Automato novoAutomato;
		novoAutomato = new Automato();
		novoAutomato.setConjuntoAlfabeto( automatoClone.getConjuntoAlfabeto() );
		
		for (int c = 0; c < arrayConjuntoEstadoNovo.size(); c++) {
			ConjuntoEstado conjuntoEstado;
			conjuntoEstado = arrayConjuntoEstadoNovo.get(c);
			
			// O primeiro estado do conjuntoDeEstadosEquivalentes eh o estado adicionado no novoAutomato
			Estado primeiroEstadoDoConjuntoEstado;
			primeiroEstadoDoConjuntoEstado = conjuntoEstado.get(0);
			novoAutomato.addEstado(primeiroEstadoDoConjuntoEstado);
			
			if (ArrayConjuntoEstado.conjuntoEstadoPossuiEstadoInicial(conjuntoEstado)) {
				novoAutomato.setEstadoInicial(primeiroEstadoDoConjuntoEstado);
			}
			if (ArrayConjuntoEstado.conjuntoEstadoPossuiEstadoFinal(conjuntoEstado)) {
				primeiroEstadoDoConjuntoEstado.setFinal(true);
			}
			
			ConjuntoObject<Transicao> conjuntoTransicao;
			conjuntoTransicao = primeiroEstadoDoConjuntoEstado.getConjuntoTransicao();
			
			for (int i = 0; i < conjuntoTransicao.size(); i++) {
				Transicao transicao;
				transicao = conjuntoTransicao.get(i);
				
				ConjuntoEstado conjuntoEstadoDestinoDaTransicao;
				conjuntoEstadoDestinoDaTransicao = arrayConjuntoEstadoNovo.getConjuntoEstado(transicao.getEstadoDestino());
				
				// O primeiro estado do conjuntoDeEstadosEquivalentes eh o estado adicionado no novoAutomato
				Estado estadoDestinoDaTransicaoNovo;
				estadoDestinoDaTransicaoNovo = conjuntoEstadoDestinoDaTransicao.get(0);
				
				transicao.setEstadoDestino(estadoDestinoDaTransicaoNovo);
			}
		}
		
		/* 	Clonar automatoClone = automatoOriginal
		 * 	Criar arrayConjuntoEstadoAnterior, arrayConjuntoEstadoNovo
		 * 	Criar conjuntoEstadoFinal e conjuntoEstadoNaoFinal
		 * 	Adiciona os conjuntos no arrayConjuntoEstadoNovo
		 * 	Enquanto alterouArrayConjuntoEstado == true (arrayConjuntoEstadoAnterior != arrayConjuntoEstadoNovo)
		 * 		alterouArrayConjuntoEstado = false
		 * 		arrayConjuntoEstadoAnterior = arrayConjuntoEstadoNovo
		 * 		arrayConjuntoEstadoNovo = new
		 * 		Para cada conjuntoEstadoAnterior do arrayConjuntoEstadoAnterior
		 * 			Obtem primeiroEstadoDoConjuntoEstado
		 * 			Cria novoConjuntoEstado
		 * 			Adiciona novoConjuntoEstado no arrayConjuntoEstadoNovo
		 * 			Clona conjuntoEstado = conjuntoEstadoAnterior
		 * 			Adiciona conjuntoEstado no arrayConjuntoEstadoNovo
		 * 			Para cada estadoDoConjuntoEstado a partir do segundo
		 * 				Verifica se as transicoes do estadoDoConjuntoEstadoAnteriorClone sao equivalentes as do
		 * 					.. primeiroEstadoDoConjuntoEstadoAnteriorClone, com base nas transicoes do arrayConjuntoEstadoAnterior
		 * 				Se as transicoes nao forem equivalentes
		 * 					Remove o estadoDoConjuntoEstado do conjuntoEstado
		 * 					Adiciona o estadoDoConjuntoEstado no novoConjuntoEstado
		 * 					Decrementa o indice do laço para-faca
		 * 					alterouArrayConjuntoEstado = true
		 * 				FIM
		 * 			FIM
		 * 			Se novoConjuntoEstado estiver vazio
		 * 				Remove novoConjuntoEstado do arrayConjuntoEstadoNovo
		 *			FIM
		 * 		FIM
		 * 	FIM
		 * 	Criar novoAutomato
		 * 	Copiar alfabeto do automatoClone para o novoAutomato
		 * 	Para cada conjuntoEstado do arrayConjuntoEstadoNovo
		 * 		Obtem primeiroEstadoDoConjuntoEstado
		 * 		Adiciona o primeiroEstadoDoConjuntoEstado no novoAutomato 
		 * 		Se algum estadoDoConjuntoEstado for inicial
		 * 			primeiroEstadoDoConjuntoEstado vira inicial
		 * 		FIM
		 * 		Se algum estadoDoConjuntoEstado for final
		 * 			primeiroEstadoDoConjuntoEstado vira final
		 * 		FIM
		 * 		Para cada transicao do primeiroEstadoDoConjuntoEstado
		 * 			Obtem conjuntoDestino do estadoDestinoDaTransicao
		 * 			Obtem o primeiroEstadoDoConjuntoDestino
		 * 			Altera o destino da transicao para o primeiroEstadoDoConjuntoDestino
		 * 		FIM
		 * 	FIM
		 */
	}
}
