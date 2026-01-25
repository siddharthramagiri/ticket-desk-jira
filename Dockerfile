# ===============================
# Stage 1: Build the application
# ===============================
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn -q -e -B dependency:go-offline

COPY src ./src
RUN mvn -q -DskipTests package



# ===============================
# Stage 2: Run the application
# ===============================
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]