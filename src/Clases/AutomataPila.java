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

public class AutomataPila extends Automata implements AutomataInterfaz {

    public ArrayList<TransicionPila> transicionesPila;
    public ArrayList<Character> simbolosPila;
    private Stack<Character> pila;

    public AutomataPila(ArrayList<Character> estados, ArrayList<Character> alfabeto, ArrayList<Character> estadosA, char estadoI, ArrayList<Character> simbolosP, ArrayList<TransicionPila> transiciones) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.aceptados = estadosA;
        this.inicial = estadoI;
        this.simbolosPila = simbolosP;
        this.transicionesPila = transiciones;
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
     * Valida una cadena de entrada usando la lógica de autómata de pila.
     *
     * @param cadena La cadena a validar.
     * @return Un String indicando si la cadena es válida o inválida.
     */
    @Override
    public String validar(String cadena) {
        this.pila = new Stack<>();
        char estadoActual = this.inicial;
        int puntero = 0;

        while (puntero <= cadena.length()) {
            char caracterActual = (puntero < cadena.length()) ? cadena.charAt(puntero) : '$'; // '$' representa el final de la cadena
            TransicionPila transicion = encontrarTransicion(estadoActual, caracterActual);

            if (transicion != null) {
                // 1. Manejar la pila
                if (transicion.simboloExtraido != '$') {
                    if (pila.isEmpty() || pila.pop() != transicion.simboloExtraido) {
                        return "Resultado de validacion para '" + cadena + "': Cadena Invalida (error de pila)";
                    }
                }
                if (transicion.simboloIntroducido != '$') {
                    pila.push(transicion.simboloIntroducido);
                }

                // 2. Cambiar de estado
                estadoActual = transicion.nombreEstadoSiguiente;

                // 3. Avanzar el puntero si la transición consumió un caracter
                if (transicion.caracter != '$') {
                    puntero++;
                }
            } else {
                return "Resultado de validacion para '" + cadena + "': Cadena Invalida (transicion no encontrada)";
            }

            // Condición de salida para evitar bucles infinitos con transiciones vacías
            if (puntero > cadena.length() + 5) { // Un margen de seguridad
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

}
