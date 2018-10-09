package expressao;

import automato.Estado;
import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoObject;

class EstadoDeSimone {
	private ConjuntoObject<ComposicaoDeSimone> conjuntoComposicao;
	private ConjuntoAlfabeto conjuntoAlfabeto;
	private Estado estado;
	
	public EstadoDeSimone() {
		this(false);
	}
	public EstadoDeSimone(boolean estadoInicial) {
		this.conjuntoComposicao = new ConjuntoObject<ComposicaoDeSimone>();
		this.conjuntoAlfabeto = new ConjuntoAlfabeto();
		
		this.estado = new Estado("q");
		this.estado.setInicial(estadoInicial);
		
		// Simbolo qualquer para nao conflitar com nome de outros estados.
		// O simbolo eh alterado apos o Automato ser gerado na classe NoDeSimone.
		this.estado.setSimbolo(this.estado.toString());
	}
	
	public void addNoDeSimone(NoDeSimone no) {
		char simboloNo;
		simboloNo = no.getSimbolo().charAt(0);
		
		if (no.nodoFimDaArvore()) {
			this.estado.setFinal(true);
			return;
		}
		
		if (this.conjuntoAlfabeto.contains(simboloNo)) {
			for (int c = 0; c < this.conjuntoAlfabeto.size(); c++) {
				ComposicaoDeSimone composicao;
				composicao = conjuntoComposicao.get(c);
				
				if (composicao.getSimbolo() == simboloNo) {
					composicao.addNoDeSimone(no);
					
					return;
				}
			}
		} else {
			ComposicaoDeSimone novaComposicao;
			novaComposicao = new ComposicaoDeSimone(simboloNo);
			novaComposicao.addNoDeSimone(no);
			
			this.conjuntoComposicao.add(novaComposicao);
			this.conjuntoAlfabeto.add(simboloNo);
		}
		
		return;
	}
	
	public void atualizarConjuntoComposicao(ConjuntoObject<ComposicaoDeSimone> conjuntoComposicao) {
		ConjuntoObject<ComposicaoDeSimone> conjuntoComposicaoFilho;
		conjuntoComposicaoFilho = new ConjuntoObject<ComposicaoDeSimone>();
		
		for (int c = 0; c < this.conjuntoComposicao.size(); c++) {
			ComposicaoDeSimone composicao;
			composicao = this.conjuntoComposicao.get(c);
			composicao = conjuntoComposicao.add(composicao);
			
			conjuntoComposicaoFilho.add(composicao);
		}
		
		this.conjuntoComposicao = conjuntoComposicaoFilho;
	}
	
	public Estado gerarEstadoAf() {
		for (int c = 0; c < this.conjuntoComposicao.size(); c++) {
			ComposicaoDeSimone composicao;
			composicao = this.conjuntoComposicao.get(c);
			
			EstadoDeSimone estadoDaComposicao;
			estadoDaComposicao = composicao.getEstadoDeSimone();
			
			Estado estadoAf;
			estadoAf = estadoDaComposicao.estado;
			
			char simboloEntrada;
			simboloEntrada = composicao.getSimbolo();
			
			this.estado.addTransicao(simboloEntrada, estadoAf);
		}
		
		return this.estado;
	}
	
	@Override
	public boolean equals(Object obj) {
		EstadoDeSimone estadoEquals;
		estadoEquals = (EstadoDeSimone) obj;
		
		if (this.conjuntoComposicao.size() != estadoEquals.conjuntoComposicao.size()) {
			return false;
		}
		
		for (int c = 0; c < this.conjuntoComposicao.size(); c++) {
			ComposicaoDeSimone composicaoThis;
			composicaoThis = this.conjuntoComposicao.get(c);
			
			boolean existeEquivalente;
			existeEquivalente = false;
			
			for (int i = 0; i < estadoEquals.conjuntoComposicao.size(); i++) {
				ComposicaoDeSimone composicaoEquals;
				composicaoEquals = estadoEquals.conjuntoComposicao.get(i);
				
				if (composicaoThis.equals(composicaoEquals)) {
					existeEquivalente = true;
					break;
				}
			}
			if (!existeEquivalente) {
				return false;
			}
		}
		
		return true;
	}
}
