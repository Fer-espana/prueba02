/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author ferna
 */

public class PasoAFD {
    public char estadoActual;
    public String cadenaConsumida;
    public char caracterEvaluado;
    public char estadoSiguiente;

    public PasoAFD(char estadoActual, String cadenaConsumida, char caracterEvaluado, char estadoSiguiente) {
        this.estadoActual = estadoActual;
        this.cadenaConsumida = cadenaConsumida;
        this.caracterEvaluado = caracterEvaluado;
        this.estadoSiguiente = estadoSiguiente;
    }
}
