package de.fuberlin.innovonto.utils.batchmanager.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface BatchRepository extends CrudRepository<Batch<?>, UUID> {
    Optional<Batch<?>> findByAssignmentId(String assignmentId);
    Optional<Batch<?>> findByBatchResultIdContains(UUID batchResultId);
    Optional<Batch<?>> findByHitIdAndWorkerIdAndAssignmentId(String hitId, String workerId, String assignmentId);
}
