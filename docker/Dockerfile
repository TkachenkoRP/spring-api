FROM openjdk:20-oracle
WORKDIR /app
COPY build/libs/spring-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]