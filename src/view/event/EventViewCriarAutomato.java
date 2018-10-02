package view.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.automato.ViewCriarAutomato;

public class EventViewCriarAutomato implements ActionListener {
	private ViewCriarAutomato view;
	
	public EventViewCriarAutomato(ViewCriarAutomato view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand;
		actionCommand = e.getActionCommand();
		
		switch (actionCommand) {
			case "SALVAR":
				this.view.salvar();
				break;
			case "LIMPAR":
				this.view.limpar();
				break;
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
