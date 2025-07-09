package Tarea3;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MiniFacebookApp {
    private static MiniFacebook fb = new MiniFacebook();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== MiniFacebook ===");
            System.out.println("1. Agregar persona");
            System.out.println("2. Agregar amistad");
            System.out.println("3. Bloquear amigo");
            System.out.println("4. Notificar cumpleaños próximos");
            System.out.println("5. Consultar nivel de amistad");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        agregarPersona();
                        break;
                    case 2:
                        agregarAmistad();
                        break;
                    case 3:
                        bloquearAmigo();
                        break;
                    case 4:
                        notificarCumpleanos();
                        break;
                    case 5:
                        consultarNivelAmistad();
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido");
            }
        }

        System.out.println("Saliendo del sistema...");
        scanner.close();
    }

    private static void agregarPersona() {
        System.out.println("\n--- Agregar nueva persona ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Día de nacimiento (1-31): ");
        int dia = Integer.parseInt(scanner.nextLine());

        System.out.print("Mes de nacimiento (1-12): ");
        int mes = Integer.parseInt(scanner.nextLine());

        System.out.print("Profesión: ");
        String profesion = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        fb.insertarPersona(nombre, dia, mes, profesion, email);
    }

    private static void agregarAmistad() {
        System.out.println("\n--- Agregar amistad ---");

        System.out.print("ID de la primera persona: ");
        int id1 = Integer.parseInt(scanner.nextLine());

        System.out.print("ID de la segunda persona: ");
        int id2 = Integer.parseInt(scanner.nextLine());

        System.out.print("Fecha de amistad (dd/mm/aaaa): ");
        String fechaStr = scanner.nextLine();

        try {
            LocalDate fecha = LocalDate.parse(fechaStr, dateFormatter);
            fb.agregarAmistad(id1, id2, fecha);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use dd/mm/aaaa");
        }
    }

    private static void bloquearAmigo() {
        System.out.println("\n--- Bloquear amigo ---");

        System.out.print("ID de la persona que bloquea: ");
        int id1 = Integer.parseInt(scanner.nextLine());

        System.out.print("ID de la persona a bloquear: ");
        int id2 = Integer.parseInt(scanner.nextLine());

        System.out.print("Fecha de bloqueo (dd/mm/aaaa): ");
        String fechaStr = scanner.nextLine();

        try {
            LocalDate fecha = LocalDate.parse(fechaStr, dateFormatter);
            fb.bloquearAmigo(id1, id2, fecha);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use dd/mm/aaaa");
        }
    }

    private static void notificarCumpleanos() {
        System.out.println("\n--- Notificar cumpleaños próximos ---");
        System.out.print("Ingrese número de días a considerar: ");
        int dias = Integer.parseInt(scanner.nextLine());
        fb.notificarCumpleanosProximos(dias);
    }

    private static void consultarNivelAmistad() {
        System.out.println("\n--- Consultar nivel de amistad ---");

        System.out.print("ID de la primera persona: ");
        int id1 = Integer.parseInt(scanner.nextLine());

        System.out.print("ID de la segunda persona: ");
        int id2 = Integer.parseInt(scanner.nextLine());

        int nivel = fb.nivelAmistad(id1, id2);

        if (nivel == Integer.MAX_VALUE) {
            System.out.println("Estas personas no están conectadas como amigos");
        } else {
            System.out.println("Nivel de amistad: " + nivel);
        }
    }
}

