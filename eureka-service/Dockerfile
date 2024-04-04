# multistage building

FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:19
EXPOSE 8761
WORKDIR /app
COPY --from=build /app/target/eureka-service-1.0.0.jar eureka-1.0.0.jar
CMD ["java", "-jar", "eureka-1.0.0.jar"]
