# Calculadora en Red (Cliente-Servidor) en Java

Proyecto en Java que implementa una **calculadora cliente-servidor** utilizando **sockets TCP**.
Permite que un cliente envíe operaciones matemáticas a un servidor, el cual procesa la solicitud y devuelve el resultado.
El programa se repite hasta que el usuario decide no realizar mas calculos.

## Características

* Arquitectura **cliente-servidor** usando TCP.
* Soporta operaciones básicas: suma, resta, multiplicación y división.
* Manejo de excepciones para division por cero y operaciones invalidas.
* El cliente puede enviar varias operaciones consecutivas hasta que decida salir.
* Separación clara de responsabilidades entre cliente y servidor.
* Código documentado para facilitar su comprensión.

## Requisitos

* Java JDK 11 o superior.
* Sistema operativo: Windows, Linux o macOS.
* IDE recomendado: IntelliJ IDEA, Eclipse o VS Code con soporte Java.

## Uso

1. Compilar los archivos dentro de la carpeta src:

```bash
javac src/*.java -d bin
```

* Esto crea la carpeta bin con los archivos compilados.

2. Ejecutar el servidor:

```bash
java -cp bin Servidor
```

3. Ejecutar el cliente (en otra terminal o computadora):

```bash
java -cp bin Cliente
```

4. Seguir las instrucciones en el cliente para enviar operaciones al servidor.

   * El cliente podra realizar multiples calculos consecutivos.
   * Para terminar, el usuario debe indicar que no quiere hacer mas calculos.

## Estructura del proyecto

```
CalculadoraRed/
│
├── src/
│   ├── Servidor.java   # Implementacion del servidor
│   └── Cliente.java    # Implementacion del cliente
├── bin/               # Archivos compilados (generados tras compilacion)
├── README.md          # Documentacion del proyecto
└── Otros archivos opcionales (por ejemplo .gitignore)
```

## Autor

**UzielTheCreator**
