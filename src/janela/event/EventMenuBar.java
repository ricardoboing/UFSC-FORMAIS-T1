package janela.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import janela.EView;
import janela.principal.Window;

public class EventMenuBar implements ActionListener {
	private Window window;
	
	public EventMenuBar(Window window) {
		this.window = window;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand;
		actionCommand = e.getActionCommand();
		
		switch (actionCommand) {
			/* AUTOMATO */
			case "AF_NOVO":
				this.window.alterarView(EView.AF_CRIAR);
				break;
			case "AF_EDITAR":
				this.window.alterarView(EView.AF_EDITAR);
				break;
			case "AF_OPERAR":
				this.window.alterarView(EView.AF_OPERAR);
				break;
				
			// GRAMATICA
			case "GR_NOVO":
				this.window.alterarView(EView.GR_CRIAR);
				break;
			case "GR_EDITAR":
				this.window.alterarView(EView.GR_EDITAR);
				break;
			
			// EXPRESSAO
			case "ER_NOVO":
				this.window.alterarView(EView.ER_CRIAR);
				break;
			case "ER_EDITAR":
				this.window.alterarView(EView.ER_EDITAR);
				break;
			default:
				this.window.alterarView(EView.INICIO);
				break;
		}
	}
}
