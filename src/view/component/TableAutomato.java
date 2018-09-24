package view.component;

import javax.swing.JScrollPane;

import automato.Automato;
import automato.Estado;
import automato.Transicao;
import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoEstado;
import conjunto.ConjuntoObject;

public class TableAutomato {
	private ViewTable viewTable;
	private String simboloEstadoErro;
	
	public TableAutomato(int x, int y, int width, int height) {
		this.viewTable = new ViewTable(x, y, width, height);
		this.simboloEstadoErro = "-";
	}
	
	public void setVisible(boolean visible) {
		this.viewTable.setVisible(visible);
	}
	
	public JScrollPane getjScrollPane() {
		return this.viewTable.getJScrollPane();
	}
	
	public void montarTable(Automato automato) {
		ConjuntoEstado estados;
		estados = automato.getConjuntoEstado();
		
		ConjuntoAlfabeto alfabeto;
		alfabeto = automato.getConjuntoAlfabeto();
		
		TableRow row_head;
		row_head = this.montarRowHead(alfabeto);
		
		TableRow row_inicial;
		row_inicial = this.montarRow(automato.getEstadoInicial(), alfabeto);
		
		Table table;
		table = new Table(row_head);
		table.addRow(row_inicial);
		
		for (int i = 0; i < estados.size(); i++) {
			Estado estado;
			estado = estados.get(i);
			
			if (estado.isInicial()) {
				continue;
			}
			
			TableRow row;
			row = this.montarRow(estado, alfabeto);
			
			table.addRow(row);
		}
		
		this.viewTable.setTable(table);
	}
	
	private TableRow montarRowHead(ConjuntoAlfabeto alfabeto) {
		TableRow row_head;
		row_head = new TableRow();
		row_head.addColumn("");
		row_head.addColumn("𝛅");
		
		for (int j = 0; j < alfabeto.size(); j++) {
			row_head.addColumn(alfabeto.get(j)+"");
		}
		
		return row_head;
	}
	private TableRow montarRow(Estado estado, ConjuntoAlfabeto alfabeto) {
		String inicialFinal;
		inicialFinal = "";
		
		if (estado.isFinal()) {
			inicialFinal += "*";
		}
		if (estado.isInicial()) {
			inicialFinal += "->";
		}
		
		TableRow row;
		row = new TableRow();
		row.addColumn(inicialFinal);
		row.addColumn(estado.getSimbolo());
		
		for (int i = 0; i < alfabeto.size(); i++) {
			char simboloAlfabeto;
			simboloAlfabeto = alfabeto.get(i);
			
			boolean possuiTransicao;
			possuiTransicao = false;
			
			ConjuntoObject<Transicao> conjuntoTransicao;
			conjuntoTransicao = estado.getConjuntoTransicao();
			
			for (int j = 0; j < conjuntoTransicao.size(); j++) {
				Transicao transicao;
				transicao = conjuntoTransicao.get(j);
				
				if (transicao.getSimboloEntrada() == simboloAlfabeto) {
					Estado destino;
					destino = transicao.getEstadoDestino();
					
					row.addColumn(destino.getSimbolo());
					possuiTransicao = true;
					break;
				}
			}
			
			if (!possuiTransicao) {
				row.addColumn(this.simboloEstadoErro);
			}
		}
		
		return row;
	}
}
