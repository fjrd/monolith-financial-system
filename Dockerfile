
FROM maven:3.8.1-openjdk-11 AS MAVEN_BUILD

WORKDIR /app
COPY pom.xml ./
COPY src ./src

RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

FROM adoptopenjdk:11-jdk-hotspot
WORKDIR /app
COPY --from=MAVEN_BUILD /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]