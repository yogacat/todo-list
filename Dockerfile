FROM openjdk:17-alpine

VOLUME /tmp
ARG JAR_FILE=target/todo-list-*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

EXPOSE 8066