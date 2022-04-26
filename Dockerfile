FROM openjdk:17.0.1-jdk-slim-bullseye@sha256:9a37f2c649301b955c2fd31fb180070404689cacba0f77404dd20afb1d7b8d84
RUN useradd -ms /bin/bash myuser

USER myuser
WORKDIR /home/newuser/project
COPY ./target/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT "java" "-jar" "app.jar" "--spring.profiles.active=dev"
