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

public class AFD extends Automata implements AutomataInterfaz {

    // Variable para guardar el historial de la última validación
    public ArrayList<PasoAFD> historial;

    public AFD(ArrayList<Character> estados, ArrayList<Character> alfabeto, ArrayList<Character> estadosA, char estadoI, ArrayList<Transicion> transiciones) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.aceptados = estadosA;
        this.inicial = estadoI;
        this.transiciones = transiciones;
        this.historial = new ArrayList<>();
    }

    /**
     * Construye y devuelve una cadena con la descripción completa del autómata.
     *
     * @param nombre El nombre del autómata a mostrar.
     * @return Un String formateado con los detalles del AFD.
     */
    @Override
    public String mostrarInfo(String nombre) {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Tipo: Automata Finito Determinista\n");
        sb.append("Estados: ").append(this.estados.toString()).append("\n");
        sb.append("Alfabeto: ").append(this.alfabeto.toString()).append("\n");
        sb.append("Estado Inicial: ").append(this.inicial).append("\n");
        sb.append("Estados de aceptacion: ").append(this.aceptados.toString()).append("\n");
        sb.append("Transiciones: ");
        if (this.transiciones != null) {
            for (Transicion t : this.transiciones) {
                sb.append(t.nombreEstadoPrimero).append("->").append(t.caracter).append(",").append(t.nombreEstadoSiguiente).append("; ");
            }
        }
        sb.append("\n---------------------------------\n");
        return sb.toString();
    }

    /**
     * Valida una cadena de entrada y devuelve el resultado como un String.
     *
     * @param entrada La cadena a validar.
     * @return Un String indicando si la cadena es válida o inválida.
     */
    @Override
    public String validar(String entrada) {
        this.historial.clear(); // Limpiar historial anterior
        char estadoActual = this.inicial;
        String consumido = "";

        for (int i = 0; i < entrada.length(); i++) {
            char caracter = entrada.charAt(i);
            char estadoSiguiente = cambiarEstado(estadoActual, caracter);

            if (estadoSiguiente == '\0') {
                return "Resultado de validacion para '" + entrada + "': Cadena Invalida (transicion no encontrada)";
            }

            // Registrar el paso
            historial.add(new PasoAFD(estadoActual, consumido, caracter, estadoSiguiente));
            consumido += caracter;
            estadoActual = estadoSiguiente;
        }

        boolean aceptada = esEstadoDeAceptacion(estadoActual);
        return "Resultado de validacion para '" + entrada + "': " + (aceptada ? "Cadena Valida" : "Cadena Invalida");
    }

    private char cambiarEstado(char estadoActual, char token) {
        if (this.transiciones == null) {
            return '\0';
        }
        for (Transicion t : this.transiciones) {
            if (t.nombreEstadoPrimero == estadoActual && t.caracter == token) {
                return t.nombreEstadoSiguiente;
            }
        }
        return '\0'; // Retorna caracter nulo si no se encuentra transicion
    }

    private boolean esEstadoDeAceptacion(char estadoFinal) {
        return this.aceptados.contains(estadoFinal);
    }

    public String generarDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph AFD {\n");
        sb.append("rankdir=LR;\n");
        sb.append("node [shape = point]; inicio;\n"); // Nodo de inicio invisible

        // Marcar estados de aceptación con doble círculo
        sb.append("node [shape = doublecircle];");
        for (Character aceptado : aceptados) {
            sb.append(" ").append(aceptado);
        }
        sb.append(";\n");

        // Resetear la forma de los nodos a círculo normal
        sb.append("node [shape = circle];\n");

        // Transición desde el inicio al estado inicial
        sb.append("inicio -> ").append(inicial).append(";\n");

        // Agregar todas las transiciones
        for (Transicion t : this.transiciones) {
            sb.append(t.nombreEstadoPrimero).append(" -> ").append(t.nombreEstadoSiguiente);
            sb.append(" [label=\"").append(t.caracter).append("\"];\n");
        }

        sb.append("}");
        return sb.toString();
    }

    /**
     * NUEVO MÉTODO: Genera el código .dot para el reporte de paso a paso.
     *
     * @param cadenaEvaluada La cadena que se validó.
     * @return El código en formato DOT para Graphviz.
     */
    public String generarDotPasoAPaso(String cadenaEvaluada) {
        if (historial == null || historial.isEmpty()) {
            return "digraph G { label=\"No hay historial para generar reporte\"; }";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("digraph AFD_Pasos {\n");
        sb.append("rankdir=LR;\n");
        sb.append("labelloc=\"t\";\n");
        sb.append("label=\"Recorrido para la cadena: ").append(cadenaEvaluada).append("\";\n");

        // Definir la forma de los nodos
        sb.append("node [shape = circle];\n");

        // Crear el camino del recorrido
        sb.append("node [color=red, fontcolor=red];\n");
        sb.append("edge [color=red, fontcolor=red];\n");

        // Transición inicial
        sb.append("inicio [shape=point];\n");
        sb.append("inicio -> ").append(historial.get(0).estadoActual).append(";\n");

        for (PasoAFD paso : historial) {
            sb.append(paso.estadoActual).append(" -> ").append(paso.estadoSiguiente);
            sb.append(" [label=\"").append(paso.caracterEvaluado).append("\"];\n");
        }

        // Marcar el último estado del recorrido. Si es de aceptación, doble círculo.
        char ultimoEstado = historial.get(historial.size() - 1).estadoSiguiente;
        if (esEstadoDeAceptacion(ultimoEstado)) {
            sb.append(ultimoEstado).append(" [shape=doublecircle];\n");
        }

        sb.append("}");
        return sb.toString();
    }

}
