# Client Services - Spring Boot Backend

## Descripción
Backend modular para gestión de clientes, construido con Java 17, Spring Boot, JPA, PostgreSQL y Flyway. Incluye CRUD, búsqueda avanzada, soft delete, migraciones automáticas y documentación OpenAPI/Swagger.

## Tecnologías principales
- Java 17
- Spring Boot 4.x
- Spring Data JPA
- PostgreSQL
- Flyway (migraciones)
- Springdoc OpenAPI (Swagger UI)
- Maven

## Configuración de la base de datos
- Motor: **PostgreSQL**
- Nombre de la base de datos: `clients`
- Usuario: `postgres`
- Contraseña: `2005`
- Host: `localhost`
- Puerto: `5432`

Puedes modificar estos valores en `src/main/resources/application.properties`:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/clients
spring.datasource.username=postgres
spring.datasource.password=2005
```

## Migraciones
Las migraciones de esquema y datos se aplican automáticamente al iniciar la app usando Flyway.
- Archivos de migración: `src/main/resources/db/migration/`
- Ejemplo:
  - `V1__insert_person_types_and_document_types.sql`: Inserta tipos de persona y documento.
  - `V2__add_active_to_client.sql`: Agrega columna `active` para soft delete.

## Comandos de ejecución
- Compilar y testear:
  ```
  mvn clean install
  ```
- Ejecutar la aplicación:
  ```
  mvn spring-boot:run
  ```

## Endpoints y documentación
- La API REST corre por defecto en: [http://localhost:8080](http://localhost:8080)
- Documentación Swagger/OpenAPI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Recomendaciones
- Asegúrate de tener PostgreSQL corriendo y la base de datos `clients` creada antes de iniciar la app.
- Las migraciones Flyway se ejecutan automáticamente al levantar el backend.
- Puedes cambiar el puerto o credenciales en `application.properties`.
- Para desarrollo, puedes usar [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools) para recarga automática.

## Scripts útiles
- Crear la base de datos en PostgreSQL:
  ```sql
  CREATE DATABASE clients;
  ```

---

Cualquier duda, revisa la documentación en Swagger o consulta al equipo de desarrollo.
