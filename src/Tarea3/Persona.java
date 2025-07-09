package Tarea3;
import java.util.*;

class Persona {
    int id;
    String nombre;
    int diaNacimiento;
    int mesNacimiento;
    String profesion;
    String email;
    String status;
    Map<Integer, Amistad> amigos; // Key: id del amigo, Value: información de amistad

    public Persona(int id, String nombre, int dia, int mes, String profesion, String email) {
        this.id = id;
        this.nombre = nombre;
        this.diaNacimiento = dia;
        this.mesNacimiento = mes;
        this.profesion = profesion;
        this.email = email;
        this.status = "activo";
        this.amigos = new HashMap<>();
    }
}