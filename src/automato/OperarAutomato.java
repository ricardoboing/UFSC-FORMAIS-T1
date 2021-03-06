/*
 *  Trabalho I: Algoritmos para Manipulacao de Linguagens Regulares
 *  
 *  Departamento de Informatica e Estatistica – Universidade Federal de Santa Catarina (UFSC)
 *  Campus Reitor Joao David Ferreira Lima, 88.040-900 – Florianopolis – SC – Brasil
 *  
 *  brunohonnef@gmail.com pedroabcorte@gmail.com ricardoboing.ufsc@gmail.com
 *  
 *  Bruno Gilmar Honnef
 *  Pedro Alexandre Barradas da Corte
 *  Ricardo do Nascimento Boing
 *  
 *  11 de Outubro de 2018
 */
package automato;

import conjunto.ArrayConjuntoEstado;
import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoEstado;
import conjunto.ConjuntoObject;
import janela.principal.ManagerLinguagem;

public class OperarAutomato {
	public static void gerarConjuntoEpsilonFecho(Automato automatoAFND) {
		ConjuntoEstado conjuntoEstado;
		conjuntoEstado = automatoAFND.getConjuntoEstado();
		
		// Add no conjunto todos os estadoDestino para
		// o qual o estado transita por epsilon
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
		
		boolean houveMudanca;
		houveMudanca = false;
		
		// Enquanto novos estados forem add no conjunto epsilon fecho
		do {
			houveMudanca = false;
			
			// O conjunto epsilon fecho do estado recebe todos os estados dos
			// conjuntos epsilon fecho dos estadoDestino para o qual
			// o estado transita
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
					
					// Se houve mudanca em algum conjunto, entao necessita de mais uma iteracao
					if (sizeAntes != conjuntoEpsilonFecho.size()) {
						houveMudanca = true;
					}
				}
			}
		} while(houveMudanca);
	}
	public static Automato determinizar(Automato automatoAFND) {
		// Gera o conjunto epsilon fecho no AFND
		OperarAutomato.gerarConjuntoEpsilonFecho(automatoAFND);
		
		Estado estadoInicialAFND;
		estadoInicialAFND = automatoAFND.getEstadoInicial();
		
		// Estado inicial do AFD eh o conjunto epsilon fecho do AFND
		String simboloEstadoInicialAFD;
		simboloEstadoInicialAFD = estadoInicialAFND.getConjuntoEpsilonFecho().getToString();
		
		Estado estadoInicialAFD;
		estadoInicialAFD = new Estado(simboloEstadoInicialAFD);
		
		// Copia o alfabeto mas desconsidera transicao por epsilon
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
		
		automatoAFD.alterarSimboloDosEstados();
		
		return automatoAFD;
	}
	public static Automato minimizar(Automato automato, ManagerLinguagem managerLinguagem) {
		// PASSO 1: DETERMINIZAR
		Automato novoAutomato;
		novoAutomato = automato.clone();
		novoAutomato = OperarAutomato.determinizar(novoAutomato);
		novoAutomato.setNome("A.M.I."+managerLinguagem.getNomeNovoAutomato());
		novoAutomato.setNomeOperacaoGerador("Determinizacao");
		novoAutomato.setNomePai1( automato.getNome() );
		novoAutomato.alterarSimboloDosEstados();
		
		managerLinguagem.addAutomato(novoAutomato);
		managerLinguagem.gerarNomeNovoAutomato();
		
		// PASSO 2: ELIMINAR ESTADOS INALCANSAVEIS
		novoAutomato = OperarAutomato.eliminarEstadosInalcansaveis(novoAutomato);
		novoAutomato.setNome("A.M.I."+managerLinguagem.getNomeNovoAutomato());
		novoAutomato.alterarSimboloDosEstados();
		
		managerLinguagem.addAutomato(novoAutomato);
		managerLinguagem.gerarNomeNovoAutomato();
		
		// PASSO 3: ELIMINAR ESTADOS MORTOS
		novoAutomato = OperarAutomato.eliminarEstadosMortos(novoAutomato);
		novoAutomato.setNome("A.M.I."+managerLinguagem.getNomeNovoAutomato());
		novoAutomato.alterarSimboloDosEstados();
		
		managerLinguagem.addAutomato(novoAutomato);
		managerLinguagem.gerarNomeNovoAutomato();
		
		// PASSO 4: ELIMINAR ESTADOS DUPLICADOS
		novoAutomato = OperarAutomato.eliminarEstadosDuplicados(novoAutomato);
		novoAutomato.alterarSimboloDosEstados();
		
		return novoAutomato;
	}
	
	public static Automato eliminarEstadosInalcansaveis(Automato automatoOriginal) {
		Automato automatoOriginalClone;
		automatoOriginalClone = automatoOriginal.clone();
		
		Automato automatoEstadoAlcansavel;
		automatoEstadoAlcansavel = new Automato();
		automatoEstadoAlcansavel.setEstadoInicial(automatoOriginalClone.getEstadoInicial());
		automatoEstadoAlcansavel.setConjuntoAlfabeto(automatoOriginalClone.getConjuntoAlfabeto());
		automatoEstadoAlcansavel.setNomePai1(automatoOriginal.getNome());
		automatoEstadoAlcansavel.setNomeOperacaoGerador("REM. INALC.");
		
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
		
		OperarAutomato.eliminarTransicoesInutil(conjuntoEstadoAlcansavel);
		
		return automatoEstadoAlcansavel;
	}
	public static Automato eliminarEstadosMortos(Automato automatoOriginal) {
		Automato automatoClone;
		automatoClone = automatoOriginal.clone();
		
		ConjuntoEstado conjuntoEstadoClone;
		conjuntoEstadoClone = automatoClone.getConjuntoEstado();
		
		Automato automatoEstadoVivo;
		automatoEstadoVivo = new Automato();
		automatoEstadoVivo.setEstadoInicial(automatoClone.getEstadoInicial());
		automatoEstadoVivo.setConjuntoAlfabeto(automatoClone.getConjuntoAlfabeto());
		automatoEstadoVivo.setNomePai1(automatoOriginal.getNome());
		automatoEstadoVivo.setNomeOperacaoGerador("REM. MORTO");
		
		ConjuntoEstado conjuntoEstadoVivo;
		conjuntoEstadoVivo = automatoEstadoVivo.getConjuntoEstado();
		conjuntoEstadoVivo.add(automatoClone.getConjuntoEstadoFinal());
		conjuntoEstadoVivo.add(automatoEstadoVivo.getEstadoInicial());
		
		int numeroTotalEstadoVivoAnterior;
		numeroTotalEstadoVivoAnterior = -1;
		
		do {
			numeroTotalEstadoVivoAnterior = conjuntoEstadoVivo.size();
			
			for (int c = 0; c < conjuntoEstadoClone.size(); c++) {
				Estado estado;
				estado = conjuntoEstadoClone.get(c);
				
				// Estado ja foi add no conjunto de vivos
				if (conjuntoEstadoVivo.contains(estado)) {
					continue;
				}
				
				ConjuntoObject<Transicao> conjuntoTransicaoDoEstado;
				conjuntoTransicaoDoEstado = estado.getConjuntoTransicao();
				
				// Verifica se algum estado para o qual transita eh vivo
				for (int i = 0; i < conjuntoTransicaoDoEstado.size(); i++) {
					Transicao transicaoDoEstadoClone;
					transicaoDoEstadoClone = conjuntoTransicaoDoEstado.get(i);
					
					// Estado eh vivo se algum estadoDestino for vivo
					if (conjuntoEstadoVivo.contains(transicaoDoEstadoClone.getEstadoDestino())) {
						conjuntoEstadoVivo.add(estado);
						break;
					}
				}
			}
			
			// Continuar enquanto novos estados forem adicionados como vivo
		} while (numeroTotalEstadoVivoAnterior != conjuntoEstadoVivo.size());
		
		OperarAutomato.eliminarTransicoesInutil(conjuntoEstadoVivo);
		
		return automatoEstadoVivo;
	}
	private static void eliminarTransicoesInutil(ConjuntoEstado conjuntoEstadoAlcansavel) {
		for (int c = 0; c < conjuntoEstadoAlcansavel.size(); c++) {
			Estado estadoAlcansavel;
			estadoAlcansavel = conjuntoEstadoAlcansavel.get(c);
			
			ConjuntoObject<Transicao> conjuntoTransicaoDoEstadoAlcansavel;
			conjuntoTransicaoDoEstadoAlcansavel = estadoAlcansavel.getConjuntoTransicao();
			
			// Percorre as transicoes e verifica se sao uteis
			for (int i = 0; i < conjuntoTransicaoDoEstadoAlcansavel.size(); i++) {
				Transicao transicaoDoEstadoAlcansavel;
				transicaoDoEstadoAlcansavel = conjuntoTransicaoDoEstadoAlcansavel.get(i);
				
				// Se o estadoDestino nao for alcansavel, entao a transicao eh inutil
				if (!conjuntoEstadoAlcansavel.contains(transicaoDoEstadoAlcansavel.getEstadoDestino())) {
					conjuntoTransicaoDoEstadoAlcansavel.remove(transicaoDoEstadoAlcansavel);
					i--;
				}
			}
		}
	}
	public static Automato eliminarEstadosDuplicados(Automato automatoOriginal) {
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
		
		// Permanece no laco enquanto novos grupos de equivalencia forem criados
		do {
			alterouArrayConjuntoEstado = false;
			
			arrayConjuntoEstadoAnterior = arrayConjuntoEstadoNovo;
			arrayConjuntoEstadoNovo = new ArrayConjuntoEstado();
			
			/* Para todo conjunto verifica-se se dois estados nao sao equivalentes
			 * Caso positivo, entao cria-se um novo conjunto
			 */
			for (int c = 0; c < arrayConjuntoEstadoAnterior.size(); c++) {
				ConjuntoEstado conjuntoEstadoAnterior;
				conjuntoEstadoAnterior = arrayConjuntoEstadoAnterior.get(c);
				
				// Cria o novo conjunto e clona o conjunto anterior, para nao modificar o original
				ConjuntoEstado conjuntoEstadoNovo, conjuntoEstadoAnteriorClone;
				conjuntoEstadoNovo = new ConjuntoEstado();
				conjuntoEstadoAnteriorClone = conjuntoEstadoAnterior.clone();
				
				arrayConjuntoEstadoNovo.add(conjuntoEstadoNovo);
				arrayConjuntoEstadoNovo.add(conjuntoEstadoAnteriorClone);
				
				Estado primeiroEstadoDoConjunto;
				primeiroEstadoDoConjunto = null;
				
				if (conjuntoEstadoAnteriorClone.size() > 0) {
					primeiroEstadoDoConjunto = conjuntoEstadoAnteriorClone.get(0);
				}
				
				for (int i = 1; i < conjuntoEstadoAnteriorClone.size(); i++) {
					Estado estadoDoConjunto;
					estadoDoConjunto = conjuntoEstadoAnteriorClone.get(i);
					
					// Se as transicoes do estadoDoConjunto nao levarem aos mesmos conjuntos que as
					// transicoes do primeiroEstadoDoConjunto, entao o estadoDoConjunto nao eh
					// equivalente ao primeiroEstadoDoConjunto (adiciona o estadoDoConjunto no novo conjunto)
					if (!arrayConjuntoEstadoAnterior.conjuntoTransicaoEquivalente(primeiroEstadoDoConjunto, estadoDoConjunto)) {
						conjuntoEstadoAnteriorClone.remove(estadoDoConjunto);
						conjuntoEstadoNovo.add(estadoDoConjunto);
						i--;
						// Se houver um novo conjunto for add
						// entao uma nova iteracao do do-while deve acontecer
						alterouArrayConjuntoEstado = true;
					}
				}
				
				// Se o novo conjunto nao possuir nenhum estado
				// eh porque nao houve mudancas na lista de conjuntos
				if (conjuntoEstadoNovo.size() == 0) {
					arrayConjuntoEstadoNovo.remove(conjuntoEstadoNovo);
				}
			}
		} while(alterouArrayConjuntoEstado);
		
		Automato novoAutomato;
		novoAutomato = new Automato();
		novoAutomato.setConjuntoAlfabeto( automatoClone.getConjuntoAlfabeto() );
		novoAutomato.setNomePai1(automatoOriginal.getNome());
		novoAutomato.setNomeOperacaoGerador("REM. DUPLIC.");
		
		/* Adiciona os estados no novoAutomato, define o inicial e os finais e
		 * altera o estadoDestino de cada transicao para o primeiro estado de cada conjuntoDeEstado
		 */
		for (int c = 0; c < arrayConjuntoEstadoNovo.size(); c++) {
			ConjuntoEstado conjuntoEstado;
			conjuntoEstado = arrayConjuntoEstadoNovo.get(c);
			
			if (conjuntoEstado.size() == 0) {
				continue;
			}
			
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
	
	public static Automato intersectar(Automato automatoOriginal1, Automato automatoOriginal2, ManagerLinguagem managerLinguagem) {
		// PASSO 1: DETERMINIZAR OS AUTOMATOS
		Automato automatoClone1, automatoClone2;
		automatoClone1 = automatoOriginal1.clone();
		automatoClone2 = automatoOriginal2.clone();
		
		automatoClone1 = OperarAutomato.determinizar(automatoClone1);
		automatoClone2 = OperarAutomato.determinizar(automatoClone2);
		
		// PASSO 2.1: COMPLEMENTAR AUTOMATO 1
		Automato automatoComplemento1;
		automatoComplemento1 = OperarAutomato.complementarAutomato(automatoClone1);
		automatoComplemento1.setNome("A.C.I."+managerLinguagem.getNomeNovoAutomato());
		automatoComplemento1.setNomePai1(automatoOriginal1.getNome());
		automatoComplemento1.alterarSimboloDosEstados();
		managerLinguagem.addAutomato(automatoComplemento1);
		managerLinguagem.gerarNomeNovoAutomato();
		
		// PASSO 2.2: COMPLEMENTAR AUTOMATO 2
		Automato automatoComplemento2;
		automatoComplemento2 = OperarAutomato.complementarAutomato(automatoClone2);
		automatoComplemento2.setNome("A.C.I."+managerLinguagem.getNomeNovoAutomato());
		automatoComplemento2.setNomePai1(automatoOriginal2.getNome());
		automatoComplemento2.alterarSimboloDosEstados();
		managerLinguagem.addAutomato(automatoComplemento2);
		managerLinguagem.gerarNomeNovoAutomato();
		
		// PASSO 3: UNIAO DO COMPLEMENTO DO AUTOMATO 1 COM COMPLEMENTO DO AUTOMATO 2
		Automato automatoUniao;
		automatoUniao = OperarAutomato.unir(automatoComplemento1, automatoComplemento2);
		automatoUniao.setNome("A.U.I."+managerLinguagem.getNomeNovoAutomato());
		automatoUniao.alterarSimboloDosEstados();
		
		managerLinguagem.addAutomato(automatoUniao);
		managerLinguagem.gerarNomeNovoAutomato();
		
		// PASSO 4: DETERMINIZAR A UNIAO
		Automato automatoDeterministico;
		automatoDeterministico = OperarAutomato.determinizar(automatoUniao);
		automatoDeterministico.setNome("A.D.I."+managerLinguagem.getNomeNovoAutomato());
		automatoDeterministico.alterarSimboloDosEstados();
		managerLinguagem.addAutomato(automatoDeterministico);
		managerLinguagem.gerarNomeNovoAutomato();
		
		// PASSO 5: COMPLEMENTO DA UNIAO
		Automato automatoInterseccao;
		automatoInterseccao = OperarAutomato.complementarAutomato(automatoDeterministico);
		automatoInterseccao.alterarSimboloDosEstados();
		
		automatoInterseccao.setNomePai1(automatoOriginal1.getNome());
		automatoInterseccao.setNomePai2(automatoOriginal2.getNome());
		automatoInterseccao.setNomeOperacaoGerador("INTERSECCAO");
		
		return automatoInterseccao;
	}
	
	public static Automato unir(Automato automatoOriginal1, Automato automatoOriginal2) {
		Automato automatoClone1, automatoClone2;
		automatoClone1 = automatoOriginal1.clone();
		automatoClone2 = automatoOriginal2.clone();
		
		ConjuntoEstado conjuntoEstado1, conjuntoEstado2;
		conjuntoEstado1 = automatoClone1.getConjuntoEstado();
		conjuntoEstado2 = automatoClone2.getConjuntoEstado();
		
		// Altera o simbolo dos estados de ambos automatos
		// para que os estados nao tenham conflitos de simbolos
		for (int c = 0; c < conjuntoEstado1.size(); c++) {
			Estado estado;
			estado = conjuntoEstado1.get(c);
			estado.setSimbolo(estado.toString());
		}
		for (int c = 0; c < conjuntoEstado2.size(); c++) {
			Estado estado;
			estado = conjuntoEstado2.get(c);
			estado.setSimbolo(estado.toString());
		}
		
		Estado estadoInicial1, estadoInicial2;
		estadoInicial1 = automatoClone1.getEstadoInicial();
		estadoInicial2 = automatoClone2.getEstadoInicial();
		
		estadoInicial1.setInicial(false);
		estadoInicial2.setInicial(false);
		
		// Novo estadoInicial transica por epsilon para os antigos estados iniciais
		Estado novoEstadoInicial;
		novoEstadoInicial = new Estado("?");
		novoEstadoInicial.setSimbolo(novoEstadoInicial.toString());
		novoEstadoInicial.addTransicao(ManagerLinguagem.EPSILON, estadoInicial1);
		novoEstadoInicial.addTransicao(ManagerLinguagem.EPSILON, estadoInicial2);
		
		// Novo estadoInicial eh final se um dos antigos iniciais for final
		if (estadoInicial1.isFinal() || estadoInicial2.isFinal()) {
			novoEstadoInicial.setFinal(true);
		}
		
		// O conjuntoAlfabeto do novoAutomato eh a unicao do alfabeto dos dois automatos
		ConjuntoAlfabeto conjuntoAlfabeto;
		conjuntoAlfabeto = automatoClone1.getConjuntoAlfabeto().clone();
		conjuntoAlfabeto.add(automatoClone2.getConjuntoAlfabeto());
		conjuntoAlfabeto.add(ManagerLinguagem.EPSILON);
		
		Automato automatoUniao;
		automatoUniao = new Automato();
		automatoUniao.addEstadoInicial(novoEstadoInicial);
		automatoUniao.addEstado(novoEstadoInicial);
		automatoUniao.addConjuntoEstado(conjuntoEstado1);
		automatoUniao.addConjuntoEstado(conjuntoEstado2);
		automatoUniao.setConjuntoAlfabeto(conjuntoAlfabeto);
		automatoUniao.alterarSimboloDosEstados();
		
		automatoUniao.setNomePai1(automatoOriginal1.getNome());
		automatoUniao.setNomePai2(automatoOriginal2.getNome());
		automatoUniao.setNomeOperacaoGerador("UNIAO");
		
		return automatoUniao;
	}
	
	public static Automato complementarAutomato(Automato automatoOriginal) {
		Automato automatoComplemento;
		automatoComplemento = OperarAutomato.completarAutomato(automatoOriginal.clone());
		automatoComplemento.setNomePai1(automatoOriginal.getNome());
		automatoComplemento.setNomeOperacaoGerador("COMPLEMENTO");
		
		ConjuntoEstado conjuntoEstado;
		conjuntoEstado = automatoComplemento.getConjuntoEstado();
		
		// Inverte os estados (final vira nao final e vice-versa)
		for (int i = 0; i < conjuntoEstado.size(); i++) {
			Estado estado;
			estado = conjuntoEstado.get(i);
			estado.setFinal(!estado.isFinal());
		}
		
		return automatoComplemento;
	}
	
	public static Automato completarAutomato(Automato automatoOriginal) {
		Estado estadoDeErro;
		estadoDeErro = new Estado("simbolo_alterado_posteriormente");
		estadoDeErro.setSimbolo(estadoDeErro.toString());
		
		// AutomatoCompleto eh uma copia do original, porem com as transicoes
		// para o estado morto explicitas
		Automato automatoCompleto;
		automatoCompleto = automatoOriginal.clone();
		automatoCompleto.setNomePai1(automatoOriginal.getNome());
		automatoCompleto.setNomeOperacaoGerador("COMPLETAR");
		
		ConjuntoAlfabeto conjuntoAlfabeto;
		conjuntoAlfabeto = automatoCompleto.getConjuntoAlfabeto();
		
		// Completa o estadoDeErro (estado morto)
		for (int c = 0; c < conjuntoAlfabeto.size(); c++) {
			char simboloDoAlfabeto;
			simboloDoAlfabeto = conjuntoAlfabeto.get(c);
			
			estadoDeErro.addTransicao(simboloDoAlfabeto, estadoDeErro);
		}
		
		ConjuntoEstado conjuntoEstado;
		conjuntoEstado = automatoCompleto.getConjuntoEstado();
		
		for (int c = 0; c < conjuntoAlfabeto.size(); c++) {
			char simboloDoAlfabeto;
			simboloDoAlfabeto = conjuntoAlfabeto.get(c);
			
			for (int i = 0; i < conjuntoEstado.size(); i++) {
				Estado estadoDoAutomatoCompleto;
				estadoDoAutomatoCompleto = conjuntoEstado.get(i);
				
				// Obtem conjunto de transicoes para um dado simbolo do alfabeto
				ConjuntoObject<Transicao> conjuntoTransicao;
				conjuntoTransicao = estadoDoAutomatoCompleto.getConjuntoTransicao(simboloDoAlfabeto);
				
				// Se nao existir transicao entao cria-se a transicao
				// para o estado morto
				if (conjuntoTransicao.size() == 0) {
					estadoDoAutomatoCompleto.addTransicao(simboloDoAlfabeto, estadoDeErro);
					automatoCompleto.addEstado(estadoDeErro);
				}
			}
		}
		
		return automatoCompleto;
	}
}
