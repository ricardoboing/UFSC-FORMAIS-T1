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

import janela.component.ViewTableAutomato;

public class EventViewTableAutomato implements ActionListener {
	private ViewTableAutomato view;
	
	public EventViewTableAutomato(ViewTableAutomato view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand;
		actionCommand = e.getActionCommand();
		
		switch (actionCommand) {
			case "ADICIONAR_SIMBOLO":
				this.view.adicionarSimbolo();
				break;
			case "REMOVER_SIMBOLO":
				this.view.removerSimbolo();
				break;
			case "ADICIONAR_ESTADO":
				this.view.adicionarEstado();
				break;
			case "REMOVER_ESTADO":
				this.view.removerEstado();
				break;
			default:
				break;
		}
	}
}
