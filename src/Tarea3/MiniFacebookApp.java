package Tarea3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/*Integrantes

- Matias Constanzo Monsalve
- Maximiliano Riquelme
- Juan Recabal
*/

public class MiniFacebookApp {
    private static MiniFacebook fb = new MiniFacebook();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== MiniFacebook - Modo ===");
            System.out.println("1. Ejecutar pruebas automáticas completas");
            System.out.println("2. Modo interactivo (pruebas manuales)");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        ejecutarPruebasCompletas();
                        break;
                    case 2:
                        modoInteractivo();
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido");
            }
        }
        scanner.close();
    }

    private static void ejecutarPruebasCompletas() {
        System.out.println("\n=== INICIANDO PRUEBAS AUTOMÁTICAS ===\n");

        MiniFacebook fbPruebas = new MiniFacebook();
        inicializarDatosPrueba(fbPruebas);

        System.out.println("=== ESTADO INICIAL ===");
        visualizarRedSocial(fbPruebas);

        System.out.println("\n=== EJECUTANDO PRUEBAS ===");
        ejecutarPruebasAmistades(fbPruebas);
        ejecutarPruebasBloqueos(fbPruebas);
        ejecutarPruebasCumpleaños(fbPruebas);
        ejecutarPruebasEdicion(fbPruebas);

        System.out.println("\n=== ESTADO FINAL ===");
        visualizarRedSocial(fbPruebas);

        System.out.println("\n=== PRUEBAS COMPLETADAS ===");
    }

    private static void inicializarDatosPrueba(MiniFacebook fb) {
        fb.insertarPersona("Juan", 27, 8, "Ingeniero", "juan@mail.com");
        fb.insertarPersona("María", 15, 12, "Doctora", "maria@mail.com");
        fb.insertarPersona("Carlos", 3, 4, "Arquitecto", "carlos@mail.com");
        fb.insertarPersona("Ana", 10, 7, "Abogada", "ana@mail.com");

        fb.agregarAmistad(1, 2, LocalDate.now().minusDays(10));
        fb.agregarAmistad(2, 3, LocalDate.now().minusDays(5));
        fb.agregarAmistad(1, 4, LocalDate.now().minusDays(2));
    }

    private static void visualizarRedSocial(MiniFacebook fb) {
        System.out.println("\nPersonas en el sistema:");
        fb.obtenerTodasPersonas().forEach(p -> {
            System.out.printf("- %s (ID: %d, Cumple: %d/%d, Email: %s, Profesión: %s)%n",
                    p.nombre, p.id, p.diaNacimiento, p.mesNacimiento, p.email, p.profesion);
        });

        System.out.println("\nRelaciones de amistad:");
        fb.obtenerTodasPersonas().forEach(p1 -> {
            fb.obtenerTodasPersonas().forEach(p2 -> {
                if (p1.id < p2.id) {
                    int nivel = fb.nivelAmistad(p1.id, p2.id);
                    if (nivel != Integer.MAX_VALUE) {
                        String bloqueo = fb.estaBloqueado(p1.id, p2.id) ? " (BLOQUEADO)" : "";
                        System.out.printf("%s ↔ %s: Nivel %d%s%n",
                                p1.nombre, p2.nombre, nivel, bloqueo);
                    }
                }
            });
        });
    }

    private static void ejecutarPruebasAmistades(MiniFacebook fb) {
        System.out.println("\n--- Pruebas de Niveles de Amistad ---");

        System.out.printf("%-15s | %-15s | %-10s | %-10s%n",
                "Persona 1", "Persona 2", "Nivel", "Esperado");
        System.out.println("-----------------+-----------------+------------+------------");

        verificarNivelAmistad(fb, 1, 2, 1);
        verificarNivelAmistad(fb, 1, 3, 2);
        verificarNivelAmistad(fb, 2, 4, 2);
        verificarNivelAmistad(fb, 3, 4, 3);
    }

    private static void verificarNivelAmistad(MiniFacebook fb, int id1, int id2, int esperado) {
        int nivel = fb.nivelAmistad(id1, id2);
        String resultado = nivel == esperado ? "✓" : "✗";
        System.out.printf("%-15s | %-15s | %-10d | %-10d %s%n",
                fb.obtenerPersona(id1).nombre,
                fb.obtenerPersona(id2).nombre,
                nivel,
                esperado,
                resultado);
    }

    private static void ejecutarPruebasBloqueos(MiniFacebook fb) {
        System.out.println("\n--- Pruebas de Bloqueos ---");

        System.out.println("\nEstado antes de bloqueo:");
        System.out.println("Nivel Juan-María: " + fb.nivelAmistad(1, 2));

        System.out.println("\nJuan bloquea a María...");
        fb.bloquearAmigo(1, 2, LocalDate.now());

        System.out.println("\nEstado después de bloqueo:");
        System.out.println("Nivel Juan-María: " +
                (fb.nivelAmistad(1, 2) == Integer.MAX_VALUE ? "∞ (bloqueado)" : fb.nivelAmistad(1, 2)));
    }

    private static void ejecutarPruebasCumpleaños(MiniFacebook fb) {
        System.out.println("\n--- Pruebas de Cumpleaños ---");

        LocalDate fechaPrueba = LocalDate.of(LocalDate.now().getYear(), 5, 1);
        System.out.println("\nSimulando fecha actual: " + fechaPrueba);

        System.out.println("\nBuscando cumpleaños en los próximos 120 días...");
        fb.notificarCumpleañosProximos(120, fechaPrueba);
    }

    private static void ejecutarPruebasEdicion(MiniFacebook fb) {
        System.out.println("\n--- Pruebas de Edición ---");

        System.out.println("\nAntes de edición:");
        System.out.println("Ana: " + fb.obtenerPersona(4).email);

        System.out.println("\nActualizando email de Ana...");
        fb.actualizarEmail(4, "ana.nuevo@mail.com");

        System.out.println("\nDespués de edición:");
        System.out.println("Ana: " + fb.obtenerPersona(4).email);
    }

    private static void modoInteractivo() {
        boolean enModoInteractivo = true;

        while (enModoInteractivo) {
            System.out.println("\n=== MODO INTERACTIVO ===");
            System.out.println("1. Agregar persona");
            System.out.println("2. Agregar amistad");
            System.out.println("3. Bloquear amigo");
            System.out.println("4. Notificar cumpleaños próximos");
            System.out.println("5. Consultar nivel de amistad");
            System.out.println("6. Visualizar red social");
            System.out.println("7. Editar persona");
            System.out.println("8. Volver al menú principal");
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
                        visualizarRedSocial(fb);
                        break;
                    case 7:
                        editarPersona();
                        break;
                    case 8:
                        enModoInteractivo = false;
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido");
            }
        }
    }

    private static void agregarPersona() {
        System.out.println("\n--- Agregar Persona ---");
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
        System.out.println("\n--- Agregar Amistad ---");
        System.out.print("ID de la primera persona: ");
        int id1 = Integer.parseInt(scanner.nextLine());

        System.out.print("ID de la segunda persona: ");
        int id2 = Integer.parseInt(scanner.nextLine());

        System.out.print("Fecha de amistad (dd/mm/aaaa): ");
        String fechaStr = scanner.nextLine();

        try {
            LocalDate fecha = LocalDate.parse(fechaStr, dateFormatter);
            fb.agregarAmistad(id1, id2, fecha);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void bloquearAmigo() {
        System.out.println("\n--- Bloquear Amigo ---");
        System.out.print("ID de la persona que bloquea: ");
        int id1 = Integer.parseInt(scanner.nextLine());

        System.out.print("ID de la persona a bloquear: ");
        int id2 = Integer.parseInt(scanner.nextLine());

        System.out.print("Fecha de bloqueo (dd/mm/aaaa): ");
        String fechaStr = scanner.nextLine();

        try {
            LocalDate fecha = LocalDate.parse(fechaStr, dateFormatter);
            fb.bloquearAmigo(id1, id2, fecha);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void notificarCumpleanos() {
        System.out.println("\n--- Notificar Cumpleaños Próximos ---");
        System.out.print("Ingrese número de días a considerar: ");
        int dias = Integer.parseInt(scanner.nextLine());

        System.out.print("¿Usar fecha actual? (s/n): ");
        String respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("s")) {
            fb.notificarCumpleañosProximos(dias);
        } else {
            System.out.print("Ingrese fecha de referencia (dd/mm/aaaa): ");
            String fechaStr = scanner.nextLine();
            try {
                LocalDate fechaRef = LocalDate.parse(fechaStr, dateFormatter);
                fb.notificarCumpleañosProximos(dias, fechaRef);
            } catch (Exception e) {
                System.out.println("Error: Usando fecha actual");
                fb.notificarCumpleañosProximos(dias);
            }
        }
    }

    private static void consultarNivelAmistad() {
        System.out.println("\n--- Consultar Nivel de Amistad ---");
        System.out.print("ID de la primera persona: ");
        int id1 = Integer.parseInt(scanner.nextLine());

        System.out.print("ID de la segunda persona: ");
        int id2 = Integer.parseInt(scanner.nextLine());

        int nivel = fb.nivelAmistad(id1, id2);
        if (nivel == Integer.MAX_VALUE) {
            if (fb.estaBloqueado(id1, id2) || fb.estaBloqueado(id2, id1)) {
                System.out.println("Estas personas están bloqueadas entre sí");
            } else {
                System.out.println("Estas personas no están conectadas como amigos");
            }
        } else {
            System.out.println("Nivel de amistad: " + nivel);
        }
    }

    private static void editarPersona() {
        System.out.println("\n--- Editar Persona ---");
        System.out.print("ID de la persona a editar: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (!fb.existePersona(id)) {
            System.out.println("Persona no encontrada");
            return;
        }

        Persona p = fb.obtenerPersona(id);
        System.out.println("\nEditando a: " + p.nombre);
        System.out.println("1. Actualizar nombre");
        System.out.println("2. Actualizar email");
        System.out.println("3. Actualizar profesión");
        System.out.println("4. Actualizar fecha de nacimiento");
        System.out.print("Seleccione qué desea editar: ");

        int opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                System.out.print("Nuevo nombre: ");
                String nombre = scanner.nextLine();
                fb.actualizarNombre(id, nombre);
                break;
            case 2:
                System.out.print("Nuevo email: ");
                String email = scanner.nextLine();
                fb.actualizarEmail(id, email);
                break;
            case 3:
                System.out.print("Nueva profesión: ");
                String profesion = scanner.nextLine();
                fb.actualizarProfesion(id, profesion);
                break;
            case 4:
                System.out.print("Nuevo día de nacimiento: ");
                int dia = Integer.parseInt(scanner.nextLine());
                System.out.print("Nuevo mes de nacimiento: ");
                int mes = Integer.parseInt(scanner.nextLine());
                fb.actualizarFechaNacimiento(id, dia, mes);
                break;
            default:
                System.out.println("Opción no válida");
        }

        System.out.println("\nDatos actualizados:");
        Persona actualizada = fb.obtenerPersona(id);
        System.out.printf("Nombre: %s\nEmail: %s\nProfesión: %s\nCumpleaños: %d/%d\n",
                actualizada.nombre, actualizada.email, actualizada.profesion,
                actualizada.diaNacimiento, actualizada.mesNacimiento);
    }
}