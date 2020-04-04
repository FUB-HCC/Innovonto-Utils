package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.client;

import javax.validation.constraints.NotBlank;

public class RatedIdeaPairDTO {

    @NotBlank
    private String leftIdea;
    @NotBlank
    private String rightIdea;

    private int similarityRating;

    public RatedIdeaPairDTO() {
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

    public int getSimilarityRating() {
        return similarityRating;
    }

    public void setSimilarityRating(int similarityRating) {
        this.similarityRating = similarityRating;
    }
}
