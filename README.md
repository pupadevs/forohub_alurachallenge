# ForoHub API

ForoHub API es una API desarrollada en Java utilizando Spring Boot, que permite la creación y gestión de tópicos y respuestas en un foro. La aplicación está diseñada siguiendo una arquitectura por capas, con uso de DTOs y patrones de diseño para mantener un código limpio y mantenible.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot**
- **MySQL 8**
- **Librerías y Dependencias**:
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-security`
  - `com.auth0:java-jwt`
  - `spring-boot-starter-validation`
  - `spring-boot-starter-web`
  - `spring-boot-devtools`
  - `mysql-connector-j`
  - `lombok`
  - `spring-boot-starter-test`
  - `spring-security-test`

## Características

- **Gestión de Tópicos**: Crea, lee, actualiza y elimina tópicos en el foro.
- **Gestión de Respuestas**: Crea, lee, actualiza y elimina respuestas para los tópicos.
- **Autenticación y Autorización**: Utiliza JWT para la autenticación y autorización de usuarios.
- **Validación**: Validación de datos de entrada utilizando Spring Boot Validation.

## Instalación

1. **Clonar el repositorio**:
    ```bash
    git clone https://github.com/tuusuario/foroHub-api.git
    cd foroHub-api
    ```

2. **Configurar la base de datos**:
    - Crear una base de datos MySQL.
    - Configurar las credenciales de la base de datos en el archivo `application.properties`.

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/foroHub
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    spring.jpa.hibernate.ddl-auto=update
    ```

3. **Construir y ejecutar la aplicación**:
    ```bash
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```

## Uso

### Endpoints
#### Usuario
- **Crear un usuario**
    - **URL**: `/register`
    - **Método**: `POST`
    - **Body**:
      ```json
      {
         "name": "Nombre del usuario",
          "email": "email del usuario",
          "password": "password del usuario",
          
      }
      ```
       **Iniciar Sesion**
    - **URL**: `/Login`
    - **Método**: `POST`
    - **Body**:
      ```json
      {
         
          "email": "email del usuario",
          "password": "password del usuario",
          
      }
      ```
#### Tópicos

- **Crear un Tópico**
    - **URL**: `/topic`
    - **Método**: `POST`
    - **Body**:
      ```json
      {
         "topicID": "Id del topico",
          "title": "Nuevo titulo del Tópico",
          "message": "Message del topico",
          "author": "Author del topico",
          "response": "Respuesta del topico 
      }
      ```

- **Obtener todos los Tópicos**
    - **URL**: `/topic`
    - **Método**: `GET`
 
      ```json
      {
      "topicID": "Id del topico",
          "title": "Nuevo titulo del Tópico",
          "message": "Message del topico",
          "author": "Author del topico",
          "response": "Respuesta del topico"
      }
      ```

- **Obtener un Tópico por ID**
    - **URL**: `/topics/ahow/{id}`
    - **Método**: `GET`

- **Actualizar un Tópico**
    - **URL**: `/topics/update/{id}`
    - **Método**: `PUT`
    - **Body**:
      ```json
      {
          "topicID": "Id del topico",
          "title": "Nuevo titulo del Tópico",
          "message": "Message del topico",
          "author": "Author del topico",
          "response": "Respuesta del topico"
      
      }
      ```

- **Eliminar un Tópico**
    - **URL**: `/topics/delete/{id}`
    - **Método**: `DELETE`

#### Respuestas

- **Crear una Respuesta**
    - **URL**: `/response`
    - **Método**: `POST`
    - **Body**:
      ```json
      {
          "content": "Contenido de la Respuesta"
      }
      ```



- **Obtener una Respuesta por ID**
    - **URL**: `/api/responses/{id}`
    - **Método**: `GET`

- **Actualizar una Respuesta**
    - **URL**: `/response/{id}`
    - **Método**: `PUT`
    - **Body**:
      ```json
      {
          "content": "Nuevo Contenido de la Respuesta"
      }
      ```


## Arquitectura

La aplicación sigue una arquitectura por capas:

- **Controller**: Maneja las solicitudes HTTP y las respuestas.
- **Service**: Contiene la lógica de negocio.
- **Repository**: Interactúa con la base de datos.
- **DTO**: Objetos de Transferencia de Datos utilizados para transferir datos entre las capas.


