package de.fuberlin.innovonto.utils.batchmanager.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubmissionRepository<BRE,S> extends CrudRepository<Submission<BRE, ?>, UUID> {
    Optional<Submission<BRE, S>> findByAssignmentId(String assignmentId);

    Optional<Submission<BRE, S>> findByHitIdAndWorkerIdAndAssignmentId(String hitId, String workerId, String assignmentId);

    Iterable<Submission<BRE, S>> findAllByProjectIdAndReviewStatus(String projectId, ReviewStatus reviewStatus);
}
