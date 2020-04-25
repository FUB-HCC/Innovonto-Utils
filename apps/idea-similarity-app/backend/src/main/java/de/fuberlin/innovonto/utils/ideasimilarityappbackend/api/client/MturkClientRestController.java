package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.client;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.IdeaPairBatchDistributorService;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.ResourceNotFoundException;
import de.fuberlin.innovonto.utils.common.web.MturkSesssionInformationMissingException;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.BatchState;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
@RequestMapping("/api/mturk/")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8181", "https://i2m-research.imp.fu-berlin.de"})
public class MturkClientRestController {
    private static final Logger log = LoggerFactory.getLogger(MturkClientRestController.class);

    private final IdeaPairBatchDistributorService distributorService;
    private final ChallengeRepository challengeRepository;
    private final IdeaRepository ideaRepository;
    private final MturkRatingSessionRepository mturkRatingSessionRepository;
    private final BatchRepository batchRepository;
    private final RatingProjectRepository ratingProjectRepository;

    @Autowired
    public MturkClientRestController(IdeaPairBatchDistributorService distributorService, ChallengeRepository challengeRepository, IdeaRepository ideaRepository, MturkRatingSessionRepository mturkRatingSessionRepository, BatchRepository batchRepository, RatingProjectRepository ratingProjectRepository) {
        this.distributorService = distributorService;
        this.challengeRepository = challengeRepository;
        this.ideaRepository = ideaRepository;
        this.mturkRatingSessionRepository = mturkRatingSessionRepository;
        this.batchRepository = batchRepository;
        this.ratingProjectRepository = ratingProjectRepository;
    }

    @ResponseBody
    @GetMapping(value = "/rating/projectMetadata")
    public RatingProject getProjectMetadata(@RequestParam String ratingProjectId) {
        Optional<RatingProject> byId = ratingProjectRepository.findById(ratingProjectId);
        if (byId.isEmpty()) {
            throw new ResourceNotFoundException("No project for id: " + ratingProjectId);
        } else {
            return byId.get();
        }
    }

    @ResponseBody
    @GetMapping(value = "/rating/ratingpairs")
    public IdeaPairBatchDTO getRatingPairsFor(@RequestParam String ratingProjectId, @RequestParam String hitId, @RequestParam String workerId, @RequestParam String assignmentId) {
        if (isBlank(hitId) || isBlank(workerId) || isBlank(assignmentId)) {
            throw new MturkSesssionInformationMissingException("Could not find mturk session information (HWA) on the result object.");
        }
        //if this batch was already allocated: return the same batch

        final Batch batchForCurrentAssignment = distributorService.allocateBatchFor(ratingProjectId, hitId, workerId, assignmentId);

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
        //Step 4: Randomize the ordering of the pairs
        List<IdeaPair> pairs = batchForCurrentAssignment.getPairs();
        //Step 5: Put everything into one object
        log.info(ratingProjectId + ": Returning batch for " + batchForCurrentAssignment.getHWA() + ", consisting of " + pairs.size() + " pairs.");
        return new IdeaPairBatchDTO(challenges, ideas, pairs);
    }

    @PostMapping(value = "/rating/submit")
    @CrossOrigin(
            origins = "*",
            allowedHeaders = "*",
            methods = {RequestMethod.GET, RequestMethod.POST})
    public MturkRatingSession submitRatingTask(@RequestBody MturkRatingSessionResultDTO submissionData) {
        if (submissionData == null || isBlank(submissionData.getHitId()) || isBlank(submissionData.getAssignmentId()) || isBlank(submissionData.getWorkerId())) {
            throw new MturkSesssionInformationMissingException("Could not find mturk session information (HWA) on the submissionData object.");
        }
        Optional<RatingProject> byRatingProjectId = ratingProjectRepository.findById(submissionData.getRatingProjectId());
        if (byRatingProjectId.isEmpty()) {
            throw new IllegalStateException("Tried to submit ratings without an allocated batch. AssignmentId is: " + submissionData.getAssignmentId());
        } else {
            final Optional<Batch> potentiallySubmittedBatch = batchRepository.findByHitIdAndWorkerIdAndAssignmentId(submissionData.getHitId(), submissionData.getWorkerId(), submissionData.getAssignmentId());
            if (potentiallySubmittedBatch.isPresent() && potentiallySubmittedBatch.get().getBatchState().equals(BatchState.SUBMITTED)) {
                return mturkRatingSessionRepository.findByAssignmentId(submissionData.getAssignmentId()).get();
            }
            RatingProject currentProject = byRatingProjectId.get();
            Optional<Batch> byAssignmentId = batchRepository.findByAssignmentId(submissionData.getAssignmentId());
            if (byAssignmentId.isEmpty()) {
                throw new IllegalStateException("Tried to submit ratings without an allocated batch. AssignmentId is: " + submissionData.getAssignmentId());
            } else {
                final Batch sourceBatch = byAssignmentId.get();
                //TODO compare HWA?
                final List<RatedIdeaPairDTO> submissionDataRatings = submissionData.getRatings();
                if (submissionDataRatings.size() != sourceBatch.getPairs().size()) {
                    log.warn("Submission Data Ratings had size:" + submissionDataRatings.size() + " while source Batch pairs had size:" + sourceBatch.getPairs().size() + " continuing.");
                }
                final MturkRatingSession resultSession = new MturkRatingSession(submissionData);
                final List<RatedIdeaPair> ratedIdeaPairs = new ArrayList<>(submissionDataRatings.size());
                for (RatedIdeaPairDTO dto : submissionDataRatings) {
                    final RatedIdeaPair ratingResult = new RatedIdeaPair();
                    ratingResult.setHitId(submissionData.getHitId());
                    ratingResult.setWorkerId(submissionData.getWorkerId());
                    ratingResult.setAssignmentId(submissionData.getAssignmentId());
                    ratingResult.setLeftIdea(dto.getLeftIdea());
                    ratingResult.setRightIdea(dto.getRightIdea());
                    ratingResult.setSimilarityRating(dto.getSimilarityRating());
                    ratingResult.setReviewStatus(ReviewStatus.UNREVIEWED);
                    ratedIdeaPairs.add(ratingResult);
                }
                resultSession.setRatings(ratedIdeaPairs);
                resultSession.setReviewStatus(ReviewStatus.UNREVIEWED);
                final MturkRatingSession savedSession = mturkRatingSessionRepository.save(resultSession);

                sourceBatch.setBatchState(BatchState.SUBMITTED);
                sourceBatch.setSubmitted(LocalDateTime.now());
                sourceBatch.setLastPublished(LocalDateTime.now());
                sourceBatch.setResultsRatingSessionId(savedSession.getId());
                batchRepository.save(sourceBatch);

                List<MturkRatingSession> sessions = currentProject.getSessions();
                if (sessions == null) {
                    sessions = new ArrayList<>();
                }
                sessions.add(savedSession);
                ratingProjectRepository.save(currentProject);
                log.info("Successfully saved results for:" + currentProject.getId() + ": HWA: " + sourceBatch.getHWA() + ", consisting of " + sourceBatch.getPairs().size() + " pairs.");
                return savedSession;
            }
        }
    }

    //Debug View to See Mturk Submit Data:
    @ResponseBody
    @PostMapping(value = "/externalSubmit", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(methods = RequestMethod.POST)
    public String submitHITDebug(HttpServletRequest request) {
        final JSONObject result = new JSONObject();
        for (Map.Entry<String, String[]> parameter : request.getParameterMap().entrySet()) {
            result.put(parameter.getKey(), Arrays.toString(parameter.getValue()));
        }
        return result.toString();
    }
}
