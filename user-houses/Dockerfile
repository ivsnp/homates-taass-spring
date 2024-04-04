# multistage building

FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:19
EXPOSE 8081
WORKDIR /app
COPY --from=build /app/target/user-houses-1.0.0.jar user-houses-1.0.0.jar
CMD ["java", "-jar", "user-houses-1.0.0.jar"]
