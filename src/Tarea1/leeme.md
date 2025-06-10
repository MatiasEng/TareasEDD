# Instrucciones para Ejecutar, Testear y Compilar el Programa
## Matias Antonio Constanzo Monsalve

## Descripción del Proyecto
Este proyecto contiene 2 clases principales y una clase auxiliar:
1. `BitMap` - Implementación de un bitmap para manejo eficiente de bits
2. `GND` - Implementación de un Grafo No Dirigido usando una matriz de adyacencia basada en bitmap
3. `Arista` - Representa una conexión entre nodos en un grafo

## Requisitos
- Java JDK 8 o superior
- Sistema operativo con terminal/consola
- Git & Intellij Idea


## Instrucciones
### Forma Facil
Los archivos estan disponibles en un repositorio de github:

https://github.com/m4ti4s1/TareasEDD.git

1. Clonar el repositorio con todo el codigo
```bash
git clone https://github.com/m4ti4s1/TareasEDD.git
```
2. Abrir el repositorio en Intellij Idea
3. Dirigirse a la ruta src/Tarea1
4. Probar el codigo con el Archivo Test
5. Alternativamente provar casos nuevo con el Menu interactivo


### Segunda Forma: ejecutar en la terminal
1. Compilar archivos .java:
Ejecuta el siguiente comando en la terminal desde el directorio raíz del proyecto:
```bash
javac Tarea1/*.java
```

2. Ejecución
El programa principal está en la clase `Test`. Para casos hechos:
Para ingresar casos nuevo ejecutar clase `Menu`

```bash
java Tarea1.Test
java Tarea1.Menu
```

3. Resultados Esperados
Al ejecutar el programa, deberías ver la siguiente salida al ejecutar el archivo Test:

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
    Menu.java
```


### 6. Modificación y Pruebas
Puedes modificar el archivo `Test.java` o ejecutar `Menu.java` para:
- Probar diferentes configuraciones de bitmap
- Crear grafos con diferentes conjuntos de aristas
- Verificar nuevos caminos en el grafo

## Notas
- El programa ya incluye casos de prueba en la clase `Test`
- Los test son ejemplos dentro del enunciado de la propia tarea
- La clase `GND` usa índices basados en 1 para los nodos (el nodo 1 es el primer nodo)
- La implementación del bitmap es eficiente en memoria, usando enteros para almacenar 32 bits cada uno
