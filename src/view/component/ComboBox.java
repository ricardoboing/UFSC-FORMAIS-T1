package view.component;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;

import util.Linguagem;

public class ComboBox {
	private JComboBox<String> jComboBox;
	
	public ComboBox(int x, int y, String actionCommand, ActionListener event) {
		this.jComboBox = new JComboBox<String>();
		this.jComboBox.setBounds(x, y, 240, 40);
		this.jComboBox.addActionListener(event);
		this.jComboBox.setActionCommand(actionCommand);
	}
	
	public void loadDefaultDisabled() {
		this.loadDefault();
		this.jComboBox.setEnabled(false);
	}
	
	public void loadDefault() {
		this.jComboBox.removeAllItems();
		this.jComboBox.addItem("Selecione");
	}
	public void load() {
		this.jComboBox.addItem("");
	}
	
	public void setEnable(boolean enable) {
		this.jComboBox.setEnabled(enable);
	}
	public void setItensLinguagem(ArrayList<Linguagem> arrayAutomato) {
		this.loadDefault();
		
		if (arrayAutomato == null) {
			return;
		}
		
		for(int c = 0; c < arrayAutomato.size(); c++) {
			Linguagem linguagem;
			linguagem = arrayAutomato.get(c);
			
			this.jComboBox.addItem(linguagem.getNome());
		}
	}
	
	public void selecionar(int index) {
		this.jComboBox.setSelectedIndex(index);
	}
	public String getSelected() {
		return (String)this.jComboBox.getSelectedItem();
	}
	public int getIndiceSelected() {
		return this.jComboBox.getSelectedIndex();
	}
	public JComboBox<String> getJComboBox() {
		return this.jComboBox;
	}
	public boolean valido() {
		return (this.getIndiceSelected() > 0);
	}
}
