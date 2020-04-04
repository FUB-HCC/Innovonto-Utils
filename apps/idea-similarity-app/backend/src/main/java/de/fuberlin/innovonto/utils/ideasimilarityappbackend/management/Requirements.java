package de.fuberlin.innovonto.utils.ideasimilarityappbackend.management;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.Idea;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.IdeaPair;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Requirements {
    private String id;
    private List<Idea> ideas;
    private List<IdeaPair> pairs;
    private int goalRatingsPerPair;
    private int batchSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Idea> getIdeas() {
        return ideas;
    }

    public void setIdeas(List<Idea> ideas) {
        this.ideas = ideas;
    }

    public List<IdeaPair> getPairs() {
        return pairs;
    }

    public void setPairs(List<IdeaPair> pairs) {
        this.pairs = pairs;
    }

    public int getGoalRatingsPerPair() {
        return goalRatingsPerPair;
    }

    public void setGoalRatingsPerPair(int goalRatingsPerPair) {
        this.goalRatingsPerPair = goalRatingsPerPair;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
}
