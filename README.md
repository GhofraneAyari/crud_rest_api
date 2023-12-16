# CRUD Rest API

A simple CRUD REST API built with Spring Boot for managing user data.

## Features
- Create, Read, Update, Delete operations for users

## Technologies
- Java 17
- Spring Boot 3.2.0
- PostgreSQL
- Spring Data JPA
- Spring HATEOAS
- Springfox (Swagger) for API documentation
- Lombok
- Problem Spring Web for exception handling
- BDDMockito
- JUnit

## API Documentation
Swagger: [http://localhost:8080/swagger-ui.index.html]

## Endpoints
- POST /api/users: Create a new user
- GET /api/users/{userId}: Retrieve user details
- PUT /api/users/{userId}: Update user data
- DELETE /api/users/{userId}: Delete a user
