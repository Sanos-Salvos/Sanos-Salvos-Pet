# Sanos-Salvos-Pet

Microservicio de gestion de mascotas y alertas de rastreo

## Puerto

8082

## Base de datos

mascotas_db

## Endpoints disponibles

POST /api/pet/registrar
GET /api/pet/listar
GET /api/pet/{id}
PUT /api/pet/actualizar/{id}
DELETE /api/pet/eliminar/{id}

## Ejecucion con Docker

docker-compose up --build

## Ejecucion manual

mvn spring-boot:run

## Tecnologias

- Java 21
- Spring Boot 3.2
- Spring Security + JWT
- PostgreSQL
- Docker
