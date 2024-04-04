# multistage building

FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:19
EXPOSE 8080
WORKDIR /app
COPY --from=build /app/target/api-gateway-1.0.0.jar api-gateway-1.0.0.jar
CMD ["java", "-jar", "api-gateway-1.0.0.jar"]