package automato;

import conjunto.ArrayConjuntoEstado;
import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoEstado;
import conjunto.ConjuntoObject;
import view.principal.ManagerLinguagem;

public class OperarAutomato {
	public OperarAutomato() {}
	
	public void gerarConjuntoEpsilonFecho(Automato automatoAFND) {
		/*	Percorre todas as transicoes
		 * 		Se o simbolo da transicao nao for epsilon
		 * 			continue
		 * 		Fim
		 * 		Adiciona o estadoDestino no conjuntoEpsilonFecho
		 * 	Fim
		*/
		
		ConjuntoEstado conjuntoEstado;
		conjuntoEstado = automatoAFND.getConjuntoEstado();
		
		for (int c = 0; c < conjuntoEstado.size(); c++) {
			Estado estado;
			estado = conjuntoEstado.get(c);
			
			ConjuntoEstado conjuntoEpsilonFecho;
			conjuntoEpsilonFecho = estado.getConjuntoEpsilonFecho();
			
			ConjuntoObject<Transicao> conjuntoTransicao;
			conjuntoTransicao = estado.getConjuntoTransicao();
			
			for (int i = 0; i < conjuntoTransicao.size(); i++) {
				Transicao transicao;
				transicao = conjuntoTransicao.get(i);
				
				Estado estadoDestino;
				estadoDestino = transicao.getEstadoDestino();
				
				if  (transicao.getSimboloEntrada() == ManagerLinguagem.EPSILON) {
					conjuntoEpsilonFecho.add(estadoDestino);
				}
			}
		}
		
		/*
		 * 	Enquanto houveMudanca
		 * 		Para todos os estadoDoAutomato
		 * 			Obtem conjuntoEpsilonFechoDoEstadoDoAutomato
		 * 			Para todo estadoDoConjuntoEpsilonFecho
		 * 				Obtem conjuntoEpsilonFechoDoEstadoDoConjuntoEpsilonFecho
		 * 				Add conjuntoEpsilonFechoDoEstadoDoConjuntoEpsilonFecho no conjuntoEpsilonFecho
		 * 				Verifica se o tamanho do conjuntoEpsilonFecho mudou
		 * 				Se o tamanho mudou
		 * 					houveMudanca = true
		 * 				Fim
		 * 			Fim
		 * 		Fim
		 * 	Fim
		 */
		
		boolean houveMudanca;
		houveMudanca = false;
		
		do {
			houveMudanca = false;
			
			for (int c = 0; c < conjuntoEstado.size(); c++) {
				Estado estado;
				estado = conjuntoEstado.get(c);
				
				ConjuntoEstado conjuntoEpsilonFecho;
				conjuntoEpsilonFecho = estado.getConjuntoEpsilonFecho();
				
				for (int i = 0; i < conjuntoEpsilonFecho.size(); i++) {
					Estado estadoEpsilonFecho;
					estadoEpsilonFecho = conjuntoEpsilonFecho.get(i);
					
					int sizeAntes;
					sizeAntes = conjuntoEpsilonFecho.size();
					
					conjuntoEpsilonFecho.add(estadoEpsilonFecho.getConjuntoEpsilonFecho());
					
					if (sizeAntes != conjuntoEpsilonFecho.size()) {
						houveMudanca = true;
					}
				}
			}
		} while(houveMudanca);
	}
	public Automato determinizar(Automato automatoAFND) {
		this.gerarConjuntoEpsilonFecho(automatoAFND);
		
		Estado estadoInicialAFND;
		estadoInicialAFND = automatoAFND.getEstadoInicial();
		
		String simboloEstadoInicialAFD;
		simboloEstadoInicialAFD = estadoInicialAFND.getConjuntoEpsilonFecho().getToString();
		
		Estado estadoInicialAFD;
		estadoInicialAFD = new Estado(simboloEstadoInicialAFD);
		
		ConjuntoAlfabeto conjuntoAlfabeto;
		conjuntoAlfabeto = automatoAFND.getConjuntoAlfabeto().clone();
		conjuntoAlfabeto.remove(ManagerLinguagem.EPSILON);
		
		Automato automatoAFD;
		automatoAFD = new Automato();
		automatoAFD.setEstadoInicial(estadoInicialAFD);
		automatoAFD.setConjuntoAlfabeto(conjuntoAlfabeto);
		
		if (estadoInicialAFND.isFinal()) {
			estadoInicialAFD.setFinal(true);
		}
		
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
				
				String[] simbolosEstadoAFND;
				simbolosEstadoAFND = simboloEstadoAFD.split(",");
				
				ConjuntoEstado conjunto;
				conjunto = new ConjuntoEstado();
				
				/* Cada caracter do simbolo do estadoAFD corresponde
				 * a um estadoAFND.
				 */
				for (int i = 0; i < simbolosEstadoAFND.length; i++) {
					String simboloEstadoAFND;
					simboloEstadoAFND = simbolosEstadoAFND[i];
					
					Estado estadoAFND;
					estadoAFND = automatoAFND.getEstado(simboloEstadoAFND);
					
					// EstadoAFD eh final se algum estadoAFND que o gerou for final
					if (estadoAFND.isFinal()) {
						estadoAFD.setFinal(true);
					}
					
					ConjuntoObject<Transicao> conjuntoTransicaoEstadoAFND;
					conjuntoTransicaoEstadoAFND = estadoAFND.getConjuntoTransicao();
					
					/* O novoEstadoAFD possui como simbolo a concatenacao do
					 * simbolo de todos estadosEpsilonFecho de todos os 
					 * estadosDestinoAFND cuja transicao reconhece a entradaDoAlfabeto
					 */
					for (int k = 0; k < conjuntoTransicaoEstadoAFND.size(); k++) {
						Transicao transicaoAFND;
						transicaoAFND = conjuntoTransicaoEstadoAFND.get(k);
						
						if (transicaoAFND.reconhecerEntrada(entradaDoAlfabeto)) {
							Estado estadoDestinoAFND;
							estadoDestinoAFND = transicaoAFND.getEstadoDestino();
							
							conjunto.add(estadoDestinoAFND.getConjuntoEpsilonFecho());
						}
					}
				}
				
				String simboloNovoEstadoAFD;
				simboloNovoEstadoAFD = conjunto.getToString();
				
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
		
		//automatoAFD.alterarSimboloDosEstados();
		
		return automatoAFD;
	}
	public Automato minimizar(Automato automato) {
		Automato novoAutomato;
		novoAutomato = automato.clone();
		novoAutomato = this.determinizar(novoAutomato);
		novoAutomato = this.eliminarEstadosInalcansaveis(novoAutomato);
		novoAutomato = this.eliminarEstadosMortos(novoAutomato);
		novoAutomato = this.eliminarEstadosMortos(novoAutomato);
		
		novoAutomato.alterarSimboloDosEstados();
		
		return novoAutomato;
	}
	
	public Automato eliminarEstadosInalcansaveis(Automato automatoOriginal) {
		Automato automatoOriginalClone;
		automatoOriginalClone = automatoOriginal.clone();
		
		Automato automatoEstadoAlcansavel;
		automatoEstadoAlcansavel = new Automato();
		automatoEstadoAlcansavel.setEstadoInicial(automatoOriginalClone.getEstadoInicial());
		automatoEstadoAlcansavel.setConjuntoAlfabeto(automatoOriginalClone.getConjuntoAlfabeto());
		
		ConjuntoEstado conjuntoEstadoAlcansavel;
		conjuntoEstadoAlcansavel = automatoEstadoAlcansavel.getConjuntoEstado();
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
		
		this.eliminarTransicoesInutil(conjuntoEstadoAlcansavel);
		
		return automatoEstadoAlcansavel;
	}
	public Automato eliminarEstadosMortos(Automato automatoOriginal) {
		Automato automatoClone;
		automatoClone = automatoOriginal.clone();
		
		ConjuntoEstado conjuntoEstadoClone;
		conjuntoEstadoClone = automatoClone.getConjuntoEstado();
		
		Automato automatoEstadoVivo;
		automatoEstadoVivo = new Automato();
		automatoEstadoVivo.setEstadoInicial(automatoClone.getEstadoInicial());
		automatoEstadoVivo.setConjuntoAlfabeto(automatoClone.getConjuntoAlfabeto());
		
		ConjuntoEstado conjuntoEstadoVivo;
		conjuntoEstadoVivo = automatoEstadoVivo.getConjuntoEstado();
		conjuntoEstadoVivo.add(automatoClone.getConjuntoEstadoFinal());
		conjuntoEstadoVivo.add(automatoEstadoVivo.getEstadoInicial());
		
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
		
		this.eliminarTransicoesInutil(conjuntoEstadoVivo);
		
		return automatoEstadoVivo;
	}
	private void eliminarTransicoesInutil(ConjuntoEstado conjuntoEstado) {
		for (int c = 0; c < conjuntoEstado.size(); c++) {
			Estado estadoAlcansavel;
			estadoAlcansavel = conjuntoEstado.get(c);
			
			ConjuntoObject<Transicao> conjuntoTransicaoDoEstadoAlcansavel;
			conjuntoTransicaoDoEstadoAlcansavel = estadoAlcansavel.getConjuntoTransicao();
			
			// Percorre as transicoes
			for (int i = 0; i < conjuntoTransicaoDoEstadoAlcansavel.size(); i++) {
				Transicao transicaoDoEstadoAlcansavel;
				transicaoDoEstadoAlcansavel = conjuntoTransicaoDoEstadoAlcansavel.get(i);
				
				if (!conjuntoEstado.contains(transicaoDoEstadoAlcansavel.getEstadoDestino())) {
					conjuntoTransicaoDoEstadoAlcansavel.remove(transicaoDoEstadoAlcansavel);
					i--;
				}
			}
		}
	}
	public Automato eliminarEstadosDuplicados(Automato automatoOriginal) {
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
				
				ConjuntoEstado conjuntoEstadoNovo, conjuntoEstadoAnteriorClone;
				conjuntoEstadoNovo = new ConjuntoEstado();
				conjuntoEstadoAnteriorClone = conjuntoEstadoAnterior.clone();
				
				arrayConjuntoEstadoNovo.add(conjuntoEstadoNovo);
				arrayConjuntoEstadoNovo.add(conjuntoEstadoAnteriorClone);
				
				Estado primeiroEstadoDoConjuntoEstado;
				primeiroEstadoDoConjuntoEstado = conjuntoEstadoAnteriorClone.get(0);
				
				for (int i = 1; i < conjuntoEstadoAnteriorClone.size(); i++) {
					Estado estadoDoConjuntoEstadoAnteriorClone;
					estadoDoConjuntoEstadoAnteriorClone = conjuntoEstadoAnteriorClone.get(i);
					
					if (!arrayConjuntoEstadoAnterior.conjuntoTransicaoEquivalente(primeiroEstadoDoConjuntoEstado, estadoDoConjuntoEstadoAnteriorClone)) {
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
		
		/* Adiciona os estados no novoAutomato, define o inicial e os finais e
		 * altera o estadoDestino de cada transicao para o primeiro estado de cada conjuntoDeEstado
		 */
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
			
			// Percorre as transicoes para atualizar o estadoDestino
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
		
		return novoAutomato;
	}
}
