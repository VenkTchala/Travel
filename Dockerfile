# Use an official Maven image as the base image
FROM maven:3.9.6-eclipse-temurin-17 as maven

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and the project files to the container
COPY pom.xml .

COPY src ./src

# Build the application using Maven
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine

LABEL authors="sharath"

WORKDIR /app

# Copy the built JAR file from the previous stage to the container
COPY --from=maven /app/target/*.jar .

# Set the command to run the application
CMD ["java", "-jar", "flight-booking-service-0.0.1-SNAPSHOT.jar"]



