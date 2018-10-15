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

public class Interpreter implements IVisitor<Table> {

	//a=8;b=80;a=7;
	// a->7 ==> b->80 ==> a->8 ==> NIL
	private Table t;

	public Interpreter(Table t) {
		this.t = t;
	}

	@Override
	public Table visit(Stm s) {//stm padrão
		s.accept(this);
		return t;
	}

	@Override
	public Table visit(AssignStm s) {//caso de a=...
		String id = s.getId();
		IntAndTableVisitor v = new IntAndTableVisitor(t);
		IntAndTable aux = s.getExp().accept(v);
		double valor = aux.result;
		t = new Table(id,valor,aux.table);
		return t;
	}

	@Override
	public Table visit(CompoundStm s) {//dividindo os dois stm
		Table aux1 = s.getStm1().accept(this);
		Table aux2 = s.getStm2().accept(this);
		return t;
	}

	@Override
	public Table visit(PrintStm s) {
		s.getExps().accept(this);
		return t;
	}

	@Override
	public Table visit(Exp e) {//as exps precisamos lidar com IntAndTableVisitor
		IntAndTableVisitor v = new IntAndTableVisitor(t);
		t = e.accept(v).table;
		return t;
	}

	@Override
	public Table visit(EseqExp e) {
		IntAndTableVisitor v = new IntAndTableVisitor(t);
		t = e.accept(v).table;
		return t;
	}

	@Override
	public Table visit(IdExp e) {
		IntAndTableVisitor v = new IntAndTableVisitor(t);
		t = e.accept(v).table;
		return t;
	}

	@Override
	public Table visit(NumExp e) {//não precisamos passar pro int IntAndTable, pois não mudaria nada.
		return t;
	}

	@Override
	public Table visit(OpExp e) {
		IntAndTableVisitor v = new IntAndTableVisitor(t);
		t = e.accept(v).table;
		return t;
	}

	@Override
	public Table visit(ExpList el) {//precisaremos pegar a parte exp dos explist
		el.accept(this);
		return t;
	}

	@Override
	public Table visit(PairExpList el) {
		IntAndTableVisitor v = new IntAndTableVisitor(t);
		IntAndTable aux = el.getHead().accept(v);
		t = aux.table;
		System.out.print(aux.result+"\n");
		el.getTail().accept(this);
		return t;
	}

	@Override
	public Table visit(LastExpList el) {
		IntAndTableVisitor v = new IntAndTableVisitor(t);
		IntAndTable aux = el.getHead().accept(v);
		t = aux.table;
		System.out.print(aux.result+"\n");
		return t;
	}


}
