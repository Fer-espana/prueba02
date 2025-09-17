/* 1. Package e importaciones */
package Analizadores;
import java_cup.runtime.*;

%%
/* 2. Configuraciones */
%{
    private ArrayList<String> listaTokens;
    private ArrayList<String> listaErrores;
    private int tokenCount = 1;

    public Scanner(java.io.Reader in, ArrayList<String> tokens, ArrayList<String> errores) {
        this(in);
        this.listaTokens = tokens;
        this.listaErrores = errores;
    }

    private void agregarToken(String lexema, String tipo, int linea, int columna) {
        String tokenInfo = String.format("%-5d | %-20s | %-20s | %-7d | %-7d", tokenCount++, lexema, tipo, linea, columna);
        this.listaTokens.add(tokenInfo);
    }
%}
%class Scanner
%public
%cup
%char
%column
%full
%line
%unicode
%init{
    yyline = 1;
    yychar = 1;
%init}

/* ================================================= */
/* EXPRESIONES REGULARES (CON BLANCOS CORREGIDO)     */
/* ================================================= */
BLANCOS=[ \r\t\u00A0]+  // Se añade \u00A0 para ignorar espacios no separables
COMMENT_1LINE = "//" [^\n]*
COMMENT_MULTILINE = "/*" [^*]* "*"* ([^*/][^*]* "*"*)* "/"
ID = [a-zA-Z_][a-zA-Z_0-9]*
CADENA = \"[^\"]*\"
ESTADO = [A-Z]
SIMBOLO_PILA = "#" | [a-zA-Z0-9_]

%%
/* 3. Reglas Lexicas */

/* Palabras Reservadas */
"Transiciones" { 
    agregarToken(yytext(), "Palabra Reservada", yyline, yychar);
    return new Symbol(sym.PR_TRANSI, yyline, yychar, yytext()); }
"verAutomatas" { 
    agregarToken(yytext(), "Palabra Reservada", yyline, yychar);
    return new Symbol(sym.PR_VER, yyline, yychar, yytext()); }
"desc" { 
    agregarToken(yytext(), "Palabra Reservada", yyline, yychar);
    return new Symbol(sym.PR_DESC, yyline, yychar, yytext()); }

/* Etiquetas y simbolos */
"<AFD" { 
    agregarToken(yytext(), "Etiqueta abierta AFD", yyline, yychar);
    return new Symbol(sym.TAG_AFD_A, yyline, yychar, yytext()); }
"</AFD>" { 
    agregarToken(yytext(), "Etiqueta cerrada AFD", yyline, yychar);
    return new Symbol(sym.TAG_AFD_C, yyline, yychar, yytext()); }
"<AP" { 
    agregarToken(yytext(), "Etiqueta abierta AP", yyline, yychar);
    return new Symbol(sym.TAG_AP_A, yyline, yychar, yytext()); }
"</AP>" { 
    agregarToken(yytext(), "Etiqueta cerrada AP", yyline, yychar);
    return new Symbol(sym.TAG_AP_C, yyline, yychar, yytext()); }
">" { 
    agregarToken(yytext(), "Simbolo mayor", yyline, yychar);
    return new Symbol(sym.MAYOR, yyline, yychar, yytext()); }
"Nombre" { 
    agregarToken(yytext(), "Nombre", yyline, yychar);
    return new Symbol(sym.ATTR_NOMBRE, yyline, yychar, yytext()); }
"{" { 
    agregarToken(yytext(), "LLave izquierda", yyline, yychar);
    return new Symbol(sym.LLAV_IZQ, yyline, yychar, yytext()); }
"}" { 
    agregarToken(yytext(), "LLave derecha", yyline, yychar);
    return new Symbol(sym.LLAV_DER, yyline, yychar, yytext()); }
"=" { 
    agregarToken(yytext(), "Igual", yyline, yychar);
    return new Symbol(sym.IGUAL, yyline, yychar, yytext()); }
"|" { 
    agregarToken(yytext(), "OR", yyline, yychar);
    return new Symbol(sym.VERTICAL, yyline, yychar, yytext()); }
"->" { 
    agregarToken(yytext(), "Flecha", yyline, yychar);
    return new Symbol(sym.FLECHA, yyline, yychar, yytext()); }
"(" { 
    agregarToken(yytext(), "Parentesis izquierdo", yyline, yychar);
    return new Symbol(sym.PAR_IZQ, yyline, yychar, yytext()); }
")" { 
    agregarToken(yytext(), "Parentesis derecho", yyline, yychar);
    return new Symbol(sym.PAR_DER, yyline, yychar, yytext()); }
"," { 
    agregarToken(yytext(), "Coma", yyline, yychar);
    return new Symbol(sym.COMA, yyline, yychar, yytext()); }
";" { 
    agregarToken(yytext(), "Punto coma", yyline, yychar);
    return new Symbol(sym.PT_COMA, yyline, yychar, yytext()); }
":" { 
    agregarToken(yytext(), "Dos puntos", yyline, yychar);
    return new Symbol(sym.DOS_PUNTOS, yyline, yychar, yytext()); }
"$" { 
    agregarToken(yytext(), "Signo dolar", yyline, yychar);
    return new Symbol(sym.VACIO, yyline, yychar, yytext()); }

\n { yychar=1; }
{BLANCOS} { /* Ignorar */ }
{COMMENT_1LINE} { /* Ignorar */ }
{COMMENT_MULTILINE} { /* Ignorar */ }

/* Tokens basados en Expresiones Regulares */
{ESTADO}       { 
    agregarToken(yytext(), "Estado", yyline, yychar);
    return new Symbol(sym.ESTADO, yyline, yychar, yytext()); }
{CADENA}       { 
    agregarToken(yytext(), "Cadena", yyline, yychar);
    return new Symbol(sym.CADENA, yyline, yychar, yytext().substring(1, yytext().length() - 1)); }
{SIMBOLO_PILA} { 
    agregarToken(yytext(), "Simbolo pila", yyline, yychar);
    return new Symbol(sym.SIMBOLO, yyline, yychar, yytext()); }
{ID}           { 
    agregarToken(yytext(), "ID", yyline, yychar);
    return new Symbol(sym.ID, yyline, yychar, yytext()); }

/* Errores */
. {
    String errorInfo = String.format("%-5d | %-10s | %-40s | %-7d | %-7d", 
        listaErrores.size() + 1, "Léxico", "Caracter desconocido: '" + yytext() + "'", yyline, yychar);
    listaErrores.add(errorInfo);
}