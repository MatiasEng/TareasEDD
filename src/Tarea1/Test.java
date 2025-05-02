package Tarea1;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        BitMap b = new BitMap(15);
        b.on(1);
        b.on(3);
        b.on(5);
        b.on(7);
        b.on(9);
        b.on(11);
        b.on(12);
        b.on(13);
        b.on(14);


        System.out.println("BitMap Rank(6): " + b.rank(6));
        System.out.println("BitMap Select(4): " + b.select(4));

        // Ejemplo de uso de Grafo
        List<Arista> aristas = new ArrayList<>();
        aristas.add(new Arista(1, 2));
        aristas.add(new Arista(1, 3));
        aristas.add(new Arista(2, 4));
        aristas.add(new Arista(3, 10));
        aristas.add(new Arista(4, 10));
        aristas.add(new Arista(5, 7));
        aristas.add(new Arista(5, 6));
        aristas.add(new Arista(6, 9));
        aristas.add(new Arista(8, 9));

        GND g = new GND(10, aristas);

        //g.obtenerVecinos(15);

        System.out.println("Vecinos de 9: " + g.obtenerVecinos(9));

        List<Integer> camino1 = new ArrayList<>();
        camino1.add(1);
        camino1.add(2);
        camino1.add(4);
        camino1.add(10);
        System.out.println("¿Es camino <1 2 4 10> un camino válido? " + g.esCamino(camino1));

        List<Integer> camino2 = new ArrayList<>();
        camino2.add(2);
        camino2.add(4);
        camino2.add(5);
        camino2.add(7);
        System.out.println("¿Es camino <2 4 5 7> un camino válido? " + g.esCamino(camino2));

    }


}
