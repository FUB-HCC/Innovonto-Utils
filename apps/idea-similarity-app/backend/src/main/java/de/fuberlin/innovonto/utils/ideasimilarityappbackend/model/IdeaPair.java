package de.fuberlin.innovonto.utils.ideasimilarityappbackend.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.jackson.IdeaPairDeserializer;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.jackson.IdeaPairSerializer;

import javax.persistence.*;

@JsonDeserialize(using = IdeaPairDeserializer.class)
@JsonSerialize(using = IdeaPairSerializer.class)
@Entity
public class IdeaPair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 2_000)
    private String leftIdea;
    @Column(length = 2_000)
    private String rightIdea;

    //hibernate
    public IdeaPair() {
    }

    public IdeaPair(String leftIdea, String rightIdea) {
        this.leftIdea = leftIdea;
        this.rightIdea = rightIdea;
    }

    public String getLeftIdea() {
        return leftIdea;
    }

    public void setLeftIdea(String left) {
        this.leftIdea = left;
    }

    public String getRightIdea() {
        return rightIdea;
    }

    public void setRightIdea(String right) {
        this.rightIdea = right;
    }
}
