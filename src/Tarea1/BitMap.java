package Tarea1;

public class BitMap {
    int[] b; // BitMap
    int size;
    BitMap(int size) {
        int ni = (int) (Math.ceil((double) size /32)); // cantidad de enteros que se necesitan para representar el BitMap
        b = new int[ni];
        this.size = size;
    }


    /**
     * Enciende el bit en la posicion i
     * Encender: Cambiar valor a 1
     * @param i posicion a encender
     */
    public void on(int i) {

        if (i >= 0 && i < size) {
            // Encontrar la posiscion en el array;
            int arrayIndex = i / 32;

            // Encontrar la posicion especifica del bit
            int bitIndex = i % 32;

            // Modificar el bit usando una mascara
            b[arrayIndex] = b[arrayIndex] | (1 << bitIndex);

        }

    }

    /**
     * Apaga el bit en la posicion i
     * Apagar: Transformar a 0
     * @param i posicion a apagar
     */
    public void off(int i) {

        if (i >= 0 && i < size) {
            // Posicion del bit en el array
            int arrayIndex = i / 32;

            // Posicion especifica del bit
            int bitIndex = i % 32;

            // Usar una mascara con un 0 en la posicion del bit
            b[arrayIndex] = b[arrayIndex] & ~(1 << bitIndex);

        }

    }

    /**
     * Retorna el valor de la posicion i en el bitmap
     * @param i posicion a consultar en el bitmap
     * @return 1 o 0 dependiendo del valor de la posicion
     */
    public byte access(int i) {
        // En caso que el bit que se quiera aceder exceda los limites del bit map
        if (i < 0 || i >= size) {
            throw new IllegalArgumentException("El indice " +  i + " esta fuera del rango permitido: " + (size-1));
        }

        // Encontrar el bit en el array
        int arrayIndex = i / 32;

        // Posicion especifica del bit
        int bitIndex = i % 32;
        int value; // Valor a retornar

        // Mover el bit a la derecha y compararlo con un 1 usando &
        value = (b[arrayIndex] >> bitIndex) & 1;

        // Castear y devolver el valor
        return (byte) value;
    }

    /**
     * Retorna la cantidad de valores en 1 hasta la posicion i
     * @param i posicion hasta que revisar
     * @return int con la cantidad de valores en 1 hasta la posicion i
     */
    public int rank(int i) {

        int count = 0;
        for (int j = 0; j < i; j++) {
            if (access(j) == 1) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retorna la posicion hasta la cual se encuentres j unos en el bitmap
     * @param j cantidad de unos a buscar
     * @return posicion o -1 si no hay tantos unos en el bitmap
     */
    public int select(int j) {
        int count = 0;

        for (int i = 0; i < size; i++) {
            if (access(i) == 1) {
                count++;
            }

            if (count == j) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int num : b) {
            sb.append(num).append(" ");
        }
        return sb.toString();
    }
}

