# Banco de Projectos de Tesina

## Requisitos

Para correr las aplicaciones necesarias se necesita tener instalado el docker desktop, el mismo se puede descargar desde el siguiente link:

https://www.docker.com/products/docker-desktop/

## Instalacion

Una vez descargado el proyecto y con el docker desktop corriendo, se requiere abrir una consola con permisos de administrador y desde el raiz del proyecto correr los siguientes comandos, que instalaran las dependencias necesarias para correrlo:

###### Comando para generar la imagen del back-end
**docker build ./TesinaProjectBank/ -t apache-bpt**

###### Comando para generar la imagen del front-end
**docker build ./TesinaProjectBank-app/ -t angular-bpt**

###### Comando para levantar el entorno de produccion
**docker compose up -d**

Luego de Terminar de crear el entorno y levantar las maquinas virtuales (esto puede tomar unos minutos dependiendo del hardware donde este montado), se puede conectar para empezar a utilizar la aplicacion en http:\\\\localhost:4200\ desde el mismo o la ip de la maquina que esta haciendo de host, desde cualquier navegador.

Las credenciales por defecto son admin para el usuario y la contraseña.
