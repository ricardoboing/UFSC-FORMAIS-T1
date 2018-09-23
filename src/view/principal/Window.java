package view.principal;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import view.EView;
import view.component.MenuBar;
import view.event.EventMenuBar;

public class Window {
	private ManagerInterface managerInterface;
	
	private MenuBar menuBar;
	private JFrame frame;
	private JPanel contentPane;
	
	/* Metodos Construtores */
	public Window() {
		this.managerInterface = new ManagerInterface();
		
		this.loadFrame();
		this.loadContentPane();
		this.loadMenuBar();
		
		this.alterarView(EView.AF_EDITAR);
		this.frame.setVisible(true);
	}
	
	/* Metodos Load */
	private void loadMenuBar() {
		EventMenuBar event;
		event = new EventMenuBar(this);
		
		this.menuBar = new MenuBar(event);
		this.frame.setJMenuBar(this.menuBar.getJMenuBar());
	}
	private void loadContentPane() {
		this.contentPane = new JPanel();
		this.contentPane.setLayout(null);
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setBounds(0, 0, 800, 600);
		
		this.frame.setContentPane(this.contentPane);
	}
	private void loadFrame() {
		this.frame = new JFrame("Trabalho I: Algoritmos para Manipulacao de Linguagens Regulares");
		this.frame.setBounds(10, 10, 820, 620);
		this.frame.setLayout(null);
		this.frame.setResizable(false);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void alterarView(EView eView) {
		JPanel jPanel;
		jPanel = this.managerInterface.alterarView(eView);
		
		this.contentPane.removeAll();
		this.contentPane.add(jPanel);
		this.frame.repaint();
		this.contentPane.repaint();
		jPanel.repaint();
	}
	
	public static void insertMessage(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
