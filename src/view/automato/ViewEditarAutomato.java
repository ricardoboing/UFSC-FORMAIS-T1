package view.automato;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import automato.Automato;
import automato.Estado;
import automato.OperarAutomato;
import gramatica.Gramatica;
import util.Linguagem;
import view.IViewEditar;
import view.View;
import view.component.MenuLateral;
import view.component.Table;
import view.component.TableAutomato;
import view.component.TableRow;
import view.event.EventViewEditarAutomato;
import view.principal.ManagerLinguagem;
import view.principal.Window;

public class ViewEditarAutomato extends View implements IViewEditar {
	private ManagerLinguagem managerLinguagem;
	private TableAutomato tableAutomato;
	
	private JTextField inputNome;
	private JTextField inputOperacao;
	private JTextField inputGerador1, inputGerador2;
	
	private JLabel labelOperacao;
	private JLabel labelGerador1, labelGerador2;
	
	private MenuLateral menuLateral;
	private Automato automatoSelecionado;
	
	private JButton buttonEditar, buttonSalvar, buttonCancelar;
	private JButton buttonMinimizar, buttonDeterminizar, buttonGerarGr;
	
	private JButton buttonAdicionarEstado, buttonRemoverEstado;
	private JButton buttonAdicionarSimbolo, buttonRemoverSimbolo;
	
	private boolean editando;
	private int numeroColunas;
	
	public ViewEditarAutomato(ManagerLinguagem managerLinguagem) {
		super();
		this.managerLinguagem = managerLinguagem;
		
		this.editando = false;
		this.menuLateral = new MenuLateral(this);
		
		this.inputNome = new JTextField();
		this.inputOperacao = new JTextField();
		this.inputGerador1 = new JTextField();
		this.inputGerador2 = new JTextField();
		
		this.inputNome.setEditable(false);
		this.inputOperacao.setEditable(false);
		this.inputGerador1.setEditable(false);
		this.inputGerador2.setEditable(false);
		
		this.loadTable();
		
		this.addJLabel(230, 20, 70, 25, "Nome:");
		this.labelOperacao = this.addJLabel(230, 462, 90, 25, "Obtido por");
		this.labelGerador1 = this.addJLabel(465, 462, 30, 25, "de");
		this.labelGerador2 = this.addJLabel(640, 462, 20, 25, "e");
		
		this.inputNome.setBounds(330, 20, 200, 25);
		this.inputOperacao.setBounds(330, 460, 120, 25);
		this.inputGerador1.setBounds(500, 460, 120, 25);
		this.inputGerador2.setBounds(670, 462, 120, 25);
		
		this.loadButton();
		
		this.addComponent(this.inputNome);
		this.addComponent(this.inputOperacao);
		this.addComponent(this.inputGerador1);
		this.addComponent(this.inputGerador2);
		this.addComponent(this.menuLateral.getJPanel());
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
		
		this.tableAutomato = new TableAutomato(230, 62, 560, 320);
		this.tableAutomato.getViewTable().setTable(table);
		this.tableAutomato.getViewTable().setEditable(true);
	}
	private void loadButton() {
		EventViewEditarAutomato event;
		event = new EventViewEditarAutomato(this);
		
		this.buttonGerarGr = new JButton("Gerar GR");
		this.buttonEditar = new JButton("Editar");
		this.buttonSalvar = new JButton("Salvar");
		this.buttonCancelar = new JButton("Cancelar");
		this.buttonMinimizar = new JButton("Minimizar");
		this.buttonDeterminizar = new JButton("Determinizar");
		
		this.buttonGerarGr.setActionCommand("GERAR_GR");
		this.buttonEditar.setActionCommand("EDITAR");
		this.buttonSalvar.setActionCommand("SALVAR");
		this.buttonCancelar.setActionCommand("CANCELAR");
		this.buttonMinimizar.setActionCommand("MINIMIZAR");
		this.buttonDeterminizar.setActionCommand("DETERMINIZAR");
		
		this.buttonGerarGr.addActionListener(event);
		this.buttonSalvar.addActionListener(event);
		this.buttonCancelar.addActionListener(event);
		this.buttonEditar.addActionListener(event);
		this.buttonMinimizar.addActionListener(event);
		this.buttonDeterminizar.addActionListener(event);
		
		this.buttonSalvar.setBounds(530, 519, 120, 35);
		this.buttonCancelar.setBounds(659, 519, 130, 35);
		this.buttonGerarGr.setBounds(220, 519, 120, 35);
		this.buttonEditar.setBounds(350, 519, 120, 35);
		this.buttonMinimizar.setBounds(480, 519, 140, 35);
		this.buttonDeterminizar.setBounds(629, 519, 160, 35);
		
		this.addComponent(this.buttonGerarGr);
		this.addComponent(this.buttonSalvar);
		this.addComponent(this.buttonCancelar);
		this.addComponent(this.buttonEditar);
		this.addComponent(this.buttonMinimizar);
		this.addComponent(this.buttonDeterminizar);
		
		
		this.buttonAdicionarEstado = new JButton("+ Estado");
		this.buttonRemoverEstado = new JButton("- Estado");
		this.buttonAdicionarSimbolo = new JButton("+ Simbolo");
		this.buttonRemoverSimbolo = new JButton("- Simbolo");
		
		this.buttonAdicionarEstado.setActionCommand("ADICIONAR_ESTADO");
		this.buttonRemoverEstado.setActionCommand("REMOVER_ESTADO");
		this.buttonAdicionarSimbolo.setActionCommand("ADICIONAR_SIMBOLO");
		this.buttonRemoverSimbolo.setActionCommand("REMOVER_SIMBOLO");
		
		this.buttonAdicionarEstado.addActionListener(event);
		this.buttonRemoverEstado.addActionListener(event);
		this.buttonAdicionarSimbolo.addActionListener(event);
		this.buttonRemoverSimbolo.addActionListener(event);
		
		this.buttonAdicionarEstado.setBounds(230, 400, 120, 35);
		this.buttonRemoverEstado.setBounds(360, 400, 120, 35);
		this.buttonAdicionarSimbolo.setBounds(530, 400, 120, 35);
		this.buttonRemoverSimbolo.setBounds(660, 400, 120, 35);
		
		this.addComponent(this.buttonAdicionarEstado);
		this.addComponent(this.buttonRemoverEstado);
		this.addComponent(this.buttonAdicionarSimbolo);
		this.addComponent(this.buttonRemoverSimbolo);
	}
	
	@Override
	public void setLinguagem(String nome) {
		Automato automato;
		automato = this.managerLinguagem.getAutomato(nome);
		
		if (automato == null) {
			return;
		}
		
		this.automatoSelecionado = automato;
		
		this.inputNome.setText(automato.getNome());
		this.tableAutomato.montarTable(automato);
		
		this.inputGerador1.setText(automato.getNomePai1());
		this.inputGerador2.setText(automato.getNomePai2());
		this.inputOperacao.setText(automato.getGeradorPor1());
		
		this.buttonGerarGr.setVisible(true);
		this.buttonEditar.setVisible(true);
		this.buttonDeterminizar.setVisible(true);
		this.buttonMinimizar.setVisible(true);
	}
	public void gerarGr() {
		Gramatica novaGramatica;
		novaGramatica = new Gramatica(this.automatoSelecionado);
		novaGramatica.setNome("A."+this.managerLinguagem.getNomeNovoGramatica());
		
		this.managerLinguagem.gerarNomeNovoGramatica();
		this.managerLinguagem.addGramatica(novaGramatica);
		
		this.atualizar();
		this.setLinguagem(this.automatoSelecionado.getNome());
	}
	public void minimizar() {
		System.out.println("MINIMIZAR");
		
		Automato novoAutomato;
		novoAutomato = OperarAutomato.minimizar(this.automatoSelecionado);
		novoAutomato.setNome("A.M."+this.managerLinguagem.getNomeNovoAutomato());
		novoAutomato.setNomePai1( this.automatoSelecionado.getNome() );
		novoAutomato.setNomeOperacaoGerador("Minimizacao");
		
		this.managerLinguagem.gerarNomeNovoAutomato();
		this.managerLinguagem.addAutomato(novoAutomato);
		
		this.atualizar();
		this.setLinguagem(this.automatoSelecionado.getNome());
	}
	public void determinizar() {
		System.out.println("DETERMINIZAR");
		
		Automato novoAutomato;
		novoAutomato = OperarAutomato.determinizar(this.automatoSelecionado);
		novoAutomato.setNome("A.D."+this.managerLinguagem.getNomeNovoAutomato());
		novoAutomato.setNomePai1( this.automatoSelecionado.getNome() );
		novoAutomato.setNomeOperacaoGerador("Determinizacao");
		
		this.managerLinguagem.gerarNomeNovoAutomato();
		this.managerLinguagem.addAutomato(novoAutomato);
		
		this.atualizar();
		this.setLinguagem(this.automatoSelecionado.getNome());
	}
	public void editar() {
		this.editando = true;
		
		this.buttonSalvar.setVisible(true);
		this.buttonCancelar.setVisible(true);
		this.buttonAdicionarEstado.setVisible(true);
		this.buttonAdicionarSimbolo.setVisible(true);
		this.buttonRemoverEstado.setVisible(true);
		this.buttonRemoverSimbolo.setVisible(true);
		
		this.buttonGerarGr.setVisible(false);
		this.buttonEditar.setVisible(false);
		this.buttonDeterminizar.setVisible(false);
		this.buttonMinimizar.setVisible(false);
	}
	public void salvar() {
		if (this.tableAutomato.getViewTable().numeroRows() < 2) {
			Window.insertMessageFalha("Devem existir ao menos uma linha de estado e uma linha de alfabeto!");
			return;
		}
		if (this.numeroColunas < 3) {
			Window.insertMessageFalha("Devem existir ao menos tres colunas:\n\"se eh inicial, se eh final, nome do estado\"!");
			return;
		}
		
		for (int c = 1; c < this.tableAutomato.getViewTable().numeroRows(); c++) {
			String nomeNovoEstado;
			nomeNovoEstado = this.tableAutomato.getViewTable().getValue(c, 2).replaceAll(" ", "");
			
			if (nomeNovoEstado.equals("")) {
				Window.insertMessageFalha("A coluna \"Estado\" deve ser preenchida!");
				return;
			}
			
			String stringColumnIsInicial, stringColumnIsFinal;
			stringColumnIsInicial = this.tableAutomato.getViewTable().getValue(c, 0).replaceAll(" ", "");
			stringColumnIsFinal = this.tableAutomato.getViewTable().getValue(c, 1).replaceAll(" ", "");
			
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
				if (automatoSelecionado.getEstadoInicial() != null) {
					Window.insertMessageFalha("O automato deve possuir apenas um estado inicial!");
					return;
				}
				automatoSelecionado.setEstadoInicial(novoEstado);
			}
			automatoSelecionado.addEstado(novoEstado);
		}
		
		if (automatoSelecionado.getEstadoInicial() == null) {
			Window.insertMessageFalha("O automato deve possuir um estado inicial!");
			return;
		}
		
		for (int c = 3; c < this.tableAutomato.getViewTable().numeroColumns(); c++) {
			String simboloAlfabeto;
			simboloAlfabeto = this.tableAutomato.getViewTable().getValue(0, c).replaceAll(" ", "");
			System.out.println("\""+simboloAlfabeto+"\"");
			if (simboloAlfabeto.length() != 1) {
				Window.insertMessageFalha("As colunas dos simbolos do alfabeto devem ser preenchidas e\npossuir no maximo 1 (um) caracter!");
				return;
			}
			automatoSelecionado.addEntradaAlfabeto(simboloAlfabeto.charAt(0));
		}
		
		for (int c = 1; c < this.tableAutomato.getViewTable().numeroRows(); c++) {
			String simboloEstado;
			simboloEstado = this.tableAutomato.getViewTable().getValue(c, 2).replaceAll(" ", "");
			
			Estado estado;
			estado = automatoSelecionado.getEstado(simboloEstado);
			
			for (int i = 3; i < this.numeroColunas; i++) {
				String simboloEstadoDestino;
				simboloEstadoDestino = this.tableAutomato.getViewTable().getValue(c, i);
				
				if (simboloEstadoDestino.equals("")) {
					continue;
				}
				
				Estado estadoDestino;
				estadoDestino = new Estado(simboloEstadoDestino);
				
				if (!automatoSelecionado.getConjuntoEstado().contains(estadoDestino)) {
					Window.insertMessageFalha("Falha ao adicionar transicao no estado "+simboloEstado+"\nTransicoes devem ficar vazia ou preenchidas com estados existentes!");
					return;
				}
				
				char simboloAlfabeto;
				simboloAlfabeto = this.tableAutomato.getViewTable().getValue(0, i).charAt(0);
				
				automatoSelecionado.addEstado(estadoDestino);
				estado.addTransicao(simboloAlfabeto, estadoDestino);
			}
		}
		
		this.atualizar();
		this.setLinguagem(this.automatoSelecionado.getNome());
		
		this.editando = false;
		this.buttonSalvar.setVisible(false);
		this.buttonCancelar.setVisible(false);
		this.buttonAdicionarEstado.setVisible(false);
		this.buttonAdicionarSimbolo.setVisible(false);
		this.buttonRemoverEstado.setVisible(false);
		this.buttonRemoverSimbolo.setVisible(false);
		
		this.buttonGerarGr.setVisible(true);
		this.buttonEditar.setVisible(true);
		this.buttonDeterminizar.setVisible(true);
		this.buttonMinimizar.setVisible(true);
	}
	public void cancelar() {
		this.editando = false;
		this.buttonSalvar.setVisible(false);
		this.buttonCancelar.setVisible(false);
		this.buttonAdicionarEstado.setVisible(false);
		this.buttonAdicionarSimbolo.setVisible(false);
		this.buttonRemoverEstado.setVisible(false);
		this.buttonRemoverSimbolo.setVisible(false);
		
		this.buttonGerarGr.setVisible(true);
		this.buttonEditar.setVisible(true);
		this.buttonDeterminizar.setVisible(true);
		this.buttonMinimizar.setVisible(true);
		
		this.atualizar();
		this.setLinguagem(this.automatoSelecionado.getNome());
	}
	public void adicionarEstado() {
		System.out.println("adicionarEstado");
		this.tableAutomato.getViewTable().addRow();
	}
	public void removerEstado() {
		System.out.println("removerEstado");
		if (this.tableAutomato.getViewTable().numeroRows() > 2) {
			this.tableAutomato.getViewTable().removeLastRow();
		}
	}
	public void adicionarSimbolo() {
		System.out.println("adicionarSimbolo "+this.numeroColunas);
		this.numeroColunas++;
		this.tableAutomato.getViewTable().addColumn();
	}
	public void removerSimbolo() {
		System.out.println("removerSimbolo");
		this.numeroColunas--;
		if (this.numeroColunas < 4) {
			this.numeroColunas = 4;
		} else {
			this.tableAutomato.getViewTable().removeLastColumn();
		}
	}
	
	@Override
	public void atualizar() {
		ArrayList<Linguagem> arrayAutomato;
		arrayAutomato = this.managerLinguagem.getConjuntoAutomato();
		
		this.menuLateral.setMenu(arrayAutomato);
		
		this.buttonAdicionarEstado.setVisible(false);
		this.buttonAdicionarSimbolo.setVisible(false);
		this.buttonRemoverEstado.setVisible(false);
		this.buttonRemoverSimbolo.setVisible(false);
		
		this.buttonGerarGr.setVisible(false);
		this.buttonSalvar.setVisible(false);
		this.buttonEditar.setVisible(false);
		this.buttonCancelar.setVisible(false);
		this.buttonMinimizar.setVisible(false);
		this.buttonDeterminizar.setVisible(false);
	}
}
