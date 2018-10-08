package util;

public class AlfabetoPortuguesMaiusculo {
	private int contador;
	private char[] array;
	
	public AlfabetoPortuguesMaiusculo() {
		this.contador = -1;
		
		this.array = new char[] {
			'S', 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'T',
			'U', 'V', 'W', 'Y', 'X',
			'Z'
		};
	}
	
	public String proximaLetra() {
		this.contador++;
		return this.array[this.contador]+"";
	}
	
	public static boolean validar(char valor) {
		char[] array = new char[] {
			'S', 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'T',
			'U', 'V', 'W', 'Y', 'X',
			'Z'
		};
		
		for (int c = 0; c < array.length; c++) {
			if (array[c] == valor) {
				return true;
			}
		}
		
		return false;
	}
}
