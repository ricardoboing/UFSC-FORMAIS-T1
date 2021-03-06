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
			case "SELECIONAR_ARQUIVO":
				this.view.selecionarArquivo();
				break;
			default:
				break;
		}
	}
}
