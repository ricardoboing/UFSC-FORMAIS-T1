package janela.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import janela.gerador.ViewEditarGerador;

public class EventViewEditarGerador implements ActionListener {
	private ViewEditarGerador view;
	
	public EventViewEditarGerador(ViewEditarGerador view) {
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
			case "EDITAR":
				this.view.editar();
				
				break;
			case "CANCELAR":
				this.view.cancelar();
				
				break;
			case "GERAR_AF":
				this.view.gerarAf();
				
				break;
			case "REMOVER":
				this.view.remover();
				break;
			default:
				break;
		}
	}
	
}