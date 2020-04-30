package de.fuberlin.innovonto.utils.batchmanager.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProjectRepository<BE,BRE,S> extends CrudRepository<Project<BE, BRE, S>, String> {
    Optional<Project<BE,BRE, S>> findById(String projectId);
}
