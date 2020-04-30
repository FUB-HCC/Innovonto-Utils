package de.fuberlin.innovonto.utils.batchmanager.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project<BE, BRE, S> {

    @Id
    private String id;

    @NotBlank
    private LocalDateTime created = LocalDateTime.now();

    //metadata
    //TODO not null?
    private int estimatedTimeInMinutes = 0;
    private double compensation = 0;
    private int batchSize = 0;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Batch<BE>> batches;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Submission<BRE, S>> submissions;

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

    public List<Batch<BE>> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch<BE>> batches) {
        this.batches = batches;
    }

    public List<Submission<BRE, S>> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission<BRE, S>> submissions) {
        this.submissions = submissions;
    }

    public long getNumberOfBatchesInProgress() {
        return batches.stream().filter((b) -> b.getBatchState().equals(BatchState.ALLOCATED)).count();
    }

    public long getNumberOfBatchesSubmitted() {
        return batches.stream().filter((b) -> b.getBatchState().equals(BatchState.SUBMITTED)).count();
    }

    public String getProgressState() {
        return getNumberOfBatchesSubmitted() + "/" + batches.size() + " Submitted, " + getNumberOfBatchesInProgress() + " in progress.";
    }

    public void addSubmission(Submission<BRE, S> submission) {
        if (submissions == null) {
            submissions = new ArrayList<>();
        }
        submissions.add(submission);
    }
}
