package view.component;

import java.util.ArrayList;

public class TableRow {
	private ArrayList<String> columns;
	
	public TableRow() {
		this.columns = new ArrayList<>();
	}
	
	public void addColumn(String column) {
		this.columns.add(column);
	}
	
	public int get_n_column() {
		return this.columns.size();
	}
	
	public Object[] to_object() {
		return this.columns.toArray();
	}
}
