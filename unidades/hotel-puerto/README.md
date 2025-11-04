# docencia-notes

Proyecto Spring Boot + JPA + SQLite (Java 17) con test de integración CRUD.

## Estructura clave

- src/main/java/com/docencia → código de la app
- src/main/resources/application.yaml → config runtime (usa ./data/notedb.sqlite)
- src/test/resources/application-test.yaml → config SOLO para tests (usa copia en resources)
- src/test/resources/notedb.sqlite → base de datos SQLite existente (copiada a target/test-classes/notedb.sqlite durante los tests)
- NoteJpaRepositoryIT → test de integración CRUD real

## Ejecutar tests

mvn clean test

## Levantar la app

Necesitas que exista ./data/notedb.sqlite o ajustar spring.datasource.url en application.yaml

mvn spring-boot:run
