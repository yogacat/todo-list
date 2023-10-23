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

### Running using Docker
To build and run the service using docker you still need to start the database first. You can do it by executing `docker compose up` command in the project directory.
After this build the project by running`mvn clean install` command.
To build the docker image run`docker build -t todo-list .` in the project directory.

Run the API by executing `docker run -e DB_HOST=host.docker.internal -p 8086:8086 todo-list` command.