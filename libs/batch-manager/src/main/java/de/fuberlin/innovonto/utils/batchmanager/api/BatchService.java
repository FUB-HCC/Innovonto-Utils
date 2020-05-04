package de.fuberlin.innovonto.utils.batchmanager.api;

import java.util.Optional;

public interface BatchService<B extends Batch> {
    Optional<B> findByHitIdAndWorkerIdAndAssignmentId(String hitId, String workerId, String assignmentId);

    Optional<B> findByAssignmentId(String assignmentId);

    B save(B batch);
}
