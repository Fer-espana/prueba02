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

public abstract class Automata {
    public ArrayList<Character> estados;
    public ArrayList<Character> alfabeto;
    public char inicial;
    public ArrayList<Character> aceptados;
    public ArrayList<Transicion> transiciones;

    // Metodo abstracto para obligar a las clases hijas a implementarlo
    public abstract String mostrarInfo(String nombre);
    public abstract String generarDot();
}
