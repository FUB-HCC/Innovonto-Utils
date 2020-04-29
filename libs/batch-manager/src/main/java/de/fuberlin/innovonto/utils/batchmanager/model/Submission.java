package de.fuberlin.innovonto.utils.batchmanager.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Submission<BRE, S> {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "de.fuberlin.innovonto.utils.common.FallbackUUIDGenerator"
    )
    private UUID id;
    //Metadata
    private LocalDateTime accepted;
    private LocalDateTime submitted;
    private LocalDateTime reviewed;

    //General:
    @NotBlank
    private String hitId;
    @NotBlank
    private String workerId;
    @NotBlank
    private String assignmentId;
    @NotBlank
    private String ratingProjectId;

    //TODO attention-checks?

    private ReviewStatus reviewStatus = ReviewStatus.UNREVIEWED;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BRE> ratings;

    private S surveyResult;
}
