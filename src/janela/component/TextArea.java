package janela.component;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextArea {
	private JTextArea textArea;
	private JScrollPane scrollPane;
	
	public TextArea(int x, int y, int width, int height) {
		this.textArea = new JTextArea();
		this.textArea.setBounds(x, y, width, height);
		
		this.scrollPane = new JScrollPane(this.textArea);
		this.scrollPane.setBounds(x, y, width, height);
	}
	
	public void setText(String text) {
		this.textArea.setText(text);
	}
	public String getText() {
		return this.textArea.getText();
	}
	public void setEnable(boolean enable) {
		this.textArea.setEditable(enable);
		
		if (enable) {
			this.textArea.setBackground(Color.WHITE);
			return;
		}
		
		this.textArea.setBackground(Color.LIGHT_GRAY);
	}
	public void setVisible(boolean visible) {
		this.scrollPane.setVisible(visible);
		this.textArea.setVisible(visible);
	}
	public JTextArea getJTextArea() {
		return this.textArea;
	}
	public JScrollPane getJScrollPane() {
		return this.scrollPane;
	}
}
