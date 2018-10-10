import automato.Automato;
import automato.Estado;
import automato.OperarAutomato;
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
		w = new Window();
		/*
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
		
		*/
		
		Automato automato;
		automato = new Automato();
		
		Estado q0, q1, q2, q3;
		q0 = new Estado("q0");
		q1 = new Estado("q1");
		q1.setFinal(true);
		q2 = new Estado("q2");
		
		automato.setEstadoInicial(q0);
		automato.addEstado(q0);
		automato.addEstado(q1);
		automato.addEstado(q2);
		
		q0.addTransicao('0', q1);
		q0.addTransicao('1', q2);
		q1.addTransicao('0', q1);
		q1.addTransicao('1', q2);
		q2.addTransicao('0', q1);
		q2.addTransicao('1',q2);
		
		q0 = new Estado("q0");
		q1 = new Estado("q1");
		q2 = new Estado("q2");
		q2.setFinal(true);
		q3 = new Estado("q3");
		q3.setFinal(true);
		
		Automato automato2;
		automato2 = new Automato();
		automato2.setEstadoInicial(q0);
		automato2.addEstado(q0);
		automato2.addEstado(q1);
		automato2.addEstado(q2);
		automato2.addEstado(q3);
		
		q0.addTransicao('0', q1);
		q0.addTransicao('1', q2);
		q1.addTransicao('0', q1);
		q1.addTransicao('1', q2);
		q2.addTransicao('1', q1);
		q2.addTransicao('0', q3);
		q3.addTransicao('0', q2);
		q3.addTransicao('1', q3);
		
		automato = OperarAutomato.complementarAutomato(automato);
		automato.print();
		
		automato2 = OperarAutomato.complementarAutomato(automato2);
		automato2.print();
		
		///q.addTransicao('',);
	}
}
