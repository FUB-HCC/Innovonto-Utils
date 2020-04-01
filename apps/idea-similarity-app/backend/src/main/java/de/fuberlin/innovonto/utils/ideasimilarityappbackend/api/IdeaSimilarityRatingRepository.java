package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IdeaSimilarityRatingRepository extends CrudRepository<IdeaSimilarityRating, UUID> {
}
