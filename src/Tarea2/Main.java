package Tarea2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        String filename = "src/Tarea2/test.txt";

        IndiceAlfabetico indice = new IndiceAlfabetico();
        procesarArchivo(filename, indice);

        // Mostrar menu interactivo
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Mostrar indice completo");
            System.out.println("2. Buscar paginas de una palabra");
            System.out.println("3. Buscar paginas con dos palabras");
            System.out.println("4. Buscar paginas con cualquiera de dos palabras");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de linea

            switch (opcion) {
                case 1:
                    System.out.println("\nIndice alfabético:");
                    System.out.println(indice.generarIndiceCompleto());
                    break;
                case 2:
                    System.out.print("Ingrese la palabra a buscar: ");
                    String palabra = scanner.nextLine();
                    Set<Integer> paginas = indice.paginasConPalabra(palabra);
                    if (paginas.isEmpty()) {
                        System.out.println("La palabra no aparece en el documento.");
                    } else {
                        System.out.println("Aparece en las paginas: " + paginas);
                    }
                    break;
                case 3:
                    System.out.print("Ingrese la primera palabra: ");
                    String p1 = scanner.nextLine();
                    System.out.print("Ingrese la segunda palabra: ");
                    String p2 = scanner.nextLine();
                    Set<Integer> paginasAmbas = indice.paginasConAmbasPalabras(p1, p2);
                    if (paginasAmbas.isEmpty()) {
                        System.out.println("No hay paginas con ambas palabras.");
                    } else {
                        System.out.println("Paginas con ambas palabras: " + paginasAmbas);
                    }
                    break;
                case 4:
                    System.out.print("Ingrese la primera palabra: ");
                    String p3 = scanner.nextLine();
                    System.out.print("Ingrese la segunda palabra: ");
                    String p4 = scanner.nextLine();
                    Set<Integer> paginasCualquiera = indice.paginasConCualquierPalabra(p3, p4);
                    if (paginasCualquiera.isEmpty()) {
                        System.out.println("No hay paginas con ninguna de las palabras.");
                    } else {
                        System.out.println("Paginas con alguna de las palabras: " + paginasCualquiera);
                    }
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private static void procesarArchivo(String nombreArchivo, IndiceAlfabetico indice) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            int paginaActual = 1;
            String linea;

            while ((linea = br.readLine()) != null) {
                // Procesar cada linea buscando palabras entre \ y |
                int inicio = 0;
                while (true) {
                    int backslash = linea.indexOf('\\', inicio);
                    if (backslash == -1) break;

                    int fin = linea.indexOf('\\', backslash + 1);
                    if (fin == -1) break;

                    String palabra = linea.substring(backslash + 1, fin);
                    indice.insertar(palabra, paginaActual);

                    inicio = fin + 1;
                }

                // Verificar si hay un cambio de pagina
                if (linea.contains("|")) {
                    paginaActual++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
