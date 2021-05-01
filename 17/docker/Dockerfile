FROM maven:3.8.1-openjdk-11-slim as build

RUN apt-get --yes update
RUN apt-get --yes install git

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
RUN git clone https://github.com/whoisacat/2020-05-otus-spring-whoisacat.git
WORKDIR 2020-05-otus-spring-whoisacat

WORKDIR 16
RUN mvn dependency:resolve
RUN mvn install -DskipTests

FROM openjdk:11-jre-slim as lib_app
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ARG JAR_FILE=$PROJECT_DIR/2020-05-otus-spring-whoisacat/16/target/*.jar
COPY --from=build ${JAR_FILE} $PROJECT_DIR/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/project/app.jar"]
