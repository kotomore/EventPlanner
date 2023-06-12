FROM openjdk:19
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} EventPlanner.jar
ENTRYPOINT ["java","-jar","/EventPlanner.jar"]