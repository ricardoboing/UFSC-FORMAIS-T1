package view.component;

import java.util.ArrayList;

import javax.swing.JPanel;

import util.Linguagem;
import view.IViewEditar;
import view.event.EventMenuLateral;

public class MenuLateral {
	private JPanel panel;
	private ViewTable viewTable;
	
	private Table table;
	
	private IViewEditar view;
	
	public MenuLateral(IViewEditar view) {
		this.view = view;
		
		EventMenuLateral event;
		event = new EventMenuLateral(this);
		
		this.viewTable = new ViewTable(0, 0, 200, 540);
		this.viewTable.addMouseListener(event);
		
		TableRow rowHead;
		rowHead = new TableRow();
		rowHead.addColumn("Menu");
		
		this.table = new Table(rowHead);
		
		this.panel = new JPanel();
		this.panel.setLayout(null);
		this.panel.setBounds(10, 20, 200, 560);
		
		this.load();
		
		
		TableRow row;
		row = new TableRow();
		row.addColumn("oi");
		this.table.addRow(row);
		this.viewTable.reloadModel();
	}
	
	private void load() {
		this.table.reload();
		this.viewTable.setTable(this.table);
		this.panel.add(this.viewTable.getJScrollPane());
	}
	private void reload() {
		if (this.viewTable != null) {
			this.panel.remove(this.viewTable.getJScrollPane());
		}
		
		this.load();
	}
	
	/* Metodos Setter */
	public void setMenu(ArrayList<Linguagem> array) {
		this.reload();
		
		for (int c = 0; c < array.size(); c++) {
			Linguagem linguagem;
			linguagem = array.get(c);
			
			TableRow row;
			row = new TableRow();
			row.addColumn(linguagem.getNome());
			
			this.table.addRow(row);
		}
		
		this.viewTable.reloadModel();
	}
	public void setLinguagem(String nome) {
		this.view.setLinguagem(nome);
	}
	
	/* Metodos Getter */
	public JPanel getJPanel() {
		return this.panel;
	}
	
	public void repaint() {
		this.panel.repaint();
		this.viewTable.repaint();
	}
}
