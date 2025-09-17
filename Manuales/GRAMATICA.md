# Gramática en Formato BNF - AutómataLab

Esta gramática describe la estructura del lenguaje utilizado por AutómataLab.

### Símbolos
- `<NO_TERMINAL>`: Representa una estructura gramatical.
- `"terminal"`: Representa un símbolo o palabra clave literal.
- `ID`: Un identificador (ej. nombre de un autómata).
- `CADENA`: Un texto entre comillas (ej. "AFD_1").
- `ESTADO`: Un carácter en mayúscula que representa un estado.
- `SIMBOLO`: Un carácter alfanumérico o '#' que representa un símbolo de alfabeto o pila.

---
### Producciones de la Gramática

<ini> ::= <sentencias>

<sentencias> ::= <sentencias> <sentencia>
             | <sentencia>

<sentencia> ::= <definicion_afd>
            | <definicion_ap>
            | <llamada_funcion>

<definicion_afd> ::= "<AFD" "Nombre" "=" CADENA ">"
                     <declaracion_estados>
                     <declaracion_alfabeto>
                     <declaracion_inicial>
                     <declaracion_aceptacion>
                     <definicion_transiciones_afd>
                     "</AFD>"

<definicion_ap> ::= "<AP" "Nombre" "=" CADENA ">"
                    <declaracion_estados>
                    <declaracion_alfabeto>
                    <declaracion_pila>
                    <declaracion_inicial>
                    <declaracion_aceptacion>
                    <definicion_transiciones_ap>
                    "</AP>"

<declaracion_estados> ::= <keyword_n_t_i_a_p> "=" "{" <lista_estados> "}" ";"
<declaracion_alfabeto> ::= <keyword_n_t_i_a_p> "=" "{" <lista_simbolos> "}" ";"
<declaracion_pila> ::= <keyword_n_t_i_a_p> "=" "{" <lista_simbolos> "}" ";"
<declaracion_inicial> ::= <keyword_n_t_i_a_p> "=" "{" ESTADO "}" ";"
<declaracion_aceptacion> ::= <keyword_n_t_i_a_p> "=" "{" <lista_estados> "}" ";"

<keyword_n_t_i_a_p> ::= ID | ESTADO

<lista_estados> ::= <lista_estados> "," ESTADO
                | ESTADO

<lista_simbolos> ::= <lista_simbolos> "," SIMBOLO
                 | SIMBOLO

<definicion_transiciones_afd> ::= "Transiciones" ":" <lista_trans_afd>
<lista_trans_afd> ::= <lista_trans_afd> <transicion_afd> | <transicion_afd>
<transicion_afd> ::= ESTADO "->" <cuerpo_trans_afd> ";"
<cuerpo_trans_afd> ::= <cuerpo_trans_afd> "|" SIMBOLO "," ESTADO
                   | SIMBOLO "," ESTADO

<definicion_transiciones_ap> ::= "Transiciones" ":" <lista_trans_ap>
<lista_trans_ap> ::= <lista_trans_ap> <transicion_ap> | <transicion_ap>
<transicion_ap> ::= ESTADO <cuerpo_trans_ap> ";"
<cuerpo_trans_ap> ::= <cuerpo_trans_ap> "|" <una_trans_ap>
                  | <una_trans_ap>
<una_trans_ap> ::= "(" <simbolo_o_vacio> ")" "->" "(" <simbolo_o_vacio> ")" "," ESTADO ":" "(" <simbolo_o_vacio> ")"

<simbolo_o_vacio> ::= SIMBOLO | "$"

<llamada_funcion> ::= "verAutomatas" "(" ")" ";"
                  | "desc" "(" ID ")" ";"
                  | ID "(" CADENA ")" ";"