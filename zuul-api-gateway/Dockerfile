FROM openjdk:8-jdk-alpine
USER root
COPY zuul.cer $JAVA_HOME/jre/lib/security
COPY root.cer $JAVA_HOME/jre/lib/security
RUN \
    cd $JAVA_HOME/jre/lib/security \
    && keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias 655575225830 -file zuul.cer \
    && keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias 633572272984 -file root.cer

COPY /target/zuul-api-gateway-0.0.1-SNAPSHOT.jar zuul-api-gateway.jar
ENTRYPOINT ["java", "-jar", "/zuul-api-gateway.jar", "-Djavax.net.debug=all"]
EXPOSE 8762