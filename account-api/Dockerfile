FROM openjdk:11 AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:11
WORKDIR account-api
COPY --from=build target/*.jar account-api.jar
ENTRYPOINT ["java", "-jar", "account-api.jar"]