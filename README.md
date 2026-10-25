# Guía de instalación para proyecto: Torre-San

Este readme detalla los pasos para poder instalar, compilar y ejecutar este proyecto

## 1. Requisitos de instalación

* Java Development Kit 8 (JDK 8)
* Eclipse IDE for Java Developers 2020-06
* Conexión a internet (solo la primera vez, para que Gradle descargue la librería LibGDX)

**No es necesario instalar Gradle ni descargar LibGDX a mano: el proyecto incluye Gradle y este descarga LibGDX automáticamente**

## 2. Instalación y configuración del proyecto

Para poder importar el proyecto al IDE se deben seguir los siguientes pasos:

1. Descargar el archivo .zip 'torreSan.zip' que contiene nuestro proyecto y descomprimirlo en una carpeta cuya ruta no tenga tildes ni caracteres especiales
2. Abrir Eclipse IDE 2020-06
3. Ir al menú superior y luego seleccionar 'File' -> 'Import...' -> 'Gradle' -> 'Existing Gradle Project' y dar click en Next
4. En 'Project root directory' buscar y seleccionar la carpeta 'torreSan' (la que contiene el archivo 'settings.gradle')
5. Dar click en Finish y esperar a que termine la descarga de dependencias (puede tardar unos minutos la primera vez)
6. Dar click derecho en el proyecto 'Torre San-core' -> 'Properties' -> 'Resource' y en 'Text file encoding' seleccionar 'Other: UTF-8'. Repetir en el proyecto 'Torre San-desktop'

**Importante: el proyecto debe importarse como 'Existing Gradle Project' y no como 'Existing Projects into Workspace', ya que si no Eclipse no descargará la librería LibGDX**

**Si aparecen errores en rojo en los 'import com.badlogic...', dar click derecho en el proyecto 'torreSan' -> 'Gradle' -> 'Refresh Gradle Project'**

## 3. Compilación y ejecución

-Para ejecutar el programa ve a 'Torre San-desktop' -> 'src' -> 'com.mygdx.game', da click derecho en 'DesktopLauncher.java' y selecciona 'Run As' -> 'Java Application'.

-Se abrirá una ventana con el menú del juego. Presiona ENTER para comenzar a jugar.

**Si al ejecutar aparece un error 'File not found' con el nombre de una imagen, selecciona el proyecto y presiona F5 para que Eclipse actualice la carpeta 'assets'**

## 4. Controles

* Flechas izquierda y derecha: moverse
* Espacio o flecha arriba: saltar
* ENTER: comenzar a jugar desde el menú / jugar de nuevo al perder
* ESC: volver al menú / salir del juego desde el menú
