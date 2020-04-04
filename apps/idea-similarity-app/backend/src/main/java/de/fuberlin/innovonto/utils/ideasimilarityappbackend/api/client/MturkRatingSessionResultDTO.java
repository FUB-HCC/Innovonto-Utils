package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.client;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class MturkRatingSessionResultDTO {

    //General:
    @NotBlank
    private String ratingProjectId;
    @NotBlank
    private String hitId;
    @NotBlank
    private String workerId;
    @NotBlank
    private String assignmentId;

    //Survey
    private String fulltextFeedback;
    private int clarityRating;

    private boolean passedAttentionCheck = false;

    private List<RatedIdeaPairDTO> ratings;

    public MturkRatingSessionResultDTO() {
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

    public List<RatedIdeaPairDTO> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatedIdeaPairDTO> ratings) {
        this.ratings = ratings;
    }

    public String getRatingProjectId() {
        return ratingProjectId;
    }

    public void setRatingProjectId(String ratingProjectId) {
        this.ratingProjectId = ratingProjectId;
    }

    @Override
    public String toString() {
        return "MturkRatingSessionResultDTO{" +
                "ratingProjectId='" + ratingProjectId + '\'' +
                ", hitId='" + hitId + '\'' +
                ", workerId='" + workerId + '\'' +
                ", assignmentId='" + assignmentId + '\'' +
                ", fulltextFeedback='" + fulltextFeedback + '\'' +
                ", clarityRating=" + clarityRating +
                ", passedAttentionCheck=" + passedAttentionCheck +
                ", ratings=" + ratings +
                '}';
    }
}
