package br.ufpe.cin.if688.jflex;

%%

/* N�o altere as configura��es a seguir */

%line
%column
%unicode
//%debug
%public
%standalone
%class MiniJava
%eofclose

%{
System.out.println("entrei");
}
&}

// Padroes Nomeados:
letter = [A-Za-z]
digit = [0-9]
whitespace = [ \n\t\r\f]
integer = {digit}+
alphanumeric = {letter}|{digit}
identifier = ({letter}|[_])({alphanumeric}|{_})*
operadores = [<]|[==]|[!=]|[!]|[&&]|[*]|[+]|[-]
reservados = "boolean" | "class" | "public" | "extends" | "static" | "void" | "main" | "String" | "int" | "while" | "if" | "else" | "return" | "length" | "true" | "false" | "this" | "new" | "System.out.println"
delimitadores = ";" | "." | "," | "=" | "(" | ")" | "{" | "}" | "[" | "]"
/* Insira as regras l�xicas abaixo */

%%

{reservados} { System.out.println("token gerado foi um reservado: ''"+yytext()+"' na linha: "+yyline+", coluna: "+yycolumn); }
{delimitadores}     { System.out.println("token gerado foi um delimitador: ''"+yytext()+"' na linha: "+yyline+", coluna: "+yycolumn); }
{whitespace} {}
("/*" [^*] ~"*/") | ("//".*) {System.out.println("comentariooooo");}
{operadores} { System.out.println("token gerado foi um operador: ''"+yytext()+"' na linha: "+yyline+", coluna: "+yycolumn); }
{integer} { System.out.println("token gerado foi um integer: ''"+yytext()+"' na linha: "+yyline+", coluna: "+yycolumn); }
{identifier} { System.out.println("token gerado foi um id: ''"+yytext()+"' na linha: "+yyline+", coluna: "+yycolumn); }
/* Insira as regras l�xicas no espa�o acima */     
     
. { System.out.println("erro "+yytext); }