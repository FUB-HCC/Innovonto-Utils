package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.management;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.business.RatingProjectService;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.BatchState;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.management.Requirements;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.management.RequirementsImporter;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/management/")
@CrossOrigin(origins = {"http://localhost:8002", "http://localhost:8181", "https://i2m-research.imp.fu-berlin.de"})
public class ManagementRestController {
    private final RatingProjectService ratingProjectService;
    private final MturkRatingSessionRepository ratingSessionRepository;
    private final BatchRepository batchRepository;
    private final RequirementsImporter requirementsImporter;

    @Autowired
    public ManagementRestController(RatingProjectService ratingProjectService, MturkRatingSessionRepository ratingSessionRepository, BatchRepository batchRepository, RequirementsImporter requirementsImporter) {
        this.ratingSessionRepository = ratingSessionRepository;
        this.batchRepository = batchRepository;
        this.requirementsImporter = requirementsImporter;
        this.ratingProjectService = ratingProjectService;
    }

    //Upload Requirements
    @PostMapping("/requirements/")
    @CrossOrigin(
            origins = "*",
            allowedHeaders = "*",
            methods = {RequestMethod.GET, RequestMethod.POST})
    public RatingProject uploadRequirements(@RequestBody Requirements requirements) {
        requirementsImporter.validateRequirements(requirements);
        return requirementsImporter.saveRequirementsAsProject(requirements);
    }

    //See all RatingProjects
    @GetMapping("/ratingProjects/")
    public Iterable<RatingProject> getAllRatingProjects() {
        return ratingProjectService.findAll();
    }

    //See all RatingProjects
    @GetMapping("/ratingProjects/{id}")
    public Optional<RatingProject> getRatingProjectById(@PathVariable String id) {
        return ratingProjectService.findById(id);
    }

    //TODO add Display Object for MturkRating Session, to make reviewing a session easier.
    @GetMapping("/mturkRatingSessions/byAssignment")
    public Optional<MturkRatingSession> getByAssignmentId(@RequestParam String assignmentId) {
        return ratingSessionRepository.findByAssignmentId(assignmentId);
    }

    @GetMapping("/mturkRatingSessions/{mturkSessionId}/set-usable")
    public MturkRatingSession setUsable(@PathVariable UUID mturkSessionId) throws NotFoundException {
        Optional<MturkRatingSession> byId = ratingSessionRepository.findById(mturkSessionId);
        if (byId.isEmpty()) {
            throw new NotFoundException("Could not find session with id: " + mturkSessionId);
        } else {
            final MturkRatingSession session = byId.get();
            session.setReviewStatus(ReviewStatus.USABLE);
            session.setReviewed(LocalDateTime.now());
            for (RatedIdeaPair rating : session.getRatings()) {
                rating.setReviewStatus(ReviewStatus.USABLE);
            }
            return ratingSessionRepository.save(session);
        }
    }

    @GetMapping("/mturkRatingSessions/{mturkSessionId}/set-unusable")
    public MturkRatingSession setUnusable(@PathVariable String mturkSessionId) throws NotFoundException {
        Optional<MturkRatingSession> byId = ratingSessionRepository.findById(UUID.fromString(mturkSessionId));
        if (byId.isEmpty()) {
            throw new NotFoundException("Could not find session with id: " + mturkSessionId);
        } else {
            final MturkRatingSession session = byId.get();
            Optional<Batch> byResultId = batchRepository.findByResultsRatingSessionId(session.getId());
            if (byResultId.isEmpty()) {
                throw new NotFoundException("Could not find source batch for session with id: " + mturkSessionId);
            } else {
                session.setReviewStatus(ReviewStatus.UNUSABLE);
                session.setAssignmentId(session.getAssignmentId() + "-unusable-" + UUID.randomUUID());
                session.setReviewed(LocalDateTime.now());
                for (RatedIdeaPair rating : session.getRatings()) {
                    rating.setReviewStatus(ReviewStatus.UNUSABLE);
                }

                final Batch sourceBatch = byResultId.get();
                sourceBatch.setSubmitted(null);
                sourceBatch.setBatchState(BatchState.UNALLOCATED);
                sourceBatch.setHitId(null);
                sourceBatch.setWorkerId(null);
                sourceBatch.setAssignmentId(null);
                sourceBatch.setResultsRatingSessionId(null);
                batchRepository.save(sourceBatch);

                return ratingSessionRepository.save(session);
            }
        }
    }
}
