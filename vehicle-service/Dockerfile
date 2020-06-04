FROM openjdk:8-jdk-alpine
COPY /target/vehicle-service-0.0.1-SNAPSHOT.jar vehicle-service.jar
RUN ["mkdir", "-p", "src/main/resources/static/images"]
ENTRYPOINT ["java", "-jar", "/vehicle-service.jar"]
EXPOSE 8093