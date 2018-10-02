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
	public void removeLastColumn() {
		if (this.columns.size() > 0) {
			this.columns.remove(this.columns.size()-1);
		}
	}
	public void removeAllColumn() {
		while (this.columns.size() > 0) {
			this.columns.remove(0);
		}
	}
	
	public String getColumn(int i) {
		return this.columns.get(i);
	}
	public int size() {
		return this.columns.size();
	}
	
	public Object[] to_object() {
		return this.columns.toArray();
	}
}
