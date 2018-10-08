package janela.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import janela.automato.ViewCriarAutomato;

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
			default:
				break;
		}
	}
}
