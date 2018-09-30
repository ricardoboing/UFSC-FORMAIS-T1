package view.component;

import javax.swing.JScrollPane;

import automato.Automato;

public class MostrarAutomato {
	private TextArea textArea;
	private TableAutomato table;
	
	public MostrarAutomato(int x, int y) {
		this.table = new TableAutomato(x, y, 340, 380);
		
		this.textArea = new TextArea(x, y, 340, 380);
		this.textArea.setEnable(false);
		
		this.hideAutomato();
	}
	
	public JScrollPane getAutomatoJScrollPane() {
		return this.table.getJScrollPane();
	}
	
	public void hideAutomato() {
		this.table.setVisible(false);
	}
	public void showAutomato(Automato automato) {
		this.table.montarTable(automato);
		this.table.setVisible(true);
	}
}
