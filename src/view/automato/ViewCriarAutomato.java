package view.automato;

import javax.swing.JButton;
import javax.swing.JTextField;

import automato.Automato;
import automato.Estado;
import view.View;
import view.component.Table;
import view.component.TableRow;
import view.component.ViewTable;
import view.event.EventViewCriarAutomato;
import view.principal.ManagerLinguagem;
import view.principal.Window;

public class ViewCriarAutomato extends View {
	private ManagerLinguagem managerLinguagem;
	private ViewTable tableAutomato;
	
	private JTextField inputNome;
	private JButton buttonSalvar, buttonLimpar;
	private JButton buttonAdicionarEstado, buttonRemoverEstado;
	private JButton buttonAdicionarSimbolo, buttonRemoverSimbolo;
	
	private int numeroColunas;
	
	public ViewCriarAutomato(ManagerLinguagem managerLinguagem) {
		super();
		this.managerLinguagem = managerLinguagem;
		
		this.inputNome = new JTextField();
		this.inputNome.setEditable(false);
		this.inputNome.setBounds(100, 20, 200, 25);
		
		this.loadTable();
		this.loadButton();
		
		this.addJLabel(10, 20, 70, 25, "Nome:");
		
		this.addComponent(this.inputNome);
		this.addComponent(this.tableAutomato.getJScrollPane());
	}
	private void loadTable() {
		this.numeroColunas = 4;
		
		TableRow tableRowHead;
		tableRowHead = new TableRow();
		tableRowHead.addColumn("Inicial");
		tableRowHead.addColumn("Final");
		tableRowHead.addColumn("Estado");
		tableRowHead.addColumn("Entrada");
		
		TableRow row1;
		row1 = new TableRow();
		row1.addColumn("");
		row1.addColumn("");
		row1.addColumn("");
		row1.addColumn("");
		
		TableRow row2;
		row2 = new TableRow();
		row2.addColumn("");
		row2.addColumn("");
		row2.addColumn("");
		row2.addColumn("");
		
		Table table = new Table(tableRowHead);
		table.addRow(row1);
		table.addRow(row2);
		
		this.tableAutomato = new ViewTable(100, 62, 680, 370);
		this.tableAutomato.setTable(table);
		this.tableAutomato.setEditable(true);
	}
	private void loadButton() {
		EventViewCriarAutomato event;
		event = new EventViewCriarAutomato(this);
		
		this.buttonSalvar = new JButton("Salvar");
		this.buttonLimpar = new JButton("Limpar");
		this.buttonAdicionarEstado = new JButton("+ Estado");
		this.buttonRemoverEstado = new JButton("- Estado");
		this.buttonAdicionarSimbolo = new JButton("+ Simbolo");
		this.buttonRemoverSimbolo = new JButton("- Simbolo");
		
		this.buttonSalvar.setActionCommand("SALVAR");
		this.buttonLimpar.setActionCommand("LIMPAR");
		this.buttonAdicionarEstado.setActionCommand("ADICIONAR_ESTADO");
		this.buttonRemoverEstado.setActionCommand("REMOVER_ESTADO");
		this.buttonAdicionarSimbolo.setActionCommand("ADICIONAR_SIMBOLO");
		this.buttonRemoverSimbolo.setActionCommand("REMOVER_SIMBOLO");
		
		this.buttonSalvar.addActionListener(event);
		this.buttonLimpar.addActionListener(event);
		this.buttonAdicionarEstado.addActionListener(event);
		this.buttonRemoverEstado.addActionListener(event);
		this.buttonAdicionarSimbolo.addActionListener(event);
		this.buttonRemoverSimbolo.addActionListener(event);
		
		this.buttonSalvar.setBounds(530, 519, 120, 35);
		this.buttonLimpar.setBounds(659, 519, 120, 35);
		this.buttonAdicionarEstado.setBounds(100, 450, 120, 35);
		this.buttonRemoverEstado.setBounds(230, 450, 120, 35);
		this.buttonAdicionarSimbolo.setBounds(530, 450, 120, 35);
		this.buttonRemoverSimbolo.setBounds(660, 450, 120, 35);
		
		this.addComponent(this.buttonSalvar);
		this.addComponent(this.buttonLimpar);
		this.addComponent(this.buttonAdicionarEstado);
		this.addComponent(this.buttonRemoverEstado);
		this.addComponent(this.buttonAdicionarSimbolo);
		this.addComponent(this.buttonRemoverSimbolo);
	}
	
	public void salvar() {
		if (this.tableAutomato.numeroRows() < 2) {
			Window.insertMessageFalha("Devem existir ao menos uma linha de estado e uma linha de alfabeto!");
			return;
		}
		if (this.numeroColunas < 3) {
			Window.insertMessageFalha("Devem existir ao menos tres colunas:\n\"se eh inicial, se eh final, nome do estado\"!");
			return;
		}
		
		Automato novoAutomato;
		novoAutomato = new Automato("");
		
		for (int c = 1; c < this.tableAutomato.numeroRows(); c++) {
			String nomeNovoEstado;
			nomeNovoEstado = this.tableAutomato.getValue(c, 2).replaceAll(" ", "");
			
			if (nomeNovoEstado.equals("")) {
				Window.insertMessageFalha("A coluna \"Estado\" deve ser preenchida!");
				return;
			}
			
			String stringColumnIsInicial, stringColumnIsFinal;
			stringColumnIsInicial = this.tableAutomato.getValue(c, 0).replaceAll(" ", "");
			stringColumnIsFinal = this.tableAutomato.getValue(c, 1).replaceAll(" ", "");
			
			if (!stringColumnIsInicial.equals(">") && !stringColumnIsInicial.equals("")) {
				Window.insertMessageFalha("A coluna \"Inicial\" deve ficar vazia ou preenchida com o simbolo \">\"");
				return;
			}
			if (!stringColumnIsFinal.equals("*") && !stringColumnIsFinal.equals("")) {
				Window.insertMessageFalha("A coluna \"Final\" deve ficar vazia ou preenchida com o simbolo \"*\"");
				return;
			}
			
			boolean isInicial, isFinal;
			isInicial = stringColumnIsInicial.equals(">");
			isFinal = stringColumnIsFinal.equals("*");
			
			Estado novoEstado;
			novoEstado = new Estado(nomeNovoEstado);
			novoEstado.setInicial(isInicial);
			novoEstado.setFinal(isFinal);
			
			if (isInicial) {
				if (novoAutomato.getEstadoInicial() != null) {
					Window.insertMessageFalha("O automato deve possuir apenas um estado inicial!");
					return;
				}
				novoAutomato.setEstadoInicial(novoEstado);
			}
			novoAutomato.addEstado(novoEstado);
		}
		
		if (novoAutomato.getEstadoInicial() == null) {
			Window.insertMessageFalha("O automato deve possuir um estado inicial!");
			return;
		}
		
		for (int c = 3; c < this.tableAutomato.numeroColumns(); c++) {
			String simboloAlfabeto;
			simboloAlfabeto = this.tableAutomato.getValue(0, c).replaceAll(" ", "");
			System.out.println("\""+simboloAlfabeto+"\"");
			if (simboloAlfabeto.length() != 1) {
				Window.insertMessageFalha("As colunas dos simbolos do alfabeto devem ser preenchidas e\npossuir no maximo 1 (um) caracter!");
				return;
			}
			novoAutomato.addEntradaAlfabeto(simboloAlfabeto.charAt(0));
		}
		
		for (int c = 1; c < this.tableAutomato.numeroRows(); c++) {
			String simboloEstado;
			simboloEstado = this.tableAutomato.getValue(c, 2).replaceAll(" ", "");
			
			Estado estado;
			estado = novoAutomato.getEstado(simboloEstado);
			
			for (int i = 3; i < this.numeroColunas; i++) {
				String simboloEstadoDestino;
				simboloEstadoDestino = this.tableAutomato.getValue(c, i);
				
				if (simboloEstadoDestino.equals("")) {
					continue;
				}
				
				Estado estadoDestino;
				estadoDestino = new Estado(simboloEstadoDestino);
				
				if (!novoAutomato.getConjuntoEstado().contains(estadoDestino)) {
					Window.insertMessageFalha("Falha ao adicionar transicao no estado "+simboloEstado+"\nTransicoes devem ficar vazia ou preenchidas com estados existentes!");
					return;
				}
				
				char simboloAlfabeto;
				simboloAlfabeto = this.tableAutomato.getValue(0, i).charAt(0);
				
				novoAutomato.addEstado(estadoDestino);
				estado.addTransicao(simboloAlfabeto, estadoDestino);
			}
		}
		
		novoAutomato.setNomeOperacaoGerador("NOVO AF");
		novoAutomato.setNome( this.managerLinguagem.getNomeNovoAutomato() );
		novoAutomato.print();
		
		this.managerLinguagem.gerarNomeNovoAutomato();
		this.managerLinguagem.addAutomato(novoAutomato);
		
		this.atualizar();
	}
	public void limpar() {
		this.numeroColunas = 0;
		this.tableAutomato.reloadModel();
		
		this.atualizar();
	}
	
	public void adicionarEstado() {
		System.out.println("adicionarEstado");
		this.tableAutomato.addRow();
	}
	public void removerEstado() {
		System.out.println("removerEstado");
		if (this.tableAutomato.numeroRows() > 2) {
			this.tableAutomato.removeLastRow();
		}
	}
	public void adicionarSimbolo() {
		System.out.println("adicionarSimbolo "+this.numeroColunas);
		this.numeroColunas++;
		this.tableAutomato.addColumn();
	}
	public void removerSimbolo() {
		System.out.println("removerSimbolo");
		this.numeroColunas--;
		if (this.numeroColunas < 4) {
			this.numeroColunas = 4;
		} else {
			this.tableAutomato.removeLastColumn();
		}
	}
}
