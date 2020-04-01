package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class IdeaSimilarityRating {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "de.fuberlin.innovonto.utils.common.FallbackUUIDGenerator"
    )
    private UUID id;

    //TODO left
    //TODO right
    //TODO rating-value
    //TODO rating:title
    //TODO session-information: HWA
    //TODO approved-flag
}
