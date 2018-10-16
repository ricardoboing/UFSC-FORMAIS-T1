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
