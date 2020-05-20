FROM openjdk:8-jdk-alpine
COPY /target/ads-service-0.0.1-SNAPSHOT.jar ads-service.jar
ENTRYPOINT ["java", "-jar", "/ads-service.jar"]
EXPOSE 8094