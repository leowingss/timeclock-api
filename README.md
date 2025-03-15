# TimeClock API

This project is a RESTful API designed for employee time tracking, created as a test assignment for a Junior Backend Java position.


## 📌Technologies
- Java 17
- Spring Boot
- Spring Data JPA
- H2 database.
- Docker for containerization
- GitHub Actions for CI/CD

## 📢 Prerequisites
Before running the application, ensure you have the following installed:
✅ Java 17 or higher 
✅ Maven 
✅ Git (optional, for cloning the repository) 

## 🚀 Running the Application

Clone the repository

```shell
git clone https://github.com/leowingss/timeclock-api.git
cd timeclock-api
```

Build the project
```shell
mvn clean install
```

Run the application
```shell
mvn spring-boot:run
```

The API will be running at: http://localhost:8080

--- 

## 🛠 Running Tests
```shell
mvn test
```

## 📖 API Documentation

This API is documented using Springdoc OpenAPI. You can access the API documentation at:

[Swagger UI](http://localhost:8080/swagger-ui/index.html)


Here’s an example of how the POST /api/time-entry endpoint is documented:

```yaml
POST /api/time-entry:
  summary: Register a new time entry
  description: Creates a new time entry for the current day. If the timestamp is not provided, the current time is automatically assigned. Time entries are not allowed on weekends.
  requestBody:
    required: true
    content:
      application/json:
        schema:
          type: object
          properties:
            type:
              type: string
              enum: [CLOCK_IN, CLOCK_OUT]
              example: CLOCK_IN
  responses:
    201:
      description: Time entry registered successfully
      content:
        application/json:
          schema:
            $ref: '#/components/schemas/TimeEntryPostResponse'
    400:
      description: Invalid input or validation error
    500:
      description: Internal server error

```