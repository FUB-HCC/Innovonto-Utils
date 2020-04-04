package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.management;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.management.Requirements;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.RatingProject;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.RatingProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/management/")
public class ManagementRestController {
    private final RatingProjectRepository ratingProjectRepository;

    @Autowired
    public ManagementRestController(RatingProjectRepository ratingProjectRepository) {
        this.ratingProjectRepository = ratingProjectRepository;
    }

    //Upload Requirements
    @PostMapping("/requirements/")
    public String uploadRequirements(@RequestBody Requirements requirements) {
        return "{\"status\":\"ok\"}";
    }

    //See all RatingProjects
    @GetMapping("/ratingProjects/")
    public Iterable<RatingProject> getAllRatingProjects() {
        return ratingProjectRepository.findAll();
    }

    //See all RatingProjects
    @GetMapping("/ratingProjects/{id}")
    public Optional<RatingProject> getRatingProjectById(@PathVariable String id) {
        return ratingProjectRepository.findById(id);
    }

    //Find results by assignmentId

    //Reset a batch (Update Batch-Status to: unallocated)
    //Approve a batch
}
