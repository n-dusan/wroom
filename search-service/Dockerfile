FROM openjdk:8-jdk-alpine
COPY /target/search-service-0.0.1-SNAPSHOT.jar search-service.jar
ENTRYPOINT ["java", "-jar", "/search-service.jar"]
EXPOSE 8091