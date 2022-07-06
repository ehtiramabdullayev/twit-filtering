FROM alpine/git
WORKDIR /app
RUN git clone https://github.com/ehtiramabdullayev/twit-filtering.git

FROM maven:latest as builder
WORKDIR /app
COPY --from=0 /app/twit-filtering /app
RUN mvn clean install

#FROM openjdk:11-jre-slim-buster
#EXPOSE 8080
#COPY target/bieber-tweets-1.0.0-SNAPSHOT.jar twitter-service.jar
#ENTRYPOINT ["java","-jar","/bieber-tweets-1.0.0-SNAPSHOT.jar"]