FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=./build/libs/tour-of-heroes-api.jar

ARG PASS_DB
ARG USER_DB
ARG HOST_DB

ENV PASS_DB=${PASS_DB}
ENV USER_DB=${USER_DB}
ENV HOST_DB=${HOST_DB}


WORKDIR /opt/app
ADD ${JAR_FILE} "app.jar"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]