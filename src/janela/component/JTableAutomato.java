package janela.component;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class JTableAutomato extends JTable {
	private boolean editavel;
	
	public JTableAutomato(String corpo[][], String cabecalho[]) {
		super(corpo, cabecalho);
		this.editavel = false;
	}
	
	@Override
	public boolean isCellEditable(int linha, int coluna) {
		// Linha de definicao do cabecalho: "Inicial | Final | NomeDoEstado | ..."
		if (linha == 0 && coluna < 3) {
			return false;
		}
		// Coluna "| Inicial |"
		if (coluna == 0) {
			return false;
		}
		
		return this.editavel;  
	}
	public void setEditavel(boolean editavel) {
		this.editavel = editavel;
	}
	public void definirBackground() {
		this.setDefaultRenderer(Object.class, new TableRenderer());
	}
}

class TableRenderer extends DefaultTableCellRenderer {
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if ((row == 0 && column < 3) || (column == 0)) {
        	comp.setBackground(new Color(238,238,238));
        } else {
        	comp.setBackground(new Color(255,255,255));
        }
        
        return comp;
    }
}