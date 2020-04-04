package de.fuberlin.innovonto.utils.ideasimilarityappbackend.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequirementsImporterIntegrationTest {

    private static String readFileToString(String filePath) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    @Test
    public void testFullCircle() throws JsonProcessingException {
        System.out.println("Setting up!");
        final RatingProjectRepository mockRatingProjectRepository = mock(RatingProjectRepository.class);
        when(mockRatingProjectRepository.findById("test-collection-round")).thenReturn(Optional.empty());
        final IdeaRepository mockIdeaRepository = mock(IdeaRepository.class);
        final ChallengeRepository mockChallengeRepository = mock(ChallengeRepository.class);
        BatchSplitter batchSplitter = new BatchSplitter();
        RequirementsImporter importer = new RequirementsImporter(mockIdeaRepository, mockChallengeRepository, mockRatingProjectRepository, batchSplitter);


        System.out.println("Running");
        final String requirementsDocument = readFileToString("src/main/resources/static/data/requirements-test.json");
        //The mockdata defines 10 pairs, wants 2 ratings per pair and has a batch size of 2
        //This means we have 5 batches to rate the whole thing once, and this times 2

        final Requirements requirements = importer.importRequirementsFromJson(requirementsDocument);
        assertThat(requirements.getBatchSize()).isEqualTo(2);
        assertThat(requirements.getGoalRatingsPerPair()).isEqualTo(2);
        importer.validateRequirements(requirements);

        final RatingProject result = importer.saveRequirementsAsProject(requirements);
        final List<Batch> createdBatches = result.getBatches();
        assertThat(createdBatches).hasSize(10);
        //Check first batch
        Batch firstBatch = createdBatches.get(0);
        assertThat(firstBatch.getBatchState()).isEqualTo(BatchState.UNALLOCATED);
        assertThat(firstBatch.getLastPublished()).isEqualTo(LocalDateTime.MIN);
        assertThat(firstBatch.getAssignmentId()).isNull();
        assertThat(firstBatch.getPairs()).hasSize(requirements.getBatchSize());
    }
}