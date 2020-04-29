package de.fuberlin.innovonto.utils.batchmanager.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubmissionRepository extends CrudRepository<Submission<?, ?>, UUID> {
    Optional<Submission<?, ?>> findByAssignmentId(String assignmentId);

    Optional<Submission<?, ?>> findByHitIdAndWorkerIdAndAssignmentId(String hitId, String workerId, String assignmentId);

    Iterable<Submission<?, ?>> findAllByRatingProjectIdAndReviewStatus(String ratingProjectId, ReviewStatus reviewStatus);
}
