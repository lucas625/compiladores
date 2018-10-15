package br.ufpe.cin.if688.visitor;

import br.ufpe.cin.if688.ast.AssignStm;
import br.ufpe.cin.if688.ast.CompoundStm;
import br.ufpe.cin.if688.ast.EseqExp;
import br.ufpe.cin.if688.ast.Exp;
import br.ufpe.cin.if688.ast.ExpList;
import br.ufpe.cin.if688.ast.IdExp;
import br.ufpe.cin.if688.ast.LastExpList;
import br.ufpe.cin.if688.ast.NumExp;
import br.ufpe.cin.if688.ast.OpExp;
import br.ufpe.cin.if688.ast.PairExpList;
import br.ufpe.cin.if688.ast.PrintStm;
import br.ufpe.cin.if688.ast.Stm;
import br.ufpe.cin.if688.symboltable.IntAndTable;
import br.ufpe.cin.if688.symboltable.Table;

public class IntAndTableVisitor implements IVisitor<IntAndTable> {
	private Table t;

	public IntAndTableVisitor(Table t) {
		this.t = t;
	}

	@Override
	public IntAndTable visit(Stm s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAndTable visit(AssignStm s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAndTable visit(CompoundStm s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAndTable visit(PrintStm s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAndTable visit(Exp e) {
		//precisamos lidar com os tipos de exp:(id,num,eseq,op)
		//os demais casos serão lidados no interpreter.
		return e.accept(this);
	}

	@Override
	public IntAndTable visit(EseqExp e) {
		Interpreter v = new Interpreter(t);
		t = e.getStm().accept(v);//organizando a table para colocar as coisas que tinham na part stm do eseq
		return e.getExp().accept(this);
	}

	@Override
	public IntAndTable visit(IdExp e) {
		String id = e.getId();
		if(t == null)
			throw new RuntimeException("variável não declarada");
		Table aux = t;
		while(aux.id!=id){
			if(aux.tail==null)
				throw new RuntimeException("variável não declarada");
			aux = aux.tail;
		}
		double valor = aux.value;
		IntAndTable aux2 = new IntAndTable(valor, t);
		return aux2;
	}

	@Override
	public IntAndTable visit(NumExp e) {//caso de achar um número
		IntAndTable aux = new IntAndTable(e.getNum(),t);
		return aux;
	}

	@Override
	public IntAndTable visit(OpExp e) {//lidando com o caso de soma, sub, mult e div
		Exp esquerda = e.getLeft();
		Exp direita = e.getRight();
		double esqR = esquerda.accept(this).result;
		double dirR = direita.accept(this).result;
		int a = e.getOper();
		IntAndTable aux;
		double resultado=0;
		if(a==1)
			resultado = esqR + dirR;
		else if(a==2)
			resultado = esqR - dirR;
		else if(a==3)
			resultado = esqR * dirR;
		else if(a==4)
			resultado = esqR / dirR;
		else
			throw new RuntimeException("Simbolo inválido");
		aux = new IntAndTable(resultado,t);
		return aux;
	}

	@Override
	public IntAndTable visit(ExpList el) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAndTable visit(PairExpList el) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAndTable visit(LastExpList el) {
		// TODO Auto-generated method stub
		return null;
	}


}
