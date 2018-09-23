package view.principal;

import javax.swing.JPanel;

import util.ELinguagem;
import view.EView;
import view.ViewInicio;
import view.automato.ViewCriarAutomato;
import view.automato.ViewEditarAutomato;
import view.automato.ViewOperarAutomato;
import view.gerador.ViewCriarGerador;
import view.gerador.ViewEditarGerador;

public class ManagerInterface {
	private ManagerLinguagem managerLinguagem;
	
	private ViewCriarAutomato viewCriarAutomato;
	private ViewEditarAutomato viewEditarAutomato;
	private ViewOperarAutomato viewOperarAutomato;
	
	private ViewCriarGerador viewCriarGeradorGR;
	private ViewEditarGerador viewEditarGeradorGR;
	
	private ViewCriarGerador viewCriarGeradorER;
	private ViewEditarGerador viewEditarGeradorER;
	
	private ViewInicio viewInicio;
	
	public ManagerInterface() {
		this.managerLinguagem = new ManagerLinguagem();
		
		this.viewCriarAutomato = new ViewCriarAutomato(this.managerLinguagem);
		this.viewEditarAutomato = new ViewEditarAutomato(this.managerLinguagem);
		this.viewOperarAutomato = new ViewOperarAutomato(this.managerLinguagem);
		
		this.viewCriarGeradorGR = new ViewCriarGerador(this.managerLinguagem, ELinguagem.GRAMATICA);
		this.viewEditarGeradorGR = new ViewEditarGerador(this.managerLinguagem, ELinguagem.GRAMATICA);
		
		this.viewCriarGeradorER = new ViewCriarGerador(this.managerLinguagem, ELinguagem.EXPRESSAO);
		this.viewEditarGeradorER = new ViewEditarGerador(this.managerLinguagem, ELinguagem.EXPRESSAO);
		
		this.viewInicio = new ViewInicio();
	}
	
	public JPanel alterarView(EView eView) {
		switch (eView) {
			/* AUTOMATO */
			case AF_CRIAR:
				
				return this.viewCriarAutomato.getJPanel();
			case AF_EDITAR:
				
				return this.viewEditarAutomato.getJPanel();
			case AF_OPERAR:
				
				return this.viewOperarAutomato.getJPanel();
				
			// GRAMATICA
			case GR_CRIAR:
				
				return this.viewCriarGeradorGR.getJPanel();
			case GR_EDITAR:
				this.viewEditarGeradorGR.reload();
				return this.viewEditarGeradorGR.getJPanel();
			
			// EXPRESSAO
			case ER_CRIAR:
				
				return this.viewCriarGeradorER.getJPanel();
			case ER_EDITAR:
				
				return this.viewEditarGeradorER.getJPanel();
			default:
				
				return this.viewInicio.getJPanel();
		}
	}
}
