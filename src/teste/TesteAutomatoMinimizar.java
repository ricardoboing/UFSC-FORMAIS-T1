package teste;

import org.junit.Before;
import org.junit.Test;

import automato.Automato;
import automato.Estado;
import automato.OperarAutomato;

public class TesteAutomatoMinimizar {
	@Before
	public void setup() {}
	
	//@Test
	public void eliminarEstadosInalcansaveis1() {
		Automato automatoAFND;
		automatoAFND = new Automato();
		automatoAFND.addEntradaAlfabeto('0');
		automatoAFND.addEntradaAlfabeto('1');
		
		Estado estadoS, estadoA, estadoB, estadoC, estadoD, estadoF, estadoG, estadoH;
		estadoS = new Estado("S");
		estadoA = new Estado("A");
		estadoB = new Estado("B");
		estadoC = new Estado("C");
		estadoD = new Estado("D");
		estadoF = new Estado("F");
		estadoG = new Estado("G");
		estadoH = new Estado("H");
		
		automatoAFND.setEstadoInicial(estadoS);
		automatoAFND.addEstado(estadoA);
		automatoAFND.addEstado(estadoG);
		automatoAFND.addEstado(estadoB);
		automatoAFND.addEstado(estadoC);
		automatoAFND.addEstado(estadoD);
		automatoAFND.addEstado(estadoF);
		automatoAFND.addEstado(estadoG);
		automatoAFND.addEstado(estadoH);
		
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
		
		OperarAutomato operar;
		operar = new OperarAutomato();
		automatoAFD = operar.eliminarEstadosInalcansaveis(automatoAFND);
		
		automatoAFND.print();
		System.out.println("\n--------------------\n");
		automatoAFD.print();
	}
	
	@Test
	public void eliminarEstadosMortos1() {
		Automato automatoAFND;
		automatoAFND = new Automato();
		automatoAFND.addEntradaAlfabeto('0');
		automatoAFND.addEntradaAlfabeto('1');
		
		Estado estadoS, estadoA, estadoB, estadoC, estadoD, estadoF, estadoG, estadoH;
		estadoS = new Estado("S");
		estadoA = new Estado("A");
		estadoB = new Estado("B");
		estadoC = new Estado("C");
		estadoD = new Estado("D");
		estadoF = new Estado("F");
		estadoG = new Estado("G");
		estadoH = new Estado("H");
		
		automatoAFND.setEstadoInicial(estadoS);
		automatoAFND.addEstado(estadoA);
		automatoAFND.addEstado(estadoG);
		automatoAFND.addEstado(estadoB);
		automatoAFND.addEstado(estadoC);
		automatoAFND.addEstado(estadoD);
		automatoAFND.addEstado(estadoF);
		automatoAFND.addEstado(estadoG);
		automatoAFND.addEstado(estadoH);
		
		estadoS.addTransicao('0', estadoF);
		estadoS.addTransicao('1', estadoA);
		
		estadoA.addTransicao('0', estadoB);
		estadoA.addTransicao('1', estadoD);
		
		estadoB.addTransicao('0', estadoC);
		estadoB.addTransicao('1', estadoB);
		
		estadoC.addTransicao('0', estadoB);
		estadoC.addTransicao('1', estadoC);
		
		estadoD.addTransicao('0', estadoB);
		estadoD.addTransicao('1', estadoF);
		
		estadoD.setFinal(true);
		estadoF.setFinal(true);
		
		Automato automatoAFD;
		
		OperarAutomato operar;
		operar = new OperarAutomato();
		automatoAFD = operar.eliminarEstadosMortos(automatoAFND);
		
		automatoAFND.print();
		System.out.println("\n--------------------\n");
		automatoAFD.print();
	}
}
