package janela.component;

import java.util.ArrayList;

import javax.swing.JPanel;

import janela.IViewEditar;
import janela.event.EventMenuLateral;
import util.Linguagem;

public class MenuLateral {
	private JPanel panel;
	private ViewTable viewTable;
	
	private IViewEditar view;
	
	public MenuLateral(IViewEditar view) {
		this.view = view;
		
		EventMenuLateral event;
		event = new EventMenuLateral(this);
		
		this.viewTable = new ViewTable(0, 0, 200, 540);
		this.viewTable.addMouseListener(event);
		this.viewTable.setHeader(new String[] {"Menu"});
		
		this.panel = new JPanel();
		this.panel.setLayout(null);
		this.panel.setBounds(10, 20, 200, 560);
		
		this.load();
	}
	
	private void load() {
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
		
		this.viewTable.removerTodasLinhas();
		
		Table table;
		table = new Table();
		table.setCabecalho(new String[] {
			"Menu"
		});
		
		for (int c = 0; c < array.size(); c++) {
			Linguagem linguagem;
			linguagem = array.get(c);
			
			table.addLinha(new String[] {
				linguagem.getNome()
			});
		}
		
		this.viewTable.setTable(table);
		this.repaint();
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
