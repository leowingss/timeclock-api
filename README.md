# TimeClock API

This project is a RESTful API designed for employee time tracking, created as a test assignment for a Junior Backend Java position.

The API allows employees to register their work hours by clocking in and out, while enforcing the following business rules:
- ✅ A maximum of 4 time entries can be registered per day.
- ✅ It is not allowed to register time entries on weekends (Saturday and Sunday).
- ✅ There must be a minimum break of 1 hour for lunch (to be implemented).
- ✅ It provides an endpoint to retrieve the total hours worked on a specific day.

---
## 📌Technologies
- Java 17
- Spring Boot
- Spring Data JPA
- H2 database
- Docker for containerization
- GitHub Actions for CI/CD

---
## 📢 Prerequisites
Before running the application, ensure you have the following installed:
- ✅ Java 17 or higher 
- ✅ Maven 
- ✅ Git (optional, for cloning the repository) 
- ✅ Docker (If running the application in a container)
----
## 🚀 Running the Application

### Clone the repository

```shell
git clone https://github.com/leowingss/timeclock-api.git
cd timeclock-api
```

### Build the project
```shell
mvn clean install
```

### Run the application
```shell
mvn spring-boot:run
```

The API will be running at: http://localhost:8080

---
## 🐳 Running with Docker

Ensure you have Docker installed and running.

### Build the image
```shell
docker build -t timeclock-api .
```

### Run the container
```shell
docker run -p 8080:8080 timeclock-api
```

The API will be running at: http://localhost:8080

### Stop the container
```shell
docker ps
docker stop <container_id>
```
--- 

## 🛠 Running Tests
```shell
mvn test
```
---
## 🛠 CI/CD Pipeline
This project uses GitHub Actions to automatically run tests on each push and pull request to the main branch.


---
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