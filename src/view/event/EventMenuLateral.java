package view.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.component.MenuLateral;
import view.component.SwingTable;

public class EventMenuLateral implements MouseListener {
	private MenuLateral menuLateral;
	
	public EventMenuLateral(MenuLateral menuLateral) {
		this.menuLateral = menuLateral;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		SwingTable swingTable;
		swingTable = (SwingTable) e.getComponent();
		
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
