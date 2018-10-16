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
		
		/* Caso seja um naoTerminal equivalente, ja existente no conjunto,
		 * mas nao igual, entao copia suas producoes.
		 * Isso pode ocorrer em casos em que um naoTerminal eh criado por
		 * conta de uma producao, e posteriormente eh criado novamente,
		 * ou vice-versa.
		 */
		if (adicionado != naoTerminal) {
			ConjuntoObject<Producao> producoes;
			producoes = naoTerminal.getConjuntoProducao();
			
			adicionado.addConjuntoProducao(producoes);
		}
		
		return adicionado;
	}
	
	@Override
	public ConjuntoNaoTerminal clone() {
		ConjuntoNaoTerminal clone;
		clone = new ConjuntoNaoTerminal();
		
		for (int c = 0; c < this.array.size(); c++) {
			clone.array.add(this.array.get(c));
		}
		
		return clone;
	}
}
