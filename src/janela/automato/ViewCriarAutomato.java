package janela.automato;

import javax.swing.JButton;
import javax.swing.JTextField;

import automato.Automato;
import janela.View;
import janela.component.TableAutomato;
import janela.component.ViewTableAutomato;
import janela.event.EventViewCriarAutomato;
import janela.principal.ManagerLinguagem;
import janela.principal.Window;
import util.Arquivo;

public class ViewCriarAutomato extends View {
	private ViewTableAutomato viewTableAutomato;
	private JTextField inputNome;
	private ManagerLinguagem managerLinguagem;
	
	private JButton buttonSalvar;
	private JButton buttonLimpar;
	private JButton buttonSelecionarArquivo;
	
	private TableAutomato table;
	
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
		
		this.table = new TableAutomato();
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
		
		this.buttonSelecionarArquivo = new JButton("Selecionar Arquivo");
		this.buttonSelecionarArquivo.setActionCommand("SELECIONAR_ARQUIVO");
		this.buttonSelecionarArquivo.addActionListener(event);
		this.buttonSelecionarArquivo.setBounds(100, 519, 250, 35);
		
		this.addComponent(this.buttonSalvar);
		this.addComponent(this.buttonLimpar);
		this.addComponent(this.buttonSelecionarArquivo);
	}
	public void salvar() {
		if (!this.viewTableAutomato.validar()) {
			return;
		}
		
		Automato novoAutomato;
		novoAutomato = this.viewTableAutomato.getAutomato();
		novoAutomato.setNomeOperacaoGerador("NOVO AF");
		novoAutomato.setNome( this.managerLinguagem.getNomeNovoAutomato() );
		novoAutomato.print();
		
		this.managerLinguagem.gerarNomeNovoAutomato();
		this.managerLinguagem.addAutomato(novoAutomato);
		
		this.atualizarNome();
		Window.insertMessage("Automato salvo com sucesso!", "Sucesso!");
	}
	public void limpar() {
		this.viewTableAutomato.limpar();
	}
	private void atualizarNome() {
		this.inputNome.setText(this.managerLinguagem.getNomeNovoAutomato());
	}
	@Override
	public void atualizar() {
		this.atualizarNome();
		this.limpar();
	}
	public void selecionarArquivo() {
		String stringAutomato;
		stringAutomato = Arquivo.openFile(Arquivo.extensaoAutomato);
		
		if (stringAutomato == null) {
			return;
		}
		
		Automato automato;
		automato = new Automato("", stringAutomato);
		
		this.table.setAutomato(automato);
		this.viewTableAutomato.setTable(this.table);
		this.viewTableAutomato.recarregarViewTableAutomato();
	}
}
