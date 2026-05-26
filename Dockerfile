# ========================================
# Dockerfile - Taxi Manager Spring Boot
# ========================================

# Étape 1 : Build avec Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copier le pom.xml et télécharger les dépendances (cache Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copier le code source et construire le JAR
COPY src ./src
# ========================================
# Dockerfile - Taxi Manager Spring Boot
# ========================================

# Étape 1 : Build avec Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copier le pom.xml et télécharger les dépendances (cache Docker)
COPY taxi-manager/pom.xml .
RUN mvn dependency:go-offline -B

# Copier le code source et construire le JAR
COPY taxi-manager/src ./src
RUN mvn clean package -DskipTests

# ========================================
# Étape 2 : Image de production légère
# ========================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Créer un utilisateur non-root pour la sécurité
RUN addgroup -S taxiapp && adduser -S taxiapp -G taxiapp

# Copier le JAR depuis l'étape build
COPY --from=build /app/target/*.jar app.jar

# Changer le propriétaire du fichier
RUN chown taxiapp:taxiapp app.jar

USER taxiapp

# Port exposé
EXPOSE 8080

# Point d'entrée
ENTRYPOINT ["java", "-jar", "app.jar"]