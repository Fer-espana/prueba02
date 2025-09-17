# Manual Técnico - AutómataLab

## 1. Información General del Proyecto

- **Nombre del Proyecto:** AutómataLab
- **Lenguaje de Programación:** Java (JDK 21)
- **Herramientas de Análisis:**
    - **Análisis Léxico:** JFlex
    - **Análisis Sintáctico:** Java CUP
- **Librerías Adicionales:**
    - **GUI:** Java Swing
    - **Visualización Gráfica:** Graphviz (requerido como dependencia externa del sistema).

## 2. Estructura del Proyecto

El proyecto está organizado en los siguientes paquetes principales:

- **`GUI`:** Contiene la clase `VentanaPrincipal.java`, que define y controla todos los elementos de la interfaz gráfica y la interacción con el usuario.
- **`Analizadores`:** Contiene los archivos fuente (`Scanner.jflex`, `Parser.cup`) y los archivos generados (`Scanner.java`, `Parser.java`, `sym.java`) responsables del análisis léxico y sintáctico.
- **`Clases`:** Contiene el modelo de datos del proyecto. Define las clases para representar los autómatas (`Automata`, `AFD`, `AutomataPila`), sus componentes (`Transicion`, `PasoAFD`, `PasoAP`) y las interfaces (`AutomataInterfaz`).
- **`Utilidades`:** Contiene clases de ayuda para tareas específicas, como `GraphvizGenerator` (para ejecutar Graphviz y crear imágenes) y `ReporteGenerator` (para crear archivos HTML).
- **`automatas`:** Contiene la clase principal `Automatas.java`, cuya única función es iniciar la `VentanaPrincipal`.

## 3. Clases y Métodos Fundamentales

### `Analizadores.Parser`
- **Función:** Orquesta el análisis sintáctico. Utiliza la gramática definida en `Parser.cup` para validar la estructura del código de entrada.
- **Lógica Clave:** Las acciones semánticas dentro de las reglas de producción se encargan de instanciar los objetos `AFD` y `AutomataPila` y almacenarlos en un `HashMap`. También ejecutan las funciones del lenguaje (`desc`, `validar`) y guardan la salida en una variable (`salidaConsola`) para ser mostrada en la GUI.

### `Clases.AFD` y `Clases.AutomataPila`
- **Función:** Representan los dos tipos de autómatas. Heredan de la clase abstracta `Automata`.
- **Métodos Importantes:**
    - `validar(String cadena)`: Implementa la lógica de validación para cada tipo de autómata. Crucialmente, también genera un `historial` de pasos que se utiliza para los reportes gráficos.
    - `generarDot(String nombre)`: Devuelve un `String` con el código en lenguaje DOT de Graphviz para representar la estructura completa del autómata.
    - `generarDotPasoAPaso(String cadena)`: (En `AFD`) Devuelve el código DOT para el reporte de recorrido, resaltando el camino.
    - `generarDotPasoAPaso()`: (En `AutomataPila`) Devuelve una lista de códigos DOT, uno por cada paso en la validación.

### `GUI.VentanaPrincipal`
- **Función:** Es el controlador principal de la aplicación.
- **Métodos Importantes:**
    - `accionEjecutar()`: Es el método central. Obtiene el texto de entrada, inicializa el `Scanner` y el `Parser`, ejecuta el análisis y actualiza los componentes de la GUI con los resultados.
    - `accionGenerarImagen()`: Gestiona la lógica para generar reportes gráficos. Pregunta al usuario el tipo de reporte, solicita la cadena de entrada si es necesario, y llama a los métodos correspondientes en las clases de autómatas y en `GraphvizGenerator`.

### `Utilidades.GraphvizGenerator`
- **Función:** Abstrae la interacción con Graphviz.
- **Método `generarImagen(String nombre, String dotSource)`:** Recibe el código DOT, lo escribe en un archivo temporal, ejecuta el comando `dot -Tpng ...` del sistema para generar la imagen, y la guarda en la carpeta `src/Imagenes`.

## 4. Mantenimiento Futuro
Para extender el proyecto, se pueden seguir los siguientes pasos:
- **Añadir nuevos tipos de autómatas:** Crear una nueva clase que herede de `Automata` e implementar su lógica. Luego, añadir la nueva sintaxis de definición al `Scanner` y al `Parser`.
- **Mejorar la validación de AP:** La lógica de validación actual del AP es funcional pero podría mejorarse para manejar casos más complejos de ambigüedad si fuera necesario.
- **Añadir Reportes en PDF:** Implementar una nueva clase en `Utilidades` que utilice una librería como iText o Apache PDFBox para generar los reportes de tokens y errores en formato PDF.