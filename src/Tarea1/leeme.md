# Instrucciones para Ejecutar, Testear y Compilar el Programa

## Descripción del Proyecto
Este proyecto contiene tres clases principales:
1. `BitMap` - Implementación de un bitmap para manejo eficiente de bits
2. `Arista` - Representa una conexión entre nodos en un grafo
3. `GND` - Implementación de un Grafo No Dirigido usando una matriz de adyacencia basada en bitmap

## Requisitos
- Java JDK 8 o superior
- Sistema operativo con terminal/consola

## Instrucciones

### 1. Compilación
Para compilar todos los archivos Java, ejecuta el siguiente comando en la terminal desde el directorio raíz del proyecto:

```bash
javac Tarea1/*.java
```

### 2. Ejecución
El programa principal está en la clase `Test`. Para ejecutarlo:

```bash
java Tarea1.Test
```

### 3. Resultados Esperados
Al ejecutar el programa, deberías ver la siguiente salida:

```
BitMap Rank(6): 3
BitMap Select(4): 7
Vecinos de 9: [6, 8]
¿Es camino <1 2 4 10> un camino válido? true
¿Es camino <2 4 5 7> un camino válido? false
```

### 4. Estructura de Archivos
Asegúrate de que los archivos estén organizados de la siguiente manera:
```
/proyecto/
  /Tarea1/
    Arista.java
    BitMap.java
    GND.java
    Test.java
```

### 5. Ejecución desde IDE (Opcional)
Si prefieres usar un IDE como IntelliJ IDEA o Eclipse:
1. Crea un nuevo proyecto Java
2. Importa los archivos manteniendo la estructura de paquetes (`Tarea1`)
3. Ejecuta la clase `Test` que contiene el método `main`

### 6. Modificación y Pruebas
Puedes modificar el archivo `Test.java` para:
- Probar diferentes configuraciones de bitmap
- Crear grafos con diferentes conjuntos de aristas
- Verificar nuevos caminos en el grafo

## Notas
- El programa ya incluye casos de prueba en la clase `Test`
- La clase `GND` usa índices basados en 1 para los nodos (el nodo 1 es el primer nodo)
- La implementación del bitmap es eficiente en memoria, usando enteros para almacenar 32 bits cada uno
