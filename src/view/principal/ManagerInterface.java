package view.principal;

import util.ELinguagem;
import view.EView;
import view.View;
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
	
	public View alterarView(EView eView) {
		View view;
		
		switch (eView) {
			/* AUTOMATO */
			case AF_CRIAR:
				view = this.viewCriarAutomato;
				break;
			case AF_EDITAR:
				view = this.viewEditarAutomato;
				break;
			case AF_OPERAR:
				view = this.viewOperarAutomato;
				break;
				
			// GRAMATICA
			case GR_CRIAR:
				view = this.viewCriarGeradorGR;
				break;
			case GR_EDITAR:
				view = this.viewEditarGeradorGR;
				break;
			
			// EXPRESSAO
			case ER_CRIAR:
				view = this.viewCriarGeradorER;
				break;
			case ER_EDITAR:
				view = this.viewEditarGeradorER;
				break;
			default:
				view = this.viewInicio;
				break;
		}
		
		return view;
	}
}
