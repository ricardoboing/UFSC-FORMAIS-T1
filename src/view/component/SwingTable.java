package view.component;

import javax.swing.JTable;

public class SwingTable extends JTable {
	private boolean isEditable;
	
	public SwingTable() {
		super();
		this.isEditable = false;
	}
	
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	
	@Override
	public boolean isCellEditable(int r, int c) {
		if (r == 0 && c < 3) {
			return false;
		}
		return this.isEditable;  
	}
}