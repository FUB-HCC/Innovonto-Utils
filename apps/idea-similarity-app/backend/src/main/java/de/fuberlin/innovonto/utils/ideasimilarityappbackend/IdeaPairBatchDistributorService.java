package de.fuberlin.innovonto.utils.ideasimilarityappbackend;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.management.BatchState;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.Batch;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.BatchRepository;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.RatingProject;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.RatingProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
public class IdeaPairBatchDistributorService {
    private static final Logger log = LoggerFactory.getLogger(IdeaPairBatchDistributorService.class);

    private final RatingProjectRepository ratingProjectRepository;
    private final BatchRepository batchRepository;

    @Autowired
    public IdeaPairBatchDistributorService(RatingProjectRepository ratingProjectRepository, BatchRepository batchRepository) {
        this.ratingProjectRepository = ratingProjectRepository;
        this.batchRepository = batchRepository;
    }

    public Batch allocateBatchFor(String ratingProjectId, String hitId, String workerId, String assignmentId) {
        Optional<RatingProject> byId = ratingProjectRepository.findById(ratingProjectId);
        if (byId.isPresent()) {
            //if there is already a batch for the assignment id: return this one
            Optional<Batch> byAssignmentId = batchRepository.findByAssignmentId(assignmentId);
            if (byAssignmentId.isPresent()) {
                final Batch batch = byAssignmentId.get();
                if (batch.getBatchState().equals(BatchState.SUBMITTED)) {
                    throw new RuntimeException("Tried to allocate a batch that is already submitted! Batch was:" + batch + " ,HWA is: (" + hitId + "|" + workerId + "|" + assignmentId + ")");
                }
                if (!hitId.equals(batch.getHitId()) || !(workerId.equals(batch.getWorkerId())) || assignmentId.equals(batch.getAssignmentId())) {
                    log.info("HWA Missmatch between batch: (" + batch.getHWA() + ") and allocation request (" + hitId + "|" + workerId + "|" + assignmentId + "). Continuing.");
                }
                batch.setLastPublished(LocalDateTime.now());
                batch.setBatchState(BatchState.ALLOCATED);
                batch.setHitId(hitId);
                batch.setWorkerId(workerId);
                batch.setAssignmentId(assignmentId);
                return batch;
            } else {
                final RatingProject ratingProject = byId.get();
                final List<Batch> batches = ratingProject.getBatches();
                final List<Batch> unallocatedBatches = batches.stream().filter((b) -> b.getBatchState().equals(BatchState.UNALLOCATED)).sorted(Comparator.comparing(Batch::getLastPublished)).collect(Collectors.toList());
                final Batch result;
                if (isNotEmpty(unallocatedBatches)) {
                    result = unallocatedBatches.get(0);
                } else {
                    final List<Batch> unsubmittedBatches = batches.stream()
                            .filter((b) -> !b.getBatchState().equals(BatchState.SUBMITTED)).sorted(Comparator.comparing(Batch::getLastPublished)).collect(Collectors.toList());
                    if (isNotEmpty(unsubmittedBatches)) {
                        result = unsubmittedBatches.get(0);
                    } else {
                        throw new IllegalStateException("Tried to allocate a batch for a project where all batches are already submitted.");
                    }
                }
                result.setLastPublished(LocalDateTime.now());
                result.setBatchState(BatchState.ALLOCATED);
                result.setHitId(hitId);
                result.setWorkerId(workerId);
                result.setAssignmentId(assignmentId);
                batchRepository.save(result);
                return result;
            }
        } else {
            throw new RuntimeException("Couldn't find rating project for id: " + ratingProjectId);
        }
    }
}
