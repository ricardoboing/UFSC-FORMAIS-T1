package expressao;

import automato.Estado;
import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoObject;

class ComposicaoDeSimone {
	private ConjuntoObject<ComposicaoDeSimone> conjuntoComposicaoFilho;
	private ConjuntoObject<NoDeSimone> conjuntoNoDeSimone;
	private ConjuntoAlfabeto conjuntoAlfabeto;
	private Estado estado;
	private int indiceComposicao;
	
	public ComposicaoDeSimone(int indiceComposicao, boolean estadoInicial) {
		this.conjuntoComposicaoFilho = new ConjuntoObject<ComposicaoDeSimone>();
		this.conjuntoNoDeSimone = new ConjuntoObject<NoDeSimone>();
		this.conjuntoAlfabeto = new ConjuntoAlfabeto();
		
		this.indiceComposicao = indiceComposicao;
		this.estado = new Estado("q"+indiceComposicao);
		this.estado.setInicial(estadoInicial);
	}
	public ComposicaoDeSimone(int indiceComposicao) {
		this(indiceComposicao, false);
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
				if (this.conjuntoAlfabeto.get(c) == simboloNo) {
					// Os indices do conjuntoAlfabeto e conjuntoComposicaoFilho estao sincronizados
					ComposicaoDeSimone composicaoBuscada;
					composicaoBuscada = this.conjuntoComposicaoFilho.get(c);
					composicaoBuscada.conjuntoNoDeSimone.add(no);
					
					return;
				}
			}
		} else {
			this.indiceComposicao++;
			
			ComposicaoDeSimone novaComposicao;
			novaComposicao = new ComposicaoDeSimone(indiceComposicao);
			novaComposicao.conjuntoNoDeSimone.add(no);
			
			this.conjuntoComposicaoFilho.add(novaComposicao);
			this.conjuntoAlfabeto.add(simboloNo);
		}
		
		return;
	}
	
	public void atualizarConjuntoComposicao(ConjuntoObject<ComposicaoDeSimone> conjuntoComposicao) {
		ConjuntoObject<ComposicaoDeSimone> conjuntoComposicaoFilho;
		conjuntoComposicaoFilho = new ConjuntoObject<ComposicaoDeSimone>();
		
		/* Ao adicionar uma composicao no conjuntoComposicao eh retornado a
		 * composicao equivalente, caso ela exista. Portanto eh atualizado o
		 * conjuntoGlobal (adicionando composicoes novas) e atualizado a lista
		 * de composicoesFilho (trocando composicoes ja existentes por suas equivalentes)
		 */
		for (int c = 0; c < this.conjuntoComposicaoFilho.size(); c++) {
			ComposicaoDeSimone composicao;
			composicao = conjuntoComposicao.add( this.conjuntoComposicaoFilho.get(c) );
			
			conjuntoComposicaoFilho.add(composicao);
		}
		
		this.conjuntoComposicaoFilho = conjuntoComposicaoFilho;
	}
	public Estado gerarEstado() {
		for (int c = 0; c < this.conjuntoComposicaoFilho.size(); c++) {
			Estado estadoDaComposicaoFilho;
			estadoDaComposicaoFilho = this.conjuntoComposicaoFilho.get(c).estado;
			
			// Os indices do conjuntoAlfabeto e conjuntoComposicaoFilho estao sincronizados
			char simboloEntrada;
			simboloEntrada = this.conjuntoAlfabeto.get(c);
			
			this.estado.addTransicao(simboloEntrada, estadoDaComposicaoFilho);
		}
		
		
		
		return this.estado;
	}
	public ConjuntoObject<NoDeSimone> getConjuntoNoDeSimone() {
		return this.conjuntoNoDeSimone;
	}
	
	@Override
	public boolean equals(Object obj) {
		ComposicaoDeSimone composicaoEquals;
		composicaoEquals = (ComposicaoDeSimone) obj;
		
		// Numero de noDeSimone eh diferente
		if (this.conjuntoNoDeSimone.size() != composicaoEquals.conjuntoNoDeSimone.size()) {
			return false;
		}
		System.out.print(this.estado.getSimbolo()+" -> ");
		// Verifica se todos os noDeSimone de uma composicao esta na outra
		for (int c = 0; c < this.conjuntoNoDeSimone.size(); c++) {
			NoDeSimone noThis, noEqual;
			noThis = this.conjuntoNoDeSimone.get(c);
			noEqual = composicaoEquals.conjuntoNoDeSimone.get(c);
			
			String s1, s2;
			s1 = noThis+"";
			s2 = noEqual+"";
			System.out.print(noThis.getSimbolo());
			System.out.print(": "+s1.substring(s1.length()-9));
			System.out.print("("+s2.substring(s1.length()-9)+")");
			System.out.print(" | ");
			
			if (!this.conjuntoNoDeSimone.contains( composicaoEquals.conjuntoNoDeSimone.get(c) )) {
				System.out.println();
				return false;
			}
		}
		System.out.println("???: "+this.conjuntoNoDeSimone.size());
		return true;
	}
}
