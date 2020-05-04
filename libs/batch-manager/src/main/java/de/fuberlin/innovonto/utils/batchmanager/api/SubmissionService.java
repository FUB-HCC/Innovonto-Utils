package de.fuberlin.innovonto.utils.batchmanager.api;

import java.util.Optional;

public interface SubmissionService<S extends Submission> {
    Optional<S> findByHitIdAndWorkerIdAndAssignmentId(String hitId, String workerId, String assignmentId);

    S save(S submission);
}
