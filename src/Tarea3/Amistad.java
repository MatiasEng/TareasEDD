package Tarea3;
/*Integrantes

- Matias Constanzo Monsalve
- Maximiliano Riquelme
- Juan Recabal
*/
import java.time.LocalDate;

public class Amistad {
    public LocalDate fechaAmistad;
    public boolean bloqueado;

    public Amistad(LocalDate fechaAmistad) {
        this.fechaAmistad = fechaAmistad;
        this.bloqueado = false;
    }
}