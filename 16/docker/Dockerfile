FROM maven:3.8.1-openjdk-11-slim

RUN apt-get --yes update
RUN apt-get --yes install git

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
RUN git clone https://github.com/whoisacat/2020-05-otus-spring-whoisacat.git
WORKDIR 2020-05-otus-spring-whoisacat
RUN git checkout 17_docker_ini

WORKDIR 16
RUN mvn dependency:resolve
RUN mvn install -DskipTests
RUN echo ll .

FROM openjdk:11-jre-slim
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

COPY --from=0 $PROJECT_DIR/2020-05-otus-spring-whoisacat/16/target/book-catalogue-spring-actuator-start-0.0.1-SNAPSHOT.jar $PROJECT_DIR

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/project/book-catalogue-spring-actuator-start-0.0.1-SNAPSHOT.jar"]
