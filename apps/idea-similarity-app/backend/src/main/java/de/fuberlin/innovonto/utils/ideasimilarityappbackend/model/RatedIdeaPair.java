package de.fuberlin.innovonto.utils.ideasimilarityappbackend.model;

import javax.persistence.*;

//TODO long-term: generify? float/double?
@Entity
public class RatedIdeaPair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 2_000)
    private String leftIdea;
    @Column(length = 2_000)
    private String rightIdea;

    @Column(length = 2_000)
    private String hitId;
    @Column(length = 2_000)
    private String workerId;
    @Column(length = 2_000)
    private String assignmentId;

    private int similarityRating;

    private ReviewStatus reviewStatus = ReviewStatus.UNREVIEWED;

    //Hibernate
    public RatedIdeaPair() {
    }

    public String getLeftIdea() {
        return leftIdea;
    }

    public void setLeftIdea(String leftIdea) {
        this.leftIdea = leftIdea;
    }

    public String getRightIdea() {
        return rightIdea;
    }

    public void setRightIdea(String rightIdea) {
        this.rightIdea = rightIdea;
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

    public int getSimilarityRating() {
        return similarityRating;
    }

    public void setSimilarityRating(int similarityRating) {
        this.similarityRating = similarityRating;
    }

    public ReviewStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    @Override
    public String toString() {
        return "RatedIdeaPair{" +
                "id=" + id +
                ", leftIdea='" + leftIdea + '\'' +
                ", rightIdea='" + rightIdea + '\'' +
                ", hitId='" + hitId + '\'' +
                ", workerId='" + workerId + '\'' +
                ", assignmentId='" + assignmentId + '\'' +
                ", similarityRating=" + similarityRating +
                ", reviewStatus=" + reviewStatus +
                '}';
    }
}
