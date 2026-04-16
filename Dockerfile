# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml
COPY pom.xml .

# Copy source code
COPY src ./src

# Create target directory to avoid issues
RUN mkdir -p target

# Package the application (Fat JAR)
RUN mvn package -DskipTests -B
