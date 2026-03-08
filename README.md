# Spring-JDBCEX

A small, focused example project that demonstrates using Spring Boot with Spring's JdbcTemplate to perform basic CRUD operations on a `Student` entity.

This repo is intended as a learning example for developers who want to understand how to use Spring JDBC (JdbcTemplate) instead of JPA/Hibernate for simple database interactions.

Contents
- Project overview and goals
- Prerequisites
- Setup and run (Windows PowerShell)
- What the example demonstrates (files to inspect)
- Schema and sample data
- How to extend the example
- Common problems and troubleshooting

Project overview and goals

This project shows a minimal but well-structured Spring Boot application that:
- Uses Spring Boot's auto-configuration and Spring JDBC (JdbcTemplate) for data access.
- Separates responsibilities into `model`, `Repository`, and `service` packages.
- Provides SQL scripts (`Schema.sql`, `Data.sql`) to create and populate the database used by the example.

Goals:
- Teach how to use `JdbcTemplate` for queries and updates.
- Show a simple repository-service pattern without JPA/Hibernate.
- Demonstrate how to keep SQL close to where it is used and how to map result sets to domain objects.

Prerequisites

- Java 11+ (verify with `java -version`)
- Maven (or use the included Maven wrapper `mvnw` / `mvnw.cmd`)
- (Optional) A SQL client if you want to inspect the database file used by tests or local setup

Quick setup and run (Windows PowerShell)

Open PowerShell in the project root (`D:\JAVA-SPRINGBOOT\Spring-JDBCEX`) and run the following commands:

```powershell
# build the project (skip tests if you want a faster build)
./mvnw.cmd -DskipTests package

# run the application
./mvnw.cmd spring-boot:run
```

If you prefer to run the packaged jar:

```powershell
./mvnw.cmd -DskipTests package
java -jar target/*.jar
```

What this example demonstrates (where to look)

- `src/main/java/org/example/springjdbcex/model/Student.java` — domain model for a student (fields, constructors, getters/setters).
- `src/main/java/org/example/springjdbcex/Repository/StudentRepo.java` — repository that uses `JdbcTemplate` for SQL operations (select/insert/update/delete). Study the SQL strings and the row-mapping logic.
- `src/main/java/org/example/springjdbcex/service/StudentService.java` — service layer that orchestrates repository calls and contains business rules.
- `src/main/java/org/example/springjdbcex/SpringJdbcexApplication.java` — Spring Boot entrypoint and configuration bootstrap.
- `src/main/resources/Schema.sql` and `Data.sql` — DDL and seed data used by Spring Boot when initializing an in-memory or embedded database.
- `src/main/resources/application.properties` — application configuration.

Database schema and sample data

The project includes `Schema.sql` and `Data.sql` in `src/main/resources` which are executed automatically by Spring Boot when using an embedded database (like H2). They define the table(s) and some sample rows so the app can run without manual DB setup.

Why use Spring JdbcTemplate here?

- Simplicity: For small datasets and simple CRUD, `JdbcTemplate` is lightweight and straightforward.
- Control: You write raw SQL, so you know exactly what executes and can optimize queries easily.
- No ORM complexity: Avoids the learning overhead or unexpected behavior of JPA mappings for simple use-cases.

When to use this approach

- Small services or microservices with simple domain models and queries.
- Applications that require highly tuned SQL and explicit control over queries.
- Projects where you want to avoid the complexity of an ORM.

When to choose JPA/Hibernate instead

- Complex domain models with lots of relationships.
- When you prefer an object graph and lazy-loading semantics.
- If you want automatic schema generation based on entities (and accept the ORM abstractions).

How to extend this example

- Add unit tests for `StudentRepo` using a test-specific datasource (H2) and `@JdbcTest`.
- Add exception handling and custom exceptions at the service layer for domain-specific errors.
- Add paging and sorting for queries that return many rows.
- Introduce transactions via `@Transactional` on service methods when multiple repository calls must be atomic.

Common problems and troubleshooting

- "Table not found" on startup: Ensure `Schema.sql` is located in `src/main/resources` and Spring Boot is configured to run it (default behavior for embedded DBs). If using an external DB, run the DDL yourself.
- Port conflict: Spring Boot uses port 8080 by default. Change `server.port` in `application.properties` or stop the process using that port.
- Dependency errors: Run `./mvnw.cmd -U clean package` to update and rebuild.

Next steps and suggestions

- Add a `docs/` folder (this repository contains `docs/ARCHITECTURE.md` and `docs/USAGE.md`) that explain architecture and class-level usage (already added).
- Convert repository tests to integration tests using Testcontainers to run a real DB in CI.
- Add a `CONTRIBUTING.md` and `CODE_OF_CONDUCT.md` if you plan to accept external contributions.

Credits

This project is a learning example. Keep comments inside the source files — this documentation intentionally does not alter or remove any existing comments in source code.

License

Choose a license (MIT/Apache-2.0) if you plan to publish this example; none is set by default in this repo.
