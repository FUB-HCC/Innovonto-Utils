package de.fuberlin.innovonto.utils.ideasimilarityappbackend;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
//For more open API stuff see
//https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations#OpenAPIDefinition
//https://springdoc.org/faq.html#how-can-i-configure-swagger-ui
@OpenAPIDefinition(info = @Info(
        title = "Idea Similarity App Backend",
        version = "0.0.1-SNAPSHOT"),
        servers = {
                @Server(
                        description = "Development",
                        url = "http://localhost:8181"
                ),
                @Server(
                        description = "Live",
                        url = "https://i2m-research.imp.fu-berlin.de"
                )
        })
public class IdeaSimilarityAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdeaSimilarityAppBackendApplication.class, args);
    }

    @Bean
    @Autowired
    public CommandLineRunner createTestData(ChallengeRepository challengeRepository,
                                            IdeaRepository ideaRepository,
                                            RatingProjectRepository ratingProjectRepository) {
        return (args) -> {
            if (ratingProjectRepository.findById("mockproject").isEmpty()) {
                challengeRepository.save(new Challenge("fabricDisplay", "Qui aliquip laborum aliqua adipisicing fugiat magna commodo non reprehenderit et tempor velit non. Esse excepteur esse enim amet dolor laborum dolor. Mollit irure anim pariatur eiusmod eu excepteur magna commodo consequat nostrud et et duis. Nisi aute cillum non culpa excepteur Lorem pariatur qui cupidatat duis. Dolore proident dolore nisi aliqua labore do esse ea.\n\nEst sunt Lorem non laborum tempor laboris Lorem aute id fugiat enim. Pariatur amet quis voluptate dolore. Veniam nostrud consectetur sint pariatur ad dolor. Magna do ad non deserunt adipisicing officia cillum culpa."));
                challengeRepository.save(new Challenge("TCO", "Qui aliquip laborum aliqua adipisicing fugiat magna commodo non reprehenderit et tempor velit non. Esse excepteur esse enim amet dolor laborum dolor. Mollit irure anim pariatur eiusmod eu excepteur magna commodo consequat nostrud et et duis. Nisi aute cillum non culpa excepteur Lorem pariatur qui cupidatat duis. Dolore proident dolore nisi aliqua labore do esse ea.\n\nEst sunt Lorem non laborum tempor laboris Lorem aute id fugiat enim. Pariatur amet quis voluptate dolore. Veniam nostrud consectetur sint pariatur ad dolor. Magna do ad non deserunt adipisicing officia cillum culpa."));
                challengeRepository.save(new Challenge("bionicRadar", "Qui aliquip laborum aliqua adipisicing fugiat magna commodo non reprehenderit et tempor velit non. Esse excepteur esse enim amet dolor laborum dolor. Mollit irure anim pariatur eiusmod eu excepteur magna commodo consequat nostrud et et duis. Nisi aute cillum non culpa excepteur Lorem pariatur qui cupidatat duis. Dolore proident dolore nisi aliqua labore do esse ea.\n\nEst sunt Lorem non laborum tempor laboris Lorem aute id fugiat enim. Pariatur amet quis voluptate dolore. Veniam nostrud consectetur sint pariatur ad dolor. Magna do ad non deserunt adipisicing officia cillum culpa."));

                ideaRepository.save(new Idea(UUID.fromString("cf65b021-620f-43fe-9473-1712be788cde"), "The technology can be used to track migration patterns of invasive organisms, such as algal bloom or certain insects. ", "bionicRadar"));
                ideaRepository.save(new Idea(UUID.fromString("207ed8be-e222-441c-bb86-3c75fc918208"), "Profiling criminals", "TCO"));
                List<IdeaPair> pairs = new ArrayList<>();
                pairs.add(new IdeaPair("cf65b021-620f-43fe-9473-1712be788cde", "207ed8be-e222-441c-bb86-3c75fc918208"));
                Batch testBatch = new Batch(pairs);

                RatingProject mockProject = new RatingProject("mockproject");
                List<Batch> batches = new ArrayList<>();
                batches.add(testBatch);
                mockProject.setBatches(batches);
                ratingProjectRepository.save(mockProject);
            }
        };
    }
}
