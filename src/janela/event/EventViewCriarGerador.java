package janela.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import janela.gerador.ViewCriarGerador;

public class EventViewCriarGerador implements ActionListener {
	private ViewCriarGerador view;
	
	public EventViewCriarGerador(ViewCriarGerador view) {
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
