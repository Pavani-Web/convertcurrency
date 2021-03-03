FROM openjdk:8-jdk-alpine
RUN addgroup -S convertcurrency && adduser -S convertcurrency -G convertcurrency
USER convertcurrency:convertcurrency
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]