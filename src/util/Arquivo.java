/*
 *  Trabalho I: Algoritmos para Manipulacao de Linguagens Regulares
 *  
 *  Departamento de Informatica e Estatistica – Universidade Federal de Santa Catarina (UFSC)
 *  Campus Reitor Joao David Ferreira Lima, 88.040-900 – Florianopolis – SC – Brasil
 *  
 *  brunohonnef@gmail.com pedroabcorte@gmail.com ricardoboing.ufsc@gmail.com
 *  
 *  Bruno Gilmar Honnef
 *  Pedro Alexandre Barradas da Corte
 *  Ricardo do Nascimento Boing
 *  
 *  11 de Outubro de 2018
 */
package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import janela.principal.Window;

public class Arquivo {
	public final static String extensaoAutomato = "af";
	public final static String extensaoGramatica = "gr";
	public final static String extensaoExpressao = "er";
	
	public static String ler(String nomeArquivo) {
		BufferedReader buffRead;
		try {
			buffRead = new BufferedReader(new FileReader(nomeArquivo));
		
			String valorArquivo;
			valorArquivo = "";
			
			while (true) {
				String valorLinha;
				valorLinha = buffRead.readLine();
				
				if (valorLinha == null) {
					break;
				}
				if (!valorArquivo.equals("")) {
					valorArquivo += "\n";
				}
				valorArquivo += valorLinha;
			}
			
			buffRead.close();
			return valorArquivo;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {}
		
		Window.insertMessageFalha("Falha ao ler arquivo!");
		return null;
	}
	public static void escrever(String nomeArquivo, String conteudo, String extensao) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_SS");
		sdf.setLenient(false);
		
		Date data;
		data = new Date();
		
		nomeArquivo = sdf.format(data)+"_"+nomeArquivo;
		
		BufferedWriter buffWrite;
		
		try {
			String src;
			src = "arquivos/"+extensao+"/"+nomeArquivo+"."+extensao;
			
			FileWriter fileWriter;
			fileWriter = new FileWriter(src);
			
			buffWrite = new BufferedWriter(fileWriter);
	        buffWrite.append(conteudo);
	        buffWrite.close();
	        return;
		} catch (IOException e) {}
		
		Window.insertMessageFalha("Falha ao salvar arquivo!");
	}
	
	public static String openFile(String extensao) {
		String diretorioAplicacao;
		diretorioAplicacao = System.getProperty("user.dir")+"/arquivos/"+extensao;
		
		FileNameExtensionFilter filtros;
		filtros = new FileNameExtensionFilter("."+extensao, extensao);
		
		JFileChooser chooser;
		chooser = new JFileChooser(diretorioAplicacao);  
		chooser.setFileFilter(filtros);  
		chooser.setAcceptAllFileFilterUsed(false);  
		
		if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
		   return null;
		}
		
		String valor;
		valor = null;
		
		File file = chooser.getSelectedFile();
		if (file != null) {
			valor = Arquivo.ler(file.getAbsolutePath());
		}
		
		return valor;
	}
}
