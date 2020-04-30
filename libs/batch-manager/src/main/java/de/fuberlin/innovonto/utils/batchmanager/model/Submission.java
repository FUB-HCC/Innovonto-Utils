package de.fuberlin.innovonto.utils.batchmanager.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Submission<BRE, S> {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "de.fuberlin.innovonto.utils.common.FallbackUUIDGenerator"
    )
    private UUID id;
    //Metadata
    private LocalDateTime accepted;
    private LocalDateTime submitted;
    private LocalDateTime reviewed;

    //General:
    @NotBlank
    private String hitId;
    @NotBlank
    private String workerId;
    @NotBlank
    private String assignmentId;
    @NotBlank
    private String projectId;

    private ReviewStatus reviewStatus = ReviewStatus.UNREVIEWED;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BRE> ratings;

    private S surveyResult;

    //Hibernate
    public Submission() {
    }

    //HWA,P,timestamps
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public ReviewStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public List<BRE> getRatings() {
        return ratings;
    }

    public void setRatings(List<BRE> ratings) {
        this.ratings = ratings;
    }

    public S getSurveyResult() {
        return surveyResult;
    }

    public void setSurveyResult(S surveyResult) {
        this.surveyResult = surveyResult;
    }
}
