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

There is an issue that I will not fix now: if the user would like to find all the tasks due to "today", for example, by
7pm the filter will not return all the entities correctly due to the trimming. I am not going to fix it because
filtering in such a way was an improvisation (to make an endpoint expandable) rather than a requirement and will take
more effort.

Most of the functionality is covered by tests in the service, that is why I am doing very minimalistic testing of the
controller.

Please be aware that the application starts on port `8066` because I have other services running on `8080` and I would
like to avoid conflicts on my machine. The database also does not use the standard port for the same reason.

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

Run the API by executing `docker run -e DB_HOST=host.docker.internal -p 8066:8066 todo-list` command.