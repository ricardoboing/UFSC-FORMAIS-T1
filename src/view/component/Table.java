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
	
	public void removeRow(int i) {
		this.body.remove(i);
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
