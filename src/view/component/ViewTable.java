package view.component;

import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class ViewTable {
	private DefaultTableModel model;
	private SwingTable swingTable;
	private JScrollPane scroll;
	private Table table;
	
	private int x, y;
	private int width, height;
	
	/* Metodos Construtor */
	public ViewTable(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.loadSwingTable();
	}
	
	/* Metodos Load */
	private void loadSwingTable() {
		this.swingTable = new SwingTable();
		this.swingTable.setBounds(this.x, this.y, this.width, this.height);
		this.swingTable.getTableHeader().setReorderingAllowed(false);
		this.swingTable.setCellSelectionEnabled(false);
		
		this.loadScrollPane();
	}
	private void loadScrollPane() {
		this.scroll = new JScrollPane();
		this.scroll.setBounds(this.x, this.y, this.width, this.height);
		this.scroll.setViewportView(this.swingTable);
	}
	public void reloadModel() {
		this.model = new DefaultTableModel(null, this.table.getHead().to_object());
		
		int c;
		for (c = 0; c < this.table.size(); c++) {
			this.model.addRow( table.getBody(c).to_object() );
		}
		
		this.swingTable.setModel(this.model);
	}
	
	public void repaint() {
		this.swingTable.repaint();
		this.scroll.repaint();
	}
	
	/* Metodos Setter */
	public void setTable(Table table) {
		this.table = table;
		this.reloadModel();
	}
	public void addMouseListener(MouseListener mouseListener) {
		this.swingTable.addMouseListener(mouseListener);
	}
	
	public void setVisible(boolean visible) {
		this.scroll.setVisible(visible);
		this.swingTable.setVisible(visible);
	}
	
	/* Metodos Getter */
	public JScrollPane getJScrollPane() {
		return this.scroll;
	}
	public DefaultTableModel getDefaultTableModel() {
		return this.model;
	}
}
