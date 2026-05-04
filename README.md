# Varenichnaya

Small Spring Boot CRUD project for learning CI/CD with Jenkins and SonarQube.

## Stack

- Java 21
- Spring Boot 4.0.6
- Maven
- Spring Web
- Spring Data JPA
- H2 database
- DTO
- Unit tests
- Integration tests
- JaCoCo
- Jenkinsfile

## Eclipse import

1. File -> Import...
2. Maven -> Existing Maven Projects
3. Select the `varenichnaya` folder
4. Finish
5. Make sure Eclipse uses JDK 21

## First check

Run from project folder:

```bash
mvn clean verify
```

Or in Eclipse:

Right click project -> Run As -> Maven build...

Goals:

```bash
clean verify
```

## Run app

```bash
mvn spring-boot:run
```

Base URL:

```text
http://localhost:8080/api/dishes
```
