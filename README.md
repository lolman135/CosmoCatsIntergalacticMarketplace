# 🐱 Cat Market Order Service

A RESTful order management service for a cat-themed marketplace, built with Kotlin and Spring Boot. The project follows **Clean Architecture** and **Domain-Driven Design (DDD)** principles.

---

## Tech Stack

| Category | Technology |
|---|---|
| Language | Kotlin (targeting Java 21) |
| Framework | Spring Boot |
| Persistence | PostgreSQL, Spring Data JPA, Liquibase |
| Authentication | OAuth2, GitHub OAuth |
| Containerization | Docker, Docker Compose |
| Testing | JUnit, Mockito-Kotlin, Testcontainers |
| Coverage | JaCoCo |
| Build Tool | Gradle (Gradle Wrapper) |

---

## Architecture

The project is structured around **Clean Architecture** with **DDD** tactical patterns:

- **Domain layer** — entities, value objects, domain events, repository interfaces
- **Application layer** — use cases, application services
- **Infrastructure layer** — JPA repositories, external integrations, persistence and data mapping
<br> • **Note**: Infrastructure layer divided for persistence, mappers and feature toggles
- **Web layer** — REST controllers, requests validation


---

## API Overview

Base path: `/api/v1`

### Categories

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/categories` | Create a category |
| `GET` | `/categories` | Get all categories |
| `GET` | `/categories/{id}` | Get category by ID |
| `PUT` | `/categories/{id}` | Update category by ID |
| `DELETE` | `/categories/{id}` | Delete category by ID |

### Products

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/products` | Create a product |
| `GET` | `/products` | Get all products |
| `GET` | `/products/{id}` | Get product by ID |
| `PUT` | `/products/{id}` | Update product by ID |
| `DELETE` | `/products/{id}` | Delete product by ID |

### Carts

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/carts` | Get current user's cart |
| `PUT` | `/carts/items/{productId}` | Add/update product in cart |
| `DELETE` | `/carts` | Clear the cart |

### Orders

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/orders` | Create order from current cart |
| `GET` | `/orders` | Get all orders |
| `GET` | `/orders/{id}` | Get order by ID |
| `DELETE` | `/orders/{id}` | Delete order by ID |

Full OpenAPI specification is available in `openapi.yaml` at the project root.

---

## Getting Started

### Prerequisites

- JDK 21+
- Docker & Docker Compose
- Gradle (or use the included Gradle Wrapper)

---

### Running with Gradle (local development)

Make sure a PostgreSQL instance is available and configure the environment variables or `application.yml` accordingly, then run:

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`.

---

### Running as a JAR

**1. Build the JAR:**

```bash
./gradlew bootJar
```

**2. Run the JAR:**

```bash
java -jar build/libs/CatMarket-*.jar \
  --spring.datasource.url=jdbc:postgresql://localhost:5432/catmarket_db \
  --spring.datasource.username=admin \
  --spring.datasource.password=AdminAdmin
```

Or export the environment variables first:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/catmarket_db
export DB_USER=admin
export DB_PASSWORD=AdminAdmin

java -jar build/libs/CatMarket-*.jar
```

---

### Running with Docker Compose

This is the recommended way to run the full stack (application + PostgreSQL).

**1. Build the JAR before composing:**

```bash
./gradlew bootJar
```

**2. Start all services:**

```bash
docker compose up --build
```

The API will be available at `http://localhost:8080`.

**Stop all services:**

```bash
docker compose down
```

**Stop and remove volumes:**

```bash
docker compose down -v
```

#### Docker Compose Services

| Service | Image | Port | Description |
|---|---|---|---|
| `catmarket` | Built locally | `8080` | Spring Boot application |
| `postgres` | `postgres:17` | `5432` | PostgreSQL database |

---

## Running Tests

```bash
./gradlew test
```

Tests use **Testcontainers** to spin up real infrastructure (PostgreSQL) during integration tests, so Docker must be running.

**Generate JaCoCo coverage report:**

```bash
./gradlew jacocoTestReport
```

The HTML report will be available at `build/reports/jacoco/test/html/index.html`.

---

## Authentication

The service uses **OAuth2** with **GitHub** as the identity provider. To configure GitHub OAuth, set the following properties (via `application.yml` or environment variables):

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          github:
            client-id: YOUR_GITHUB_CLIENT_ID
            client-secret: YOUR_GITHUB_CLIENT_SECRET
```

Register a new OAuth App at [GitHub Developer Settings](https://github.com/settings/developers) and set the callback URL to:

```
http://localhost:8080/login/oauth2/code/github
```

