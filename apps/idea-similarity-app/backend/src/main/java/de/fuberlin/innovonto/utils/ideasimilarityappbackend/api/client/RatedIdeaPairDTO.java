package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.client;

public class RatedIdeaPairDTO {
    private String id;
    private long leftId;
    private long rightId;

    private int similarityRating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getSimilarityRating() {
        return similarityRating;
    }

    public void setSimilarityRating(int similarityRating) {
        this.similarityRating = similarityRating;
    }

    public long getRightId() {
        return rightId;
    }

    public void setRightId(long rightId) {
        this.rightId = rightId;
    }

    public long getLeftId() {
        return leftId;
    }

    public void setLeftId(long leftId) {
        this.leftId = leftId;
    }

    @Override
    public String toString() {
        return "RatingTaskResultItemDTO{" +
                "id='" + id + '\'' +
                ", similarityRating=" + similarityRating +
                '}';
    }
}
