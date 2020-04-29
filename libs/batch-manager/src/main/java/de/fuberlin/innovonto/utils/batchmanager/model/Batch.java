package de.fuberlin.innovonto.utils.batchmanager.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Batch<BE> {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "de.fuberlin.innovonto.utils.common.FallbackUUIDGenerator"
    )
    private UUID id;
    @NotBlank
    private LocalDateTime created;
    private LocalDateTime lastPublished;
    private LocalDateTime submitted;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private BatchState batchState = BatchState.UNALLOCATED;

    //TODO generify: This is mturk specific
    @NotBlank
    private String hitId;
    @NotBlank
    private String workerId;
    @NotBlank
    //TODO assignmentId SHOULD be unique. what happens if its not?
    private String assignmentId;

    @ElementCollection
    private List<UUID> batchResultId;

    //TODO warum ist das many to many?
    @ManyToMany(cascade = CascadeType.ALL)
    private List<BE> batchElements;

    //hibernate
    public Batch() {
    }

    public Batch(List<BE> batchElements) {
        this.batchElements = batchElements;
        this.created = LocalDateTime.now();
        this.lastPublished = LocalDateTime.MIN;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getLastPublished() {
        return lastPublished;
    }

    public void setLastPublished(LocalDateTime lastPublished) {
        this.lastPublished = lastPublished;
    }

    public LocalDateTime getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
    }

    public BatchState getBatchState() {
        return batchState;
    }

    public void setBatchState(BatchState batchState) {
        this.batchState = batchState;
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

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public List<UUID> getBatchResultId() {
        return batchResultId;
    }

    public void setBatchResultId(List<UUID> batchResultId) {
        this.batchResultId = batchResultId;
    }

    public List<BE> getBatchElements() {
        return batchElements;
    }

    public void setBatchElements(List<BE> batchElements) {
        this.batchElements = batchElements;
    }

    public String getHWA() {
        return hitId + "|" + workerId + "|" + assignmentId;
    }
}
