package Task2;

import java.util.TreeMap;

class EntradaIndice {
    String palabra;
    TreeMap<Integer, PaginaOcurrencia> ocurrencias;

    public EntradaIndice(String palabra, int pagina) {
        this.palabra = palabra;
        this.ocurrencias = new TreeMap<>();
        this.ocurrencias.put(pagina, new PaginaOcurrencia(pagina));
    }

    public void agregarOcurrencia(int pagina) {
        ocurrencias.compute(pagina, (k, v) -> {
            if (v == null) return new PaginaOcurrencia(pagina);
            v.incrementar();
            return v;
        });
    }

    public String getOcurrenciasFormateadas() {
        StringBuilder sb = new StringBuilder();
        for (PaginaOcurrencia po : ocurrencias.values()) {
            if (sb.length() > 0) sb.append(",");
            sb.append(po);
        }
        return sb.toString();
    }
}
