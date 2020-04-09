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

### Full Circle

#### Importing a Project

POST http://localhost:8080/api/management/requirements/

POST-BODY:
        
    {
        "id": "test-collection-round",
        "challenges": [{
            "@id": "https://innovonto-core.imp.fu-berlin.de/entities/ideaContests/i2m-bionic-radar",
            "description": "The second idea contest within the Ideas-to-Market Project. The Fraunhofer FHR, which provided a new radar technology (called Bionic Radar) that uses machine learning to uniquely detect movement patterns via radar, working even in bad weather."
        }],
        "ideas": [{
                "@id": "https://innovonto-core.imp.fu-berlin.de/entities/ideas/1993da2b-008e-44de-aa1f-20f91df22123",
                "@type": "gi2mo:Idea",
                "content": "This is also good for detected shape under the sea. Perhaps buried tresure that we have no idea about.",
                "hasIdeaContest": "https://innovonto-core.imp.fu-berlin.de/entities/ideaContests/i2m-bionic-radar"
            },
            {
                "@id": "https://innovonto-core.imp.fu-berlin.de/entities/ideas/207ed8be-e222-441c-bb86-3c75fc918208",
                "@type": "gi2mo:Idea",
                "content": "The way this technology can be used is by detecting the flow of people. For example in a crowded area, if we  can study the way people move, the flow of the crowd and where they go, we can design large areas to be more open. One specific place would be an airport, if we can utilize this technology and learn where people tend to walk, where people tend to gravitate to when going through an airport.  From personal experience I have seen that people tend to 'clog up' a certain area when they go through an airport. If the technology could figure out how we can design a large space, we could better prevent long lines, and too many people crowded in a space.",
                "hasIdeaContest": "https://innovonto-core.imp.fu-berlin.de/entities/ideaContests/i2m-bionic-radar"
            },
            {
                "@id": "https://innovonto-core.imp.fu-berlin.de/entities/ideas/38c55775-2352-409d-adf4-ecb50123e60a",
                "@type": "gi2mo:Idea",
                "content": "The technology can be used to track migration patterns of invasive organisms, such as algal bloom or certain insects. ",
                "hasIdeaContest": "https://innovonto-core.imp.fu-berlin.de/entities/ideaContests/i2m-bionic-radar"
            },
            {
                "@id": "https://innovonto-core.imp.fu-berlin.de/entities/ideas/584397b2-a2de-46c6-b0c3-0a1d244ed6b4",
                "@type": "gi2mo:Idea",
                "content": "Can be used in the wilderness to see migration patterns and how often the same animals come back without having to use geo tags on them",
                "hasIdeaContest": "https://innovonto-core.imp.fu-berlin.de/entities/ideaContests/i2m-bionic-radar"
            },
            {
                "@id": "https://innovonto-core.imp.fu-berlin.de/entities/ideas/6f7a5f9b-dc13-4467-a0e8-bc72a1b26025",
                "@type": "gi2mo:Idea",
                "content": "You could use the technology to detect unwanted animals.  In Florida, they have in recent years, had an influx of pythons not indigenous to the area.  The snakes are causing issues with the ecosystem.  ",
                "hasIdeaContest": "https://innovonto-core.imp.fu-berlin.de/entities/ideaContests/i2m-bionic-radar"
            },
            {
                "@id": "https://innovonto-core.imp.fu-berlin.de/entities/ideas/8af6f8b6-0973-4992-8ea3-585d5efd7e10",
                "@type": "gi2mo:Idea",
                "content": "Profiling criminals",
                "hasIdeaContest": "https://innovonto-core.imp.fu-berlin.de/entities/ideaContests/i2m-bionic-radar"
            },
            {
                "@id": "https://innovonto-core.imp.fu-berlin.de/entities/ideas/9660eb93-cef2-4cfd-82d8-52077c0d9a20",
                "@type": "gi2mo:Idea",
                "content": "Use the technology for deaf people to write",
                "hasIdeaContest": "https://innovonto-core.imp.fu-berlin.de/entities/ideaContests/i2m-bionic-radar"
            },
            {
                "@id": "https://innovonto-core.imp.fu-berlin.de/entities/ideas/b7b884b5-335a-44f6-99ad-2d38b2f6d884",
                "@type": "gi2mo:Idea",
                "content": "This technology can record a person's gait and indicate what corrections can be made for a healthier posture.",
                "hasIdeaContest": "https://innovonto-core.imp.fu-berlin.de/entities/ideaContests/i2m-bionic-radar"
            },
            {
                "@id": "https://innovonto-core.imp.fu-berlin.de/entities/ideas/cf65b021-620f-43fe-9473-1712be788cde",
                "@type": "gi2mo:Idea",
                "content": "This can enable me to interact and see where others are in my home.",
                "hasIdeaContest": "https://innovonto-core.imp.fu-berlin.de/entities/ideaContests/i2m-bionic-radar"
            },
            {
                "@id": "https://innovonto-core.imp.fu-berlin.de/entities/ideas/fb85b2da-bc85-4ecc-b44c-5e9b94a72238",
                "@type": "gi2mo:Idea",
                "content": "Security systems could use this technology to identify intruders. ",
                "hasIdeaContest": "https://innovonto-core.imp.fu-berlin.de/entities/ideaContests/i2m-bionic-radar"
            }
        ],
        "pairs": [
            ["1993da2b-008e-44de-aa1f-20f91df22123", "207ed8be-e222-441c-bb86-3c75fc918208"],
            ["1993da2b-008e-44de-aa1f-20f91df22123", "38c55775-2352-409d-adf4-ecb50123e60a"],
            ["1993da2b-008e-44de-aa1f-20f91df22123", "584397b2-a2de-46c6-b0c3-0a1d244ed6b4"],
            ["1993da2b-008e-44de-aa1f-20f91df22123", "6f7a5f9b-dc13-4467-a0e8-bc72a1b26025"],
            ["1993da2b-008e-44de-aa1f-20f91df22123", "8af6f8b6-0973-4992-8ea3-585d5efd7e10"],
            ["1993da2b-008e-44de-aa1f-20f91df22123", "9660eb93-cef2-4cfd-82d8-52077c0d9a20"],
            ["1993da2b-008e-44de-aa1f-20f91df22123", "b7b884b5-335a-44f6-99ad-2d38b2f6d884"],
            ["1993da2b-008e-44de-aa1f-20f91df22123", "fb85b2da-bc85-4ecc-b44c-5e9b94a72238"],
            ["207ed8be-e222-441c-bb86-3c75fc918208", "584397b2-a2de-46c6-b0c3-0a1d244ed6b4"],
            ["207ed8be-e222-441c-bb86-3c75fc918208", "584397b2-a2de-46c6-b0c3-0a1d244ed6b4"]
        ],
        "goalRatingsPerPair": 2,
        "batchSize": 2
    }
    
#### Working on a Project

#### Getting the PAIRS for one worker

    http://localhost:8080/api/mturk/rating/ratingpairs?ratingProjectId=test-collection-round&hitId=mockHit&workerId=mockWorker&assignmentId=mockAssignment
    
#### Submitting Results

POST http://localhost:8080/api/mturk/rating/submit

POST-Body

    {
      "ratingProjectId": "test-collection-round",
      "hitId": "mockHit",
      "workerId": "mockWorker",
      "assignmentId": "mockAssignment",
      "fulltextFeedback": "This is feedback :)",
      "clarityRating": 0,
      "passedAttentionCheck": true,
      "ratings": [
        {
          "leftIdea": "1993da2b-008e-44de-aa1f-20f91df22123",
          "rightIdea": "207ed8be-e222-441c-bb86-3c75fc918208",
          "similarityRating": 120
        },
    	{
          "leftIdea": "1993da2b-008e-44de-aa1f-20f91df22123",
          "rightIdea": "38c55775-2352-409d-adf4-ecb50123e60a",
          "similarityRating": 0
        }
      ]
    }
    
#### Reviewing Results

    http://localhost:8080/api/management/mturkRatingSessions/byAssignment?assignmentId=mockAssignment
    
    
#### Approving/Rejecting Results
For example, if the byAssignment gave you a session with id="d7e104d6-0aea-4f6f-9e62-9ef37ea9098e"

    http://localhost:8080/api/management/mturkRatingSessions/d7e104d6-0aea-4f6f-9e62-9ef37ea9098e/set-usable
    http://localhost:8080/api/management/mturkRatingSessions/d7e104d6-0aea-4f6f-9e62-9ef37ea9098e/set-unusable

### Export

    http://localhost:8080/api/management/export/usableRatingsForProject?ratingProjectId=test-collection-round
    
Should Return something like this:

    {
      "@context": {
        "hasMturkSession": {
          "@id": "http://purl.org/innovonto/mturk/hasMturkSession",
          "@type": "@id"
        },
        "workerId": "http://purl.org/innovonto/mturk/workerId",
        "hasLeftIdea": "oid:hasLeftIdea",
        "hitId": "http://purl.org/innovonto/mturk/hitId",
        "created": {
          "@id": "dcterms:created",
          "@type": "http://www.w3.org/2001/XMLSchema#dateTime"
        },
        "description": "dcterms:description",
        "gi2mo": "http://purl.org/gi2mo/ns#",
        "oid": "http://purl.org/innovonto/legacy/types#",
        "minRatingValue": {
          "@id": "gi2mo:minRatingValue",
          "@type": "http://www.w3.org/2001/XMLSchema#long"
        },
        "title": "dcterms:title",
        "hasSimilarityRating": {
          "@id": "oid:hasSimilarityRating",
          "@type": "@id"
        },
        "assignmentId": "http://purl.org/innovonto/mturk/assignmentId",
        "content": "gi2mo:content",
        "hasRightIdea": "oid:hasRightIdea",
        "hasIdeaContest": "gi2mo:hasIdeaContest",
        "maxRatingValue": {
          "@id": "gi2mo:maxRatingValue",
          "@type": "http://www.w3.org/2001/XMLSchema#long"
        },
        "ratingValue": {
          "@id": "gi2mo:ratingValue",
          "@type": "http://www.w3.org/2001/XMLSchema#long"
        },
        "dcterms": "http://purl.org/dc/terms/",
        "hasRatingProject": "oid:hasRatingProject",
        "inov": "http://purl.org/innovonto/types/#"
      },
      "@graph": [
        {
          "@id": "http://purl.org/innovonto/mx-master/similarities/mturkSessions/1f90b482-b080-4323-9a96-41cd6b8d7bab",
          "@type": "http://purl.org/innovonto/mturk/MturkSession",
          "created": "2020-04-09T11:55:24.127408",
          "hasRatingProject": "http://purl.org/innovonto/mx-master/similarities/ratingProjects/test-collection-round",
          "hasSimilarityRating": [
            {
              "@id": "http://purl.org/innovonto/mx-master/similarities/ratings/2",
              "@type": "oid:SimilarityRating",
              "created": "2020-04-09T11:55:24.127408",
              "description": "A similarity rating, obtained via a slider rating between 0 and 400.",
              "title": "Manual Crowdsourced Rating",
              "maxRatingValue": "400",
              "minRatingValue": "0",
              "ratingValue": "0",
              "hasLeftIdea": "https://innovonto-core.imp.fu-berlin.de/entities/ideas/1993da2b-008e-44de-aa1f-20f91df22123",
              "hasRightIdea": "https://innovonto-core.imp.fu-berlin.de/entities/ideas/38c55775-2352-409d-adf4-ecb50123e60a",
              "hasMturkSession": "http://purl.org/innovonto/mx-master/similarities/mturkSessions/1f90b482-b080-4323-9a96-41cd6b8d7bab"
            },
            {
              "@id": "http://purl.org/innovonto/mx-master/similarities/ratings/1",
              "@type": "oid:SimilarityRating",
              "created": "2020-04-09T11:55:24.127408",
              "description": "A similarity rating, obtained via a slider rating between 0 and 400.",
              "title": "Manual Crowdsourced Rating",
              "maxRatingValue": "400",
              "minRatingValue": "0",
              "ratingValue": "120",
              "hasLeftIdea": "https://innovonto-core.imp.fu-berlin.de/entities/ideas/1993da2b-008e-44de-aa1f-20f91df22123",
              "hasRightIdea": "https://innovonto-core.imp.fu-berlin.de/entities/ideas/207ed8be-e222-441c-bb86-3c75fc918208",
              "hasMturkSession": "http://purl.org/innovonto/mx-master/similarities/mturkSessions/1f90b482-b080-4323-9a96-41cd6b8d7bab"
            }
          ],
          "assignmentId": "http://purl.org/innovonto/mturk/assignmentIdmockAssignment",
          "hitId": "http://purl.org/innovonto/mturk/hit/mockHit",
          "workerId": "http://purl.org/innovonto/mturk/worker/mockWorker"
        }
      ]
    }