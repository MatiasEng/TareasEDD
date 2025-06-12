package Tarea2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class IndiceAlfabetico {
    private NodoAVL raiz;

    // Metodos basicos del AVL
    private int altura(NodoAVL nodo) {
        return nodo == null ? 0 : nodo.altura;
    }

    private int balance(NodoAVL nodo) {
        return nodo == null ? 0 : altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL T2 = x.derecho;

        x.derecho = y;
        y.izquierdo = T2;

        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;
        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;

        return x;
    }

    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL T2 = y.izquierdo;

        y.izquierdo = x;
        x.derecho = T2;

        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;
        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;

        return y;
    }

    public void insertar(String palabra, int pagina) {
        raiz = insertar(raiz, palabra, pagina);
    }

    private NodoAVL insertar(NodoAVL nodo, String palabra, int pagina) {
        if (nodo == null) {
            return new NodoAVL(new EntradaIndice(palabra, pagina));
        }

        int cmp = palabra.compareToIgnoreCase(nodo.entrada.palabra);
        if (cmp < 0) {
            nodo.izquierdo = insertar(nodo.izquierdo, palabra, pagina);
        } else if (cmp > 0) {
            nodo.derecho = insertar(nodo.derecho, palabra, pagina);
        } else {
            // Palabra ya existe, agregar ocurrencia
            nodo.entrada.agregarOcurrencia(pagina);
            return nodo;
        }

        // Actualizar altura
        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));

        // Balancear el arbol
        int balance = balance(nodo);

        // Casos de desbalance
        if (balance > 1 && palabra.compareToIgnoreCase(nodo.izquierdo.entrada.palabra) < 0) {
            return rotarDerecha(nodo);
        }
        if (balance < -1 && palabra.compareToIgnoreCase(nodo.derecho.entrada.palabra) > 0) {
            return rotarIzquierda(nodo);
        }
        if (balance > 1 && palabra.compareToIgnoreCase(nodo.izquierdo.entrada.palabra) > 0) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }
        if (balance < -1 && palabra.compareToIgnoreCase(nodo.derecho.entrada.palabra) < 0) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    public EntradaIndice buscar(String palabra) {
        return buscar(raiz, palabra);
    }

    private EntradaIndice buscar(NodoAVL nodo, String palabra) {
        if (nodo == null) return null;

        int cmp = palabra.compareToIgnoreCase(nodo.entrada.palabra);
        if (cmp < 0) {
            return buscar(nodo.izquierdo, palabra);
        } else if (cmp > 0) {
            return buscar(nodo.derecho, palabra);
        } else {
            return nodo.entrada;
        }
    }

    // Metodos para generar el indice
    public String generarIndiceCompleto() {
        List<String> entries = new ArrayList<>();
        collectEntries(raiz, entries);

        if (entries.isEmpty()) {
            return "El indice esta vacio";
        }

        return formatAlphabeticalIndex(entries);
    }
    private void collectEntries(NodoAVL nodo, List<String> entries) {
        if (nodo != null) {
            collectEntries(nodo.izquierdo, entries);
            entries.add(nodo.entrada.palabra + " " + nodo.entrada.getOcurrenciasFormateadas());
            collectEntries(nodo.derecho, entries);
        }
    }

    private void generarIndiceCompleto(NodoAVL nodo, StringBuilder sb) {
        if (nodo != null) {
            generarIndiceCompleto(nodo.izquierdo, sb);
            if (sb.length() > 0) sb.append(" ");
            sb.append(nodo.entrada.palabra).append(" ").append(nodo.entrada.getOcurrenciasFormateadas());
            generarIndiceCompleto(nodo.derecho, sb);
        }
    }

    private String formatAlphabeticalIndex(List<String> entries) {
        StringBuilder sb = new StringBuilder();
        char currentLetter = '\0';

        for (String entry : entries) {
            String[] parts = entry.split(" ", 2);
            if (parts.length != 2) continue;

            String word = parts[0];
            String occurrences = parts[1];
            char firstLetter = Character.toUpperCase(word.charAt(0));

            if (firstLetter != currentLetter) {
                if (sb.length() > 0) sb.append("\n");
                sb.append("-").append(firstLetter).append("-\n");
                currentLetter = firstLetter;
            }

            sb.append(word).append(" ").append(occurrences).append("\n");
        }

        return sb.toString().trim();
    }

    // Metodos para consultas interactivas
    public Set<Integer> paginasConPalabra(String palabra) {
        EntradaIndice entrada = buscar(palabra);
        if (entrada == null) return new TreeSet<>();
        return new TreeSet<>(entrada.ocurrencias.keySet());
    }

    public Set<Integer> paginasConAmbasPalabras(String palabra1, String palabra2) {
        Set<Integer> paginas1 = paginasConPalabra(palabra1);
        Set<Integer> paginas2 = paginasConPalabra(palabra2);

        paginas1.retainAll(paginas2);
        return paginas1;
    }

    public Set<Integer> paginasConCualquierPalabra(String palabra1, String palabra2) {
        Set<Integer> paginas1 = paginasConPalabra(palabra1);
        Set<Integer> paginas2 = paginasConPalabra(palabra2);

        paginas1.addAll(paginas2);
        return paginas1;
    }
}
