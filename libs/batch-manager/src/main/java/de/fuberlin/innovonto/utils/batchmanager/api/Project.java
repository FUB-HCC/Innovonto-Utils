package de.fuberlin.innovonto.utils.batchmanager.api;

import java.util.List;

public interface Project {
    String getId();

    List<Batch> getBatches();

    void addSubmission(Submission submission);

    long getNumberOfBatches();

    long getNumberOfBatchesInProgress();

    long getNumberOfBatchesSubmitted();
}
