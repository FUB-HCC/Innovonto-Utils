package de.fuberlin.innovonto.utils.ideasimilarityappbackend.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.jackson.IdeaPairDeserializer;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.jackson.IdeaPairSerializer;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RequirementsImporter {
    private final IdeaRepository ideaRepository;
    private final ChallengeRepository challengeRepository;
    private final RatingProjectRepository ratingProjectRepository;
    private final BatchSplitter batchSplitter;

    @Autowired
    public RequirementsImporter(IdeaRepository ideaRepository, ChallengeRepository challengeRepository, RatingProjectRepository ratingProjectRepository, BatchSplitter batchSplitter) {
        this.ideaRepository = ideaRepository;
        this.challengeRepository = challengeRepository;
        this.ratingProjectRepository = ratingProjectRepository;
        this.batchSplitter = batchSplitter;
    }

    public Requirements importRequirementsFromJson(String jsonInput) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        final SimpleModule module = new SimpleModule();
        module.addSerializer(IdeaPair.class, new IdeaPairSerializer());
        module.addDeserializer(IdeaPair.class, new IdeaPairDeserializer());
        mapper.registerModule(module);
        return mapper.readValue(jsonInput, Requirements.class);
    }

    public void validateRequirements(Requirements requirements) throws ValidationException {
        //Step 1: Check if already existing id
        if (ratingProjectRepository.findById(requirements.getId()).isPresent()) {
            throw new ValidationException("Validation Failed: Already Existing Project with id: " + requirements.getId());
        }
        //Step 2: Check That all ideas are present
        final List<UUID> ideaIds = requirements.getIdeas().stream().map(Idea::getId).collect(Collectors.toList());
        final Set<UUID> ideaIdsFoundInPairs = new HashSet<>();
        for (IdeaPair pair : requirements.getPairs()) {
            ideaIdsFoundInPairs.add(UUID.fromString(pair.getLeftIdea()));
            ideaIdsFoundInPairs.add(UUID.fromString(pair.getRightIdea()));
        }
        for (UUID ideaIdFromPair : ideaIdsFoundInPairs) {
            if (!ideaIds.contains(ideaIdFromPair)) {
                throw new ValidationException("Idea Id found in pair: " + ideaIdFromPair + " is not present in ideas");
            }
        }
        //Step 3: Check That all challenges are present
        final List<String> challengeIdsInIdeas = requirements.getIdeas().stream().map(Idea::getHasIdeaContest).collect(Collectors.toList());
        final List<String> challengeIdsInRequirements = requirements.getChallenges().stream().map(Challenge::getId).collect(Collectors.toList());
        for (String challengeId : challengeIdsInIdeas) {
            if (!challengeIdsInRequirements.contains(challengeId)) {
                if (!challengeRepository.findById(challengeId).isPresent()) {
                    throw new ValidationException("Unknown challenge: " + challengeId + " not found in either the requirements.json nor the database");
                }
            }
        }

        //Step 4: Check that resulting batches are balanced (TODO long-term: generify)
        if (!((requirements.getPairs().size() * requirements.getGoalRatingsPerPair()) % requirements.getBatchSize() == 0)) {
            throw new ValidationException("Estimated Ratings: " + (requirements.getPairs().size() * requirements.getGoalRatingsPerPair()) + " is not evenly divisible by: " + (requirements.getBatchSize()));
        }
    }

    //TODO FIXME: this will fail on re-upload of a challenge.
    public RatingProject saveRequirementsAsProject(Requirements requirements) {
        final RatingProject result = new RatingProject(requirements.getId());
        //save challenges
        for (Challenge challenge : requirements.getChallenges()) {
            challengeRepository.save(challenge);
        }

        //save ideas
        for (Idea idea : requirements.getIdeas()) {
            ideaRepository.save(idea);
        }
        List<Batch> splittedBatches = batchSplitter.createBatchesFor(requirements);
        result.setBatches(splittedBatches);
        ratingProjectRepository.save(result);
        return result;
    }

}
