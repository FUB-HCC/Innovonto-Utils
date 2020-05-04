package de.fuberlin.innovonto.utils.batchmanager.api;

import java.util.Optional;

public interface SubmissionService {
    Optional<Submission> findByHitIdAndWorkerIdAndAssignmentId(String hitId, String workerId, String assignmentId);

    Submission save(Submission submission);
}
