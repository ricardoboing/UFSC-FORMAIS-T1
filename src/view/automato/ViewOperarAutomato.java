package view.automato;

import javax.swing.JButton;

import automato.Automato;
import util.Linguagem;
import view.View;
import view.component.ComboBox;
import view.component.MostrarAutomato;
import view.event.EventViewOperarAutomato;
import view.principal.ManagerLinguagem;

public class ViewOperarAutomato extends View {
	private ManagerLinguagem managerLinguagem;
	
	private MostrarAutomato mostrarAutomato1, mostrarAutomato2;
	private ComboBox comboBoxAutomato1, comboBoxAutomato2;
	
	private JButton buttonUnir, buttonIntersectar, buttonLimpar;
	
	public ViewOperarAutomato(ManagerLinguagem manager) {
		super();
		this.managerLinguagem = manager;
		
		this.loadMostrarAutomato();
		this.loadLabel();
		this.loadJCombox();
		this.loadJButton();
	}
	
	private void loadMostrarAutomato() {
		this.mostrarAutomato1 = new MostrarAutomato(20, 100);
		this.mostrarAutomato2 = new MostrarAutomato(430, 100);
		
		this.addComponent(this.mostrarAutomato1.getAutomatoJScrollPane());
		this.addComponent(this.mostrarAutomato2.getAutomatoJScrollPane());
	}
	private void loadLabel() {
		this.addComponent( this.addJLabel(20, 20, 100, 40, "Automato 1:") );
		this.addComponent( this.addJLabel(430, 20, 100, 40, "Automato 2:") );
	}
	private void loadJButton() {
		EventViewOperarAutomato event;
		event = new EventViewOperarAutomato(this);
		
		this.buttonUnir = new JButton("Unir");
		this.buttonIntersectar = new JButton("Intersectar");
		this.buttonLimpar = new JButton("Limpar");
		
		this.buttonUnir.setActionCommand("UNIR");
		this.buttonIntersectar.setActionCommand("INTERSECTAR");
		this.buttonLimpar.setActionCommand("LIMPAR");
		
		this.buttonUnir.addActionListener(event);
		this.buttonIntersectar.addActionListener(event);
		this.buttonLimpar.addActionListener(event);
		
		this.buttonUnir.setBounds(360, 519, 100, 35);
		this.buttonIntersectar.setBounds(470, 519, 170, 35);
		this.buttonLimpar.setBounds(650, 519, 120, 35);
		
		this.buttonUnir.setEnabled(false);
		this.buttonIntersectar.setEnabled(false);
		
		this.addComponent(this.buttonUnir);
		this.addComponent(this.buttonIntersectar);
		this.addComponent(this.buttonLimpar);
	}
	private void loadJCombox() {
		EventViewOperarAutomato event;
		event = new EventViewOperarAutomato(this);
		
		this.comboBoxAutomato1 = new ComboBox(120, 20, "AUTOMATO_1", event);
		this.comboBoxAutomato2 = new ComboBox(530, 20, "AUTOMATO_2", event);
		
		this.addComponent(this.comboBoxAutomato1.getJComboBox());
		this.addComponent(this.comboBoxAutomato2.getJComboBox());
	}
	
	public void atualizarJButton() {
		boolean valido;
		valido = this.comboBoxAutomato2.valido() && this.comboBoxAutomato1.valido();
		
		this.buttonUnir.setEnabled(valido);
		this.buttonIntersectar.setEnabled(valido);
	}
	
	private void atualizarMostrarAutomato(ComboBox comboBox, MostrarAutomato mostrarAutomato) {
		if (comboBox.getIndiceSelected() == 0) {
			mostrarAutomato.hideAutomato();
			return;
		}
		
		String nomeBuscado;
		nomeBuscado = comboBox.getSelected();
		
		Linguagem iLinguagem;
		iLinguagem = this.managerLinguagem.getAutomato(nomeBuscado);
		
		if (iLinguagem == null) {
			return;
		}
		
		mostrarAutomato.showAutomato((Automato)iLinguagem);
	}
	public void atualizarMostrarAutomato1() {
		this.atualizarMostrarAutomato(comboBoxAutomato1, mostrarAutomato1);
	}
	public void atualizarMostrarAutomato2() {
		this.atualizarMostrarAutomato(comboBoxAutomato2, mostrarAutomato2);
	}
	
	public void atualizarJCombox1() {
		int index;
		index = this.comboBoxAutomato1.getIndiceSelected();
		
		this.comboBoxAutomato1.setItensLinguagem( this.managerLinguagem.getConjuntoAutomato() );
		this.comboBoxAutomato1.selecionar(index);
	}
	public void atualizarJCombox2() {
		int index;
		index = this.comboBoxAutomato2.getIndiceSelected();
		
		this.comboBoxAutomato2.setItensLinguagem( this.managerLinguagem.getConjuntoAutomato() );
		this.comboBoxAutomato2.selecionar(index);
	}
	
	public void unir() {
		System.out.println("ViewOperarAutomato::Unir");
	}
	public void intersectar() {
		System.out.println("ViewOperarAutomato::Intersectar");
	}
	public void limpar() {
		System.out.println("ViewOperarAutomato::LImpar");
		
		this.comboBoxAutomato1.selecionar(0);
		this.comboBoxAutomato2.selecionar(0);
		
		this.atualizarMostrarAutomato1();
		this.atualizarMostrarAutomato2();
	}
	
	@Override
	public void atualizar() {
		this.comboBoxAutomato1.load();
		this.comboBoxAutomato2.load();
	}
}
