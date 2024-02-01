# [StripeChallenge](https://github.com/Marc0Franc0/StripeChallenge#stripechallenge)

## FrontendAngular

### Características
- Inicio de sesión
- Vista de datos del usuario

### Tecnologías
- Angular 15.2.1

### Ejecución
1. Clonar repositorio: git clone https://github.com/Marc0Franc0/ChallengeStripe
2. Ir al directorio del proyecto: cd ChallengeStripe/FrontendAngular
3. Seguir pasos de ejecución

### Ejecutar localmente

Para construir y ejecutar la aplicación necesita:
- [Node.js](https://nodejs.org/en/download/)
  
```shell
npm install
```

```shell
ng serve
```
 
## BackendSpring

### Características
- Registro de usuario e inicio de sesión con autenticación JWT
- Cifrado de contraseña usando BCrypt

### Tecnologías
- Spring Boot 3.0
- Java 17
- Spring Security
- JSON Web Tokens (JWT)
- BCrypt
- Maven
- PostgreSQL

### Ejecución
1. Clonar repositorio: git clone https://github.com/Marc0Franc0/ChallengeStripe
2. Ir al directorio del proyecto: cd ChallengeStripe\BackendSpring
3. Seguir pasos de ejecución

### Ejecutar con Maven

Para construir y ejecutar la aplicación necesita:

- [JDK 17+](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 3+](https://maven.apache.org)

Configurar datos de la base de datos Postgress: [application.properties](https://github.com/Marc0Franc0/Forum/blob/main/src/main/resources/application-dev.properties)

Ejecutar localmente

```shell
mvn clean install
```
```shell
mvn spring-boot:run
```

Dirigirse a:
- [Documentación Swagger](http://localhost:8080/swagger-ui/index.html)

### Endpoints

#### Crear usuario
`POST /api/v1/auth/register`
http://localhost:8080/api/v1/auth/register

#### Iniciar sesión
`POST /api/v1/auth/login`
http://localhost:8080/api/v1/auth/login

#### Obtener usuario
`GET /api/v1/users/{username}`
http://localhost:8080/api/v1/users/{username}

#### Crear suscripción
`POST /api/v1/subs/`
http://localhost:8080/api/v1/subs/

#### Modificar estado de suscripción
`PUT /api/v1/subs/{id}/{active}`
http://localhost:8080/api/v1/subs/{id}/{active}

#### Confirmar pago de suscripción
`POST /api/v1/stripe/confirm`
http://localhost:8080/api/v1/stripe/confirm

#### Cancelar pago de suscripción
`POST /api/v1/stripe/cancel/{id}`
http://localhost:8080/api/v1/stripe/cancel/{id}

