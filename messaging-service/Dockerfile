FROM openjdk:8-jdk-alpine
COPY /target/messaging-service-0.0.1-SNAPSHOT.jar messaging-service.jar
ENTRYPOINT ["java", "-jar", "/messaging-service.jar"]
EXPOSE 8097