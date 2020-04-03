package de.fuberlin.innovonto.utils.ideasimilarityappbackend.model;

import com.google.gson.annotations.SerializedName;

public class Idea {

    @SerializedName("@id")
    private String id;
    private String content;
    private String hasIdeaContest;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHasIdeaContest() {
        return hasIdeaContest;
    }

    public void setHasIdeaContest(String hasIdeaContest) {
        this.hasIdeaContest = hasIdeaContest;
    }
}
