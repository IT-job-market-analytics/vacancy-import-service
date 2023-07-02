FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /
COPY /src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean package

FROM openjdk:17-alpine
WORKDIR /
COPY --from=build /target/*.jar vacancy-import-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "vacancy-import-service.jar"]