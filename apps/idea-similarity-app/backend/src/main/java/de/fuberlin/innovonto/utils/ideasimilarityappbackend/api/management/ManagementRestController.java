package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.management;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.management.Requirements;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.management.RequirementsImporter;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.RatingProject;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.RatingProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/management/")
public class ManagementRestController {
    private final RatingProjectRepository ratingProjectRepository;
    private final RequirementsImporter requirementsImporter;

    @Autowired
    public ManagementRestController(RatingProjectRepository ratingProjectRepository, RequirementsImporter requirementsImporter) {
        this.ratingProjectRepository = ratingProjectRepository;
        this.requirementsImporter = requirementsImporter;
    }

    //Upload Requirements
    @PostMapping("/requirements/")
    public RatingProject uploadRequirements(@RequestBody Requirements requirements) {
        requirementsImporter.validateRequirements(requirements);
        return requirementsImporter.saveRequirementsAsProject(requirements);
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
