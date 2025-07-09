package Tarea3;
import java.time.LocalDate;

class Amistad {
    LocalDate fechaAmistad;
    boolean bloqueado;

    public Amistad(LocalDate fechaAmistad) {
        this.fechaAmistad = fechaAmistad;
        this.bloqueado = false;
    }
}