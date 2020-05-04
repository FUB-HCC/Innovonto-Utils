package de.fuberlin.innovonto.utils.batchmanager.api;

import java.util.Optional;

public interface ProjectService<P extends Project> {
    Optional<P> findById(String projectId);

    P save(P project);
}
