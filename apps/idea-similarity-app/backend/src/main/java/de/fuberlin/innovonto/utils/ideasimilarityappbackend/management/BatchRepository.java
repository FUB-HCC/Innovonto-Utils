package de.fuberlin.innovonto.utils.ideasimilarityappbackend.management;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BatchRepository extends CrudRepository<Batch, UUID> {
}
