# todo-list

This is a RESTful API that provides backend for the todo list.

I chose Spring Boot because I already did a lot of similar things so the test task development will be faster, though I
can do the same in Quarkus. Java 17 is the version I'm currently using, this is why I chose it.

I chose to have fewer endpoints in the controller because changing status/description is similar, requirements are
similar and I don't want to have duplicated code that in the end does the same thing.
I prefer returning exceptions as a JSON as well, for this I have an exception handler. It will convert an error message
to JSON and return the error message in the body. In this case if there is frontend, it is easier to display errors to
the user. Exceptions are logged.

To return tasks by different parameters I implemented one endpoint with the filter (it is a simple filter because there
was no requirement to have a lot of options), however as a user of the TodoList application I can imagine scenarios
when I need to find tasks that must be done by the specific date. This provides more flexibility and allows to
potentially extend the filter by providing more filtering options. I am aware that Entities contain date with time, thus
time will be trimmed when searching using the filter (for example due to "today").

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

To run the service locally you need to start the database first. You can do it by executing `docker compose up` command
in the project directory.
After this execute `mvn spring-boot:run` to start the application.

### Running using Docker

To build and run the service using docker you still need to start the database first. You can do it by
executing `docker compose up` command in the project directory.
After this build the project by running`mvn clean install` command.
To build the docker image run`docker build -t todo-list .` in the project directory.

Run the API by executing `docker run -e DB_HOST=host.docker.internal -p 8086:8086 todo-list` command.