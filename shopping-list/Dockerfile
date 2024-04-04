# multistage building

FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:19
EXPOSE 8083
WORKDIR /app
COPY --from=build /app/target/shopping-list-1.0.0.jar shopping-list-1.0.0.jar
CMD ["java", "-jar", "shopping-list-1.0.0.jar"]
