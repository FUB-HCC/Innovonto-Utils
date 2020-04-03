package de.fuberlin.innovonto.utils.ideasimilarityappbackend.management;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void testFullCircle() {
        System.out.println("Setting up!");
        RequirementsImporter importer = new RequirementsImporter();
        BatchSplitter batchSplitter = new BatchSplitter();


        System.out.println("Running");
        final String requirementsDocument = readFileToString("E:\\projects\\mixed\\Innovonto-Utils\\apps\\idea-similarity-app\\backend\\src\\main\\resources\\static\\data\\requirements-test.json");
        //The mockdata defines 10 pairs, wants 2 ratings per pair and has a batch size of 2
        //This means we have 5 batches to rate the whole thing once, and this times 2

        Requirements requirements = importer.importRequirements(requirementsDocument);
        assertThat(requirements.getBatchSize()).isEqualTo(2);
        assertThat(requirements.getGoalRatingsPerPair()).isEqualTo(2);
        List<Batch> createdBatches = batchSplitter.createBatchesFor(requirements);

        assertThat(createdBatches).hasSize(10);
        //Check first batch
        Batch firstBatch = createdBatches.get(0);

    }
}