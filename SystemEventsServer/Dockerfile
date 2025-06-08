FROM eclipse-temurin:21-jre

WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 6565
EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]
