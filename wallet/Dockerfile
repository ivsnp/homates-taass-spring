FROM openjdk:19
VOLUME /tmp
EXPOSE 8082
ARG DEPENDENCY=target
ADD ${DEPENDENCY}/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
