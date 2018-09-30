package view.automato;

import javax.swing.JButton;
import javax.swing.JTextField;

import view.View;
import view.component.TableAutomato;
import view.principal.ManagerLinguagem;

public class ViewCriarAutomato extends View {
	private ManagerLinguagem managerLinguagem;
	private TableAutomato tableAutomato;
	
	private JTextField inputNome;
	private JButton buttonSalvar, buttonLimpar;
	
	public ViewCriarAutomato(ManagerLinguagem managerLinguagem) {
		super();
		this.managerLinguagem = managerLinguagem;
		
		this.inputNome = new JTextField();
		this.inputNome.setEditable(false);
		this.inputNome.setBounds(100, 20, 200, 25);
		
		this.tableAutomato = new TableAutomato(100, 62, 680, 400);
		
		this.addJLabel(10, 20, 70, 25, "Nome:");
		
		this.loadButton();
		
		this.addComponent(this.inputNome);
		this.addComponent(this.tableAutomato.getJScrollPane());
	}
	
	private void loadButton() {
		this.buttonSalvar = new JButton("Salvar");
		this.buttonLimpar = new JButton("Limpar");
		
		this.buttonSalvar.setActionCommand("SALVAR");
		this.buttonLimpar.setActionCommand("LIMPAR");
		
		//this.buttonSalvar.addActionListener(event);
		//this.buttonLimpar.addActionListener(event);
		
		this.buttonSalvar.setBounds(530, 519, 120, 35);
		this.buttonLimpar.setBounds(659, 519, 120, 35);
		
		this.addComponent(this.buttonSalvar);
		this.addComponent(this.buttonLimpar);
	}
	
	public void salvar() {
		this.buttonSalvar.setVisible(false);
		this.buttonLimpar.setVisible(false);
		
		this.atualizar();
	}
	public void cancelar() {
		this.buttonSalvar.setVisible(false);
		this.buttonLimpar.setVisible(false);
		
		this.atualizar();
	}
	
}
