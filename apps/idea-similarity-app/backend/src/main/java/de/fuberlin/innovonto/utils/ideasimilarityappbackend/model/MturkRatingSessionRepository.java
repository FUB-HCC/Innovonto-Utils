package de.fuberlin.innovonto.utils.ideasimilarityappbackend.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface MturkRatingSessionRepository extends CrudRepository<MturkRatingSession, UUID> {

    Optional<MturkRatingSession> findByAssignmentId(String assignmentId);

    Optional<MturkRatingSession> findByHitIdAndWorkerIdAndAssignmentId(String hitId, String workerId, String assignmentId);

    Iterable<MturkRatingSession> findAllByRatingProjectIdAndReviewStatus(String ratingProjectId, ReviewStatus reviewStatus);
}
