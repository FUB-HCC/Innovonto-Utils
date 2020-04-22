package de.fuberlin.innovonto.utils.ideasimilarityappbackend.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class RatingProject {

    @Id
    private String id;

    private LocalDateTime created;

    @ColumnDefault("0")
    private int estimatedTimeInMinutes = 0;
    @ColumnDefault("0")
    private double compensation = 0;
    @ColumnDefault("0")
    private int batchSize = 0;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Batch> batches;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MturkRatingSession> sessions;

    //Hibernate
    public RatingProject() {
        created = LocalDateTime.now();
    }

    public RatingProject(String id) {
        this();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public String getProgressState() {
        int goalBatches = batches.size();
        long batchesInProgress = batches.stream().filter((b) -> b.getBatchState().equals(BatchState.ALLOCATED)).count();
        long batchesSubmitted = batches.stream().filter((b) -> b.getBatchState().equals(BatchState.SUBMITTED)).count();
        return batchesSubmitted + "/" + goalBatches + " Submitted, " + batchesInProgress + " in progress.";
    }

    public List<MturkRatingSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<MturkRatingSession> sessions) {
        this.sessions = sessions;
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
}
