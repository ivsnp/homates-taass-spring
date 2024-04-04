# multistage building

FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:19
EXPOSE 8082
WORKDIR /app
COPY --from=build /app/target/wallet-1.0.0.jar wallet-1.0.0.jar
CMD ["java", "-jar", "wallet-1.0.0.jar"]