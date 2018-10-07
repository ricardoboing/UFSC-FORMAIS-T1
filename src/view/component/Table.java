package view.component;

import java.util.ArrayList;

public class Table {
	protected ArrayList<ArrayList<String>> table;
	protected String[] cabecalho;
	protected int numeroColunas;
	
	public Table() {
		this.cabecalho = new String[0];
		this.numeroColunas = 1;
		this.table = new ArrayList<ArrayList<String>>();
	}
	
	public void addLinha() {
		this.addLinha(null);
	}
	public void addLinha(String conteudo[]) {
		ArrayList<String> novaColuna;
		novaColuna = new ArrayList<String>();
		
		for (int c = 0; c < this.numeroColunas; c++) {
			String valor;
			valor = null;
			
			if (conteudo != null) {
				valor = conteudo[c];
			}
			novaColuna.add(valor);
		}
		
		this.table.add(novaColuna);
	}
	public void addColuna() {
		String novoCabecalho[];
		novoCabecalho = new String[this.numeroColunas+1];
		
		for (int c = 0; c < numeroColunas; c++) {
			novoCabecalho[c] = this.cabecalho[c];
		}
		novoCabecalho[this.numeroColunas] = "Simbolo";
		
		for (int c = 0; c < this.table.size(); c++) {
			this.table.get(c).add("");
		}
		
		this.numeroColunas++;
		this.cabecalho = novoCabecalho;
	}
	
	
	public void removerUltimaColuna() {
		if (this.numeroColunas <= 3) {
			return;
		}
		
		String novoCabecalho[];
		novoCabecalho = new String[this.numeroColunas-1];
		
		for (int c = 0; c < numeroColunas-1; c++) {
			novoCabecalho[c] = this.cabecalho[c];
		}
		this.cabecalho = novoCabecalho;
		
		for (int c = 0; c < this.table.size(); c++) {
			this.table.get(c).remove(this.numeroColunas-1);
		}
		this.numeroColunas--;
	}
	public void removerUltimaLinha() {
		if (this.table.size() > 0) {
			this.table.remove(this.table.size()-1);
		}
	}
	public String[][] getCorpo() {
		String[][] corpo;
		corpo = new String[this.table.size()][this.numeroColunas];
		
		for (int c = 0; c < this.table.size(); c++) {
			for (int i = 0; i < this.numeroColunas; i++) {
				corpo[c][i] = this.table.get(c).get(i);
			}
		}
		
		return corpo;
	}
	
	public void setCabecalho(String[] cabecalho) {
		this.cabecalho = cabecalho;
		this.numeroColunas = cabecalho.length;
	}
	public String[] getCabecalho() {
		return this.cabecalho;
	}
	public int getNumeroLinhas() {
		return this.table.size();
	}
	public int getNumeroColunas() {
		return this.numeroColunas;
	}
}
