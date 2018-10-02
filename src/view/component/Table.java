package view.component;

import java.util.ArrayList;

public class Table {
	private TableRow head;
	private ArrayList<TableRow> body;
	
	/* Metodos Construtor */
	public Table(TableRow head) {
		this.head = head;
		this.reload();
	}
	public void reload() {
		this.body = new ArrayList<TableRow>();
	}
	
	/* Metodos add/remove */
	public void addRow(TableRow row) {
		this.body.add(row);
	}
	public void addColumn(String column) {
		for (int c = 0; c < this.body.size(); c++) {
			this.body.get(c).addColumn(column);
		}
		this.head.addColumn("Entrada");
	}
	public void removeLastRow() {
		if (this.body.size() > 0) {
			this.body.remove(this.body.size()-1);
		}
	}
	public void removeLastColumn() {
		for (int c = 0; c < this.body.size(); c++) {
			this.body.get(c).removeLastColumn();
		}
		this.head.removeLastColumn();
	}
	public void removeAllRow() {
		while (this.body.size() > 0) {
			this.body.remove(0);
		}
	}
	public void removeAllColumn() {
		for (int c = 0; c < this.body.size(); c++) {
			this.body.get(c).removeAllColumn();
		}
		this.head.removeAllColumn();
	}
	
	/* Metodos Getter */
	public TableRow getHead() {
		return this.head;
	}
	public TableRow getBody(int i) {
		return this.body.get(i);
	}
	public int size() {
		return this.body.size();
	}
}
