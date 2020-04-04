package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.client;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.Challenge;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.Idea;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.IdeaPair;

import java.util.List;
import java.util.Map;

public class IdeaPairBatchDTO {
    private Map<String, Challenge> challenges;
    private List<Idea> ideas;
    private List<IdeaPair> ideaPairs;

    public IdeaPairBatchDTO(Map<String, Challenge> challenges, List<Idea> ideas, List<IdeaPair> ideaPairs) {
        this.challenges = challenges;
        this.ideas = ideas;
        this.ideaPairs = ideaPairs;
    }

    public List<Idea> getIdeas() {
        return ideas;
    }

    public Map<String, Challenge> getChallenges() {
        return challenges;
    }

    public List<IdeaPair> getIdeaPairs() {
        return ideaPairs;
    }
}
