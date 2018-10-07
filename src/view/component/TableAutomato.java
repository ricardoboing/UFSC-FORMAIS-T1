package view.component;

import java.util.ArrayList;

import automato.Automato;
import automato.Estado;
import automato.Transicao;
import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoEstado;
import conjunto.ConjuntoObject;

public class TableAutomato extends Table {
	public TableAutomato() {
		super();
	}
	
	public void setAutomato(Automato automato) {
		ConjuntoAlfabeto conjuntoAlfabeto;
		conjuntoAlfabeto = automato.getConjuntoAlfabeto();
		
		ConjuntoEstado conjuntoEstado;
		conjuntoEstado = automato.getConjuntoEstado();
		
		this.table = new ArrayList<ArrayList<String>>();
		this.numeroColunas = conjuntoAlfabeto.size()+3;
		
		String cabecalho[];
		cabecalho = new String[this.numeroColunas];
		cabecalho[0] = "Inicial";
		cabecalho[1] = "Final";
		cabecalho[2] = "Estado";
		
		for (int c = 0; c < conjuntoEstado.size()+1; c++) {
			ArrayList<String> colunas;
			colunas = new ArrayList<String>();
			
			for (int i = 0; i < this.numeroColunas; i++) {
				colunas.add("");
			}
			
			this.table.add(colunas);
		}
		for (int c = 3; c < this.numeroColunas; c++) {
			cabecalho[c] = "Simbolo";
		}
		
		this.cabecalho = cabecalho;
		
		for (int c = 0; c < conjuntoAlfabeto.size(); c++) {
			String simbolo;
			simbolo = conjuntoAlfabeto.get(c)+"";
			simbolo = simbolo.replaceAll(" ", "");
			
			this.table.get(0).set(c+3, simbolo);
		}
		for (int c = 0; c < conjuntoEstado.size(); c++) {
			Estado estado;
			estado = conjuntoEstado.get(c);
			
			if (estado.isInicial()) {
				this.table.get(c+1).set(0, ">");
			}
			if (estado.isFinal()) {
				this.table.get(c+1).set(1, "*");
			}
			
			this.table.get(c+1).set(2, estado.getSimbolo());
			
			ConjuntoObject<Transicao> conjuntoTransicao;
			conjuntoTransicao = estado.getConjuntoTransicao();
			
			for (int i = 0; i < conjuntoAlfabeto.size(); i++) {
				char simboloAlfabeto;
				simboloAlfabeto = conjuntoAlfabeto.get(i);
				
				for (int j = 0; j < conjuntoTransicao.size(); j++) {
					Transicao transicao;
					transicao = conjuntoTransicao.get(j);
					
					if (transicao.getSimboloEntrada() != simboloAlfabeto) {
						continue;
					}
					
					String celulaTable;
					celulaTable = this.table.get(c+1).get(i+3);
					
					if (!celulaTable.equals("")) {
						celulaTable += ", ";
					}
					celulaTable += transicao.getEstadoDestino().getSimbolo();
					
					this.table.get(c+1).set(i+3, celulaTable);
				}
			}
		}
	}
}
