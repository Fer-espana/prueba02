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

public abstract class Automata implements AutomataInterfaz { // <--- CAMBIO 1: implementar la interfaz
    public ArrayList<Character> estados;
    public ArrayList<Character> alfabeto;
    public char inicial;
    public ArrayList<Character> aceptados;
    public ArrayList<Transicion> transiciones;

    // Metodos abstractos para obligar a las clases hijas a implementarlos
    public abstract String mostrarInfo(String nombre);
    public abstract String generarDot(String nombre); 
    
    @Override // <--- CAMBIO 2: Añadir la declaración del método de la interfaz
    public abstract String validar(String cadena); 
}
