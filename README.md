# demoInditex
demo inditex
# Inditex Price API

Microservicio REST desarrollado como prueba técnica para la plataforma de Inditex. La aplicación expone un endpoint para consultar el precio aplicable a un producto en función de la fecha, el identificador del producto y el identificador de la cadena.

---

## 🛠 Tecnologías utilizadas

- Java 17
- Spring Boot 3.5.0
- Maven
- H2 (modo PostgreSQL, solo perfil Dev)
- JPA/Hibernate
- OpenAPI 3.0
- Docker
- Karate (tests E2E)

---

## 📦 Estructura general

El proyecto está organizado en capas siguiendo una arquitectura **hexagonal**, separando claramente dominio, aplicación e infraestructura.

---

## 🚀 Ejecución

### Opción 1: Docker

Se puede ejecutar directamente vía Docker:
No hay imagen de postgres en el compose, h2 basta.
## 🐘 Perfil `dev` con PostgreSQL (opcional)

Si deseas usar una base de datos PostgreSQL en lugar de H2 (solo en desarrollo):

1. Arranca PostgreSQL con Docker Compose:

```bash
docker-compose up -d


```bash
docker build -t inditex-price-api .
docker run -p 8080:8080 inditex-price-api
-----------------------------Postgres------------------------------
mvn spring-boot:run -Dspring-profiles.active=dev

