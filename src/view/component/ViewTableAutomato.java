package view.component;

import javax.swing.JButton;
import javax.swing.JPanel;

import automato.Automato;
import automato.Estado;
import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoEstado;
import view.event.EventViewTableAutomato;

public class ViewTableAutomato extends ViewTable {
	private JButton buttonAddEstado, buttonAddSimbolo;
	private JButton buttonRemoverSimbolo, buttonRemoverEstado;
	
	public ViewTableAutomato(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.loadButtons(x, y+height);
	}
	private void loadButtons(int x, int y) {
		EventViewTableAutomato event;
		event = new EventViewTableAutomato(this);
		
		this.buttonRemoverEstado = new JButton("- Estado");
		this.buttonRemoverSimbolo = new JButton("- Simbolo");
		this.buttonAddEstado = new JButton("+ Estado");
		this.buttonAddSimbolo = new JButton("+ Simbolo");
		
		this.buttonRemoverEstado.setActionCommand("REMOVER_ESTADO");
		this.buttonRemoverSimbolo.setActionCommand("REMOVER_SIMBOLO");
		this.buttonAddEstado.setActionCommand("ADICIONAR_ESTADO");
		this.buttonAddSimbolo.setActionCommand("ADICIONAR_SIMBOLO");
		
		this.buttonRemoverEstado.setBounds(x+ 130, y+18, 120, 35);
		this.buttonRemoverSimbolo.setBounds(660, y+18, 120, 35);
		this.buttonAddEstado.setBounds(x, y+18, 120, 35);
		this.buttonAddSimbolo.setBounds(530, y+18, 120, 35);
		
		this.buttonRemoverEstado.addActionListener(event);
		this.buttonRemoverSimbolo.addActionListener(event);
		this.buttonAddEstado.addActionListener(event);
		this.buttonAddSimbolo.addActionListener(event);
	}
	public Automato getAutomato() {
		Automato novoAutomato;
		novoAutomato = new Automato("");
		
		ConjuntoEstado conjuntoEstado;
		conjuntoEstado = novoAutomato.getConjuntoEstado();
		
		ConjuntoAlfabeto conjuntoAlfabeto;
		conjuntoAlfabeto = novoAutomato.getConjuntoAlfabeto();
		
		for (int c = 3; c < this.jTable.getColumnCount(); c++) {
			String simboloAlfabeto;
			simboloAlfabeto = (String)this.jTable.getValueAt(0, c);
			
			if (simboloAlfabeto == null) {
				simboloAlfabeto = " ";
			}
			if (simboloAlfabeto.equals("")) {
				simboloAlfabeto = " ";
			}
			
			conjuntoAlfabeto.add(simboloAlfabeto.charAt(0));
		}
		
		for (int c = 1; c < this.jTable.getRowCount(); c++) {
			String isInicial, isFinal, simboloEstado;
			isInicial = (String)this.jTable.getValueAt(c, 0);
			isFinal = (String)this.jTable.getValueAt(c, 1);
			simboloEstado = (String)this.jTable.getValueAt(c, 2);
			
			if (isInicial == null) {
				isInicial = "";
			}
			if (isFinal == null) {
				isFinal = "";
			}
			if (simboloEstado == null) {
				simboloEstado = "";
			}
			
			System.out.println("adicionou "+simboloEstado+" "+isFinal+" "+isInicial);
			simboloEstado = simboloEstado.replaceAll(" ", "");
			isInicial = isInicial.replaceAll(" ", "");
			isFinal = isFinal.replaceAll(" ", "");
			
			System.out.println("adicionou "+simboloEstado+" "+isFinal+" "+isInicial);
			
			Estado novoEstado;
			novoEstado = new Estado(simboloEstado);
			novoEstado = conjuntoEstado.add(novoEstado);
			novoEstado.setInicial(isInicial.equals(">"));
			novoEstado.setFinal(isFinal.equals("*"));
			
			for (int i = 3; i < this.jTable.getColumnCount(); i++) {
				String simboloEstadoDestino;
				simboloEstadoDestino = (String)this.jTable.getValueAt(c, i);
				
				if (simboloEstadoDestino == null) {
					continue;
				}
				simboloEstadoDestino = simboloEstadoDestino.replaceAll(" ", "");
				if (simboloEstadoDestino.equals("")) {
					continue;
				}
				
				Estado estadoDestino;
				estadoDestino = new Estado(simboloEstadoDestino);
				estadoDestino = conjuntoEstado.add(estadoDestino);
				
				String simboloAlfabeto;
				simboloAlfabeto = (String)this.jTable.getValueAt(0, i);
				
				novoEstado.addTransicao(simboloAlfabeto.charAt(0), estadoDestino);
			}
		}
		
		return novoAutomato;
	}
	@Override
	protected void recritarJTable() {
		this.jTable = new JTableAutomato(
			this.table.getCorpo(),
			this.table.getCabecalho()
		);
		this.jTable.setBounds(this.scrollPane.getBounds());
		this.jTable.getTableHeader().setReorderingAllowed(false);
		this.jTable.setCellSelectionEnabled(false);
		
		this.scrollPane.setViewportView(this.jTable);
	}
	
	public void setEditavel(boolean editavel) {
		JTableAutomato jTableAutomato;
		jTableAutomato = (JTableAutomato) this.jTable;
		jTableAutomato.setEditavel(editavel);
		
		this.buttonAddEstado.setVisible(editavel);
		this.buttonAddSimbolo.setVisible(editavel);
		this.buttonRemoverEstado.setVisible(editavel);
		this.buttonRemoverSimbolo.setVisible(editavel);
	}
	public void definirBackground() {
		JTableAutomato jTableAutomato;
		jTableAutomato = (JTableAutomato) this.jTable;
		jTableAutomato.definirBackground();
	}
	public void definirTamanhoCelula() {
		this.jTable.setRowHeight(20);
	}
	
	public void adicionarButtons(JPanel panel) {
		panel.add(this.buttonAddEstado);
		panel.add(this.buttonAddSimbolo);
		panel.add(this.buttonRemoverEstado);
		panel.add(this.buttonRemoverSimbolo);
	}
	
	public void adicionarEstado() {
		System.out.println("adicionarEstado");
		
		this.atualizarTable();
		this.table.addLinha();
		this.recarregarViewTableAutomato();
	}
	public void adicionarSimbolo() {
		System.out.println("adicionarSimbolo ");
		
		this.atualizarTable();
		this.table.addColuna();
		this.recarregarViewTableAutomato();
	}
	public void removerEstado() {
		System.out.println("removerEstado");
		
		if (this.table.getNumeroLinhas() < 3) {
			return;
		}
		
		this.atualizarTable();
		this.table.removerUltimaLinha();
		this.recarregarViewTableAutomato();
	}
	public void removerSimbolo() {
		System.out.println("removerSimbolo");
		
		if (this.table.getNumeroColunas() < 4) {
			return;
		}
		
		this.atualizarTable();
		this.table.removerUltimaColuna();
		this.recarregarViewTableAutomato();
	}
	
	public void recarregarViewTableAutomato() {
		this.setTable(this.table);
		this.setEditavel(true);
		this.definirTamanhoCelula();
		this.definirBackground();
	}
	private void atualizarTable() {
		Automato automato;
		automato = this.getAutomato();
		
		TableAutomato novoTable;
		novoTable = new TableAutomato();
		novoTable.setAutomato(automato);
		
		this.table = novoTable;
	}
}
