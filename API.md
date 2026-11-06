# Task API Documentation

## Base URL

```text
http://localhost:8080
```

All task resources use the `/tasks` path.

## Architecture

The API is organized into layers:

```text
Client
  -> Controller
  -> DTO validation
  -> Service
  -> Repository
  -> PostgreSQL database
```

- **Controller**: Handles HTTP requests and responses.
- **DTOs**: Define and validate the JSON accepted and returned by the API.
- **Service**: Contains task business logic.
- **Repository**: Performs database operations through Spring Data JPA.
- **Entity**: Maps a task to the `tasks` database table.
- **Exception handler**: Converts application errors into HTTP error responses.

## Task JSON

A task has the following fields:

```json
{
  "id": 1,
  "description": "Complete the Spring Boot REST API",
  "status": "TODO",
  "createdAt": "2026-09-04T15:00:00.000+00:00",
  "updatedAt": "2026-09-04T15:00:00.000+00:00"
}
```

Valid status values are:

```text
TODO
IN_PROGRESS
DONE
```

## Endpoints

### Create a task

```http
POST /tasks
Content-Type: application/json
```

Request body:

```json
{
  "description": "Complete the Spring Boot REST API",
  "status": "TODO"
}
```

Returns `201 Created`, including a `Location` header pointing to the new task.

### Get all tasks

```http
GET /tasks
```

Returns `200 OK` with an array of tasks.

### Filter tasks by status

```http
GET /tasks?status=TODO
```

The status filter is case-insensitive. For example, `todo` is accepted as
`TODO`.

### Get one task

```http
GET /tasks/{id}
```

Example:

```http
GET /tasks/1
```

Returns `200 OK`. If the ID does not exist, returns `404 Not Found`.

### Replace a task

```http
PUT /tasks/{id}
Content-Type: application/json
```

`PUT` replaces the task fields, so both fields are required:

```json
{
  "description": "Complete the Spring Boot REST API",
  "status": "IN_PROGRESS"
}
```

Returns `200 OK` with the updated task.

### Partially update a task

```http
PATCH /tasks/{id}
Content-Type: application/json
```

One or both fields may be supplied:

```json
{
  "description": "Add API documentation"
}
```

Or:

```json
{
  "status": "DONE"
}
```

At least one field is required. Returns `200 OK` with the updated task.

### Delete a task

```http
DELETE /tasks/{id}
```

Returns `204 No Content` when deletion succeeds. If the ID does not exist,
returns `404 Not Found`.

## Validation and errors

- `description` is required for `POST` and `PUT`.
- `description` must not be blank.
- `description` may contain a maximum of 100 characters.
- `status` is required for `POST` and `PUT`.
- `status` must be `TODO`, `IN_PROGRESS`, or `DONE`.
- `PATCH` must contain at least one field.

Example error:

```json
{
  "error": "Task not found with id: 10"
}
```

Common status codes:

| Status | Meaning |
| --- | --- |
| `200 OK` | Request completed successfully |
| `201 Created` | A task was created |
| `204 No Content` | A task was deleted |
| `400 Bad Request` | Request data is invalid |
| `404 Not Found` | The task ID does not exist |
