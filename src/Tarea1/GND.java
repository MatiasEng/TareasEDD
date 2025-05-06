package Tarea1;

import java.util.ArrayList;
import java.util.List;

public class GND {
    private BitMap matrizAdjacencia;
    private int numNodes;

    /**
     * Constructor del Grafo No Dirigido
     * @param n Numero de nodos
     * @param aristas Conjuto que establece conexiones entre nodos
     */
    public GND (int n, List<Arista> aristas) {
        numNodes = n;
        matrizAdjacencia = new BitMap(n*n);


        for (Arista arista : aristas) {

            int u = arista.origin - 1;
            int v = arista.destine - 1;

            matrizAdjacencia.on(u * numNodes + v);
            matrizAdjacencia.on(v * numNodes + u);

        }

    }

    public int getNumNodes() {
        return numNodes;
    }

    /**
     * Metodo retorna los vecinos del nodo node
     * @param node Nodo a revisar los vecinos
     * @return Lista de int con el valor del nodo de sus vecinos
     */
    public List<Integer> obtenerVecinos(int node) {

        // En caso que el nodo no cumpla con el tamano del bitmap
        if (node < 1 || node > numNodes) {
            throw new IllegalArgumentException("Node numero " + node + " excede el limite: " + numNodes);
        }

        // Crear lista que almacenara el valor de los vecinos
        List<Integer> vecinos = new ArrayList<>();

        // Buscar los vecinos
        for (int i = 0; i < numNodes; i++) {

            // Basicamente lo que hace esta condicion es buscar el valor de matrix[node-1][i]
            // (node -1) ya que el GND tiene base 1, y el bitmap tiene base 0
            if (matrizAdjacencia.access((node - 1) * numNodes + i) == 1) {
                // Volver a indice 1
                vecinos.add(i + 1);
            }
        }

        return vecinos;
    }

    public boolean esCamino(List<Integer> camino) {


        if (camino == null || camino.size() <= 1) {
            return true;
        }

        for (int i = 0; i < camino.size() -1; i++) {
            int u = camino.get(i) - 1; // posicion i en base 0
            int v = camino.get(i + 1) - 1; // posicion i + 1 en base 0

            if (u < 0 || u >= numNodes || v < 0 || v >= numNodes) {
                return false;
            }

            // Revisa si el valor en la matriz[u][v] es 0
            // lo que significa que no hay conexion entre los 2 nodos
            if (matrizAdjacencia.access(u * numNodes + v) == 0) {
                return false;
            }
        }

        // Si no se rompe el ciclo re retorna true
        // Significando que el camino es valido
        return true;
    }




}

