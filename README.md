# Sanos-Salvos-Pet

[cite_start]Microservicio encargado de la gestión integral de mascotas (CRUD), historiales de salud y envío de eventos de notificación mediante Apache Kafka dentro del ecosistema Sanos y Salvos[cite: 72].

## Requisitos
* Java JDK 17
* Maven 3.8+
* Instancia de PostgreSQL
* Apache Kafka (Producer)

## Instalación
1. Clonar repositorio:
   git clone https://github.com/TU_USUARIO/Sanos-Salvos-Pet.git
   cd Sanos-Salvos-Pet

2. Instalar dependencias:
   .\mvnw clean install (Windows) o ./mvnw clean install (Linux/Mac)

## Configuración
Archivo: `src/main/resources/application.properties`
```properties
server.port=8081
spring.application.name=pet-service

# Base de Datos
spring.datasource.url=jdbc:postgresql://localhost:5432/pet_db
spring.datasource.username=postgres
spring.datasource.password=tu_password

# Kafka Config
spring.kafka.bootstrap-servers=localhost:9092
```

## Ejecucion Pruebas Api
```
Registrar mascota

Desde consola: mvn spring-boot:run

El servicio quedará disponible en: http://localhost:8081
{
"nombre": "Firulais",
"especie": "Perro",
"raza": "Labrador",
"usuarioId": 1
}
```
## Tecnologías utilizadas

Spring Boot 3.2.4

Spring Cloud OpenFeign (Patrón Proxy)

Resilience4j (Circuit Breaker)