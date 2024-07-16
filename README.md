# BookMyTicket

BookMyTicket is an online movie ticket booking application that allows users to select cities, theatres, running movie shows, and book multiple available seats. The application is built using Java and Spring Boot.

## Table of Contents
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Running Tests](#running-tests)
- [Contributing](#contributing)
- [License](#license)

## Features
- User registration and login
- City and theatre selection
- Viewing running movie shows
- Selecting and booking multiple seats
- Payment processing and booking confirmation
- Admin functionalities for adding movies, seats, and theatres

## Technology Stack
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Swagger for API documentation
- Docker for containerization

## Getting Started
### Prerequisites
- Java 11 or higher
- Gradle
- PostgreSQL
- Docker (optional for running PostgreSQL)

### Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/lakshaygpt28/BookMyTicket.git
    cd BookMyTicket
    ```

2. Configure the PostgreSQL database:
    - Create a database named `bookmyticket`
    - Update the `application.properties` file with your database credentials

3. Build the project:
    ```bash
    ./gradlew clean build
    ```

4. Run the application:
    ```bash
    ./gradlew bootRun
    ```

## Configuration
### Database Configuration
The application uses PostgreSQL as the primary database. Update the `src/main/resources/application.properties` file with your PostgreSQL database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bookmyticket
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### Swagger Configuration
Swagger is configured to generate API documentation. Access the Swagger UI at `http://localhost:8080/swagger-ui.html` after running the application.

## API Documentation
The API documentation is available via Swagger. Once the application is running, you can access it at:
```
http://localhost:8080/swagger-ui.html
```

## Running Tests
### Unit Tests
Run the unit tests using Gradle:
```bash
./gradlew test
```

### Integration Tests
Run the integration tests using Gradle:
```bash
./gradlew integrationTest
```

## Contributing
Contributions are welcome! Please create an issue or pull request for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
```

This `README.md` should be more accurate for your Gradle-based project.