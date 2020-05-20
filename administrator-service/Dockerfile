FROM openjdk:8-jdk-alpine
COPY /target/administrator-service-0.0.1-SNAPSHOT.jar administrator-service.jar
ENTRYPOINT ["java", "-jar", "/administrator-service.jar"]
EXPOSE 8096