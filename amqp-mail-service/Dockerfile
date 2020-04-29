FROM maven:3.6.3-ibmjava-8-alpine AS appServer
LABEL maintainer="nikolic.dusan@uns.ac.rs"

WORKDIR /usr/src/mail
COPY . .
RUN ["mvn", "package", "-DskipTests"]


FROM openjdk:8-jdk-alpine
WORKDIR /app
#EXPOSE 8080
COPY --from=appServer /usr/src/mail/target/rabbit-mail-0.0.1-SNAPSHOT.jar ./


CMD ["java", "-jar", "rabbit-mail-0.0.1-SNAPSHOT.jar"]
