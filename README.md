# Bachelor thesis project Studrive Documentation
### by Dennis Zeller, Reutlingen University, 31.01.2022
## Overview

This project provides a prototype to determine the impact of microservice patterns on non-functional requirements.

## Subject

The Studrive application is a fictional carpooling service for students only. Users can register, offer rides or even book seats.

## Required Installation

- Java 8 or higher
- Gradle
- Docker
- Docker-compose

## Microservices

- [user-service](https://github.com/dnszlr/studrive/tree/master/user-service): Management of users and payment details
- [offer-service](https://github.com/dnszlr/studrive/tree/master/offer-service): Management of offered rides
- [order-service](https://github.com/dnszlr/studrive/tree/master/order-service): Management of ordered seats
- [accounting-service](https://github.com/dnszlr/studrive/tree/master/accounting-service): Management of accounts for booked seats
- [studrive-gateway](https://github.com/dnszlr/studrive/tree/master/studrive-gateway): The Studrive applications API gateway
## Getting started

In every Microservice's application.properties file the spring profile has to be selected to run locally or via docker container.

The following steps are required for a deployment via docker containers.

1. Navigate to the local folder where the Studrive application is located.
2. **./gradlew build**
3. **docker-compose up**

To stop the application simply write **docker-compose down**

## Microservice Endpoints

| Microservice       | Port | Language | Framework    | Database   | Messaging    |
|--------------------|------|----------|--------------|------------|--------------|
| studrive-gateway   | 9000 | Java     | Spring Boot  | -          | -            |
| user-service       | 9001 | Java     | Spring Boot | PostgreSQL | -            |
| offer-service      | 9002 | Java     | Spring Boot | MongoDB    | RabbitMQ     |
| order-service      | 9003 | Java     | Spring Boot | MongoDB    | RabbitMQ |
| accounting-service | 9004 | Java     | Spring Boot | PostgreSQL | RabbitMQ |

Using a port from the table, a service can be reached via localhost

## Swagger UI

API documentation is provided via Swagger UI. These can be found via the following links.
- [studrive-gateway](https://github.com/dnszlr/studrive/tree/master/studrive-gateway): http://localhost:9000/studrive-swagger
- [user-service](https://github.com/dnszlr/studrive/tree/master/user-service): http://localhost:9001/swagger-ui.html
- [offer-service](https://github.com/dnszlr/studrive/tree/master/offer-service): http://localhost:9002/swagger-ui.html
- [order-service](https://github.com/dnszlr/studrive/tree/master/order-service): http://localhost:9003/swagger-ui.html

To create a Swagger UI for future microservices, the [openapi-config](https://github.com/dnszlr/studrive/tree/master/openapi-config) module must be imported to the respective service.

To include the paths of a microservice into the studrive-gateway Swagger UI a example can be found [here](https://github.com/dnszlr/studrive/blob/master/offer-service/src/main/java/com/zeller/studrive/offerservice/OfferServiceApplication.java)

To extend the Swagger UI of the studrive gateway, the respective paths must be added to the [application.yml](https://github.com/dnszlr/studrive/blob/master/studrive-gateway/src/main/resources/application.yml) file.

## Useful tools

- Actuator: Provides endpoints for further measurements or to monitor the system. Example for user-service, http://localhost:9001/actuator/
- RabbitMQ Management: Admin Interface for rabbitMQ. Can be found here http://localhost:15672/
- pgadmin4: Admin Interface for PostgreSQL database. Can be found here http://localhost:5050/
- mongo-express: Admin Interface for MongoDB database. Can be found here http://localhost:8081/

## Measurement of non-functional requirements

The scripts that are used to measure the non-functional requirements availability and performance efficiency requirements can be found [here](https://github.com/dnszlr/studrive/tree/master/clientside-shellscript).

