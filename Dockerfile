FROM maven:3-openjdk-11 AS build

COPY . .

RUN mvn clean install

FROM adoptopenjdk/openjdk11:alpine-jre AS target

# Refer to Maven build -> finalName
ARG JAR_FILE=target/spring-cloud-gateway*.jar

# cd /opt/app
WORKDIR /opt/app

COPY --from=build ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]