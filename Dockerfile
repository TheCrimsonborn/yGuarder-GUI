# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Download dependencies from Maven Central into lib/
RUN BASE="https://repo1.maven.org/maven2" && mkdir -p lib && \
    curl -fsSL "$BASE/org/openjfx/javafx-controls/21/javafx-controls-21-linux.jar" -o lib/javafx-controls-21-linux.jar && \
    curl -fsSL "$BASE/org/openjfx/javafx-graphics/21/javafx-graphics-21-linux.jar" -o lib/javafx-graphics-21-linux.jar && \
    curl -fsSL "$BASE/org/openjfx/javafx-base/21/javafx-base-21-linux.jar"          -o lib/javafx-base-21-linux.jar && \
    curl -fsSL "$BASE/org/openjfx/javafx-fxml/21/javafx-fxml-21-linux.jar"          -o lib/javafx-fxml-21-linux.jar && \
    curl -fsSL "$BASE/io/github/mkpaz/atlantafx-base/2.0.1/atlantafx-base-2.0.1.jar" -o lib/atlantafx-base-2.0.1.jar && \
    curl -fsSL "$BASE/com/yworks/yguard/5.0.0/yguard-5.0.0.jar"                     -o lib/yguard-5.0.0.jar && \
    curl -fsSL "$BASE/org/ow2/asm/asm/9.6/asm-9.6.jar"                              -o lib/asm-9.6.jar && \
    curl -fsSL "$BASE/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar"               -o lib/slf4j-api-2.0.9.jar && \
    curl -fsSL "$BASE/org/slf4j/slf4j-simple/2.0.9/slf4j-simple-2.0.9.jar"         -o lib/slf4j-simple-2.0.9.jar && \
    curl -fsSL "$BASE/org/apache/ant/ant/1.10.14/ant-1.10.14.jar"                   -o lib/ant-1.10.14.jar && \
    curl -fsSL "$BASE/org/apache/ant/ant-launcher/1.10.14/ant-launcher-1.10.14.jar" -o lib/ant-launcher-1.10.14.jar

# Build fat JAR
RUN mkdir -p target/classes/com/example && \
    cp src/main/resources/com/example/*.fxml   target/classes/com/example/ && \
    cp src/main/resources/com/example/*.properties target/classes/com/example/ && \
    CP=$(find lib -name "*.jar" | tr '\n' ':') && \
    javac -cp "$CP" $(find src/main/java -name "*.java") -d target/classes && \
    jar cfe target/yguarder-gui.jar com.example.Launcher -C target/classes .

# Stage 2: Minimal runtime image (headless - for CI/server-side use)
FROM eclipse-temurin:21-jre-alpine AS runtime

WORKDIR /app
COPY --from=build /app/target/yguarder-gui.jar .
COPY --from=build /app/lib ./lib

LABEL org.opencontainers.image.title="yGuarder GUI" \
      org.opencontainers.image.description="Professional Java Obfuscator GUI built on yGuard 5" \
      org.opencontainers.image.source="https://github.com/$GITHUB_REPOSITORY" \
      org.opencontainers.image.licenses="MIT"
