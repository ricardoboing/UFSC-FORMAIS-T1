package view.automato;

import javax.swing.JButton;
import javax.swing.JTextField;

import automato.Automato;
import view.View;
import view.component.Table;
import view.component.ViewTableAutomato;
import view.event.EventViewCriarAutomato;
import view.principal.ManagerLinguagem;

public class ViewCriarAutomato extends View {
	private ViewTableAutomato viewTableAutomato;
	private JTextField inputNome;
	private ManagerLinguagem managerLinguagem;
	
	private JButton buttonSalvar, buttonLimpar;
	
	private Table table;
	
	public ViewCriarAutomato(ManagerLinguagem managerLinguagem) {
		super();
		this.managerLinguagem = managerLinguagem;
		
		this.inputNome = new JTextField();
		this.inputNome.setEditable(false);
		this.inputNome.setBounds(100, 20, 200, 25);
		
		this.viewTableAutomato = new ViewTableAutomato(100, 62, 680, 370);
		this.viewTableAutomato.adicionarButtons(this.jPanel);
		
		this.loadTable();
		this.loadButton();
		
		this.addJLabel(10, 20, 70, 25, "Nome:");
		this.addComponent(this.inputNome);
		this.addComponent(this.viewTableAutomato.getJScrollPane());
	}
	private void loadTable() {
		String[] cabecalho, linhaAlfabeto, linhaEstadoInicial;
		cabecalho = new String[] {
			"Inicial", "Final", "Estado"
		};
		linhaAlfabeto = new String[] {
			"", "", ""
		};
		linhaEstadoInicial = new String[] {
			">", "*", "q0"
		};
		
		this.table = new Table();
		this.table.setCabecalho(cabecalho);
		this.table.addLinha(linhaAlfabeto);
		this.table.addLinha(linhaEstadoInicial);
		
		this.viewTableAutomato.setTable(table);
		this.viewTableAutomato.recarregarViewTableAutomato();
	}
	private void loadButton() {
		EventViewCriarAutomato event;
		event = new EventViewCriarAutomato(this);
		
		this.buttonSalvar = new JButton("Salvar");
		this.buttonSalvar.setActionCommand("SALVAR");
		this.buttonSalvar.addActionListener(event);
		this.buttonSalvar.setBounds(530, 519, 120, 35);
		
		this.buttonLimpar = new JButton("Limpar");
		this.buttonLimpar.setActionCommand("LIMPAR");
		this.buttonLimpar.addActionListener(event);
		this.buttonLimpar.setBounds(659, 519, 120, 35);
		
		this.addComponent(this.buttonSalvar);
		this.addComponent(this.buttonLimpar);
	}
	
	public void salvar() {
		Automato novoAutomato;
		novoAutomato = this.viewTableAutomato.getAutomato();
		novoAutomato.setNomeOperacaoGerador("NOVO AF");
		novoAutomato.setNome( this.managerLinguagem.getNomeNovoAutomato() );
		novoAutomato.print();
		
		this.managerLinguagem.gerarNomeNovoAutomato();
		this.managerLinguagem.addAutomato(novoAutomato);
		
		this.atualizar();
	}
	public void limpar() {
		this.atualizar();
	}
}
