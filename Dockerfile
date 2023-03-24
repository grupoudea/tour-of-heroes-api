FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=./build/libs/tour-of-heroes-api.jar
WORKDIR /opt/app
COPY ${JAR_FILE} "app.jar"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]