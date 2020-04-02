package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.batch;

import java.util.List;
import java.util.Map;

public class IdeaPairBatchDTO {
    private Map<String,ChallengeDTO> challenges;
    private List<IdeaDTO> ideas;
    private String[][] ideaPairs;

    public IdeaPairBatchDTO(Map<String, ChallengeDTO> challenges, List<IdeaDTO> ideas, String[][] ideaPairs) {
        this.challenges = challenges;
        this.ideaPairs = ideaPairs;
        this.ideas = ideas;
    }

    public List<IdeaDTO> getIdeas() {
        return ideas;
    }

    public Map<String, ChallengeDTO> getChallenges() {
        return challenges;
    }

    public String[][] getIdeaPairs() {
        return ideaPairs;
    }

}
