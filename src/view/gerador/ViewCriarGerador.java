package view.gerador;

import javax.swing.JButton;
import javax.swing.JTextField;

import expressao.Expressao;
import gramatica.Gramatica;
import util.ELinguagem;
import view.View;
import view.component.TextArea;
import view.event.EventViewCriarGerador;
import view.principal.ManagerLinguagem;

public class ViewCriarGerador extends View {
	private ManagerLinguagem managerLinguagem;
	private ELinguagem eLinguagem;
	
	private TextArea textArea;
	private JTextField inputNome;
	
	private JButton buttonSalvar, buttonLimpar;
	
	public ViewCriarGerador(ManagerLinguagem managerLinguagem, ELinguagem eLinguagem) {
		super();
		this.managerLinguagem = managerLinguagem;
		this.eLinguagem = eLinguagem;
		
		this.inputNome = new JTextField();
		this.inputNome.setEditable(false);
		this.inputNome.setBounds(100, 20, 200, 25);
		
		this.textArea = new TextArea(100, 62, 680, 400);
		
		this.addJLabel(10, 20, 70, 25, "Nome:");
		this.addComponent(this.inputNome);
		this.addComponent(this.textArea.getJScrollPane());
		
		this.loadButton();
		this.atualizarNome();
	}
	private void loadButton() {
		EventViewCriarGerador event;
		event = new EventViewCriarGerador(this);
		
		this.buttonSalvar = new JButton("Salvar");
		this.buttonLimpar = new JButton("Limpar");
		
		this.buttonSalvar.setActionCommand("SALVAR");
		this.buttonLimpar.setActionCommand("LIMPAR");
		
		this.buttonSalvar.addActionListener(event);
		this.buttonLimpar.addActionListener(event);
		
		this.buttonSalvar.setBounds(540, 519, 120, 35);
		this.buttonLimpar.setBounds(669, 519, 120, 35);
		
		this.addComponent(this.buttonSalvar);
		this.addComponent(this.buttonLimpar);
	}
	public void atualizarNome() {
		switch (this.eLinguagem) {
			case EXPRESSAO:
				this.inputNome.setText( this.managerLinguagem.getNomeNovoExpressao() );
				break;
			case GRAMATICA:
				this.inputNome.setText( this.managerLinguagem.getNomeNovoGramatica() );
				break;
			default:
				break;
		}
	}
	
	public void salvar() {
		String conteudo;
		conteudo = this.textArea.getText();
		
		String nome;
		nome = this.inputNome.getText();
		
		switch (this.eLinguagem) {
			case EXPRESSAO:
				System.out.println("salvar expressao");
				Expressao expressao;
				expressao = new Expressao(nome, conteudo);
				
				this.managerLinguagem.addExpressao(expressao);
				break;
			case GRAMATICA:
				System.out.println("salvar gramatica");
				Gramatica gramatica;
				gramatica = new Gramatica(nome, conteudo);
				
				this.managerLinguagem.addGramatica(gramatica);
				break;
			default:
				break;
		}
	}
	public void limpar() {
		this.textArea.setText("");
	}
}