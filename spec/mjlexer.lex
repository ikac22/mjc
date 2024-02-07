package mjc;

import java_cup.runtime.*;
import org.apache.log4j.Logger;

%%

%{
	Logger log = Logger.getLogger(getClass());

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacie o poziciji tokena
	private Symbol new_symbol(int type, Object value){
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{

	return new_symbol(sym.EOF);

%eofval}

%%

" " 		{}
"\b"		{}
"\t"		{}
"\n"		{}
"\r"		{}

"program"	{ return new_symbol(sym.PROG, yytext()); }
"break"		{ return new_symbol(sym.BREAK, yytext()); }
"class"		{ return new_symbol(sym.CLASS, yytext()); }
"else"		{ return new_symbol(sym.ELSE, yytext()); }
"const"		{ return new_symbol(sym.CONST, yytext()); }
"if"		{ return new_symbol(sym.IF, yytext()); }
"new"		{ return new_symbol(sym.NEW, yytext()); }
"print"		{ return new_symbol(sym.PRINT, yytext()); }
"read"		{ return new_symbol(sym.READ, yytext()); }
"return"	{ return new_symbol(sym.RETURN, yytext()); }
"void"		{ return new_symbol(sym.VOID, yytext()); }
"extends"	{ return new_symbol(sym.EXTENDS, yytext()); }
"continue"	{ return new_symbol(sym.CONTINUE, yytext()); }
"for"		{ return new_symbol(sym.FOR, yytext()); }
"static"	{ return new_symbol(sym.STATIC, yytext()); }
"namespace" { return new_symbol(sym.NAMESPACE, yytext()); }

"=>"		{ return new_symbol(sym.ARROW); }

"+"			{ return new_symbol(sym.PLUS, yytext()); }
"-"			{ return new_symbol(sym.MINUS, yytext()); }
"*"			{ return new_symbol(sym.MUL, yytext()); }
"/"			{ return new_symbol(sym.DIV, yytext()); }
"%"			{ return new_symbol(sym.MOD, yytext()); }
"="			{ return new_symbol(sym.EQUAL, yytext()); }

"&&"		{ return new_symbol(sym.AND, yytext()); }
"||"		{ return new_symbol(sym.OR, yytext()); }

"=="		{ return new_symbol(sym.EQUALSTO, yytext()); }
"!="		{ return new_symbol(sym.DIFFERENT, yytext()); }
">"			{ return new_symbol(sym.GREATER, yytext()); }
">="		{ return new_symbol(sym.GREATERE, yytext()); }
"<"			{ return new_symbol(sym.LESS, yytext()); }
"<="		{ return new_symbol(sym.LESSE, yytext()); }

"++" 		{ return new_symbol(sym.INC, yytext()); }
"--"		{ return new_symbol(sym.DEC, yytext()); }

";"			{ return new_symbol(sym.SEMI, yytext()); }
":"			{ return new_symbol(sym.COLON, yytext()); }
"."			{ return new_symbol(sym.DOT, yytext()); }
","			{ return new_symbol(sym.COMMA, yytext()); }


"("			{ return new_symbol(sym.LPAREN, yytext()); }
")"			{ return new_symbol(sym.RPAREN, yytext()); }
"{"			{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"["			{ return new_symbol(sym.LSQBRACE, yytext()); }
"]"			{ return new_symbol(sym.RSQBRACE, yytext()); }

"//"				{ yybegin(COMMENT); }
<COMMENT> . 		{ yybegin(COMMENT); }
<COMMENT> "\n" 		{ yybegin(YYINITIAL); }
<COMMENT> "\r\n" 	{ yybegin(YYINITIAL); }

"true" | "false"		{ return new_symbol(sym.BOOL, yytext()); }
"'"[ -~]"'"				{ return new_symbol(sym.CHAR, yytext()); }
[0-9]+ 					{ return new_symbol(sym.NUMBER, new Integer(yytext())); }
[a-zA-Z][a-zA-Z0-9_]*	{ return new_symbol(sym.IDENT, yytext()); }
. { 
	log.error("Unexpected character ("+yytext()+") on line "+(yyline+1) + " column " + yycolumn); 
	return new_symbol(sym.error, yytext());
}

