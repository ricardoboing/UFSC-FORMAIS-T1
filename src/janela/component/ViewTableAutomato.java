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

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import automato.Automato;
import automato.Estado;
import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoEstado;
import janela.event.EventViewTableAutomato;
import janela.principal.Window;

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
	public boolean validar() {
		// Verifica preenchimento da coluna de simbolo de estados
		for (int c = 1; c < this.jTable.getRowCount(); c++) {
			String valorColunaFinal;
			valorColunaFinal = (String)this.jTable.getValueAt(c, 1);
			
			if (valorColunaFinal != null) {
				valorColunaFinal = valorColunaFinal.replaceAll(" ", "");
				
				if (!valorColunaFinal.equals("") && !valorColunaFinal.equals("*")) {
					Window.insertMessageFalha("Entrada invalida! A coluna \"Final\" deve estar em branco (estado nao final)\nou contendo o caracter * (estado final)");
					return false;
				}
			}
			
			String valorColunaEstado;
			valorColunaEstado = (String)this.jTable.getValueAt(c, 2);
			
			if (valorColunaEstado == null) {
				Window.insertMessageFalha("Entrada invalida! A coluna \"Estado\" deve ser preenchida!");
				return false;
			}
			valorColunaEstado = valorColunaEstado.replaceAll(" ", "");
			if (valorColunaEstado.equals("")) {
				Window.insertMessageFalha("Entrada invalida! A coluna \"Estado\" deve ser preenchida!");
				return false;
			}
		}
		
		ConjuntoAlfabeto conjuntoAlfabeto;
		conjuntoAlfabeto = new ConjuntoAlfabeto();
		
		// Verifica preenchimento da linha do alfabeto
		for (int c = 3; c < this.jTable.getColumnCount(); c++) {
			String valorSimbolo;
			valorSimbolo = (String)this.jTable.getValueAt(0, c);
			
			if (valorSimbolo == null) {
				Window.insertMessageFalha("Entrada invalida! A coluna \"Simbolo\" deve ser preenchida!");
				continue;
			}
			valorSimbolo = valorSimbolo.replaceAll(" ", "");
			if (valorSimbolo.equals("")) {
				Window.insertMessageFalha("Entrada invalida! A coluna \"Simbolo\" deve ser preenchida!");
				return false;
			}
			if (valorSimbolo.length() != 1) {
				Window.insertMessageFalha("Entrada invalida! A coluna \"Simbolo\" deve conter exatamente um simbolo de entrada!");
				return false;
			}
			if (conjuntoAlfabeto.contains(valorSimbolo.charAt(0))) {
				Window.insertMessageFalha("Entrada invalida! A coluna \"Simbolo\" deve ser preenchida com simbolos nao repetidos!");
				return false;
			}
			
			conjuntoAlfabeto.add(valorSimbolo.charAt(0));
		}
		
		ConjuntoEstado conjuntoEstado;
		conjuntoEstado = new ConjuntoEstado();
		
		// Verifica se existem dois estados com mesmo simbolo
		for (int c = 1; c < this.jTable.getRowCount(); c++) {
			String simboloEstado;
			simboloEstado = (String)this.jTable.getValueAt(c, 2);
			
			Estado novoEstado;
			novoEstado = new Estado(simboloEstado);
			
			// Estado duplicado
			if (conjuntoEstado.contains(novoEstado)) {
				Window.insertMessageFalha("Entrada invalida! Existem um ou mais estados com o mesmo simbolo/nome!");
				return false;
			}
			
			novoEstado = conjuntoEstado.add(novoEstado);
		}
		
		// Verifica se os estados transitam para estados validos/existentes
		for (int c = 1; c < this.jTable.getRowCount(); c++) {
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
				
				String[] arraySimboloEstadoDestino;
				arraySimboloEstadoDestino = simboloEstadoDestino.split(",");
				
				for (int j = 0; j < arraySimboloEstadoDestino.length; j++) {
					Estado estadoDestino;
					estadoDestino = new Estado(arraySimboloEstadoDestino[j]);
					if (!conjuntoEstado.contains(estadoDestino)) {
						Window.insertMessageFalha("Entrada invalida! Os estados devem transitar para estados existentes!");
						return false;
					}
				}
			}
		}
		
		return true;
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
			
			if (novoEstado.isInicial()) {
				novoAutomato.setEstadoInicial(novoEstado);
			}
			
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
				
				String[] arraySimboloEstadoDestino;
				arraySimboloEstadoDestino = simboloEstadoDestino.split(",");
				
				for (int j = 0; j < arraySimboloEstadoDestino.length; j++) {
					Estado estadoDestino;
					estadoDestino = new Estado(arraySimboloEstadoDestino[j]);
					estadoDestino = conjuntoEstado.add(estadoDestino);
					
					String simboloAlfabeto;
					simboloAlfabeto = (String)this.jTable.getValueAt(0, i);
					
					novoEstado.addTransicao(simboloAlfabeto.charAt(0), estadoDestino);
				}
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
		this.jTable.getTableHeader().setResizingAllowed(false);
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
		String cabecalho[];
		cabecalho = new String[this.jTable.getColumnCount()];
		cabecalho[0] = "Inicial";
		cabecalho[1] = "Final";
		cabecalho[2] = "Estado";
		
		for (int c = 3; c < cabecalho.length; c++) {
			cabecalho[c] = "Simbolo";
		}
		
		Table table;
		table = new Table();
		table.setCabecalho(cabecalho);
		
		for (int c = 0; c < this.jTable.getRowCount(); c++) {
			ArrayList<String> colunas;
			colunas = new ArrayList<String>();
			
			for (int i = 0; i < this.jTable.getColumnCount(); i++) {
				String valorColuna;
				valorColuna = (String)this.jTable.getValueAt(c, i);
				
				if (valorColuna == null) {
					valorColuna = "";
				}
				
				colunas.add(valorColuna);
			}
			
			table.addLinha(colunas.toArray());
		}
		
		this.table = table;
		this.recarregarViewTableAutomato();
	}
	public void limpar() {
		String cabecalho[];
		cabecalho = new String[] {
			"Inicial", "Final", "Estado"
		};
		String[] linhaAlfabeto, linhaEstado1;
		linhaAlfabeto = new String[] {
			"", "", ""
		};
		linhaEstado1 = new String[] {
			">", "", "q0"
		};
		
		Table table;
		table = new Table();
		table.setCabecalho(cabecalho);
		table.addLinha(linhaAlfabeto);
		table.addLinha(linhaEstado1);
		
		this.table = table;
		this.recarregarViewTableAutomato();
	}
}
