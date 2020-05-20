FROM openjdk:8-jdk-alpine
COPY /target/service-registry-0.0.1-SNAPSHOT.jar eureka-service-registry.jar
ENTRYPOINT ["java", "-jar", "/eureka-service-registry.jar"]
EXPOSE 8761