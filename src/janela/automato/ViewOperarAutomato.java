package janela.automato;

import javax.swing.JButton;

import automato.Automato;
import automato.OperarAutomato;
import janela.View;
import janela.component.ComboBox;
import janela.component.TableAutomato;
import janela.component.ViewTableAutomato;
import janela.event.EventViewOperarAutomato;
import janela.principal.ManagerLinguagem;
import janela.principal.Window;

public class ViewOperarAutomato extends View {
	private ManagerLinguagem managerLinguagem;
	
	private ComboBox comboBoxAutomato1, comboBoxAutomato2;
	
	private JButton buttonUnir, buttonIntersectar, buttonLimpar;
	private ViewTableAutomato viewTableAutomato1, viewTableAutomato2;
	
	public ViewOperarAutomato(ManagerLinguagem manager) {
		super();
		this.managerLinguagem = manager;
		
		this.loadLabel();
		this.loadJCombox();
		this.loadJButton();
		
		this.viewTableAutomato1 = new ViewTableAutomato(20, 100, 340, 380);
		this.viewTableAutomato2 = new ViewTableAutomato(430, 100, 340, 380);
		
		this.viewTableAutomato1.setEditavel(false);
		this.viewTableAutomato2.setEditavel(false);
		
		this.addComponent(this.viewTableAutomato1.getJScrollPane());
		this.addComponent(this.viewTableAutomato2.getJScrollPane());
	}
	
	private void loadLabel() {
		this.addComponent( this.addJLabel(20, 20, 100, 40, "Automato 1:") );
		this.addComponent( this.addJLabel(430, 20, 100, 40, "Automato 2:") );
	}
	private void loadJButton() {
		EventViewOperarAutomato event;
		event = new EventViewOperarAutomato(this);
		
		this.buttonUnir = new JButton("Unir");
		this.buttonUnir.setActionCommand("UNIR");
		this.buttonUnir.addActionListener(event);
		this.buttonUnir.setBounds(360, 519, 100, 35);
		this.buttonUnir.setEnabled(false);
		
		this.buttonIntersectar = new JButton("Intersectar");
		this.buttonIntersectar.setActionCommand("INTERSECTAR");
		this.buttonIntersectar.addActionListener(event);
		this.buttonIntersectar.setBounds(470, 519, 170, 35);
		this.buttonIntersectar.setEnabled(false);
		
		this.buttonLimpar = new JButton("Limpar");
		this.buttonLimpar.setActionCommand("LIMPAR");
		this.buttonLimpar.addActionListener(event);
		this.buttonLimpar.setBounds(650, 519, 120, 35);
		
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
	
	private void atualizarMostrarAutomato(ComboBox comboBox, ViewTableAutomato viewTableAutomato) {
		if (comboBox.getIndiceSelected() == 0) {
			viewTableAutomato.setVisible(false);
			return;
		}
		
		String nomeBuscado;
		nomeBuscado = comboBox.getSelected();
		
		Automato automato;
		automato = this.managerLinguagem.getAutomato(nomeBuscado);
		
		if (automato == null) {
			return;
		}
		
		TableAutomato tableAutomato;
		tableAutomato = new TableAutomato();
		tableAutomato.setAutomato(automato);
		
		viewTableAutomato.setTable(tableAutomato);
		viewTableAutomato.setVisible(true);
	}
	public void atualizarMostrarAutomato1() {
		System.out.println("???oadsaudhaua");
		this.atualizarMostrarAutomato(comboBoxAutomato1, this.viewTableAutomato1);
	}
	public void atualizarMostrarAutomato2() {
		System.out.println("???aaaaaaaaaaasduhasdasduh");
		this.atualizarMostrarAutomato(comboBoxAutomato2, this.viewTableAutomato2);
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
		
		Automato automato1, automato2;
		automato1 = this.managerLinguagem.getAutomato(this.comboBoxAutomato1.getSelected());
		automato2 = this.managerLinguagem.getAutomato(this.comboBoxAutomato1.getSelected());
		
		Automato novoAutomato;
		novoAutomato = OperarAutomato.unir(automato1, automato2);
		novoAutomato.setNome("U."+this.managerLinguagem.getNomeNovoAutomato());
		
		this.managerLinguagem.gerarNomeNovoAutomato();
		this.managerLinguagem.addAutomato(novoAutomato);
		
		Window.insertMessage("Automato gerado com sucesso!", "Sucesso!");
	}
	public void intersectar() {
		System.out.println("ViewOperarAutomato::Intersectar");
		
		Automato automato1, automato2;
		automato1 = this.managerLinguagem.getAutomato(this.comboBoxAutomato1.getSelected());
		automato2 = this.managerLinguagem.getAutomato(this.comboBoxAutomato1.getSelected());
		
		Automato novoAutomato;
		novoAutomato = OperarAutomato.intersectar(automato1, automato2, this.managerLinguagem);
		novoAutomato.setNome("I."+this.managerLinguagem.getNomeNovoAutomato());
		
		this.managerLinguagem.gerarNomeNovoAutomato();
		this.managerLinguagem.addAutomato(novoAutomato);
		
		Window.insertMessage("Automato gerado com sucesso!", "Sucesso!");
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
