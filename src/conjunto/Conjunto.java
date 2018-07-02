package conjunto;

public class Conjunto<T> extends ConjuntoGenerico<T> {
	public Conjunto() {
		super();
	}
	
	public boolean equals(Conjunto<T> conjuntoComparar) {
		/* Para que os conjuntos sejam equivalentes eh preciso
		 * que todos objetos de um estejam no outro (mesmo numero
		 * de elementos)
		 */
		if (this.size() != conjuntoComparar.size()) {
			return false;
		}
		/* Verifica a existencia de algum objeto nao pertencente
		 * simultaneamente aos dois conjuntos (esteja contido em
		 * um dos conjuntos mas nao esteja no outro)
		 */
		for (int c = 0; c < this.size(); c++) {
			T objeto;
			objeto = this.array.get(c);
			
			if (!conjuntoComparar.contains(objeto)) {
				return false;
			}
		}
		
		return true;
	}
}
