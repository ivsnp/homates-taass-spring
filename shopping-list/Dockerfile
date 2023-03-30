#STAGE 1: build
#non so se si deve effettivamente fare così perchè nel suo esempio lo usava avendo front end e backend nello stesso prog
#FROM maven:3.8.1-jdk-19-slim AS build
#RUN mkdir -p /workspace
#WORKDIR /workspace
#COPY pom.xml /workspace
#COPY src /workspace/src
#RUN mvn -f pom.xml clean package


#STAGE 2: run
FROM openjdk:19
RUN groupadd -r spring && useradd -r -gspring spring
USER spring
VOLUME /tmp
EXPOSE 8081
ARG DEPENDENCY=target
ADD ${DEPENDENCY}/*.jar listespesa.jar
#COPY --from=build /workspace/target/*.jar listespesa.jar
ENTRYPOINT ["java","-jar","/listespesa.jar"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/listespesa.jar"]
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} listespesa.jar
#CMD ["java", "-jar", "listespesa.jar"]

