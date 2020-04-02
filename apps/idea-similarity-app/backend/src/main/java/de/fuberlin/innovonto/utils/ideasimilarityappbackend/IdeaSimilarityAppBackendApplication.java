package de.fuberlin.innovonto.utils.ideasimilarityappbackend;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.batch.ChallengeDTO;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.batch.IdeaDTO;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.batch.IdeaPairBatchDTO;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.batch.IdeaPairBatchDistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class IdeaSimilarityAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdeaSimilarityAppBackendApplication.class, args);
    }

    @Bean
    @Autowired
    public CommandLineRunner createTestData(IdeaPairBatchDistributorService distributorService) {
        return (args) -> {
            final Map<String, ChallengeDTO> challenges = new HashMap<>();
            challenges.put("fabricDisplay", new ChallengeDTO("fd", "Qui aliquip laborum aliqua adipisicing fugiat magna commodo non reprehenderit et tempor velit non. Esse excepteur esse enim amet dolor laborum dolor. Mollit irure anim pariatur eiusmod eu excepteur magna commodo consequat nostrud et et duis. Nisi aute cillum non culpa excepteur Lorem pariatur qui cupidatat duis. Dolore proident dolore nisi aliqua labore do esse ea.\n\nEst sunt Lorem non laborum tempor laboris Lorem aute id fugiat enim. Pariatur amet quis voluptate dolore. Veniam nostrud consectetur sint pariatur ad dolor. Magna do ad non deserunt adipisicing officia cillum culpa."));
            challenges.put("TCO", new ChallengeDTO("tco", "Qui aliquip laborum aliqua adipisicing fugiat magna commodo non reprehenderit et tempor velit non. Esse excepteur esse enim amet dolor laborum dolor. Mollit irure anim pariatur eiusmod eu excepteur magna commodo consequat nostrud et et duis. Nisi aute cillum non culpa excepteur Lorem pariatur qui cupidatat duis. Dolore proident dolore nisi aliqua labore do esse ea.\n\nEst sunt Lorem non laborum tempor laboris Lorem aute id fugiat enim. Pariatur amet quis voluptate dolore. Veniam nostrud consectetur sint pariatur ad dolor. Magna do ad non deserunt adipisicing officia cillum culpa."));
            challenges.put("bionicRadar", new ChallengeDTO("br", "Qui aliquip laborum aliqua adipisicing fugiat magna commodo non reprehenderit et tempor velit non. Esse excepteur esse enim amet dolor laborum dolor. Mollit irure anim pariatur eiusmod eu excepteur magna commodo consequat nostrud et et duis. Nisi aute cillum non culpa excepteur Lorem pariatur qui cupidatat duis. Dolore proident dolore nisi aliqua labore do esse ea.\n\nEst sunt Lorem non laborum tempor laboris Lorem aute id fugiat enim. Pariatur amet quis voluptate dolore. Veniam nostrud consectetur sint pariatur ad dolor. Magna do ad non deserunt adipisicing officia cillum culpa."));
			final List<IdeaDTO> ideas = new ArrayList<>();
            ideas.add(new IdeaDTO("cf65b021-620f-43fe-9473-1712be788cde", "This can enable me to interact and see where others are in my home."));
            ideas.add(new IdeaDTO("207ed8be-e222-441c-bb86-3c75fc918208", "The way this technology can be used is by detecting the flow of people. For example in a crowded area, if we can study the way people move, the flow of the crowd and where they go, we can design large areas to be more open. One specific place would be an airport, if we can utilize this technology and learn where people tend to walk, where people tend to gravitate to when going through an airport. From personal experience I have seen that people tend to 'clog up' a certain area when they go through an airport. If the technology could figure out how we can design a large space, we could better prevent long lines, and too many people crowded in a space."));
            ideas.add(new IdeaDTO("6f7a5f9b-dc13-4467-a0e8-bc72a1b26025", "You could use the technology to detect unwanted animals. In Florida, they have in recent years, had an influx of pythons not indigenous to the area. The snakes are causing issues with the ecosystem."));
            ideas.add(new IdeaDTO("8c55775-2352-409d-adf4-ecb50123e60a", "The technology can be used to track migration patterns of invasive organisms, such as algal bloom or certain insects. "));
            ideas.add(new IdeaDTO("8af6f8b6-0973-4992-8ea3-585d5efd7e10", "Profiling criminals"));
            ideas.add(new IdeaDTO("fb85b2da-bc85-4ecc-b44c-5e9b94a72238", "Security systems could use this technology to identify intruders."));

			final String[][] pairs = new String[2][2];
            pairs[0][0] = "cf65b021-620f-43fe-9473-1712be788cde";
            pairs[0][1] = "207ed8be-e222-441c-bb86-3c75fc918208";
            pairs[1][0] = "207ed8be-e222-441c-bb86-3c75fc918208";
            pairs[1][1] = "6f7a5f9b-dc13-4467-a0e8-bc72a1b26025";

            distributorService.setMockData(new IdeaPairBatchDTO(challenges, ideas, pairs));
        };
    }
}
