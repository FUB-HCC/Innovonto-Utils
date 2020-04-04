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
    private LocalDateTime lastPublished;
    private BatchState batchState = BatchState.UNALLOCATED;
    private String assignmentId;
    //TODO has MturkRatingSession (Nullable)

    @ManyToMany(cascade = CascadeType.ALL)
    private List<IdeaPair> pairs;

    //TODO link to requirements to calculate goal/current
    //TODO Batch State should be reset, when an assignment is approved but unusable
    //TODO When an assignment is rejected, the allocation SHOULD stay the same
    //hibernate
    public Batch() {
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

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public List<IdeaPair> getPairs() {
        return pairs;
    }

    public void setPairs(List<IdeaPair> pairs) {
        this.pairs = pairs;
    }
}
