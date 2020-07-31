package de.fuberlin.innovonto.utils.ideasimilarityappbackend.business;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.RatingProject;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.RatingProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingProjectService {
    private final RatingProjectRepository ratingProjectRepository;

    @Autowired
    public RatingProjectService(RatingProjectRepository ratingProjectRepository) {
        this.ratingProjectRepository = ratingProjectRepository;
    }

    public Optional<RatingProject> findById(String ratingProjectId) {
        //TODO sanitize, toLowerCase?
        return ratingProjectRepository.findById(ratingProjectId.trim());
    }

    public void save(RatingProject project) {
        //TODO toLowerCase?
        ratingProjectRepository.save(project);
    }

    public Iterable<RatingProject> findAll() {
        return ratingProjectRepository.findAll();
    }
}
