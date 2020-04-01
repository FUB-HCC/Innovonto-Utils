package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.results;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.results.MturkSimilarityRatingDTO;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;

public class MturkSimilarityTaskResultDTO {
    //General:
    @NotBlank
    private String hitId;
    @NotBlank
    private String workerId;
    @NotBlank
    private String assignmentId;

    //Survey
    private String fulltextFeedback;
    private int clarityRating;

    private MturkSimilarityRatingDTO[] ratings;

    public MturkSimilarityTaskResultDTO() {
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

    public MturkSimilarityRatingDTO[] getRatings() {
        return ratings;
    }

    public void setRatings(MturkSimilarityRatingDTO[] ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "MturkSimilarityTaskResultDTO{" +
                "hitId='" + hitId + '\'' +
                ", workerId='" + workerId + '\'' +
                ", assignmentId='" + assignmentId + '\'' +
                ", fulltextFeedback='" + fulltextFeedback + '\'' +
                ", clarityRating=" + clarityRating +
                ", ratings=" + (ratings == null ? null : Arrays.asList(ratings)) +
                '}';
    }
}
