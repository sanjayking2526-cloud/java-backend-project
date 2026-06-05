# 1. Build stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

COPY pom.xml .

RUN mkdir -p src/main/java
COPY Main.java src/main/java/

RUN mvn clean package -DskipTests

# 2. Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
# இங்க மட்டும் ஜார் பேரை மாத்தியிருக்கோம்
COPY --from=build /app/target/*-with-dependencies.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]