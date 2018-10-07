package view.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.component.ViewTableAutomato;

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
