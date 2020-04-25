#FROM node:alpine3.11 AS agentFront
#LABEL maintainer="nikolic.dusan@uns.ac.rs"
#
#WORKDIR /usr/src/agent
#COPY agent-ui .
#RUN ["npm", "install"]
#RUN ["npm", "run", "build", "--prod"]


FROM maven:3.6.3-ibmjava-8-alpine AS agentMonolith

WORKDIR /usr/src/agent
COPY agent-monolith .
#COPY --from=agentFront /usr/src/agent/dist/agent-ui ./src/main/resources/static

RUN ["mvn", "package", "-DskipTests"]


FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=agentMonolith /usr/src/agent/target/wroom-agent-0.0.1-SNAPSHOT.jar ./

EXPOSE 8080
CMD ["java", "-jar", "wroom-agent-0.0.1-SNAPSHOT.jar"]