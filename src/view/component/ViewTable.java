package view.component;

import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewTable {
	protected Table table;
	protected JTable jTable;
	protected JScrollPane scrollPane;
	
	protected MouseListener mouseListener;
	
	/* Metodos Construtor */
	public ViewTable(int x, int y, int width, int height) {
		this.scrollPane = new JScrollPane();
		this.scrollPane.setBounds(x, y, width, height);
		
		this.table = new Table();
		this.recritarJTable();
	}
	protected void recritarJTable() {
		this.jTable = new JTable(this.table.getCorpo(),this.table.getCabecalho()) {
			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		
		this.jTable.setBounds(this.scrollPane.getBounds());
		this.jTable.getTableHeader().setReorderingAllowed(false);
		this.jTable.setCellSelectionEnabled(false);
		
		if (this.mouseListener != null) {
			this.jTable.addMouseListener(this.mouseListener);
		}
		
		this.scrollPane.setViewportView(this.jTable);
	}
	public void repaint() {
		this.jTable.repaint();
		this.scrollPane.repaint();
	}
	
	public void addLinha() {
		this.table.addLinha();
	}
	public void addColuna() {
		this.table.addColuna();
	}
	public void addMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
		this.jTable.addMouseListener(mouseListener);
	}
	
	public void removeUltimaColuna() {
		this.table.removerUltimaColuna();
	}
	public void removeUltimaLinha() {
		this.table.removerUltimaLinha();
	}
	public void removerTodasLinhas() {
		this.table = new Table();
	}
	
	public void setVisible(boolean visible) {
		this.scrollPane.setVisible(visible);
		this.jTable.setVisible(visible);
	}
	public void setHeader(String header[]) {
		this.table.setCabecalho(header);
		this.scrollPane.remove(this.jTable);
		
		this.recritarJTable();
	}
	public void setTable(Table table) {
		this.table = table;
		this.scrollPane.remove(this.jTable);
		this.recritarJTable();
	}
	
	public JScrollPane getJScrollPane() {
		return this.scrollPane;
	}
}
