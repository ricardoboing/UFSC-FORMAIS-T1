package view.gerador;

import javax.swing.JButton;
import javax.swing.JTextField;

import util.ELinguagem;
import util.LinguagemGerador;
import view.IViewEditar;
import view.View;
import view.component.MenuLateral;
import view.component.TextArea;
import view.event.EventViewEditarGerador;
import view.principal.ManagerLinguagem;

public class ViewEditarGerador extends View implements IViewEditar {
	private ManagerLinguagem managerLinguagem;
	private ELinguagem eLinguagem;
	
	private TextArea textArea;
	private JTextField inputNome;
	
	private MenuLateral menuLateral;
	private LinguagemGerador geradorSelecionado;
	
	private JButton buttonEditar, buttonSalvar, buttonCancelar;
	private JButton buttonGerarAF;
	
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
		this.buttonCancelar = new JButton("Cancelar");
		this.buttonGerarAF = new JButton("GerarAF");
		
		this.buttonEditar.setActionCommand("EDITAR");
		this.buttonSalvar.setActionCommand("SALVAR");
		this.buttonCancelar.setActionCommand("CANCELAR");
		this.buttonGerarAF.setActionCommand("GERAR_AF");
		
		this.buttonSalvar.addActionListener(event);
		this.buttonCancelar.addActionListener(event);
		this.buttonEditar.addActionListener(event);
		this.buttonGerarAF.addActionListener(event);
		
		this.buttonSalvar.setBounds(530, 519, 120, 35);
		this.buttonCancelar.setBounds(659, 519, 130, 35);
		this.buttonEditar.setBounds(530, 519, 120, 35);
		this.buttonGerarAF.setBounds(659, 519, 130, 35);
		
		this.addComponent(this.buttonEditar);
		this.addComponent(this.buttonSalvar);
		this.addComponent(this.buttonCancelar);
		this.addComponent(this.buttonGerarAF);
		
		this.cancelar();
	}
	
	@Override
	public void setLinguagem(String nome) {
		
	}
	
	public void editar() {
		//this.editando = true;
		this.buttonSalvar.setVisible(true);
		this.buttonCancelar.setVisible(true);
		
		this.buttonEditar.setVisible(false);
		this.buttonGerarAF.setVisible(false);
		
		this.textArea.setEnable(true);
	}
	public void salvar() {
		//this.editando = false;
		this.buttonSalvar.setVisible(false);
		this.buttonCancelar.setVisible(false);
		
		this.buttonEditar.setVisible(true);
		this.buttonGerarAF.setVisible(true);
		
		this.textArea.setEnable(false);
	}
	public void cancelar() {
		//this.editando = false;
		this.buttonSalvar.setVisible(false);
		this.buttonCancelar.setVisible(false);
		
		this.buttonEditar.setVisible(true);
		this.buttonGerarAF.setVisible(true);
		
		this.textArea.setEnable(false);
	}
	
	public void reload() {
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
		
		this.menuLateral.repaint();
	}
}
