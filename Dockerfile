# ========================================
# Dockerfile - Taxi Manager Spring Boot
# ========================================

FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY taxi-manager/pom.xml .
RUN mvn dependency:go-offline -B

COPY taxi-manager/src ./src
RUN mvn clean package -DskipTests

# ========================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S taxiapp && adduser -S taxiapp -G taxiapp

# Creer le dossier de persistance
RUN mkdir -p /data && chown taxiapp:taxiapp /data

COPY --from=build /app/target/*.jar app.jar
RUN chown taxiapp:taxiapp app.jar

USER taxiapp

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]