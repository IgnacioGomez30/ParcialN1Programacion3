# API REST de Detección de Mutantes por ADN

## Descripción
Esta API analiza secuencias de ADN determinando si corresponden a un Mutante o Humano.

## URL de Producción
La API está hosteada en Render:

URL: [https://parcialn1programacion3-1.onrender.com](https://parcialn1programacion3-1.onrender.com)

## Diagrama de Secuencia
![Diagrama de Secuencia](diagrama.png) <!-- Asegúrate de tener la imagen en la ruta correcta -->

## Construida con:
- Spring Boot
- Java 17
- Gradle
- Hibernate
- Open API
- H2 Database
- Render

Se envian request a través de Postman.
### POST
[https://parcialn1programacion3-1.onrender.com/mutant](https://parcialn1programacion3-1.onrender.com/mutant)

### GET
[https://parcialn1programacion3-1.onrender.com/stats](https://parcialn1programacion3-1.onrender.com/stats)

Endpoint /mutant/
Este endpoint detecta si una secuencia de ADN pertenece a un mutante según los criterios de Magneto. La solicitud debe enviarse como un POST en formato JSON con el siguiente esquema:

### Criterios de aceptacion
Clave dna: Debe ser un arreglo de strings representando cada fila de la matriz de ADN.
Restricciones de Matriz:
Tamaño mínimo: 4x4.
Formato NxN: El número de filas y columnas debe ser igual.
Bases Nitrogenadas Válidas: Cada string debe contener solo las letras A, C, T, o G.
Datos Completos: No debe haber valores nulos ni vacíos.
### Respuestas del Endpoint
Mutante detectado: Devuelve HTTP 200 OK.
No es mutante: Devuelve HTTP 403 Forbidden.
Ingreso inválido: Devuelve un HTTP 400 Bad Request
