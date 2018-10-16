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
package janela.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import janela.automato.ViewOperarAutomato;

public class EventViewOperarAutomato implements ActionListener {
	private ViewOperarAutomato view;
	
	public EventViewOperarAutomato(ViewOperarAutomato view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand;
		actionCommand = e.getActionCommand();
		
		switch (actionCommand) {
			case "AUTOMATO_1":
				this.view.atualizarJCombox1();
				this.view.atualizarMostrarAutomato1();
				
				break;
			case "AUTOMATO_2":
				this.view.atualizarJCombox2();
				this.view.atualizarMostrarAutomato2();
				
				break;
			case "UNIR":
				this.view.unir();
				
				break;
			case "INTERSECTAR":
				this.view.intersectar();
				
				break;
			case "LIMPAR":
				this.view.limpar();
				
				break;
			default:
				break;
		}
		
		this.view.atualizarJButton();
	}
}
