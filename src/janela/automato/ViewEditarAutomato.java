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
	
	private JButton buttonSalvarEmDisco;
	private JButton buttonSalvar, buttonCancelar;
	private JButton buttonEditar, buttonRemover;
	private JButton buttonMinimizar, buttonDeterminizar;
	private JButton buttonReconhecer, buttonGerarGr;
	
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
		this.inputReconhecer.setBounds(571, 470, 79, 35);
		
		this.loadButton();
		
		this.addComponent(this.inputNome);
		this.addComponent(this.inputOperacao);
		this.addComponent(this.inputGerador1);
		this.addComponent(this.inputGerador2);
		this.addComponent(this.menuLateral.getJPanel());
		this.addComponent(this.viewTableAutomato.getJScrollPane());
	}
	private void loadButton() {
		EventViewEditarAutomato event;
		event = new EventViewEditarAutomato(this);
		
		this.buttonGerarGr = new JButton("Gerar GR");
		this.buttonSalvarEmDisco = new JButton("Salvar em disco");
		this.buttonSalvar = new JButton("Salvar");
		this.buttonEditar = new JButton("Editar");
		this.buttonCancelar = new JButton("Cancelar");
		this.buttonRemover = new JButton("Remover");
		this.buttonMinimizar = new JButton("Minimizar");
		this.buttonDeterminizar = new JButton("Determinizar");
		this.buttonReconhecer = new JButton("Reconhecer");
		
		this.buttonGerarGr.setActionCommand("GERAR_GR");
		this.buttonSalvar.setActionCommand("SALVAR");
		this.buttonSalvarEmDisco.setActionCommand("SALVAR_EM_DISCO");
		this.buttonEditar.setActionCommand("EDITAR");
		this.buttonRemover.setActionCommand("REMOVER");
		this.buttonCancelar.setActionCommand("CANCELAR");
		this.buttonMinimizar.setActionCommand("MINIMIZAR");
		this.buttonDeterminizar.setActionCommand("DETERMINIZAR");
		this.buttonReconhecer.setActionCommand("RECONHECER");
		
		this.buttonGerarGr.addActionListener(event);
		this.buttonSalvar.addActionListener(event);
		this.buttonSalvarEmDisco.addActionListener(event);
		this.buttonEditar.addActionListener(event);
		this.buttonRemover.addActionListener(event);
		this.buttonCancelar.addActionListener(event);
		this.buttonMinimizar.addActionListener(event);
		this.buttonDeterminizar.addActionListener(event);
		this.buttonReconhecer.addActionListener(event);
		
		this.buttonSalvar.setBounds(541, 519, 110, 35);
		this.buttonSalvarEmDisco.setBounds(380, 470, 180, 35);
		this.buttonEditar.setBounds(541, 519, 110, 35);
		this.buttonRemover.setBounds(660, 519, 120, 35);
		this.buttonCancelar.setBounds(660, 519, 120, 35);
		this.buttonGerarGr.setBounds(230, 470, 140, 35);
		this.buttonMinimizar.setBounds(230, 519, 140, 35);
		this.buttonDeterminizar.setBounds(379, 519, 150, 35);
		this.buttonReconhecer.setBounds(660, 470, 120, 35);
		
		this.addComponent(this.buttonGerarGr);
		this.addComponent(this.buttonSalvar);
		this.addComponent(this.buttonSalvarEmDisco);
		this.addComponent(this.buttonEditar);
		this.addComponent(this.buttonRemover);
		this.addComponent(this.buttonCancelar);
		this.addComponent(this.buttonMinimizar);
		this.addComponent(this.buttonDeterminizar);
		//this.addComponent(this.buttonReconhecer);
		//this.addComponent(this.inputReconhecer);
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
		
		this.buttonSalvar.setVisible(false);
		this.buttonSalvarEmDisco.setVisible(true);
		this.buttonEditar.setVisible(true);
		this.buttonRemover.setVisible(true);
		this.buttonCancelar.setVisible(false);
		
		this.buttonGerarGr.setVisible(true);
		this.buttonGerarGr.setEnabled(true);
		this.buttonMinimizar.setVisible(true);
		this.buttonDeterminizar.setVisible(true);
		this.buttonReconhecer.setVisible(true);
		this.buttonMinimizar.setEnabled(true);
		this.buttonDeterminizar.setEnabled(true);
		this.buttonReconhecer.setEnabled(true);
		this.inputReconhecer.setEditable(true);
		this.inputReconhecer.setVisible(true);
		
		this.viewTableAutomato.setVisible(true);
		this.loadTable();
		this.viewTableAutomato.setEditavel(false);
	}
	public void gerarGr() {
		Gramatica novaGramatica;
		novaGramatica = new Gramatica(this.automatoSelecionado);
		novaGramatica.setNome("A."+this.managerLinguagem.getNomeNovoGramatica());
		
		this.managerLinguagem.gerarNomeNovoGramatica();
		this.managerLinguagem.addGramatica(novaGramatica);
		
		this.atualizar();
		this.setLinguagem(this.automatoSelecionado.getNome());
		Window.insertMessage("Gramatica gerada com sucesso!", "Sucesso!");
	}
	public void minimizar() {
		System.out.println("MINIMIZAR");
		
		Automato novoAutomato;
		novoAutomato = OperarAutomato.minimizar(this.automatoSelecionado, this.managerLinguagem);
		novoAutomato.setNome("A.M."+this.managerLinguagem.getNomeNovoAutomato());
		novoAutomato.setNomePai1( this.automatoSelecionado.getNome() );
		novoAutomato.setNomeOperacaoGerador("Minimizacao");
		
		this.managerLinguagem.gerarNomeNovoAutomato();
		this.managerLinguagem.addAutomato(novoAutomato);
		
		this.atualizar();
		this.setLinguagem(this.automatoSelecionado.getNome());
		Window.insertMessage("Automato minimizado criado com sucesso!", "Sucesso!");
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
		
		Window.insertMessage("Automato determinizado criado com sucesso!", "Sucesso!");
	}
	public void editar() {
		this.buttonGerarGr.setEnabled(false);
		this.buttonSalvar.setVisible(true);
		this.buttonSalvarEmDisco.setVisible(false);
		this.buttonEditar.setVisible(false);
		this.buttonRemover.setVisible(false);
		this.buttonCancelar.setVisible(true);
		this.buttonMinimizar.setEnabled(false);
		this.buttonDeterminizar.setEnabled(false);
		this.buttonReconhecer.setEnabled(false);
		this.inputReconhecer.setEditable(false);
		this.inputReconhecer.setVisible(true);
		
		this.viewTableAutomato.setEditavel(true);
	}
	public void cancelar() {
		this.setLinguagem(this.automatoSelecionado.getNome());
	}
	public void remover() {
		this.managerLinguagem.removerAutomato(this.automatoSelecionado);
		this.menuLateral.setMenu(this.managerLinguagem.getConjuntoAutomato());
		
		this.automatoSelecionado = this.managerLinguagem.getUltimaAutomato();
		
		if (this.automatoSelecionado == null) {
			this.viewTableAutomato.limpar();
			this.atualizar();
		} else {
			this.setLinguagem(this.automatoSelecionado.getNome());
		}
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
		this.buttonSalvarEmDisco.setVisible(false);
		this.buttonEditar.setVisible(false);
		this.buttonCancelar.setVisible(false);
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
		
		this.setLinguagem(this.automatoSelecionado.getNome());
	}
	public void salvarEmDisco() {
		if (this.automatoSelecionado == null) {
			Window.insertMessageFalha("Operacao falhou!");
			return;
		}
		
		Arquivo.escrever(this.automatoSelecionado.getNome(), this.automatoSelecionado.getStringConjuntoTransicao(), Arquivo.extensaoAutomato);
		Window.insertMessage("Automato salvo com sucesso!", "Sucesso!");
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
