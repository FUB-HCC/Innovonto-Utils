package de.fuberlin.innovonto.utils.batchmanager.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface Batch {
    UUID getId();

    BatchState getBatchState();

    void setBatchState(BatchState batchState);

    //TODO generify? This is mturk specific
    String getHitId();

    void setHitId(String hitId);

    String getWorkerId();

    void setWorkerId(String workerId);

    String getAssignmentId();

    void setAssignmentId(String assignmentId);

    String getHWA();

    LocalDateTime getLastPublished();

    void setLastPublished(LocalDateTime lastPublished);

    LocalDateTime getSubmitted();

    void setSubmitted(LocalDateTime lastPublished);

    void addSubmissionId(UUID submissionId);

    Iterable<UUID> getSubmissionIds();
}
