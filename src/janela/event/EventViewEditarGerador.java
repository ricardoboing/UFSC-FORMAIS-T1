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
			case "SALVAR_EM_DISCO":
				this.view.salvarEmDisco();
				break;
			default:
				break;
		}
	}
	
}
