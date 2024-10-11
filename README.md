# Weather Application

## Description
Weather Application is a Spring Boot-based application that provides weather information and manages users. The application uses an external API to fetch weather data and provides a REST API for user management.

## Technologies
- Java 17 or higher
- Spring Boot 3.0.0 or higher
- Maven
- PostgreSQL
- Spring Boot Starter Data JPA
- Spring Boot Starter Web
- PostgreSQL JDBC Driver
- Lombok
- Spring Boot Starter Test
- Jakarta Validation API
- Springdoc OpenAPI UI
- Spring Boot Starter Security
- JJWT API
- JJWT Implementation
- JJWT Jackson
- Mockito Core
- Mockito JUnit Jupiter

## Installation and Running
1. Clone the repository:
    ```sh
    git clone https://github.com/irizidkova/weather-application.git
    cd weather-application
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```
    
3. Configure your PostgreSQL database detailes according to the src/main/resources/application.properties file.
   ```sh
   spring.datasource.url=jdbc:postgresql://localhost:5432/bank
   spring.datasource.username=postgres
   spring.datasource.password=postgres
   ```

5. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## Project Structure
- `src/main/java/com/example/demo` - main application source code
  - `controller` - REST controllers
  - `service` - business logic
  - `repository` - data access
  - `model` - data entities
  - `DTO` - data transfer objects
  - `security` - project security

## API Documentation
The API documentation available in swagger.yaml by path src/main/java/resources/templates/docs/swagger.yaml
