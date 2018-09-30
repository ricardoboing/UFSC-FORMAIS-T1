package view.automato;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import automato.Automato;
import automato.OperarAutomato;
import gramatica.Gramatica;
import util.Linguagem;
import view.IViewEditar;
import view.View;
import view.component.MenuLateral;
import view.component.TableAutomato;
import view.event.EventViewEditarAutomato;
import view.principal.ManagerLinguagem;

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
	
	private OperarAutomato operarAutomato;
	
	private JButton buttonEditar, buttonSalvar, buttonCancelar;
	private JButton buttonMinimizar, buttonDeterminizar, buttonGerarGr;
	
	private boolean editando;
	
	public ViewEditarAutomato(ManagerLinguagem managerLinguagem) {
		super();
		this.managerLinguagem = managerLinguagem;
		
		this.operarAutomato = new OperarAutomato();
		
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
		
		this.tableAutomato = new TableAutomato(230, 62, 560, 380);
		
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
		novoAutomato = this.operarAutomato.minimizar(this.automatoSelecionado);
		novoAutomato.setNome("A.M."+this.managerLinguagem.getNomeNovoAutomato());
		novoAutomato.setNomePai1( this.automatoSelecionado.getNome() );
		novoAutomato.setNomeOperacaoGerador1("Minimizacao");
		
		this.managerLinguagem.gerarNomeNovoAutomato();
		this.managerLinguagem.addAutomato(novoAutomato);
		
		this.atualizar();
		this.setLinguagem(this.automatoSelecionado.getNome());
	}
	public void determinizar() {
		System.out.println("DETERMINIZAR");
		
		Automato novoAutomato;
		novoAutomato = this.operarAutomato.determinizar(this.automatoSelecionado);
		novoAutomato.setNome("A.D."+this.managerLinguagem.getNomeNovoAutomato());
		novoAutomato.setNomePai1( this.automatoSelecionado.getNome() );
		novoAutomato.setNomeOperacaoGerador1("Determinizacao");
		
		this.managerLinguagem.gerarNomeNovoAutomato();
		this.managerLinguagem.addAutomato(novoAutomato);
		
		this.atualizar();
		this.setLinguagem(this.automatoSelecionado.getNome());
	}
	public void editar() {
		this.editando = true;
		
		this.buttonSalvar.setVisible(true);
		this.buttonCancelar.setVisible(true);
		
		this.buttonGerarGr.setVisible(false);
		this.buttonEditar.setVisible(false);
		this.buttonDeterminizar.setVisible(false);
		this.buttonMinimizar.setVisible(false);
	}
	public void salvar() {
		this.editando = false;
		this.buttonSalvar.setVisible(false);
		this.buttonCancelar.setVisible(false);
		
		this.buttonGerarGr.setVisible(true);
		this.buttonEditar.setVisible(true);
		this.buttonDeterminizar.setVisible(true);
		this.buttonMinimizar.setVisible(true);
		
		this.atualizar();
		this.setLinguagem(this.automatoSelecionado.getNome());
	}
	public void cancelar() {
		this.editando = false;
		this.buttonSalvar.setVisible(false);
		this.buttonCancelar.setVisible(false);
		
		this.buttonGerarGr.setVisible(true);
		this.buttonEditar.setVisible(true);
		this.buttonDeterminizar.setVisible(true);
		this.buttonMinimizar.setVisible(true);
		
		this.atualizar();
		this.setLinguagem(this.automatoSelecionado.getNome());
	}
	@Override
	public void atualizar() {
		ArrayList<Linguagem> arrayAutomato;
		arrayAutomato = this.managerLinguagem.getConjuntoAutomato();
		
		this.menuLateral.setMenu(arrayAutomato);
		
		this.buttonGerarGr.setVisible(false);
		this.buttonSalvar.setVisible(false);
		this.buttonEditar.setVisible(false);
		this.buttonCancelar.setVisible(false);
		this.buttonMinimizar.setVisible(false);
		this.buttonDeterminizar.setVisible(false);
	}
}
