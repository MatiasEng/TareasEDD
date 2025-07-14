package Tarea3;

import java.util.HashMap;
import java.util.Map;

/*Integrantes

- Matias Constanzo Monsalve
- Maximiliano Riquelme
- Juan Recabal
*/
public class Persona {
    public int id;
    public String nombre;
    public int diaNacimiento;
    public int mesNacimiento;
    public String profesion;
    public String email;
    public String status;
    public Map<Integer, Amistad> amigos;

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