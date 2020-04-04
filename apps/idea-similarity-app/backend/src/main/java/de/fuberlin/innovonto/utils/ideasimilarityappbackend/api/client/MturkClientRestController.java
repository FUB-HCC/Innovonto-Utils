package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.client;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.MturkSesssionInformationMissingException;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.IdeaPairBatchDistributorService;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
@RequestMapping("/api/mturk/")
public class MturkClientRestController {
    private final IdeaPairBatchDistributorService distributorService;
    private final ChallengeRepository challengeRepository;
    private final IdeaRepository ideaRepository;

    @Autowired
    public MturkClientRestController(IdeaPairBatchDistributorService distributorService, ChallengeRepository challengeRepository, IdeaRepository ideaRepository) {
        this.distributorService = distributorService;
        this.challengeRepository = challengeRepository;
        this.ideaRepository = ideaRepository;
    }

    @ResponseBody
    @GetMapping(value = "/rating/ratingpairs")
    public IdeaPairBatchDTO getRatingPairsFor(@RequestParam String ratingProjectId, @RequestParam String hitId, @RequestParam String workerId, @RequestParam String assignmentId) {
        if (isBlank(hitId) || isBlank(workerId) || isBlank(assignmentId)) {
            throw new MturkSesssionInformationMissingException("Could not find mturk session information (HWA) on the result object.");
        }
        //if this batch was already allocated: return the same batch

        final Batch batchForCurrentAssignment = distributorService.allocateBatchFor(ratingProjectId, hitId, workerId, assignmentId);

        //TODO convertBatch to DTO
        //Step 1: Go through all pairs: collect ideaIds
        final Set<UUID> ideaIds = new HashSet<>();
        for (IdeaPair pair : batchForCurrentAssignment.getPairs()) {
            ideaIds.add(UUID.fromString(pair.getLeftIdea()));
            ideaIds.add(UUID.fromString(pair.getRightIdea()));
        }

        //Step 2: Get Ideas
        final List<Idea> ideas = StreamSupport.stream(ideaRepository.findAllById(ideaIds).spliterator(), false)
                .collect(Collectors.toList());
        //Step 3: Get Challenges
        final Set<String> challengeIds = new HashSet<>();
        for (Idea idea : ideas) {
            challengeIds.add(idea.getHasIdeaContest());
        }
        final Map<String, Challenge> challenges = new HashMap<>();
        for (String challengeId : challengeIds) {
            Challenge value = challengeRepository.findById(challengeId).get();
            challenges.put(value.getId(), value);
        }
        //Step 4: Put everything into one object
        return new IdeaPairBatchDTO(challenges, ideas, batchForCurrentAssignment.getPairs());
    }

    @ResponseBody
    @PostMapping(value = "/rating/submit")
    public MturkRatingSession submitRatingTask(MturkRatingSessionResultDTO result) {
        if (result == null || isBlank(result.getHitId()) || isBlank(result.getAssignmentId()) || isBlank(result.getWorkerId())) {
            throw new MturkSesssionInformationMissingException("Could not find mturk session information (HWA) on the result object.");
        }
        //TODO convert DTOs to DB Objects
        //TODO save the DB Objects
        //TODO update the Batch: State, Published, Results
        return new MturkRatingSession();
    }

    //Debug View to See Mturk Submit Data:
    @ResponseBody
    @PostMapping(value = "/externalSubmit", produces = MediaType.APPLICATION_JSON_VALUE)
    public String submitHITDebug(HttpServletRequest request) {
        final JSONObject result = new JSONObject();
        for (Map.Entry<String, String[]> parameter : request.getParameterMap().entrySet()) {
            result.put(parameter.getKey(), Arrays.toString(parameter.getValue()));
        }
        return result.toString();
    }
}
