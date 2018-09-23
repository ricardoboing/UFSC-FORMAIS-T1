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
			case "D":
				
				break;
			case "M":
				
				break;
			default:
				break;
		}
	}
}
