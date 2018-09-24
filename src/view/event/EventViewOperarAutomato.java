package view.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.automato.ViewOperarAutomato;

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
