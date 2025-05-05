
package Tarea1;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static GND graph;
    private static BitMap bitMap;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getUserChoice();
            if (choice == 0) break;
            processChoice(choice);
        }
        scanner.close();
        System.out.println("Fin Programa.");
    }

    private static void displayMenu() {
        System.out.println("\n=== Menu test BitMap y GND ===");
        System.out.println("1. Crear nuevo grafo");
        System.out.println("2. Agregar arista a nodo");
        System.out.println("3. Obtener vecinos de un nodo");
        System.out.println("4. Verificar si un camino es valido");
        System.out.println("5. Crear nuevo BitMap");
        System.out.println("6. Encender bit en BitMap (ON)");
        System.out.println("7. Apagar bit en BitMap (OFF)");
        System.out.println("8. Acceder valor en BitMap");
        System.out.println("9. Contar 1s hasta posicion(rank)");
        System.out.println("10. Encontrar posicion hasta cantidad de 1s (select)");
        System.out.println("0. Salir");
        System.out.print("Ingresa Opcion: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void processChoice(int choice) {
        try {
            switch (choice) {
                case 1:
                    createNewGraph();
                    break;
                case 2:
                    addEdgeToGraph();
                    break;
                case 3:
                    getNodeNeighbors();
                    break;
                case 4:
                    checkPath();
                    break;
                case 5:
                    createNewBitMap();
                    break;
                case 6:
                    setBitOn();
                    break;
                case 7:
                    setBitOff();
                    break;
                case 8:
                    accessBit();
                    break;
                case 9:
                    rankBitMap();
                    break;
                case 10:
                    selectBitMap();
                    break;
                default:
                    System.out.println("Opcion invalida. Intenta Denuevo.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void createNewGraph() {
        System.out.print("Ingresa numero de nodos: ");

        int n = Integer.parseInt(scanner.nextLine());
        List<Arista> edges = new ArrayList<>();

        System.out.println("Ingresa arista (formato: origen destino)");
        System.out.println("Separados por enter, Ingresa 'fin' para terminar");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("fin")) break;
            String[] parts = input.split(" ");
            if (parts.length == 2) {
                int origin = Integer.parseInt(parts[0]);
                int destine = Integer.parseInt(parts[1]);
                edges.add(new Arista(origin, destine));
            } else {
                System.out.println("Formato invalido. Intenta denuevo.");
            }
        }
        graph = new GND(n, edges);
        System.out.println("Grafo creado correctamente!");
    }

    private static void addEdgeToGraph() {
        if (graph == null) {
            System.out.println("No existe grafo. Crea un grafo primero.");
            return;
        }

        System.out.println("Ingresa arista (formato: origen destino)");

        String[] parts = scanner.nextLine().split(" ");
        if (parts.length == 2) {
            int origin = Integer.parseInt(parts[0]);
            int destine = Integer.parseInt(parts[1]);
            Arista edge = new Arista(origin, destine);
            graph = new GND(graph.getNumNodes(), new ArrayList<>() {{ add(edge); }});
            System.out.println("Arista agregada correctamente!");
        } else {
            System.out.println("Formato invalido.");
        }
    }

    private static void getNodeNeighbors() {
        if (graph == null) {
            System.out.println("No existe Grafo. Crea un grafo primero.");
            return;
        }
        System.out.print("Ingresa numero de nodo: ");
        int node = Integer.parseInt(scanner.nextLine());
        List<Integer> neighbors = graph.obtenerVecinos(node);
        System.out.println("Vecinos del nodo " + node + ": " + neighbors);
    }

    private static void checkPath() {
        if (graph == null) {
            System.out.println("No existe Grafo. Crea un grafo primero.");
            return;
        }
        System.out.println("Ingresa un camino(separado por espacios ej: 1 2 3):");
        String[] nodes = scanner.nextLine().split(" ");
        List<Integer> path = new ArrayList<>();
        for (String node : nodes) {
            path.add(Integer.parseInt(node));
        }
        boolean isValid = graph.esCamino(path);
        System.out.println("El camino es " + (isValid ? "valido" : "invalido"));
    }

    private static void createNewBitMap() {
        System.out.print("Ingresa tamano del BitMap: ");
        int size = Integer.parseInt(scanner.nextLine());
        bitMap = new BitMap(size);
        System.out.println("BitMap creado correctamente!");
    }

    private static void setBitOn() {
        if (bitMap == null) {
            System.out.println("No existe BitMap . Crea un BitMap primero.");
            return;
        }
        System.out.print("Ingresa posicion del bit para encender (ON): ");
        int pos = Integer.parseInt(scanner.nextLine());
        bitMap.on(pos);
        System.out.println("Bit en posicion " + pos + " encendido (ON).");
    }

    private static void setBitOff() {
        if (bitMap == null) {
            System.out.println("No existe BitMap . Crea un BitMap primero.");
            return;
        }
        System.out.print("Ingresa posicion del bit para apagar (OFF): ");
        int pos = Integer.parseInt(scanner.nextLine());
        bitMap.off(pos);
        System.out.println("Bit en posicion " + pos + " apagado (OFF).");
    }

    private static void accessBit() {
        if (bitMap == null) {
            System.out.println("No existe BitMap . Crea un BitMap primero.");
            return;
        }
        System.out.print("Ingresa posicion del bit para acceder: ");
        int pos = Integer.parseInt(scanner.nextLine());
        byte value = bitMap.access(pos);
        System.out.println("Bit en posicion " + pos + ": " + value);
    }

    private static void rankBitMap() {
        if (bitMap == null) {
            System.out.println("No existe BitMap . Crea un BitMap primero.");
            return;
        }
        System.out.println("Contar 1s hasta posicion: ");
        int pos = Integer.parseInt(scanner.nextLine());
        int count = bitMap.rank(pos);
        System.out.println("Cantidad de 1s hasta posicion " + pos + ": " + count);
    }

    private static void selectBitMap() {
        if (bitMap == null) {
            System.out.println("No existe BitMap . Crea un BitMap primero.");
            return;
        }
        System.out.print("Ingresa j para encontrar posicion hasta j 1s: ");
        int j = Integer.parseInt(scanner.nextLine());
        int pos = bitMap.select(j);
        System.out.println("Posicion hasta " + j + " cantidad de 1s: " + pos);
    }
}
