package view.component;

import javax.swing.JTable;

public class SwingTable extends JTable {
	
	public SwingTable() {
		super();
	}
	
	@Override
	public boolean isCellEditable(int r, int c) {  
		return false;  
	}
}