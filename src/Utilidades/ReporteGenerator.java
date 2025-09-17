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
import java.util.ArrayList;


public class ReporteGenerator {

    public static String generarHtmlReporte(String titulo, String[] encabezados, ArrayList<String> filas) {
        try {
            File tempFile = File.createTempFile(titulo.replaceAll("\\s+", ""), ".html");
            PrintWriter writer = new PrintWriter(tempFile, "UTF-8");

            // Escribir el encabezado del HTML
            writer.println("<!DOCTYPE html>");
            writer.println("<html lang=\"es\">");
            writer.println("<head>");
            writer.println("<meta charset=\"UTF-8\">");
            writer.println("<title>" + titulo + "</title>");
            writer.println("<style>");
            writer.println("body { font-family: sans-serif; margin: 2em; background-color: #f4f4f9; }");
            writer.println("h1 { color: #333; }");
            writer.println("table { border-collapse: collapse; width: 80%; margin: auto; box-shadow: 0 2px 3px #ccc; }");
            writer.println("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }");
            writer.println("thead { background-color: #4CAF50; color: white; }");
            writer.println("tbody tr:nth-child(even) { background-color: #f2f2f2; }");
            writer.println("</style>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<h1>" + titulo + "</h1>");
            writer.println("<table>");
            
            // Escribir encabezados de la tabla
            writer.println("<thead><tr>");
            for (String encabezado : encabezados) {
                writer.println("<th>" + encabezado + "</th>");
            }
            writer.println("</tr></thead>");

            // Escribir filas de la tabla
            writer.println("<tbody>");
            for (String fila : filas) {
                writer.println("<tr>");
                String[] celdas = fila.split("\\|");
                for (String celda : celdas) {
                    writer.println("<td>" + celda.trim() + "</td>");
                }
                writer.println("</tr>");
            }
            writer.println("</tbody>");
            writer.println("</table>");
            writer.println("</body>");
            writer.println("</html>");

            writer.close();
            return tempFile.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}