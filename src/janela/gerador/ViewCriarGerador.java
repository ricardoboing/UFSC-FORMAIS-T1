package janela.gerador;

import javax.swing.JButton;
import javax.swing.JTextField;

import expressao.Expressao;
import gramatica.Gramatica;
import janela.View;
import janela.component.TextArea;
import janela.event.EventViewCriarGerador;
import janela.principal.ManagerLinguagem;
import janela.principal.Window;
import util.Arquivo;
import util.ELinguagem;

public class ViewCriarGerador extends View {
	private ManagerLinguagem managerLinguagem;
	private ELinguagem eLinguagem;
	
	private TextArea textArea;
	private JTextField inputNome;
	
	private JButton buttonSalvar;
	private JButton buttonLimpar;
	private JButton buttonSelecionarArquivo;
	
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
		this.buttonSalvar.setActionCommand("SALVAR");
		this.buttonSalvar.addActionListener(event);
		this.buttonSalvar.setBounds(530, 519, 120, 35);
		
		this.buttonLimpar = new JButton("Limpar");
		this.buttonLimpar.setActionCommand("LIMPAR");
		this.buttonLimpar.addActionListener(event);
		this.buttonLimpar.setBounds(659, 519, 120, 35);
		
		this.buttonSelecionarArquivo = new JButton("Selecionar Arquivo");
		this.buttonSelecionarArquivo.setActionCommand("SELECIONAR_ARQUIVO");
		this.buttonSelecionarArquivo.addActionListener(event);
		this.buttonSelecionarArquivo.setBounds(100, 519, 250, 35);
		
		this.addComponent(this.buttonSalvar);
		this.addComponent(this.buttonLimpar);
		this.addComponent(this.buttonSelecionarArquivo);
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
		String nome, extensao, stringTextArea;
		nome = this.inputNome.getText();
		stringTextArea = this.textArea.getText();
		extensao = "";
		
		switch (this.eLinguagem) {
			case GRAMATICA:
				if (!Gramatica.entradaValida(stringTextArea)) {
					Window.insertMessageEntradaInvalida();
					break;
				}
				
				Gramatica gramatica;
				gramatica = new Gramatica(nome, stringTextArea);
				
				this.managerLinguagem.addGramatica(gramatica);
				this.managerLinguagem.gerarNomeNovoGramatica();
				
				extensao = Arquivo.extensaoGramatica;
				
				Window.insertMessage("Gramatica inserida com sucesso!", "Sucesso!");
				break;
			case EXPRESSAO:
				if (!Expressao.entradaValida(stringTextArea)) {
					Window.insertMessageEntradaInvalida();
					break;
				}
				
				Expressao expressao;
				expressao = new Expressao(nome, stringTextArea);
				
				this.managerLinguagem.addExpressao(expressao);
				this.managerLinguagem.gerarNomeNovoExpressao();
				
				extensao = Arquivo.extensaoExpressao;
				
				Window.insertMessage("Expressao inserida com sucesso!", "Sucesso!");
				break;
			default:
				break;
		}
		
		if (!extensao.equals("")) {
			Arquivo.escrever(nome, stringTextArea, extensao);
		}
		
		this.atualizar();
	}
	public void limpar() {
		this.textArea.setText("");
	}
	@Override
	public void atualizar() {
		this.atualizarNome();
		this.jPanel.repaint();
	}
	public void selecionarArquivo() {
		String extensao;
		if (eLinguagem == ELinguagem.GRAMATICA) {
			extensao = Arquivo.extensaoGramatica;
		} else {
			extensao = Arquivo.extensaoExpressao;
		}
		
		String conteudo;
		conteudo = Arquivo.openFile(extensao);
		
		if (conteudo == null) {
			return;
		}
		
		this.textArea.setText(conteudo);
	}
}
