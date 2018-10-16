/*
 *  Trabalho I: Algoritmos para Manipulacao de Linguagens Regulares
 *  
 *  Departamento de Informatica e Estatistica – Universidade Federal de Santa Catarina (UFSC)
 *  Campus Reitor Joao David Ferreira Lima, 88.040-900 – Florianopolis – SC – Brasil
 *  
 *  brunohonnef@gmail.com pedroabcorte@gmail.com ricardoboing.ufsc@gmail.com
 *  
 *  Bruno Gilmar Honnef
 *  Pedro Alexandre Barradas da Corte
 *  Ricardo do Nascimento Boing
 *  
 *  11 de Outubro de 2018
 */
package janela.principal;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import janela.EView;
import janela.View;
import janela.component.MenuBar;
import janela.event.EventMenuBar;

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
		
		this.alterarView(EView.AF_CRIAR);
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
		View view;
		view = this.managerInterface.alterarView(eView);
		
		JPanel jPanel;
		jPanel = view.getJPanel();
		
		this.contentPane.removeAll();
		this.contentPane.add(jPanel);
		this.frame.repaint();
		this.contentPane.repaint();
		
		jPanel.repaint();
		view.atualizar();
	}
	
	public static void insertMessage(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	public static void insertMessageFalha(String message) {
		JOptionPane.showMessageDialog(null, message, "Falhou!", JOptionPane.WARNING_MESSAGE);
	}
	public static void insertMessageEntradaInvalida() {
		JOptionPane.showMessageDialog(null, "Entrada invalida!", "Falha!", JOptionPane.WARNING_MESSAGE);
	}
}
