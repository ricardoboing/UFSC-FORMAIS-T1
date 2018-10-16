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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import janela.component.MenuLateral;

public class EventMenuLateral implements MouseListener {
	private MenuLateral menuLateral;
	
	public EventMenuLateral(MenuLateral menuLateral) {
		this.menuLateral = menuLateral;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		JTable swingTable;
		swingTable = (JTable) e.getComponent();
		
		int countSelected;
		countSelected = swingTable.getSelectedRow();
		
		String nome;
		nome = (String)swingTable.getValueAt(countSelected, 0);
		
		this.menuLateral.setLinguagem(nome);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
