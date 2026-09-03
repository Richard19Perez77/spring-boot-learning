# Spring Boot Learning

A small personal sandbox for learning **Spring Boot**. It started as a Gradle-generated Spring Boot 3 app named `rick` and grew into a handful of REST endpoints, a servlet filter that logs every request, and a JPA entity that stores those logs.

This is not a production service. The controllers return hardcoded strings so you can see routing, filters, and persistence without extra domain logic.

## What it includes

- Spring Boot 3.4 REST API (`spring-boot-starter-web`)
- Request logging filter (`RequestLoggingFilter`)
- Spring Data JPA persistence of request logs
- In-memory **H2** by default (no database install required)
- Optional **PostgreSQL** profile
- Sample endpoints for a generic API, a finance slice, and an e-commerce slice

## Requirements

- Java 17 or newer (Java 21 works)
- Git

Gradle is included via the wrapper (`gradlew` / `gradlew.bat`).

## Run locally

From the project root:

```bash
./gradlew bootRun
```

On Windows PowerShell:

```powershell
.\gradlew.bat bootRun
```

The app listens on [http://localhost:8080](http://localhost:8080).

On startup it prints every Spring bean (a leftover from the official getting-started sample). After that, try:

| Method | URL | Response |
| ------ | --- | -------- |
| GET | `/api/hello` | `Hello, Spring Boot!` |
| GET | `/api/finance/hellofinance` | `Hello, Finance!` |
| GET | `/api/finance/finances` | `Finances` |
| GET | `/api/ecomm` | `Hello, EComm!` |
| GET | `/api/ecomm/getproducts` | `Products` |

H2 console (default profile only): [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

- JDBC URL: `jdbc:h2:mem:learningdb`
- User: `sa`
- Password: *(empty)*

Request logs are stored in the `user_request_logs` table.

## Tests

```bash
./gradlew test
```

## Optional PostgreSQL

Create a local database named `rickdb` with user `rick` / `rickrick`, then start with the `postgres` profile:

```bash
./gradlew bootRun --args='--spring.profiles.active=postgres'
```

Settings live in `src/main/resources/application-postgres.properties`. Change those credentials for anything other than local practice.

## Project layout

```
src/main/java/com/rick/webservice/rick/
  RickApplication.java          # Spring Boot entry point
  HelloController.java          # /api/hello
  HelloFinanceController.java   # /api/finance/*
  HelloECommController.java     # /api/ecomm/*
  RequestLoggingFilter.java     # logs and persists each request
  UserRequestLog.java           # JPA entity
  UserRequestLogRepository.java # Spring Data repository
  RequestLog.java               # unused leftover entity
```

## Why it used to fail

JPA auto-configuration was turned off so missing Postgres would not break startup, but `RequestLoggingFilter` still required `UserRequestLogRepository`. Spring could not create that bean, so the app never started. Default H2 plus enabled JPA fixes that.
