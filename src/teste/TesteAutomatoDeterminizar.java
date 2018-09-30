package teste;

import org.junit.Before;
import org.junit.Test;

import automato.Automato;
import automato.Estado;
import automato.OperarAutomato;
import conjunto.ConjuntoEstado;

public class TesteAutomatoDeterminizar {
	@Before
	public void setup() {
		
	}
	
	//@Test
	public void determinizar1() {
		Automato automatoAFND;
		automatoAFND = new Automato();
		automatoAFND.addEntradaAlfabeto('0');
		automatoAFND.addEntradaAlfabeto('1');
		
		Estado estadoS, estadoA, estadoB, estadoC, estadoD, estadoF;
		estadoS = new Estado("S");
		estadoA = new Estado("A");
		estadoB = new Estado("B");
		estadoC = new Estado("C");
		estadoD = new Estado("D");
		estadoF = new Estado("F");
		
		automatoAFND.setEstadoInicial(estadoS);
		automatoAFND.addEstado(estadoA);
		automatoAFND.addEstado(estadoB);
		automatoAFND.addEstado(estadoC);
		automatoAFND.addEstado(estadoD);
		automatoAFND.addEstado(estadoF);
		
		estadoS.addTransicao('0', estadoS);
		estadoS.addTransicao('0', estadoF);
		estadoS.addTransicao('1', estadoA);
		
		estadoA.addTransicao('0', estadoB);
		estadoA.addTransicao('1', estadoC);
		
		estadoB.addTransicao('0', estadoD);
		estadoB.addTransicao('1', estadoS);
		estadoB.addTransicao('1', estadoF);
		
		estadoC.addTransicao('0', estadoA);
		estadoC.addTransicao('1', estadoB);
		
		estadoD.addTransicao('0', estadoC);
		estadoD.addTransicao('1', estadoD);
		
		estadoF.setFinal(true);
		estadoD.setFinal(true);
		
		Automato automatoAFD;
		automatoAFD = OperarAutomato.determinizar(automatoAFND);
		
		automatoAFND.print();
		System.out.println("\n--------------------\n");
		automatoAFD.print();
	}
	//@Test
	public void determinizar2() {
		Automato automatoAFND;
		automatoAFND = new Automato();
		automatoAFND.addEntradaAlfabeto('a');
		automatoAFND.addEntradaAlfabeto('&');
		
		Estado estadoS, estadoF;
		estadoS = new Estado("q0");
		estadoF = new Estado("q1");
		
		automatoAFND.setEstadoInicial(estadoS);
		automatoAFND.addEstado(estadoF);
		
		estadoS.addTransicao('&', estadoF);
		estadoS.addTransicao('a', estadoS);
		
		estadoF.setFinal(true);
		
		Automato automatoAFD;
		automatoAFD = OperarAutomato.determinizar(automatoAFND);
		
		automatoAFND.print();
		System.out.println("\n--------------------\n");
		automatoAFD.print();
	}
	@Test
	public void determinizar3() {
		Automato automatoAFND;
		automatoAFND = new Automato();
		automatoAFND.addEntradaAlfabeto('a');
		automatoAFND.addEntradaAlfabeto('b');
		automatoAFND.addEntradaAlfabeto('c');
		automatoAFND.addEntradaAlfabeto('&');
		
		Estado estadoP, estadoQ, estadoR;
		estadoP = new Estado("P");
		estadoQ = new Estado("Q");
		estadoR = new Estado("R");
		
		automatoAFND.setEstadoInicial(estadoP);
		automatoAFND.addEstado(estadoQ);
		automatoAFND.addEstado(estadoR);
		
		estadoP.addTransicao('b', estadoQ);
		estadoP.addTransicao('c', estadoR);
		estadoP.addTransicao('&', estadoP);
		estadoP.addTransicao('&', estadoQ);
		
		estadoQ.addTransicao('a', estadoP);
		estadoQ.addTransicao('b', estadoR);
		estadoQ.addTransicao('c', estadoP);
		estadoQ.addTransicao('c', estadoQ);
		
		estadoR.setFinal(true);
		
		Automato automatoAFD;
		automatoAFD = OperarAutomato.determinizar(automatoAFND);
		
		automatoAFND.print();
		System.out.println("\n--------------------\n");
		automatoAFD.print();
	}
	//@Test
	public void gerarConjuntoEpsilonFecho() {
		Automato automatoAFND;
		automatoAFND = new Automato();
		automatoAFND.addEntradaAlfabeto('a');
		automatoAFND.addEntradaAlfabeto('&');
		
		Estado estadoP, estadoQ, estadoR;
		estadoP = new Estado("P");
		estadoQ = new Estado("Q");
		estadoR = new Estado("R");
		
		automatoAFND.setEstadoInicial(estadoP);
		automatoAFND.addEstado(estadoQ);
		automatoAFND.addEstado(estadoR);
		
		estadoP.addTransicao('b', estadoQ);
		estadoP.addTransicao('c', estadoR);
		//estadoP.addTransicao('&', estadoP);
		estadoP.addTransicao('&', estadoQ);
		
		estadoQ.addTransicao('a', estadoP);
		estadoQ.addTransicao('b', estadoR);
		estadoQ.addTransicao('c', estadoP);
		estadoQ.addTransicao('c', estadoQ);
		
		//estadoQ.addTransicao('&', estadoP);
		//estadoQ.addTransicao('&', estadoQ);
		estadoQ.addTransicao('&', estadoR);
		estadoR.addTransicao('&', estadoP);
		
		estadoR.setFinal(true);
		
		OperarAutomato.gerarConjuntoEpsilonFecho(automatoAFND);
		
		ConjuntoEstado conjuntoEstado;
		conjuntoEstado = automatoAFND.getConjuntoEstado();
		
		for (int c = 0; c < conjuntoEstado.size(); c++) {
			Estado estado;
			estado = conjuntoEstado.get(c);
			
			ConjuntoEstado conjuntoEpsilonFecho;
			conjuntoEpsilonFecho = estado.getConjuntoEpsilonFecho();
			
			System.out.print(estado.getSimbolo()+": {");
			
			for (int i = 0; i < conjuntoEpsilonFecho.size(); i++) {
				Estado estadoEpsilonFecho;
				estadoEpsilonFecho = conjuntoEpsilonFecho.get(i);
				
				if (i != 0) {
					System.out.print(", ");
				}
				
				System.out.print(estadoEpsilonFecho.getSimbolo());
			}
			
			System.out.println("}");
		}
		
		automatoAFND.print();
		System.out.println("\n--------------------\n");
	}
	//@Test
	public void conjuntoEstadoToString() {
		ConjuntoEstado conjuntoEstado;
		conjuntoEstado = new ConjuntoEstado();
		conjuntoEstado.add(new Estado("q0"));
		conjuntoEstado.add(new Estado("q1"));
		conjuntoEstado.add(new Estado("q2"));
		conjuntoEstado.add(new Estado("q3"));
		conjuntoEstado.add(new Estado("q4"));
		
		System.out.println(conjuntoEstado.getToString());
	}
}
