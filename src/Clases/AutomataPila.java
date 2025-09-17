/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author ferna
 */
import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;

public class AutomataPila extends Automata implements AutomataInterfaz {

    public ArrayList<TransicionPila> transicionesPila;
    public ArrayList<Character> simbolosPila;
    private Stack<Character> pila;
    // Variable para guardar el historial
    public ArrayList<PasoAP> historial;

    public AutomataPila(ArrayList<Character> estados, ArrayList<Character> alfabeto, ArrayList<Character> estadosA, char estadoI, ArrayList<Character> simbolosP, ArrayList<TransicionPila> transiciones) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.aceptados = estadosA;
        this.inicial = estadoI;
        this.simbolosPila = simbolosP;
        this.transicionesPila = transiciones;
        this.historial = new ArrayList<>();
    }

    /**
     * Construye y devuelve una cadena con la descripción completa del autómata
     * de pila.
     *
     * @param nombre El nombre del autómata a mostrar.
     * @return Un String formateado con los detalles del AP.
     */
    @Override
    public String mostrarInfo(String nombre) {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Tipo: Automata de Pila\n");
        sb.append("Estados: ").append(this.estados.toString()).append("\n");
        sb.append("Alfabeto: ").append(this.alfabeto.toString()).append("\n");
        sb.append("Simbolos de Pila: ").append(this.simbolosPila.toString()).append("\n");
        sb.append("Estado Inicial: ").append(this.inicial).append("\n");
        sb.append("Estados de aceptacion: ").append(this.aceptados.toString()).append("\n");
        sb.append("Transiciones: ");
        if (this.transicionesPila != null) {
            for (TransicionPila t : this.transicionesPila) {
                sb.append(String.format("%c(%c) -> (%c),%c:(%c); ", t.nombreEstadoPrimero, t.caracter, t.simboloExtraido, t.nombreEstadoSiguiente, t.simboloIntroducido));
            }
        }
        sb.append("\n---------------------------------\n");
        return sb.toString();
    }

    /**
     * Valida una cadena y REGISTRA el historial de pasos del AP.
     *
     * @param cadena
     * @return
     */
    @Override
    public String validar(String cadena) {
        this.pila = new Stack<>();
        this.historial.clear(); // Limpiar historial
        char estadoActual = this.inicial;
        int puntero = 0;

        // Estado inicial del historial
        historial.add(new PasoAP(estadoActual, cadena, pila));

        while (puntero <= cadena.length()) {
            char caracterActual = (puntero < cadena.length()) ? cadena.charAt(puntero) : '$';
            TransicionPila transicion = encontrarTransicion(estadoActual, caracterActual);

            if (transicion != null) {
                // ... (lógica de validación se mantiene igual) ...
                if (transicion.simboloExtraido != '$') {
                    if (pila.isEmpty() || pila.pop() != transicion.simboloExtraido) {
                        return "Resultado de validacion para '" + cadena + "': Cadena Invalida (error de pila)";
                    }
                }
                if (transicion.simboloIntroducido != '$') {
                    pila.push(transicion.simboloIntroducido);
                }

                estadoActual = transicion.nombreEstadoSiguiente;
                if (transicion.caracter != '$') {
                    puntero++;
                }

                // Registrar el nuevo paso en el historial
                String entradaRestante = (puntero < cadena.length()) ? cadena.substring(puntero) : "";
                historial.add(new PasoAP(estadoActual, entradaRestante, pila));

            } else {
                return "Resultado de validacion para '" + cadena + "': Cadena Invalida (transicion no encontrada)";
            }

            if (historial.size() > cadena.length() * 5 + 10) { // Límite de seguridad más robusto
                return "Resultado de validacion para '" + cadena + "': Cadena Invalida (posible bucle infinito)";
            }
        }

        if (esEstadoDeAceptacion(estadoActual) && pila.isEmpty()) {
            return "Resultado de validacion para '" + cadena + "': Cadena Valida";
        } else {
            return "Resultado de validacion para '" + cadena + "': Cadena Invalida (no terminó en estado de aceptación o la pila no está vacía)";
        }
    }

    /**
     * Busca una transición válida desde el estado actual con el caracter o una
     * transición vacía.
     */
    private TransicionPila encontrarTransicion(char estadoActual, char caracter) {
        // Prioridad 1: Transición que coincide con el caracter
        for (TransicionPila t : this.transicionesPila) {
            if (t.nombreEstadoPrimero == estadoActual && t.caracter == caracter) {
                if (t.simboloExtraido == '$' || (!pila.isEmpty() && pila.peek() == t.simboloExtraido)) {
                    return t;
                }
            }
        }
        // Prioridad 2: Transición vacía ($)
        for (TransicionPila t : this.transicionesPila) {
            if (t.nombreEstadoPrimero == estadoActual && t.caracter == '$') {
                if (t.simboloExtraido == '$' || (!pila.isEmpty() && pila.peek() == t.simboloExtraido)) {
                    return t;
                }
            }
        }
        return null;
    }

    private boolean esEstadoDeAceptacion(char estadoFinal) {
        return this.aceptados.contains(estadoFinal);
    }

    public String generarDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph AP {\n");
        sb.append("rankdir=LR;\n");
        sb.append("node [shape = point]; inicio;\n");

        sb.append("node [shape = doublecircle];");
        for (Character aceptado : aceptados) {
            sb.append(" ").append(aceptado);
        }
        sb.append(";\n");

        sb.append("node [shape = circle];\n");
        sb.append("inicio -> ").append(inicial).append(";\n");

        for (TransicionPila t : this.transicionesPila) {
            String label = String.format("%c, %c / %c", t.caracter, t.simboloExtraido, t.simboloIntroducido);
            sb.append(t.nombreEstadoPrimero).append(" -> ").append(t.nombreEstadoSiguiente);
            sb.append(" [label=\"").append(label).append("\"];\n");
        }

        sb.append("}");
        return sb.toString();
    }

    /**
     * NUEVO MÉTODO: Genera una lista de códigos .dot, uno para cada paso del
     * historial.
     *
     * @return Una lista de Strings, donde cada String es un código DOT
     * completo.
     */
    public ArrayList<String> generarDotPasoAPaso() {
        ArrayList<String> dots = new ArrayList<>();
        if (historial == null || historial.isEmpty()) {
            return dots;
        }

        for (int i = 0; i < historial.size(); i++) {
            PasoAP paso = historial.get(i);
            StringBuilder sb = new StringBuilder();

            sb.append("digraph AP_Paso_").append(i).append(" {\n");
            sb.append("rankdir=LR;\n");

            // Subgrafo para la tabla de Entrada y Pila
            sb.append("subgraph cluster_info {\n");
            sb.append("label=\"Paso ").append(i).append("\";\n");
            sb.append("info [shape=none, margin=0, label=<\n");
            sb.append("<table border='1' cellborder='1' cellspacing='0'>\n");
            sb.append("<tr><td bgcolor='lightblue'><b>Entrada</b></td></tr>\n");
            sb.append("<tr><td>").append(paso.entradaRestante.isEmpty() ? "λ" : paso.entradaRestante).append("</td></tr>\n");
            sb.append("<tr><td bgcolor='lightgreen'><b>Pila</b></td></tr>\n");

            // Construir representación de la pila
            if (paso.pila.isEmpty()) {
                sb.append("<tr><td>(vacía)</td></tr>\n");
            } else {
                ArrayList<Character> pilaInvertida = new ArrayList<>(paso.pila);
                Collections.reverse(pilaInvertida);
                for (Character c : pilaInvertida) {
                    sb.append("<tr><td>").append(c).append("</td></tr>\n");
                }
            }
            sb.append("</table>>];\n");
            sb.append("}\n");

            // Grafo del autómata
            sb.append("node [shape = doublecircle];");
            for (Character aceptado : aceptados) {
                sb.append(" ").append(aceptado);
            }
            sb.append(";\n");

            sb.append("node [shape = circle];\n");

            // Resaltar estado actual
            sb.append(paso.estadoActual).append(" [style=filled, fillcolor=lightgreen];\n");

            for (TransicionPila t : this.transicionesPila) {
                String label = String.format("%c, %c / %c", t.caracter, t.simboloExtraido, t.simboloIntroducido);
                sb.append(t.nombreEstadoPrimero).append(" -> ").append(t.nombreEstadoSiguiente);
                sb.append(" [label=\"").append(label).append("\"];\n");
            }

            sb.append("}");
            dots.add(sb.toString());
        }
        return dots;
    }

}
