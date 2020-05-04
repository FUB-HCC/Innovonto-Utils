package de.fuberlin.innovonto.utils.batchmanager.services.testmodel;

import de.fuberlin.innovonto.utils.batchmanager.api.Batch;
import de.fuberlin.innovonto.utils.batchmanager.api.BatchState;
import de.fuberlin.innovonto.utils.batchmanager.api.Project;
import de.fuberlin.innovonto.utils.batchmanager.api.Submission;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InMemoryProject implements Project {
    private String id;

    private LocalDateTime created = LocalDateTime.now();

    private int estimatedTimeInMinutes = 0;
    private double compensation = 0;
    private int batchSize = 0;

    private List<InMemoryBatch> batches;

    private List<InMemorySubmission> submissions;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public int getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
    }

    public double getCompensation() {
        return compensation;
    }

    public void setCompensation(double compensation) {
        this.compensation = compensation;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    @Override
    public List<Batch> getBatches() {
        return new ArrayList<>(batches);
    }

    public void setBatches(List<InMemoryBatch> batches) {
        this.batches = batches;
    }

    public void addSubmission(Submission submission) {
        if (submissions == null) {
            submissions = new ArrayList<>();
        }
        if(submission instanceof InMemorySubmission) {
            submissions.add((InMemorySubmission) submission);
        }
    }

    @Override
    public long getNumberOfBatches() {
        return batches.size();
    }

    @Override
    public long getNumberOfBatchesInProgress() {
        return batches.stream().filter((b) -> b.getBatchState().equals(BatchState.ALLOCATED)).count();
    }

    @Override
    public long getNumberOfBatchesSubmitted() {
        return batches.stream().filter((b) -> b.getBatchState().equals(BatchState.SUBMITTED)).count();
    }

    //TODO lift?
    public String getProgressState() {
        return getNumberOfBatchesSubmitted() + "/" + batches.size() + " Submitted, " + getNumberOfBatchesInProgress() + " in progress.";
    }
}
