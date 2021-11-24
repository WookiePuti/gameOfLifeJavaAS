FROM adoptopenjdk/maven-openjdk11:latest

WORKDIR /
COPY pom.xml pom.xml
COPY ./src ./src

RUN mvn package

CMD ["java", "-jar", "./target/gameOfLife-jar-with-dependencies.jar"]