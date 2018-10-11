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

public class MaxArgsVisitor implements IVisitor<Integer> {
	int printAtual =0;
	@Override
	public Integer visit(Stm s) {
		return s.accept(this);
	}

	@Override
	public Integer visit(AssignStm s) {
		return s.getExp().accept(this);
	}

	@Override
	public Integer visit(CompoundStm s) {
		int esq = s.getStm1().accept(this);
		int dir = s.getStm2().accept(this);
		return Math.max(esq, dir);
	}

	@Override
	public Integer visit(PrintStm s) {
		return s.getExps().accept(this);
	}

	@Override
	public Integer visit(Exp e) {
		return e.accept(this);
	}

	@Override
	public Integer visit(EseqExp e) {
		int stmt = e.getStm().accept(this);
		int exp = e.getExp().accept(this);
		return Math.max(stmt,exp);
	}

	@Override
	public Integer visit(IdExp e) { return 0; }

	@Override
	public Integer visit(NumExp e) {
		return 0;
	}

	@Override
	public Integer visit(OpExp e) {
		int left = e.getLeft().accept(this);
		int right = e.getRight().accept(this);
		int max = Math.max(left, right);
		return max;
	}

	@Override
	public Integer visit(ExpList el) {
		return el.accept(this);
	}

	@Override
	public Integer visit(PairExpList el) {
		printAtual++;//aumentamos aqui, pois chegamos em um novo parâmetro do print
		int head = el.getHead().accept(this);
		int tail = el.getTail().accept(this);
		return Math.max(tail, head);
	}

	@Override
	public Integer visit(LastExpList el) {
		//aqui vamos lidar com o caso do eseqExp, retornando o maior dos prints.
		printAtual++;
		int max1 = printAtual;
		printAtual=0;
		int max2 = el.getHead().accept(this);
		return Math.max(max1,max2);
	}

}
