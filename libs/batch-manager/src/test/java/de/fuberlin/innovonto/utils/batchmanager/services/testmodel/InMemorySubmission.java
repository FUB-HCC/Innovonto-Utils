package de.fuberlin.innovonto.utils.batchmanager.services.testmodel;

import de.fuberlin.innovonto.utils.batchmanager.api.Submission;
import de.fuberlin.innovonto.utils.batchmanager.api.SubmissionState;

import java.time.LocalDateTime;
import java.util.UUID;

public class InMemorySubmission implements Submission {
    private UUID id;
    //Metadata
    private LocalDateTime accepted;
    private LocalDateTime submitted;
    private LocalDateTime reviewed;

    private String hitId;
    private String workerId;
    private String assignmentId;

    private String projectId;

    private SubmissionState submissionState = SubmissionState.UNREVIEWED;

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getAccepted() {
        return accepted;
    }

    public void setAccepted(LocalDateTime accepted) {
        this.accepted = accepted;
    }

    public LocalDateTime getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
    }

    public LocalDateTime getReviewed() {
        return reviewed;
    }

    public void setReviewed(LocalDateTime reviewed) {
        this.reviewed = reviewed;
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

    @Override
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public SubmissionState getSubmissionState() {
        return submissionState;
    }

    public void setSubmissionState(SubmissionState submissionState) {
        this.submissionState = submissionState;
    }

    @Override
    public String getHWA() {
        return hitId + "|" + workerId + "|" + assignmentId;
    }

}
