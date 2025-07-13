package Tarea3;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MiniFacebook {
    private Map<Integer, Persona> personas;
    private AtomicInteger contadorId;

    public MiniFacebook() {
        this.personas = new HashMap<>();
        this.contadorId = new AtomicInteger(1);
    }

    // Métodos básicos
    public void insertarPersona(String nombre, int dia, int mes, String profesion, String email) {
        int nuevoId = contadorId.getAndIncrement();
        Persona nueva = new Persona(nuevoId, nombre, dia, mes, profesion, email);
        personas.put(nuevoId, nueva);
        System.out.println("Persona " + nombre + " agregada con ID: " + nuevoId);
    }

    public void agregarAmistad(int id1, int id2, LocalDate fecha) {
        if (!personas.containsKey(id1) || !personas.containsKey(id2)) {
            throw new IllegalArgumentException("Una o ambas personas no existen");
        }

        Persona p1 = personas.get(id1);
        Persona p2 = personas.get(id2);

        p1.amigos.put(id2, new Amistad(fecha));
        p2.amigos.put(id1, new Amistad(fecha));

        System.out.println(p1.nombre + " y " + p2.nombre + " son ahora amigos");
        notificarNuevaAmistad(p1, p2, fecha);
        notificarNuevaAmistad(p2, p1, fecha);
    }

    private void notificarNuevaAmistad(Persona p1, Persona p2, LocalDate fecha) {
        for (Map.Entry<Integer, Amistad> entrada : p2.amigos.entrySet()) {
            if (!entrada.getValue().bloqueado && entrada.getKey() != p1.id) {
                Persona amigo = personas.get(entrada.getKey());
                System.out.println("\n--- Notificación de Amistad ---");
                System.out.println("Para: " + amigo.email);
                System.out.println("Fecha: " + fecha);
                System.out.println("Mensaje: Hola " + amigo.nombre + ",\n" +
                        p2.nombre + " se ha hecho amigo de " + p1.nombre +
                        ". Tal vez quieras conocerlo/a.");
                System.out.println("-------------------------------");
            }
        }
    }

    public void bloquearAmigo(int id1, int id2, LocalDate fecha) {
        if (!personas.containsKey(id1) || !personas.containsKey(id2)) {
            throw new IllegalArgumentException("Una o ambas personas no existen");
        }

        if (personas.get(id1).amigos.containsKey(id2)) {
            personas.get(id1).amigos.get(id2).bloqueado = true;
            System.out.println(personas.get(id1).nombre + " ha bloqueado a " + personas.get(id2).nombre);
        } else {
            System.out.println("Estas personas no son amigos");
        }
    }

    // Métodos de actualización
    public void actualizarNombre(int id, String nuevoNombre) {
        if (personas.containsKey(id)) {
            personas.get(id).nombre = nuevoNombre;
        }
    }

    public void actualizarEmail(int id, String nuevoEmail) {
        if (personas.containsKey(id)) {
            personas.get(id).email = nuevoEmail;
        }
    }

    public void actualizarProfesion(int id, String nuevaProfesion) {
        if (personas.containsKey(id)) {
            personas.get(id).profesion = nuevaProfesion;
        }
    }

    public void actualizarFechaNacimiento(int id, int nuevoDia, int nuevoMes) {
        if (personas.containsKey(id) && esFechaNacimientoValida(nuevoDia, nuevoMes)) {
            personas.get(id).diaNacimiento = nuevoDia;
            personas.get(id).mesNacimiento = nuevoMes;
        }
    }

    private boolean esFechaNacimientoValida(int dia, int mes) {
        try {
            MonthDay.of(mes, dia);
            return true;
        } catch (DateTimeException e) {
            System.out.println("Fecha de nacimiento inválida: " + dia + "/" + mes);
            return false;
        }
    }

    // Métodos de consulta
    public int nivelAmistad(int id1, int id2) {
        if (!personas.containsKey(id1) || !personas.containsKey(id2)) {
            return Integer.MAX_VALUE;
        }

        if (personas.get(id1).amigos.containsKey(id2) &&
                !personas.get(id1).amigos.get(id2).bloqueado) {
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

            for (Map.Entry<Integer, Amistad> entrada : personas.get(actual).amigos.entrySet()) {
                int amigoId = entrada.getKey();
                Amistad amistad = entrada.getValue();

                if (!amistad.bloqueado && !visitados.contains(amigoId)) {
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

    public List<Persona> obtenerTodasPersonas() {
        return new ArrayList<>(personas.values());
    }

    public Persona obtenerPersona(int id) {
        return personas.get(id);
    }

    public boolean estaBloqueado(int id1, int id2) {
        if (!personas.containsKey(id1) || !personas.get(id1).amigos.containsKey(id2)) {
            return false;
        }
        return personas.get(id1).amigos.get(id2).bloqueado;
    }

    public LocalDate obtenerFechaBloqueo(int id1, int id2) {
        if (!personas.containsKey(id1) || !personas.get(id1).amigos.containsKey(id2)) {
            return null;
        }
        return personas.get(id1).amigos.get(id2).fechaAmistad;
    }

    public boolean existePersona(int id) {
        return personas.containsKey(id);
    }

    // Métodos para cumpleaños
    public void notificarCumpleañosProximos(int k) {
        notificarCumpleañosProximos(k, LocalDate.now());
    }

    public void notificarCumpleañosProximos(int k, LocalDate fechaReferencia) {
        boolean encontrados = false;

        for (Persona persona : personas.values()) {
            LocalDate cumpleEsteAnio = LocalDate.of(fechaReferencia.getYear(),
                    persona.mesNacimiento,
                    persona.diaNacimiento);

            if (cumpleEsteAnio.isBefore(fechaReferencia)) {
                cumpleEsteAnio = cumpleEsteAnio.plusYears(1);
            }

            long diasFaltantes = fechaReferencia.until(cumpleEsteAnio, ChronoUnit.DAYS);

            if (diasFaltantes <= k) {
                encontrados = true;
                System.out.println("\n[INFO] " + persona.nombre + " cumple años el " +
                        cumpleEsteAnio.format(DateTimeFormatter.ofPattern("dd/MM")) +
                        " (en " + diasFaltantes + " días)");
                notificarAmigosCumple(persona, cumpleEsteAnio);
            }
        }

        if (!encontrados) {
            System.out.println("No se encontraron cumpleaños en los próximos " + k + " días");
        }
    }

    private void notificarAmigosCumple(Persona cumpleanero, LocalDate fechaCumple) {
        for (Map.Entry<Integer, Amistad> entrada : cumpleanero.amigos.entrySet()) {
            if (!entrada.getValue().bloqueado) {
                Persona amigo = personas.get(entrada.getKey());
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

    // Métodos adicionales para pruebas
    public void insertarPersonaParaPrueba(int id, String nombre, int dia, int mes,
                                          String profesion, String email) {
        if (personas.containsKey(id)) {
            throw new IllegalArgumentException("ID ya existe: " + id);
        }
        Persona nueva = new Persona(id, nombre, dia, mes, profesion, email);
        personas.put(id, nueva);
        contadorId.getAndUpdate(current -> Math.max(current, id + 1));
    }
}