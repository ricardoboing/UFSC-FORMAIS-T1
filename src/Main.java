import automato.Automato;
import expressao.NoDeSimone;
import view.principal.Window;

public class Main {
	/*	[X] Minimizacao AFD (falta arrumar as chamadas do metodo)
	 * 	[ ] Interseccao AFD ()
	 * 	[ ] Uniao AFD       ()
	 *  [ ] AFND -> AFD     (falta &-transicao) - (S->aA|bB|b|a\nA->aA|a\nB->bB|b)
	 *  [X] GR   -> AFND    (falta teste exaustivo)
	 * 	[X] AFD  -> GR      (falta teste exaustivo)
	 * 	[ ] ER   -> AFD     (falta alguns bugs em casos aparentemente isolados; arvore sintatica funcionando; falta dar uma geral no codigo)
	 */
	public static void main(String[] args) {
		Window w;
		w = new Window();
		
		NoDeSimone no;
		//no = new NoDeSimone("(ab|ac)*a?(ba?c)*");
		//no = new NoDeSimone("(ab|ac)*a?|(ba?c)*");
		//no = new NoDeSimone("((((a)(b)(c | d) | e) | (f)))*");
		//no = new NoDeSimone("(a|b)");
		no = new NoDeSimone("(a|b).c");
		no = new NoDeSimone("1?1?(00?11?)*0?0?");
		no = new NoDeSimone("(1|0)?((10)*(01)*)*(1|0)?");
		no.gerarArvoreSintatica();
		
		String value;
		value = no.arvoreToString();
		
		System.out.println(value);
		
		Automato automato;
		automato = no.gerarAutomato();
		automato.print();
	}
}
