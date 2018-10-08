package janela.automato;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import automato.Automato;
import automato.OperarAutomato;
import gramatica.Gramatica;
import janela.IViewEditar;
import janela.View;
import janela.component.MenuLateral;
import janela.component.TableAutomato;
import janela.component.ViewTableAutomato;
import janela.event.EventViewEditarAutomato;
import janela.principal.ManagerLinguagem;
import janela.principal.Window;
import util.Arquivo;
import util.Linguagem;

public class ViewEditarAutomato extends View implements IViewEditar {
	private ManagerLinguagem managerLinguagem;
	private ViewTableAutomato viewTableAutomato;
	
	private JTextField inputNome;
	private JTextField inputOperacao, inputReconhecer;
	private JTextField inputGerador1, inputGerador2;
	
	private JLabel labelGerador1, labelGerador2;
	
	private MenuLateral menuLateral;
	private Automato automatoSelecionado;
	
	private JButton buttonSalvar, buttonRemover;
	private JButton buttonMinimizar, buttonDeterminizar, buttonGerarGr;
	private JButton buttonReconhecer;
	
	public ViewEditarAutomato(ManagerLinguagem managerLinguagem) {
		super();
		this.managerLinguagem = managerLinguagem;
		this.menuLateral = new MenuLateral(this);
		
		this.viewTableAutomato = new ViewTableAutomato(230, 62, 550, 280);
		this.viewTableAutomato.adicionarButtons(this.jPanel);
		this.viewTableAutomato.setEditavel(false);
		
		this.inputNome = new JTextField();
		this.inputOperacao = new JTextField();
		this.inputGerador1 = new JTextField();
		this.inputGerador2 = new JTextField();
		this.inputReconhecer = new JTextField();
		
		this.inputNome.setEditable(false);
		this.inputOperacao.setEditable(false);
		this.inputGerador1.setEditable(false);
		this.inputGerador2.setEditable(false);
		
		this.addJLabel(230, 20, 70, 25, "Nome:");
		this.addJLabel(230, 422, 90, 25, "Obtido por");
		this.labelGerador1 = this.addJLabel(465, 422, 30, 25, "de");
		this.labelGerador2 = this.addJLabel(635, 422, 20, 25, "e");
		
		this.inputNome.setBounds(330, 20, 200, 25);
		this.inputOperacao.setBounds(330, 420, 120, 25);
		this.inputGerador1.setBounds(495, 420, 120, 25);
		this.inputGerador2.setBounds(660, 422, 120, 25);
		this.inputReconhecer.setBounds(530, 470, 120, 35);
		
		this.loadButton();
		
		this.addComponent(this.inputNome);
		this.addComponent(this.inputOperacao);
		this.addComponent(this.inputGerador1);
		this.addComponent(this.inputGerador2);
		this.addComponent(this.inputReconhecer);
		this.addComponent(this.menuLateral.getJPanel());
		this.addComponent(this.viewTableAutomato.getJScrollPane());
	}
	private void loadButton() {
		EventViewEditarAutomato event;
		event = new EventViewEditarAutomato(this);
		
		this.buttonGerarGr = new JButton("Gerar GR");
		this.buttonSalvar = new JButton("Salvar");
		this.buttonRemover = new JButton("Remover");
		this.buttonMinimizar = new JButton("Minimizar");
		this.buttonDeterminizar = new JButton("Determinizar");
		this.buttonReconhecer = new JButton("Reconhecer");
		
		this.buttonGerarGr.setActionCommand("GERAR_GR");
		this.buttonSalvar.setActionCommand("SALVAR");
		this.buttonRemover.setActionCommand("REMOVER");
		this.buttonMinimizar.setActionCommand("MINIMIZAR");
		this.buttonDeterminizar.setActionCommand("DETERMINIZAR");
		this.buttonReconhecer.setActionCommand("RECONHECER");
		
		this.buttonGerarGr.addActionListener(event);
		this.buttonSalvar.addActionListener(event);
		this.buttonRemover.addActionListener(event);
		this.buttonMinimizar.addActionListener(event);
		this.buttonDeterminizar.addActionListener(event);
		this.buttonReconhecer.addActionListener(event);
		
		this.buttonSalvar.setBounds(541, 519, 110, 35);
		this.buttonRemover.setBounds(660, 519, 120, 35);
		this.buttonGerarGr.setBounds(230, 470, 120, 35);
		this.buttonMinimizar.setBounds(230, 519, 140, 35);
		this.buttonDeterminizar.setBounds(379, 519, 150, 35);
		this.buttonReconhecer.setBounds(660, 470, 120, 35);
		
		this.addComponent(this.buttonGerarGr);
		this.addComponent(this.buttonSalvar);
		this.addComponent(this.buttonRemover);
		this.addComponent(this.buttonMinimizar);
		this.addComponent(this.buttonDeterminizar);
		this.addComponent(this.buttonReconhecer);
	}
	
	private void loadTable() {
		TableAutomato table;
		table = new TableAutomato();
		table.setAutomato(this.automatoSelecionado);
		
		this.viewTableAutomato.setTable(table);
		this.viewTableAutomato.recarregarViewTableAutomato();
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
		
		String nomePai1, nomePai2;
		nomePai1 = automato.getNomePai1();
		nomePai2 = automato.getNomePai2();
		
		if (nomePai1.equals("")) {
			this.inputGerador1.setVisible(false);
			this.labelGerador1.setVisible(false);
		} else {
			this.labelGerador1.setVisible(true);
			this.inputGerador1.setVisible(true);
			this.inputGerador1.setText(nomePai1);
		}
		
		if (nomePai2.equals("")) {
			this.labelGerador2.setVisible(false);
			this.inputGerador2.setVisible(false);
		} else {
			this.labelGerador2.setVisible(true);
			this.inputGerador2.setVisible(true);
			this.inputGerador2.setText(nomePai2);
		}
		
		this.inputOperacao.setText(automato.getGeradorPor1());
		
		this.buttonGerarGr.setVisible(true);
		this.buttonDeterminizar.setVisible(true);
		this.buttonMinimizar.setVisible(true);
		this.buttonSalvar.setVisible(true);
		this.buttonRemover.setVisible(true);
		this.buttonReconhecer.setVisible(true);
		this.inputReconhecer.setVisible(true);
		
		this.viewTableAutomato.setVisible(true);
		this.viewTableAutomato.setEditavel(true);
		this.loadTable();
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
	
	public void remover() {
		this.managerLinguagem.removerAutomato(this.automatoSelecionado);
		this.menuLateral.setMenu(this.managerLinguagem.getConjuntoAutomato());
		
		this.viewTableAutomato.limpar();
		this.automatoSelecionado = null;
		
		this.atualizar();
	}
	
	@Override
	public void atualizar() {
		ArrayList<Linguagem> arrayAutomato;
		arrayAutomato = this.managerLinguagem.getConjuntoAutomato();
		
		this.menuLateral.setMenu(arrayAutomato);
		
		this.labelGerador1.setVisible(false);
		this.labelGerador2.setVisible(false);
		this.inputGerador1.setVisible(false);
		this.inputGerador2.setVisible(false);
		this.inputReconhecer.setVisible(false);
		
		this.buttonGerarGr.setVisible(false);
		this.buttonSalvar.setVisible(false);
		this.buttonRemover.setVisible(false);
		this.buttonMinimizar.setVisible(false);
		this.buttonDeterminizar.setVisible(false);
		this.buttonReconhecer.setVisible(false);
		
		this.viewTableAutomato.setEditavel(false);
		this.viewTableAutomato.setVisible(false);
	}
	public void salvar() {
		if (!this.viewTableAutomato.validar()) {
			return;
		}
		
		Automato automato;
		automato = this.viewTableAutomato.getAutomato();
		automato.print();
		
		this.automatoSelecionado.setConjuntoAlfabeto(automato.getConjuntoAlfabeto());
		this.automatoSelecionado.setConjuntoEstado(automato.getConjuntoEstado());
		this.automatoSelecionado.setEstadoInicial(automato.getEstadoInicial());
		
		Arquivo.escrever(this.automatoSelecionado.getNome(), this.automatoSelecionado.getStringConjuntoTransicao(), Arquivo.extensaoAutomato);
	}
	public void reconhecer() {
		String entrada;
		entrada = this.inputReconhecer.getText();
		
		if (this.automatoSelecionado.reconhecerEntrada(entrada)) {
			Window.insertMessage("Entrada reconhecida!", "Sucesso!");
		} else {
			Window.insertMessageFalha("Entrada nao reconhecida!");
		}
	}
}
