package de.fuberlin.innovonto.utils.batchmanager.api;

import java.util.UUID;

public interface Submission {
    UUID getId();

    //TODO generify? This is mturk specific
    String getHitId();

    void setHitId(String hitId);

    String getWorkerId();

    void setWorkerId(String workerId);

    String getAssignmentId();

    void setAssignmentId(String assignmentId);

    String getProjectId();

    String getHWA();

    SubmissionState getSubmissionState();
}
