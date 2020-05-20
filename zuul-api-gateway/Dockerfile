FROM openjdk:8-jdk-alpine
COPY /target/zuul-api-gateway-0.0.1-SNAPSHOT.jar zuul-api-gateway.jar
ENTRYPOINT ["java", "-jar", "/zuul-api-gateway.jar"]
EXPOSE 8762