# Event Planner API

## Based
#### Java + Spring Boot + Spring Security + JWT + PostgreSQL


## Swagger
http://localhost:8080/swagger-ui/index.html#/

## Postman
Local Server collection<br>
https://www.postman.com/crimson-shadow-526437/workspace/eventplanner


## Install && launch

<b>With Docker</b>
```
git clone https://github.com/kotomore/EventPlanner.git
cd EventPlanner
mvn package
docker-compose up
```

<b>Without Docker</b>
#### Requirements: Java 17, PostgreSQL 15


Create postgres DB with name `postgres`.<br>
Change your DB username & password in `application.properties` file


```
git clone https://github.com/kotomore/EventPlanner.git
cd EventPlanner
mvn package
java -jar target/EventPlanner-0.0.1-SNAPSHOT.jar
```
