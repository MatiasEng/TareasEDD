package Tarea2;

import java.io.*;
import java.util.*;

class PaginaOcurrencia {
    int pagina;
    int contador;

    public PaginaOcurrencia(int pagina) {
        this.pagina = pagina;
        this.contador = 1;
    }

    public void incrementar() {
        contador++;
    }

    @Override
    public String toString() {
        return contador > 1 ? pagina + "(" + contador + ")" : String.valueOf(pagina);
    }
}



