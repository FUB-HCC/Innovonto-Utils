package de.fuberlin.innovonto.utils.ideasimilarityappbackend.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class RatingProject {

    @Id
    private String id;

    private LocalDateTime created;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Batch> batches;

    //TODO has Results?

    //Hibernate
    public RatingProject() {
        created = LocalDateTime.now();
    }

    public RatingProject(String id) {
        this();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }

    public LocalDateTime getCreated() {
        return created;
    }
}
