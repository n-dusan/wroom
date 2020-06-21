FROM node:alpine3.11 AS agentFront
LABEL maintainer="nikolic.dusan@uns.ac.rs"

WORKDIR /usr/src/agent
COPY agent-monolith-ui .
RUN ["npm", "install"]
#node smart guys didnt patch their package
RUN ["npm", "install", "is-promise@2.1.0"]
RUN ["npm", "run", "build", "--prod"]
FROM maven:3.6.3-ibmjava-8-alpine AS agentMonolith

WORKDIR /usr/src/agent
COPY agent-monolith .
COPY --from=agentFront /usr/src/agent/dist/agent-ui ./src/main/resources/static

RUN ["mvn", "package", "-DskipTests"]


FROM openjdk:8-jdk-alpine
WORKDIR /app
RUN ["mkdir", "-p", "src/main/resources/static/images"]
RUN ["mkdir", "-p", "target/classes/static/images"]
COPY --from=agentMonolith /usr/src/agent/target/wroom-agent-0.0.1-SNAPSHOT.jar ./

EXPOSE 8081
CMD ["java", "-jar", "wroom-agent-0.0.1-SNAPSHOT.jar"]