package br.ufpe.cin.if688.minijava.main;


//Definição dos imports
import br.ufpe.cin.if688.minijava.ANTLR.*;
import br.ufpe.cin.if688.minijava.ast.*;
import br.ufpe.cin.if688.minijava.visitor.PrettyPrintVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

//

public class Main {

	public static void main(String[] args) {
		String caminhos[] = new String[8];
		String aux = "C:\\Users\\Lucas Aurelio\\Desktop\\MiniJava-master\\src\\main\\java\\br\\ufpe\\cin\\if688\\minijava\\main\\tests\\";
		caminhos[0] = aux+"BinarySearch";
		caminhos[1] = aux+"BinaryTree";
		caminhos[2] = aux+"BubbleSort";
		caminhos[3] = aux+"Factorial";
		caminhos[4] = aux+"LinearSearch";
		caminhos[5] = aux+"LinkedList";
		caminhos[6] = aux+"QuickSort";
		caminhos[7] = aux+"TreeVisitor";
		for(String caminho : caminhos){
			try {
				CharStream charStream = CharStreams.fromFileName(caminho);
				gramaticaLexer lexer = new gramaticaLexer(charStream);
				CommonTokenStream cts = new CommonTokenStream(lexer);
				gramaticaParser parser = new gramaticaParser(cts);
				MiniJavaVisitor mjv = new MiniJavaVisitor();
				Program goal = (Program) mjv.visit(parser.goal());
				PrettyPrintVisitor ppv = new PrettyPrintVisitor();
				ppv.visit(goal);
				System.out.println();
			}catch (IOException e){
				System.out.println("deu errado em: "+caminho);
			}
		}
	}
}