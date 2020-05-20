FROM openjdk:8-jdk-alpine
COPY /target/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar
ENTRYPOINT ["java", "-jar", "/auth-service.jar"]
EXPOSE 8092