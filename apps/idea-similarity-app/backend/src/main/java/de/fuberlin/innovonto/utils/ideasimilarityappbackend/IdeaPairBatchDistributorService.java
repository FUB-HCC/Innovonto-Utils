package de.fuberlin.innovonto.utils.ideasimilarityappbackend;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.client.IdeaPairBatchDTO;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.Batch;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.RatingProject;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.RatingProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdeaPairBatchDistributorService {
    private final RatingProjectRepository ratingProjectRepository;

    @Autowired
    public IdeaPairBatchDistributorService(RatingProjectRepository ratingProjectRepository) {
        this.ratingProjectRepository = ratingProjectRepository;
    }

    /**
     * This should take one of the pre-defined batches and allocate it to one HWA combination
     * If the combination is already present: use the one that is there
     */
    public Batch allocateBatchFor(String ratingProjectId, String hitId, String workerId, String assignmentId) {
        Optional<RatingProject> byId = ratingProjectRepository.findById(ratingProjectId);
        if (byId.isPresent()) {
            RatingProject ratingProject = byId.get();
            return ratingProject.getBatches().get(0);
            //TODO get Batches. Which batches do we want?
            //TODO filter by:

        } else {
            throw new RuntimeException("Couldn't find rating project for id: " + ratingProjectId);
        }
    }
}
