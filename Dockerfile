FROM maven:3.9.11-eclipse-temurin-21 AS build

# Définit le répertoire de travail à l'intérieur du conteneur
WORKDIR /app

# Copie le fichier pom.xml et les fichiers de code source
COPY pom.xml ./
COPY src ./src

# Construit l'application
RUN mvn clean install -DskipTests

# Étape 2: Création de l'image d'exécution
FROM openjdk:21-jre-slim

# Définit le répertoire de travail
WORKDIR /app

# Copie le JAR construit depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Expose le port de l'application (si nécessaire)
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]

