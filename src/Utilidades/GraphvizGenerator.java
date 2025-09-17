/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

/**
 *
 * @author ferna
 */
import java.io.File;
import java.io.PrintWriter;


public class GraphvizGenerator {
    
    public static String generarImagen(String nombreArchivo, String dotSource) {
        String baseFileName = nombreArchivo.replaceAll("\\s+", "_");
        File dotFile;
        try {
            // Crear un archivo temporal para el código DOT
            dotFile = File.createTempFile(baseFileName, ".dot");
            try (PrintWriter writer = new PrintWriter(dotFile)) {
                writer.print(dotSource);
            }

            // Definir la ruta del archivo de salida
            String outputPath = System.getProperty("java.io.tmpdir") + baseFileName + ".png";
            
            // Crear el comando para ejecutar Graphviz
            ProcessBuilder pb = new ProcessBuilder(
                "dot",
                "-Tpng",
                dotFile.getAbsolutePath(),
                "-o",
                outputPath
            );
            
            // Ejecutar el proceso
            Process process = pb.start();
            process.waitFor(); // Esperar a que termine la generación de la imagen

            // Limpiar el archivo .dot temporal
            dotFile.delete();

            return outputPath; // Devolver la ruta de la imagen generada

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
