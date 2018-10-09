import automato.Automato;
import expressao.NoDeSimone;
import janela.principal.Window;

public class Main {
	/*	[X] Minimizacao AFD (falta arrumar as chamadas do metodo)
	 * 	[X] Interseccao AFD (falta teste exaustivo)
	 * 	[X] Uniao AFD       (falta teste exaustivo)
	 *  [X] AFND -> AFD     (falta teste exaustivo)
	 *  [X] GR   -> AFND    (falta teste exaustivo)
	 * 	[X] AFD  -> GR      (falta teste exaustivo)
	 * 	[X] ER   -> AFD     (falta teste exaustivo; gerando estado duplicado)
	 */
	public static void main(String[] args) {
		Window w;
		//w = new Window();
		
		NoDeSimone no;
		//no = new NoDeSimone("(ab|ac)*a?(ba?c)*");
		//no = new NoDeSimone("(ab|ac)*a?|(ba?c)*");
		no = new NoDeSimone("((((a)(b)(c | d) | e) | (f)))");
		no = new NoDeSimone("((((a)(b)(c | d) | e) | (f)))*");
		no = new NoDeSimone("(a|b)?g*");
		//no = new NoDeSimone("(a|b).c");
		//no = new NoDeSimone("1?1?(00?11?)*0?0?");
		//no = new NoDeSimone("(a|b)?((ab)*(ba)*)*(a|b)?");
		no.gerarArvoreSintatica();
		
		String value;
		value = no.arvoreToString();
		
		System.out.println(value);
		
		Automato automato;
		automato = no.gerarAutomato();
		automato.print();
	}
}
