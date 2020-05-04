package de.fuberlin.innovonto.utils.batchmanager.api;

import java.util.Optional;

public interface ProjectService {
    Optional<Project> findById(String projectId);

    Project save(Project project);
}
