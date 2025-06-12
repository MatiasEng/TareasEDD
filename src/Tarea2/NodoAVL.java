package Tarea2;

class NodoAVL {
    EntradaIndice entrada;
    NodoAVL izquierdo, derecho;
    int altura;

    public NodoAVL(EntradaIndice entrada) {
        this.entrada = entrada;
        this.altura = 1;
    }
}
