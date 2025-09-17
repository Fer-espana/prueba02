/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author ferna
 */

import java.util.Stack;

public class PasoAP {
    public char estadoActual;
    public String entradaRestante;
    public Stack<Character> pila;

    public PasoAP(char estadoActual, String entradaRestante, Stack<Character> pila) {
        this.estadoActual = estadoActual;
        this.entradaRestante = entradaRestante;
        // Creamos una copia de la pila para que no se modifique externamente
        this.pila = (Stack<Character>) pila.clone();
    }
}
