# Idea Similarity Rating App

This project is based on the following stack:
* [java (openjdk 14)](https://openjdk.java.net/projects/jdk/14/)
* [Gradle](https://gradle.org/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Data Rest](https://docs.spring.io/spring-data/rest/docs/current/reference/html/)
* [Docker](https://www.docker.com/)
* [Google Jib Plugin for Gradle](https://github.com/GoogleContainerTools/jib)
* [Gitlab docker registry](https://docs.gitlab.com/ee/user/project/container_registry.html)

Docker Integration was done following the [Spring Boot Docker Tutorial](https://spring.io/guides/gs/spring-boot-docker/)

## Building

Building the standalone jar:

    ./gradlew bootJar
   
Creates a boot jar in /build/libs/idea-similarity-app-backend-0.0.1-SNAPSHOT.jar

## Debugging
### Run local

Running the standalone server created by the gradle bootJar task:
        
        java -jar idea-similarity-app-backend-0.0.1-SNAPSHOT.jar
        
        curl "http://localhost:8080/api"
        

### Run local docker-container
    
    ./gradlew jibDockerBuild
    
    
## Deployment

    ./gradlew jib