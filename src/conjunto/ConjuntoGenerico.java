package conjunto;

import java.util.ArrayList;
import java.util.Iterator;

abstract class ConjuntoGenerico<T> {
	protected ArrayList<T> array;
	
	ConjuntoGenerico() {
		this.array = new ArrayList<T>();
	}
	
	public T add(T objeto) {
		/* Verifica se o simbolo ja foi adicionado ou se
		 * ele eh o eplison
		 */
		if (!this.contains(objeto)) {
			this.array.add(objeto);
			return objeto;
		}
		
		return objeto;
	}
	public void add(ConjuntoGenerico<T> conjunto) {
		for (int c = 0; c < conjunto.size(); c++) {
			T objeto;
			objeto = conjunto.get(c);
			
			this.add(objeto);
		}
	}
	
	public boolean remove(T objeto) {
		if ( this.contains(objeto) ) {
			this.array.remove(objeto);
			return true;
		}
		
		return false;
	}
	public void removeAll() {
		this.array.removeAll(this.array);
	}
	
	public int size() {
		return this.array.size();
	}
	public boolean isEmpty() {
		return this.array.isEmpty();
	}
	public boolean contains(T objeto) {
		return this.array.contains(objeto);
	}
	public T get(int c) {
		return this.array.get(c);
	}
	public ArrayList<T> getArrayList() {
		return this.array;
	}
	public Iterator<T> getIterador() {
		return this.array.iterator();
	}
}
