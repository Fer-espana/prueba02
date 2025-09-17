/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author ferna
 */
public class Transicion {
    public char caracter;
    public char nombreEstadoPrimero;
    public char nombreEstadoSiguiente;

    public Transicion(char c, char nombreEstado, char nombreSiguiente) {
        this.caracter = c;
        this.nombreEstadoPrimero = nombreEstado;
        this.nombreEstadoSiguiente = nombreSiguiente;
    }
}
