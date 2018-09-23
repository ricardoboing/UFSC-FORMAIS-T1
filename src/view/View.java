package view;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public abstract class View {
	protected JPanel jPanel;
	
	public View() {
		this.jPanel = new JPanel() {
			@Override
			public void repaint() {
				super.repaint();
				
				Component[] components;
				components = this.getComponents();
				
				for (int c = 0; c < components.length; c++) {
					Component component;
					component = components[c];
					
					component.setVisible(false);
					component.setVisible(true);
				}
			}
		};
		this.jPanel.setLayout(null);
		this.jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.jPanel.setBounds(5, 5, 800, 600);
		this.jPanel.setVisible(true);
	}
	public JPanel getJPanel() {
		return this.jPanel;
	}
	
	public void setLinguagem(String nome) {}
	
	protected JLabel addJLabel(int x, int y, int width, int height, String title) {
		JLabel label;
		label = new JLabel(title);
		label.setBounds(x, y, width, height);
		
		this.jPanel.add(label);
		
		return label;
	}
	protected void addComponent(Component component) {
		this.jPanel.add(component);
	}
}
