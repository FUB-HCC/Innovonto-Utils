package de.fuberlin.innovonto.utils.batchmanager.api;

import java.util.Optional;

public interface BatchService {
    Optional<Batch> findByHitIdAndWorkerIdAndAssignmentId(String hitId, String workerId, String assignmentId);

    Optional<Batch> findByAssignmentId(String assignmentId);

    void save(Batch batch);
}
