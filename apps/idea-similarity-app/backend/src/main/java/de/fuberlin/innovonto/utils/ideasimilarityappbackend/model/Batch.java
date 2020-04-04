package de.fuberlin.innovonto.utils.ideasimilarityappbackend.model;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.management.BatchState;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.IdeaPair;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Batch {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "de.fuberlin.innovonto.utils.common.FallbackUUIDGenerator"
    )
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime lastPublished;
    private LocalDateTime submitted;

    private BatchState batchState = BatchState.UNALLOCATED;
    private String hitId;
    private String workerId;
    private String assignmentId;
    private UUID resultsRatingSessionId;

    //TODO warum ist das many to many?
    @ManyToMany(cascade = CascadeType.ALL)
    private List<IdeaPair> pairs;

    //hibernate
    public Batch() {
        this.created = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public Batch(List<IdeaPair> pairs) {
        this.lastPublished = LocalDateTime.MIN;
        this.pairs = pairs;
    }

    public LocalDateTime getLastPublished() {
        return lastPublished;
    }

    public void setLastPublished(LocalDateTime lastPublished) {
        this.lastPublished = lastPublished;
    }

    public BatchState getBatchState() {
        return batchState;
    }

    public void setBatchState(BatchState batchState) {
        this.batchState = batchState;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getHitId() {
        return hitId;
    }

    public void setHitId(String hitId) {
        this.hitId = hitId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public List<IdeaPair> getPairs() {
        return pairs;
    }

    public void setPairs(List<IdeaPair> pairs) {
        this.pairs = pairs;
    }

    public UUID getResultsRatingSessionId() {
        return resultsRatingSessionId;
    }

    public void setResultsRatingSessionId(UUID resultsRatingSessionId) {
        this.resultsRatingSessionId = resultsRatingSessionId;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "id=" + id +
                ", lastPublished=" + lastPublished +
                ", batchState=" + batchState +
                ", hitId='" + hitId + '\'' +
                ", workerId='" + workerId + '\'' +
                ", assignmentId='" + assignmentId + '\'' +
                ", pairs=" + pairs +
                '}';
    }

    public String getHWA() {
        return hitId + "|" + workerId + "|" + assignmentId;
    }
}
