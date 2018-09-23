package view.component;

import java.util.ArrayList;

import javax.swing.JScrollPane;

import automato.Automato;
import automato.Estado;

public class TableAutomato {
	private ViewTable viewTable;
	private String simboloEstadoErro;
	
	public TableAutomato(int x, int y, int width, int height) {
		this.viewTable = new ViewTable(x, y, width, height);
		this.simboloEstadoErro = "-";
	}
	
	public void set_visible(boolean visible) {
		this.viewTable.setVisible(visible);
	}
	
	public JScrollPane get_jScrollPane() {
		return this.viewTable.getJScrollPane();
	}
	
	public void montar_table(Automato automato) {
		
	}
	
	private TableRow montar_row_head(ArrayList<Character> alfabeto) {
		TableRow row_head;
		row_head = new TableRow();
		row_head.addColumn("");
		row_head.addColumn("");
		
		for (int j = 0; j < alfabeto.size(); j++) {
			row_head.addColumn(alfabeto.get(j)+"");
		}
		
		return row_head;
	}
	private TableRow montar_row(Estado estado, ArrayList<Character> alfabeto) {
		String inicialFinal;
		inicialFinal = "";
		
		TableRow row;
		row = new TableRow();
		row.addColumn(inicialFinal);
		
		
		
		
		return row;
	}
}
