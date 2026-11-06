# TaskFlow — Spring Boot Task API

A REST API for managing tasks with Spring Boot, Spring Data JPA, and PostgreSQL.

## Features

- Create, list, retrieve, replace, partially update, and delete tasks
- Filter tasks by status: `TODO`, `IN_PROGRESS`, or `DONE`
- Validate request DTOs and return structured error responses
- Track creation and update timestamps

## Run locally

Requirements: Java 17 or newer and PostgreSQL.

Create a PostgreSQL database, then configure your connection through environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5433/TestDB
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=your_local_password
./mvnw spring-boot:run
```

On Windows, use `mvnw.cmd spring-boot:run`. The API runs at `http://localhost:8080`.
Hibernate updates the database schema on startup (`ddl-auto=update`).

## Example

```bash
curl -X POST http://localhost:8080/tasks \
  -H 'Content-Type: application/json' \
  -d '{"description":"Ship my task API","status":"TODO"}'
```

See [API.md](API.md) for endpoints, request examples, and error responses.

## Build and test

```bash
./mvnw clean package
```

The application context test requires a running PostgreSQL database with the configured connection settings.
