/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prueba02;

import Analizadores.Parser;
import Analizadores.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author ferna
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Ruta al archivo de entrada que contiene las definiciones de automatas
            String ruta = "C:\\Users\\ferna\\Documents\\GitHub\\prueba02\\Ejemplo1.atm";
            
            System.out.println("Iniciando analisis del archivo: " + ruta);
            
            // Crear el analizador lexico que lee el archivo
            Scanner lexico = new Scanner(new BufferedReader(new FileReader(ruta)));
            
            // Crear el analizador sintactico que utiliza el analizador lexico
            Parser sintactico = new Parser(lexico);
            
            // Ejecutar el analisis sintactico
            sintactico.parse();
            
            System.out.println("\nAnalisis completado exitosamente.");
            
        } catch (Exception ex) {
            System.err.println("Error fatal al analizar el archivo: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
