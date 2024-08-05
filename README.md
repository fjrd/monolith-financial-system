# Application Name

This is a sample application for demonstrating the implementation of a Java Spring Boot application with Postgres, and additional tools. The application includes user management, account management, and other essential features.

## Prerequisites

- [Docker](https://www.docker.com/products/docker-desktop)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Technologies Used

- **Java 11**
- **Spring Boot**
- **Postgres**
- **Maven**
- **Liquibase for Database Migrations**
- **JWT for Authentication**
- **Testcontainers for Integration Testing**
- **OpenAPI for API Documentation**

## Documentation

For detailed specifications and requirements, please refer to the [Specification.md](./docs/Specification.md) file.

## API Documentation

To access the API documentation, visit [Swagger UI](http://localhost:8080/swagger-ui/index.html#/).

## Database Schema

For a visual representation of the database schema, please refer to the [DatabaseSchema.md](./docs/DatabaseSchema.md) file.

## Lock system schema

Primitive lock system schema [Lock schema](./docs/PrimitiveLockSystem.md).


## Start up
```shell
docker-compose -f mfs-iac/docker-compose.yml up --build
```
