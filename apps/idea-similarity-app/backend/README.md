# Idea Similarity Rating App

This project is based on the following stack:
* [java (openjdk 14)](https://openjdk.java.net/projects/jdk/14/)
* [Gradle](https://gradle.org/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Springdoc OpenAPI](https://springdoc.org/)
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

For debugging, there is a swagger ui running at:

        http://localhost:8080/swagger-ui.html
        
Furthermore, there is a h2 console running at:

    http://localhost:8080/h2-console/
   
The Debug Config for it is:

    Driver Class: org.h2.Driver
    JDBC URL: jdbc:h2:mem:testdb
    User Name: sa
    Password: <empty>

### Run local docker-container
    
    ./gradlew jibDockerBuild
    
    
## Deployment

    ./gradlew jib
    
    
## Usage

Constructing the Initial Import:

Manually create a json file with the following information:

You can get the Idea Data by constructing a json-ld document like this:

    PREFIX gi2mo: <http://purl.org/gi2mo/ns#>  
    
    CONSTRUCT { 
      ?idea a gi2mo:Idea;
        gi2mo:content ?content;
        gi2mo:hasIdeaContest ?contest.
    }
    WHERE { 
        ?idea a gi2mo:Idea;
           gi2mo:content ?content;
           gi2mo:hasIdeaContest ?contest.
    }
    LIMIT 10
     