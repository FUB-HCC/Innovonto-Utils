package de.fuberlin.innovonto.utils.ideasimilarityappbackend.model;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.client.MturkRatingSessionResultDTO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class MturkRatingSession {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "de.fuberlin.innovonto.utils.common.FallbackUUIDGenerator"
    )
    private UUID id;
    //Metadata
    //TODO accepted
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
    private String ratingProjectId;

    //Survey
    private String fulltextFeedback;
    private int clarityRating;

    private boolean passedAttentionCheck = false;

    private ReviewStatus reviewStatus = ReviewStatus.UNREVIEWED;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RatedIdeaPair> ratings;

    //Hibernate
    public MturkRatingSession() {
    }

    public MturkRatingSession(MturkRatingSessionResultDTO submissionData) {
        this.hitId = submissionData.getHitId();
        this.workerId = submissionData.getWorkerId();
        this.assignmentId = submissionData.getAssignmentId();
        this.ratingProjectId = submissionData.getRatingProjectId();
        this.fulltextFeedback = submissionData.getFulltextFeedback();
        this.clarityRating = submissionData.getClarityRating();
        this.passedAttentionCheck = submissionData.isPassedAttentionCheck();
        this.submitted = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getFulltextFeedback() {
        return fulltextFeedback;
    }

    public void setFulltextFeedback(String fulltextFeedback) {
        this.fulltextFeedback = fulltextFeedback;
    }

    public int getClarityRating() {
        return clarityRating;
    }

    public void setClarityRating(int clarityRating) {
        this.clarityRating = clarityRating;
    }

    public boolean isPassedAttentionCheck() {
        return passedAttentionCheck;
    }

    public void setPassedAttentionCheck(boolean passedAttentionCheck) {
        this.passedAttentionCheck = passedAttentionCheck;
    }

    public ReviewStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public List<RatedIdeaPair> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatedIdeaPair> ratings) {
        this.ratings = ratings;
    }

    public LocalDateTime getSubmitted() {
        return submitted;
    }

    public void setReviewed(LocalDateTime reviewed) {
        this.reviewed = reviewed;
    }

    public LocalDateTime getReviewed() {
        return reviewed;
    }

    public String getRatingProjectId() {
        return ratingProjectId;
    }

    public void setRatingProjectId(String ratingProjectId) {
        this.ratingProjectId = ratingProjectId;
    }

    @Override
    public String toString() {
        return "MturkRatingSession{" +
                "id=" + id +
                ", submitted=" + submitted +
                ", reviewed=" + reviewed +
                ", hitId='" + hitId + '\'' +
                ", workerId='" + workerId + '\'' +
                ", assignmentId='" + assignmentId + '\'' +
                ", ratingProjectId='" + ratingProjectId + '\'' +
                ", fulltextFeedback='" + fulltextFeedback + '\'' +
                ", clarityRating=" + clarityRating +
                ", passedAttentionCheck=" + passedAttentionCheck +
                ", reviewStatus=" + reviewStatus +
                '}';
    }
}
