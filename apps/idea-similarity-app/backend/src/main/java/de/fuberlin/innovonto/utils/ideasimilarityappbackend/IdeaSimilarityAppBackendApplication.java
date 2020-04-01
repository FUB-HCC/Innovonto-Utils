package de.fuberlin.innovonto.utils.ideasimilarityappbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IdeaSimilarityAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdeaSimilarityAppBackendApplication.class, args);
	}

	@Bean
	@Autowired
	public CommandLineRunner createTestData() {
		return (args) -> {

		};
	}
}
