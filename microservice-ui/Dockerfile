FROM node:alpine3.11 AS microserviceFront
LABEL maintainer="nikolic.dusan@uns.ac.rs"

WORKDIR /usr/src/front
COPY wroom-ui .
RUN ["npm", "install"]
#node smart guys didnt patch their package
RUN ["npm", "install", "is-promise@2.1.0"]
RUN ["npm", "run", "build", "--prod"]

FROM maven:3.6.3-ibmjava-8-alpine AS frontSpring
WORKDIR /usr/src/front
COPY ui-spring-container .
COPY --from=microserviceFront /usr/src/front/dist/wroom-ui ./src/main/resources/static

RUN ["mvn", "package", "-DskipTests"]


FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=frontSpring /usr/src/front/target/microservice-ui-0.0.1-SNAPSHOT.jar ./

EXPOSE 4201
CMD ["java", "-jar", "microservice-ui-0.0.1-SNAPSHOT.jar"]