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

    public AFD(ArrayList<Character> estados, ArrayList<Character> alfabeto, ArrayList<Character> estadosA, char estadoI, ArrayList<Transicion> transiciones) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.aceptados = estadosA;
        this.inicial = estadoI;
        this.transiciones = transiciones;
    }

    /**
     * Construye y devuelve una cadena con la descripción completa del autómata.
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
     * @param entrada La cadena a validar.
     * @return Un String indicando si la cadena es válida o inválida.
     */
    @Override
    public String validar(String entrada) {
        char estadoActual = this.inicial;

        for (int i = 0; i < entrada.length(); i++) {
            char caracter = entrada.charAt(i);
            estadoActual = cambiarEstado(estadoActual, caracter);
            if (estadoActual == '\0') { // Usamos '\0' para indicar un estado nulo/error
                return "Resultado de validacion para '" + entrada + "': Cadena Invalida (transicion no encontrada)";
            }
        }

        boolean aceptada = esEstadoDeAceptacion(estadoActual);
        return "Resultado de validacion para '" + entrada + "': " + (aceptada ? "Cadena Valida" : "Cadena Invalida");
    }

    private char cambiarEstado(char estadoActual, char token) {
        if (this.transiciones == null) return '\0';
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
}