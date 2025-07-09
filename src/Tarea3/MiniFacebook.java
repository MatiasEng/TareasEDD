package Tarea3;
import java.util.*;
import java.time.LocalDate;
import java.time.MonthDay;

class MiniFacebook {
    private Map<Integer, Persona> personas;
    private int contadorId;

    public MiniFacebook() {
        this.personas = new HashMap<>();
        this.contadorId = 1;
    }

    // 1. Insertar persona
    public void insertarPersona(String nombre, int dia, int mes, String profesion, String email) {
        Persona nueva = new Persona(contadorId++, nombre, dia, mes, profesion, email);
        personas.put(nueva.id, nueva);
        System.out.println("Persona " + nombre + " agregada con ID: " + nueva.id);
    }

    // 2. Agregar amistad
    public void agregarAmistad(int id1, int id2, LocalDate fecha) {
        if (!personas.containsKey(id1) || !personas.containsKey(id2)) {
            System.out.println("Una o ambas personas no existen");
            return;
        }

        Persona p1 = personas.get(id1);
        Persona p2 = personas.get(id2);

        // Agregar amistad bidireccional
        p1.amigos.put(id2, new Amistad(fecha));
        p2.amigos.put(id1, new Amistad(fecha));

        // Notificar amigos directos
        notificarNuevaAmistad(p1, p2, fecha);
        notificarNuevaAmistad(p2, p1, fecha);

        System.out.println(p1.nombre + " y " + p2.nombre + " son ahora amigos");
    }

    private void notificarNuevaAmistad(Persona p1, Persona p2, LocalDate fecha) {
        for (int amigoId : p2.amigos.keySet()) {
            if (amigoId != p1.id) {
                Persona amigo = personas.get(amigoId);
                System.out.println("\n--- Email de notificación ---");
                System.out.println("Para: " + amigo.email);
                System.out.println("Fecha: " + fecha);
                System.out.println("Mensaje: Hola " + amigo.nombre + ", " + p2.nombre +
                        " se ha hecho amigo de " + p1.nombre +
                        ". Tal vez quieras conocerlo/a.");
                System.out.println("----------------------------\n");
            }
        }
    }

    // 3. Bloquear amigo
    public void bloquearAmigo(int id1, int id2, LocalDate fecha) {
        if (!personas.containsKey(id1) || !personas.containsKey(id2)) {
            System.out.println("Una o ambas personas no existen");
            return;
        }

        Persona p1 = personas.get(id1);
        if (p1.amigos.containsKey(id2)) {
            p1.amigos.get(id2).bloqueado = true;
            System.out.println(p1.nombre + " ha bloqueado a " + personas.get(id2).nombre);
        } else {
            System.out.println("Estas personas no son amigos");
        }
    }
    private long calcularDiasHastaCumple(Persona persona) {
        LocalDate hoy = LocalDate.now();
        LocalDate cumpleEsteAnio = LocalDate.of(hoy.getYear(), persona.mesNacimiento, persona.diaNacimiento);

        if (cumpleEsteAnio.isBefore(hoy)) {
            // Si el cumpleaños ya pasó este año, calcular para el próximo año
            cumpleEsteAnio = cumpleEsteAnio.plusYears(1);
        }

        return hoy.until(cumpleEsteAnio, java.time.temporal.ChronoUnit.DAYS);
    }
    // 4. Notificar cumpleaños próximos
    public void notificarCumpleanosProximos(int k) {
        for (Persona persona : personas.values()) {
            long diasFaltantes = calcularDiasHastaCumple(persona);

            if (diasFaltantes <= k) {
                LocalDate fechaCumple = LocalDate.now().plusDays(diasFaltantes);
                notificarAmigosCumple(persona, fechaCumple);
            }
        }
    }

    private void notificarAmigosCumple(Persona cumpleanero, LocalDate fechaCumple) {
        for (int amigoId : cumpleanero.amigos.keySet()) {
            if (!cumpleanero.amigos.get(amigoId).bloqueado) {
                Persona amigo = personas.get(amigoId);
                System.out.println("\n--- Email de cumpleaños ---");
                System.out.println("Para: " + amigo.email);
                System.out.println("Fecha: " + LocalDate.now());
                System.out.println("Mensaje: Hola " + amigo.nombre + ", " +
                        cumpleanero.nombre + " cumple años el " +
                        fechaCumple.getDayOfMonth() + "/" +
                        fechaCumple.getMonthValue() +
                        ". ¡No olvides felicitarlo/a!");
                System.out.println("---------------------------\n");
            }
        }
    }

    // 5. Nivel de amistad
    public int nivelAmistad(int id1, int id2) {
        if (!personas.containsKey(id1) || !personas.containsKey(id2)) {
            return Integer.MAX_VALUE;
        }

        // Primero verificamos si son amigos directos
        if (personas.get(id1).amigos.containsKey(id2)) {
            return 1;
        }

        // BFS para encontrar el camino más corto
        Queue<Integer> cola = new LinkedList<>();
        Map<Integer, Integer> niveles = new HashMap<>();
        Set<Integer> visitados = new HashSet<>();

        cola.offer(id1);
        niveles.put(id1, 0);
        visitados.add(id1);

        while (!cola.isEmpty()) {
            int actual = cola.poll();
            int nivelActual = niveles.get(actual);

            for (int amigoId : personas.get(actual).amigos.keySet()) {
                if (!visitados.contains(amigoId)) {
                    if (amigoId == id2) {
                        return nivelActual + 1;
                    }
                    visitados.add(amigoId);
                    niveles.put(amigoId, nivelActual + 1);
                    cola.offer(amigoId);
                }
            }
        }

        return Integer.MAX_VALUE;
    }
}