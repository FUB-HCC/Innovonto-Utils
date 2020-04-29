package de.fuberlin.innovonto.utils.batchmanager.model;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository<BE> extends CrudRepository<Project<BE, ?, ?>, String> {

}
