package view.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.automato.ViewEditarAutomato;

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
			case "EDITAR":
				this.view.editar();
				break;
			case "CANCELAR":
				this.view.cancelar();
				break;
			case "GERAR_GR":
				this.view.gerarGr();
				break;
			default:
				break;
		}
	}
}
