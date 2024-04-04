# multistage building

FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:19
EXPOSE 8084
WORKDIR /app
COPY --from=build /app/target/calendar-1.0.0.jar calendar-1.0.0.jar
CMD ["java", "-jar", "calendar-1.0.0.jar"]
