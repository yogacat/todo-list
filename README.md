# todo-list
This is a RESTful API that provides backend for the todo list. 

## Tech stack
- Java 17
- Maven
- Spring Boot
- Postgres Database 15
- In memory database for testing

## How To
### Build the service
From the project directory run execute command `mvn clean package` in the console to build the project.

### Run automatic tests
From the project directory run execute command `mvn test`.

### Run the service locally
To run the service locally you need to start the database first. You can do it by executing `docker compose up` command in the project directory. 
After this execute `mvn spring-boot:run` to start the application.