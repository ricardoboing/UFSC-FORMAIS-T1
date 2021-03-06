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

import janela.automato.ViewEditarAutomato;

public class EventViewEditarAutomato implements ActionListener {
	private ViewEditarAutomato view;
	
	public EventViewEditarAutomato(ViewEditarAutomato view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand;
		actionCommand = e.getActionCommand();
		
		switch (actionCommand) {
			case "DETERMINIZAR":
				this.view.determinizar();
				break;
			case "MINIMIZAR":
				this.view.minimizar();
				break;
			case "SALVAR":
				this.view.salvar();
				break;
			case "GERAR_GR":
				this.view.gerarGr();
				break;
			case "SALVAR_EM_DISCO":
				this.view.salvarEmDisco();
				break;
			case "RECONHECER":
				this.view.reconhecer();
				break;
			case "EDITAR":
				this.view.editar();
				break;
			case "REMOVER":
				this.view.remover();
				break;
			case "CANCELAR":
				this.view.cancelar();
				break;
			default:
				break;
		}
	}
}
