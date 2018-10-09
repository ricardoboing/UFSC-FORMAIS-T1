package janela.gerador;

import javax.swing.JButton;
import javax.swing.JTextField;

import automato.Automato;
import expressao.Expressao;
import gramatica.Gramatica;
import janela.IViewEditar;
import janela.View;
import janela.component.MenuLateral;
import janela.component.TextArea;
import janela.event.EventViewEditarGerador;
import janela.principal.ManagerLinguagem;
import janela.principal.Window;
import util.Arquivo;
import util.ELinguagem;
import util.LinguagemGerador;

public class ViewEditarGerador extends View implements IViewEditar {
	private ManagerLinguagem managerLinguagem;
	private ELinguagem eLinguagem;
	
	private TextArea textArea;
	private JTextField inputNome;
	
	private MenuLateral menuLateral;
	private LinguagemGerador geradorSelecionado;
	
	private JButton buttonSalvarEmDisco;
	private JButton buttonEditar, buttonSalvar, buttonCancelar;
	private JButton buttonGerarAF, buttonRemover;
	
	public ViewEditarGerador(ManagerLinguagem managerLinguagem, ELinguagem eLinguagem) {
		super();
		this.managerLinguagem = managerLinguagem;
		this.eLinguagem = eLinguagem;
		
		this.menuLateral = new MenuLateral(this);
		
		this.inputNome = new JTextField();
		this.inputNome.setEditable(false);
		this.inputNome.setBounds(330, 20, 200, 25);
		
		this.textArea = new TextArea(230, 62, 560, 340);
		
		this.addJLabel(230, 20, 70, 25, "Nome:");
		this.addComponent(this.inputNome);
		this.addComponent(this.menuLateral.getJPanel());
		this.addComponent(this.textArea.getJScrollPane());
		
		this.loadButton();
	}
	private void loadButton() {
		EventViewEditarGerador event;
		event = new EventViewEditarGerador(this);
		
		this.buttonEditar = new JButton("Editar");
		this.buttonSalvar = new JButton("Salvar");
		this.buttonSalvarEmDisco = new JButton("Salvar em disco");
		this.buttonCancelar = new JButton("Cancelar");
		this.buttonGerarAF = new JButton("Gerar AF");
		this.buttonRemover = new JButton("Remover");
		
		this.buttonEditar.setActionCommand("EDITAR");
		this.buttonSalvar.setActionCommand("SALVAR");
		this.buttonSalvarEmDisco.setActionCommand("SALVAR_EM_DISCO");
		this.buttonCancelar.setActionCommand("CANCELAR");
		this.buttonGerarAF.setActionCommand("GERAR_AF");
		this.buttonRemover.setActionCommand("REMOVER");
		
		this.buttonSalvar.addActionListener(event);
		this.buttonSalvarEmDisco.addActionListener(event);
		this.buttonCancelar.addActionListener(event);
		this.buttonEditar.addActionListener(event);
		this.buttonGerarAF.addActionListener(event);
		this.buttonRemover.addActionListener(event);
		
		this.buttonSalvar.setBounds(530, 519, 120, 35);
		this.buttonSalvarEmDisco.setBounds(360, 519, 160, 35);
		this.buttonCancelar.setBounds(659, 519, 130, 35);
		this.buttonEditar.setBounds(530, 519, 120, 35);
		this.buttonGerarAF.setBounds(230, 519, 120, 35);
		this.buttonRemover.setBounds(659, 519, 130, 35);
		
		this.addComponent(this.buttonEditar);
		this.addComponent(this.buttonSalvar);
		this.addComponent(this.buttonSalvarEmDisco);
		this.addComponent(this.buttonCancelar);
		this.addComponent(this.buttonGerarAF);
		this.addComponent(this.buttonRemover);
		
		this.disableComponents();
	}
	
	@Override
	public void setLinguagem(String nome) {
		switch (this.eLinguagem) {
			case GRAMATICA:
				Gramatica gramatica;
				gramatica = this.managerLinguagem.getGramatica(nome);
				
				this.geradorSelecionado = gramatica;
				
				this.textArea.setText(gramatica.getStringConjuntoProducao());
				break;
			case EXPRESSAO:
				Expressao expressao;
				expressao = this.managerLinguagem.getExpressao(nome);
				
				this.geradorSelecionado = expressao;
				
				this.textArea.setText(expressao.getToStringOriginal());
			default:
				break;
		}
		
		this.cancelar();
	}
	
	public void gerarAf() {
		String siglaOperador;
		siglaOperador = "";
		
		switch (this.eLinguagem) {
			case GRAMATICA:
				siglaOperador += "G";
				break;
			case EXPRESSAO:
				siglaOperador += "E";
				break;
			default:
				break;
		}
		
		String nomeAutomato;
		nomeAutomato = siglaOperador+"."+this.managerLinguagem.getNomeNovoAutomato();
		this.managerLinguagem.gerarNomeNovoAutomato();
		
		Automato novoAutomato;
		novoAutomato = new Automato(nomeAutomato, this.geradorSelecionado);
		novoAutomato.setNomePai1( this.geradorSelecionado.getNome() );
		novoAutomato.setNomeOperacaoGerador(siglaOperador+"R -> AF");
		
		this.managerLinguagem.addAutomato(novoAutomato);
		
		Window.insertMessage("Automato gerado com sucesso!", "Sucesso!");
	}
	public void editar() {
		this.buttonSalvar.setVisible(true);
		this.buttonCancelar.setVisible(true);
		this.buttonSalvarEmDisco.setVisible(false);
		
		this.buttonEditar.setVisible(false);
		this.buttonGerarAF.setVisible(false);
		this.buttonRemover.setVisible(false);
		
		this.textArea.setEnable(true);
	}
	public void salvar() {
		String stringTextArea;
		stringTextArea = this.textArea.getText();
		
		switch (this.eLinguagem) {
			case GRAMATICA:
				if (!Gramatica.entradaValida(stringTextArea)) {
					Window.insertMessageEntradaInvalida();
					break;
				}
				
				Gramatica gramaticaEditando;
				gramaticaEditando = (Gramatica)this.geradorSelecionado;
				gramaticaEditando.gerarGramatica(stringTextArea);
				
				Window.insertMessage("Gramatica salva com sucesso!", "Sucesso!");
				break;
			case EXPRESSAO:
				if (!Expressao.entradaValida(stringTextArea)) {
					Window.insertMessageEntradaInvalida();
					break;
				}
				
				Window.insertMessage("Expressao salva com sucesso!", "Sucesso!");
				break;
			default:
				break;
		}
		
		this.buttonSalvar.setVisible(false);
		this.buttonCancelar.setVisible(false);
		this.buttonSalvarEmDisco.setVisible(true);
		
		this.buttonEditar.setVisible(true);
		this.buttonGerarAF.setVisible(true);
		this.buttonRemover.setVisible(true);
		
		this.textArea.setEnable(false);
	}
	public void cancelar() {
		this.buttonSalvar.setVisible(false);
		this.buttonCancelar.setVisible(false);
		this.buttonSalvarEmDisco.setVisible(true);
		
		this.buttonEditar.setVisible(true);
		this.buttonGerarAF.setVisible(true);
		this.buttonRemover.setVisible(true);
		
		this.textArea.setEnable(false);
	}
	
	private void disableComponents() {
		this.buttonSalvar.setVisible(false);
		this.buttonCancelar.setVisible(false);
		this.buttonEditar.setVisible(false);
		this.buttonGerarAF.setVisible(false);
		this.buttonRemover.setVisible(false);
		this.buttonSalvarEmDisco.setVisible(false);
		
		this.textArea.setEnable(false);
		this.textArea.setText("");
	}
	@Override
	public void atualizar() {
		switch (this.eLinguagem) {
			case EXPRESSAO:
				this.menuLateral.setMenu( this.managerLinguagem.getConjuntoExpressao() );
				break;
			case GRAMATICA:
				this.menuLateral.setMenu( this.managerLinguagem.getConjuntoGramatica() );
				break;
			default:
				break;
		}
		
		this.disableComponents();
	}
	public void remover() {
		switch (this.eLinguagem) {
			case EXPRESSAO:
				this.managerLinguagem.removerExpressao((Expressao)this.geradorSelecionado);
				this.menuLateral.setMenu(this.managerLinguagem.getConjuntoExpressao());
				break;
			case GRAMATICA:
				this.managerLinguagem.removerGramatica((Gramatica)this.geradorSelecionado);
				this.menuLateral.setMenu(this.managerLinguagem.getConjuntoGramatica());
				break;
			default:
				break;
		}
		
		this.geradorSelecionado = null;
		this.atualizar();
	}
	public void salvarEmDisco() {
		if (this.geradorSelecionado == null) {
			Window.insertMessageFalha("Operacao falhou!");
			return;
		}
		
		String stringTextArea, extensao;
		stringTextArea = this.textArea.getText();
		extensao = "";
		
		switch (this.eLinguagem) {
			case GRAMATICA:
				extensao = Arquivo.extensaoGramatica;
				Window.insertMessage("Gramatica salva com sucesso!", "Sucesso!");
				break;
			case EXPRESSAO:
				extensao = Arquivo.extensaoExpressao;
				Window.insertMessage("Expressao salva com sucesso!", "Sucesso!");
				break;
			default:
				break;
		}
		
		String nome;
		nome = this.geradorSelecionado.getNome();
		
		if (!extensao.equals("")) {
			Arquivo.escrever(nome, stringTextArea, extensao);
		}
	}
}
