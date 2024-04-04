# multistage building

FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:19
EXPOSE 8085
WORKDIR /app
COPY --from=build /app/target/bacheca-1.0.0.jar board-1.0.0.jar
CMD ["java", "-jar", "board-1.0.0.jar"]
