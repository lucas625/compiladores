// Generated from C:/Users/Lucas Aurelio/Desktop/MiniJava-master/src/main/java/br/ufpe/cin/if688/minijava\gramatica.g4 by ANTLR 4.7
package br.ufpe.cin.if688.minijava.ANTLR;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link gramaticaParser}.
 */
public interface gramaticaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link gramaticaParser#goal}.
	 * @param ctx the parse tree
	 */
	void enterGoal(gramaticaParser.GoalContext ctx);
	/**
	 * Exit a parse tree produced by {@link gramaticaParser#goal}.
	 * @param ctx the parse tree
	 */
	void exitGoal(gramaticaParser.GoalContext ctx);
	/**
	 * Enter a parse tree produced by {@link gramaticaParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void enterMainClass(gramaticaParser.MainClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link gramaticaParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void exitMainClass(gramaticaParser.MainClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link gramaticaParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(gramaticaParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link gramaticaParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(gramaticaParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link gramaticaParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(gramaticaParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link gramaticaParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(gramaticaParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link gramaticaParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(gramaticaParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link gramaticaParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(gramaticaParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link gramaticaParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(gramaticaParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link gramaticaParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(gramaticaParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link gramaticaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(gramaticaParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link gramaticaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(gramaticaParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(gramaticaParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(gramaticaParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link gramaticaParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(gramaticaParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link gramaticaParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(gramaticaParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link gramaticaParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(gramaticaParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link gramaticaParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(gramaticaParser.IntegerLiteralContext ctx);
}