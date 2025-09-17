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

    public static File generarImagen(String nombreArchivo, String dotSource) {
        // Obtenemos la ruta del directorio del proyecto
        String projectDir = System.getProperty("user.dir");
        // Definimos la carpeta de salida para las imágenes
        File outputDir = new File(projectDir + "/src/Imagenes/");
        
        // Creamos la carpeta si no existe
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        String baseFileName = nombreArchivo.replaceAll("[^a-zA-Z0-9_]", "_"); // Limpiamos el nombre del archivo
        File dotFile;
        
        try {
            dotFile = File.createTempFile(baseFileName, ".dot");
            try (PrintWriter writer = new PrintWriter(dotFile)) {
                writer.print(dotSource);
            }

            // La ruta de salida ahora apunta a nuestra carpeta 'Imagenes'
            File outputFile = new File(outputDir, baseFileName + ".png");
            
            ProcessBuilder pb = new ProcessBuilder(
                "dot",
                "-Tpng",
                dotFile.getAbsolutePath(),
                "-o",
                outputFile.getAbsolutePath()
            );
            
            Process process = pb.start();
            process.waitFor();

            dotFile.delete();

            return outputFile;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
