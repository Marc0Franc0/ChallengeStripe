# [Forum](https://github.com/Marc0Franc0/Forum#forum)

El proyecto tiene la posibilidad de gestionar un foro, con el objetivo de facilitar la administración de tópicos dentro del mismo y con autenticación de usuarios.

## Características
- Registro de usuario e inicio de sesión con autenticación JWT
- Cifrado de contraseña usando BCrypt
- Autorización basada en roles con Spring Security
- CRUD para las entidades "Topic"

## Tecnologías
- Spring Boot 3.0
- Spring Security
- JSON Web Tokens (JWT)
- BCrypt
- Maven
- MySQL

## Ejecución
1. Clonar repositorio: git clone https://github.com/Marc0Franc0/Forum
2. Ir al directorio del proyecto: cd Forum
3. Seguir pasos para ejecución con Maven

## Requerimientos para ejecutar con Maven

Para construir y ejecutar la aplicación necesita:

- [JDK 20+](https://www.oracle.com/java/technologies/downloads/#java20)
- [Maven 3+](https://maven.apache.org)

Configurar datos de la base de datos MySQL: [application.properties](https://github.com/Marc0Franc0/Forum/blob/main/src/main/resources/application-dev.properties)

Ejecutar localmente

```shell
mvn clean install
```
```shell
mvn spring-boot:run
```

Dirigirse a:
- [http://localhost:8080/](http://localhost:8080/)
- [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
