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
package janela.component;

import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import janela.event.EventMenuBar;

public class MenuBar {
	private JMenuBar menuBar;
	private EventMenuBar event;
	
	public MenuBar(EventMenuBar event) {
		this.event = event;
		
		this.menuBar = new JMenuBar();
		this.menuBar.setFont(UIManager.getFont("MenuBar.font"));
		this.menuBar.setForeground(Color.BLACK);
		this.menuBar.setBackground(Color.LIGHT_GRAY);
		this.menuBar.setBounds(0, 0, 820, 30);
		
		this.gerarMenuAF();
		this.gerarMenuER();
		this.gerarMenuGR();
	}
	
	/* Metodos Gerador Menu */
	private void gerarMenuAF() {
		JMenuItem criar, editar, operar;
		criar = new JMenuItem("Novo");
		criar.setActionCommand("AF_NOVO");
		criar.addActionListener(this.event);
		
		editar = new JMenuItem("Editar");
		editar.setActionCommand("AF_EDITAR");
		editar.addActionListener(this.event);
		
		operar = new JMenuItem("Operacoes");
		operar.setActionCommand("AF_OPERAR");
		operar.addActionListener(this.event);
		
		JMenu menu;
		menu = new JMenu("Automato");
		menu.add(criar);
		menu.add(editar);
		menu.add(operar);
		
		this.menuBar.add(menu);
	}
	private void gerarMenuGR() {
		JMenuItem criar, editar;
		criar = new JMenuItem("Novo");
		criar.setActionCommand("GR_NOVO");
		criar.addActionListener(this.event);
		
		editar = new JMenuItem("Editar");
		editar.setActionCommand("GR_EDITAR");
		editar.addActionListener(this.event);
		
		JMenu menu;
		menu = new JMenu("Gramatica");
		menu.add(criar);
		menu.add(editar);
		
		this.menuBar.add(menu);
	}
	private void gerarMenuER() {
		JMenuItem criar, editar;
		criar = new JMenuItem("Novo");
		criar.setActionCommand("ER_NOVO");
		criar.addActionListener(this.event);
		
		editar = new JMenuItem("Editar");
		editar.setActionCommand("ER_EDITAR");
		editar.addActionListener(this.event);
		
		JMenu menu;
		menu = new JMenu("Expressao");
		menu.add(criar);
		menu.add(editar);
		
		this.menuBar.add(menu);
	}
	
	/* Metodos Getters */
	public JMenuBar getJMenuBar() {
		return this.menuBar;
	}
}
