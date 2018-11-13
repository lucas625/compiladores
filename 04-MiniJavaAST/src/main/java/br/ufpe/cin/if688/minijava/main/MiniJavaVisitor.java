package br.ufpe.cin.if688.minijava.main;
import br.ufpe.cin.if688.minijava.ANTLR.*;
import br.ufpe.cin.if688.minijava.ast.*;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class MiniJavaVisitor implements gramaticaVisitor {
    @Override
    public Object visitGoal(gramaticaParser.GoalContext ctx) {//goal
        //main e n classes
        MainClass main = (MainClass) ctx.mainClass().accept(this);//pegando o main
        ClassDeclList classes = new ClassDeclList();//lista das classes adicionais
        for(gramaticaParser.ClassDeclarationContext classAux : ctx.classDeclaration()){
            classes.addElement((ClassDecl) classAux.accept(this));
        }
        return new Program(main, classes);
    }

    @Override
    public Object visitMainClass(gramaticaParser.MainClassContext ctx) {//main
        //dois identifiers e 1 stmt
        Identifier id1 = (Identifier) ctx.identifier(0).accept(this);
        Identifier id2 = (Identifier) ctx.identifier(1).accept(this);
        Statement stmt = (Statement) ctx.statement().accept(this);
        return new MainClass(id1, id2, stmt);
    }

    @Override
    public Object visitClassDeclaration(gramaticaParser.ClassDeclarationContext ctx) {//outras classes
        //1 identifier 0/1 identifier n varDeclaration n methodDeclaration
        Identifier id1 = (Identifier) ctx.identifier(0).accept(this);
        MethodDeclList metodos = new MethodDeclList();
        for(gramaticaParser.MethodDeclarationContext methodAux : ctx.methodDeclaration()){
            metodos.addElement((MethodDecl) methodAux.accept(this));
        }
        VarDeclList variaveis = new VarDeclList();
        for(gramaticaParser.VarDeclarationContext varAux : ctx.varDeclaration()){
            variaveis.addElement((VarDecl) varAux.accept(this));
        }
        if(ctx.identifier().size()>1){
            Identifier id2 = (Identifier) ctx.identifier(1).accept(this);
            return new ClassDeclExtends(id1,id2,variaveis,metodos);
        }
        return new ClassDeclSimple(id1,variaveis,metodos);
    }

    @Override
    public Object visitVarDeclaration(gramaticaParser.VarDeclarationContext ctx) {//variaveis
        //tipo e identificador
        Type tipo = (Type) ctx.type().accept(this);
        Identifier id = (Identifier) ctx.identifier().accept(this);
        return new VarDecl(tipo,id);
    }

    @Override
    public Object visitMethodDeclaration(gramaticaParser.MethodDeclarationContext ctx) {//metodos
        Type tipo = (Type) ctx.type(0).accept(this);
        Identifier id = (Identifier) ctx.identifier(0).accept(this);
        FormalList lista = new FormalList();
        for(int i=1;i<ctx.type().size();i++){
            Formal formal = new Formal((Type) ctx.type(i).accept(this),(Identifier) ctx.identifier(i).accept(this));
        }
        VarDeclList var = new VarDeclList();
        StatementList stmts = new StatementList();
        for(gramaticaParser.VarDeclarationContext varAux : ctx.varDeclaration()){
            var.addElement((VarDecl) varAux.accept(this));
        }
        for(gramaticaParser.StatementContext stmt : ctx.statement()){
            stmts.addElement((Statement) stmt.accept(this));
        }
        Exp exp = (Exp) ctx.expression().accept(this);
        return new MethodDecl(tipo,id,lista,var,stmts,exp);
    }

    @Override
    public Object visitType(gramaticaParser.TypeContext ctx) {//tipo
        String aux = ctx.getText();
        if (aux.equals("int[]"))
            return new IntArrayType();
        if (aux.equals("boolean"))
            return new BooleanType();
        if (aux.equals("int"))
            return new IntegerType();
        return new IdentifierType(aux);
    }

    @Override
    public Object visitStatement(gramaticaParser.StatementContext ctx) {//stmt
        String aux = ctx.getStart().getText();
        if("{".equals(aux)){
            StatementList stmts = new StatementList();
            for(gramaticaParser.StatementContext stmt : ctx.statement()){
                stmts.addElement((Statement) stmt.accept(this));
            }
            return new Block(stmts);
        }
        if("if".equals(aux)){
            Exp exp = (Exp) ctx.expression(0).accept(this);
            Statement s1 = (Statement) ctx.statement(0).accept(this);
            Statement s2 = (Statement) ctx.statement(1).accept(this);
            return new If(exp, s1, s2);
        }
        if("while".equals(aux)){
            Exp exp = (Exp) ctx.expression(0).accept(this);
            Statement stmt = (Statement) ctx.statement(0).accept(this);
            return new While(exp,stmt);
        }
        if("System.out.println".equals(aux)){
            Exp exp = (Exp) ctx.expression(0).accept(this);
            return new Print(exp);
        }
        if(ctx.expression().size() > 1){
            Identifier id = (Identifier) ctx.identifier().accept(this);
            Exp exp1 = (Exp) ctx.expression(0).accept(this);
            Exp exp2 = (Exp) ctx.expression(1).accept(this);
            return new ArrayAssign(id,exp1,exp2);

        }
        Identifier id = (Identifier) ctx.identifier().accept(this);
        Exp exp = (Exp) ctx.expression(0).accept(this);
        return new Assign(id,exp);
    }

    @Override
    public Object visitExpression(gramaticaParser.ExpressionContext ctx) {//Expressões
        int tamanho = ctx.expression().size();
        if(tamanho == 0){//apenas aqueles com uma única expression
            String aux = ctx.getText();
            if (aux != null && aux.matches("[-+]?\\d*\\.?\\d+"))
                return ctx.integerLiteral().accept(this);
            if ("true".equals(aux))
                return new True();
            if ("false".equals(aux))
                return new False();
            if ("this".equals(aux))
                return new This();
            if (aux != null && "n".equals(aux.substring(0, 1))) {
                Identifier i = (Identifier) ctx.identifier().accept(this);
                return new NewObject(i);
            }
            return new IdentifierExp(ctx.getText());
        }
        if (tamanho == 1) {
            Exp exp = (Exp) ctx.expression(0).accept(this);
            String aux = ctx.getText();
            if ("(".equals(aux))
                return exp;
            if ("!".equals(aux))
                return new Not(exp);
            if ("new".equals(aux))
                return new NewArray(exp);
            return new ArrayLength(exp);
        }

        int exps = ctx.getChildCount();
        if(exps==3){
            Exp exp1 = (Exp) ctx.expression(0).accept(this);
            Exp exp2 = (Exp) ctx.expression(1).accept(this);
            String op = ctx.getChild(1).getText();
            if ("&&".equals(op))
                return new And(exp1, exp2);
            else if ("+".equals(op))
                return new Plus(exp1, exp2);
            else if ("-".equals(op))
                return new Minus(exp1, exp2);
            else if ("<".equals(op))
                return new LessThan(exp1, exp2);
            return new Times(exp1, exp2);
        }
        if(exps==4){
            Exp exp1 = (Exp) ctx.expression(0).accept(this);
            Exp exp2 = (Exp) ctx.expression(1).accept(this);
            return new ArrayLookup(exp1, exp2);
        }
        Exp exp = (Exp) ctx.expression(0).accept(this);
        Identifier id = (Identifier) ctx.identifier().accept(this);
        ExpList expL = new ExpList();
        for (int i = 1; i < ctx.expression().size(); i++) {
            expL.addElement((Exp) ctx.expression(i).accept(this));
        }
        return new Call(exp, id, expL);

    }

    @Override
    public Object visitIdentifier(gramaticaParser.IdentifierContext ctx) {
        return new Identifier(ctx.getText());
    }

    @Override
    public Object visitIntegerLiteral(gramaticaParser.IntegerLiteralContext ctx) {
        return new IntegerLiteral(Integer.parseInt(ctx.getText()));
    }

    @Override
    public Object visit(ParseTree parseTree) {
        return parseTree.accept(this);
    }

    @Override
    public Object visitChildren(RuleNode ruleNode) {
        return null;
    }

    @Override
    public Object visitTerminal(TerminalNode terminalNode) {
        return null;
    }

    @Override
    public Object visitErrorNode(ErrorNode errorNode) {
        return null;
    }
}