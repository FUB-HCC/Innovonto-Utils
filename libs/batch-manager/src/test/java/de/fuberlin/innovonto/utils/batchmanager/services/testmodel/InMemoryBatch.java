package de.fuberlin.innovonto.utils.batchmanager.services.testmodel;

import de.fuberlin.innovonto.utils.batchmanager.api.Batch;
import de.fuberlin.innovonto.utils.batchmanager.api.BatchState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryBatch implements Batch {

    private UUID id;

    private LocalDateTime created;
    private LocalDateTime lastPublished;
    private LocalDateTime submitted;

    private BatchState batchState = BatchState.UNALLOCATED;

    private String hitId;
    private String workerId;
    private String assignmentId;

    private List<UUID> submissionIds;
    private List<MockBatchElement> mockBatchElements;

    public InMemoryBatch(List<MockBatchElement> mockBatchElements) {
        this.mockBatchElements = mockBatchElements;
        this.lastPublished = LocalDateTime.MIN;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public LocalDateTime getLastPublished() {
        return lastPublished;
    }

    @Override
    public void setLastPublished(LocalDateTime lastPublished) {
        this.lastPublished = lastPublished;
    }

    @Override
    public LocalDateTime getSubmitted() {
        return submitted;
    }

    @Override
    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
    }

    @Override
    public BatchState getBatchState() {
        return batchState;
    }

    @Override
    public void setBatchState(BatchState batchState) {
        this.batchState = batchState;
    }

    @Override
    public String getHitId() {
        return hitId;
    }

    @Override
    public void setHitId(String hitId) {
        this.hitId = hitId;
    }

    @Override
    public String getWorkerId() {
        return workerId;
    }

    @Override
    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    @Override
    public String getAssignmentId() {
        return assignmentId;
    }

    @Override
    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getHWA() {
        return hitId + "|" + workerId + "|" + assignmentId;
    }

    @Override
    public void addSubmissionId(UUID submissionId) {
        if (this.submissionIds == null) {
            this.submissionIds = new ArrayList<>();
        }
        this.submissionIds.add(submissionId);
    }

    @Override
    public List<UUID> getSubmissionIds() {
        return submissionIds;
    }

    public List<MockBatchElement> getBatchElements() {
        return this.mockBatchElements;
    }
}
